package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by 23878410v on 06/04/17.
 */
public class HttpServerThread extends Thread {
    private static final Logger LOGGER = Logger.getLogger( HttpServerThread.class.getName() );
    private static FileHandler fh = null;
    static final int HttpServerPORT = 9000;
    ServerSocket httpServerSocket;

    public HttpServerThread() {
        Utils.initLogger("http-server.log", fh, LOGGER);
    }

    @Override
    public void run() {
        Socket socket = null;
        LOGGER.log( Level.FINE, "Socket HTTP on port {0}", HttpServerPORT);
        try {
            httpServerSocket = new ServerSocket(HttpServerPORT);
            while(true){
                socket = httpServerSocket.accept();
                LOGGER.log( Level.FINE, "Socket established connection from {0}", socket.getInetAddress().getHostAddress());
                HttpClientWorker worker = new HttpClientWorker(socket);
                worker.start();
            }
        }catch(IOException e){
            LOGGER.log( Level.SEVERE, e.toString(), e );
        }
    }
}
