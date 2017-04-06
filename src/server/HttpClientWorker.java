package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by 23878410v on 06/04/17.
 */
public class HttpClientWorker extends Thread {
    private static final Logger LOGGER = Logger.getLogger( HttpClientWorker.class.getName() );
    private static FileHandler fh = null;
    Socket socket;

    public HttpClientWorker(Socket socket) {
        Utils.initLogger("http-worker.log", fh, LOGGER);
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader is;
        PrintWriter os;
        try {
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new PrintWriter(socket.getOutputStream(), true);
            String request = is.readLine();
            StringTokenizer tokenizer = new StringTokenizer(request);
            String httpMethod = tokenizer.nextToken();
            String httpQueryString = tokenizer.nextToken();
            LOGGER.log( Level.FINE, "HTTP-Worker to {0} request {1}", new String[]{socket.getInetAddress().getHostAddress(), request});
            String response = "";
            switch(httpMethod){
                case "GET":
                    os.print("HTTP/1.1 200" + "\r\n");
                    response = "" +
                            "<html>" +
                            "<head>" +
                            "<meta charset=\"UTF-8\">" +
                            "<title>Hola Mundo</title>" +
                            "</head>" +
                            "<body>" +
                            "<h1>La teva IP es = "+ socket.getInetAddress().getHostAddress() +"</h1>" +
                            "<p>Lluís Bayer Soler</p>" +
                            "</body>" +
                            "</html>";
                    break;

                default:
                    os.print("HTTP/1.1 501" + "\r\n");
                    response = "" +
                            "<html>" +
                            "<head>" +
                            "<meta charset=\"UTF-8\">" +
                            "<title>Error</title>" +
                            "</head>" +
                            "<body>" +
                            "<h1>Request Not Implemented</h1>" +
                            "<p>The method request "+httpMethod+" isn't recognized on the server</p>" +
                            "</body>" +
                            "</html>";
                    break;
            }

            os.print("Content type: text/html" + "\r\n");
            os.print("Content length: " + response.length() + "\r\n");
            os.print("\r\n");
            os.print(response + "\r\n");
            os.flush();
            LOGGER.log( Level.FINE, "HTTP-Worker to {0} closes", socket.getInetAddress().getHostAddress());
            socket.close();
        }catch(IOException e){
            LOGGER.log( Level.SEVERE, e.toString(), e );
        }
    }
}
