import server.HttpServerThread;

/**
 * Created by 23878410v on 06/04/17.
 */
public class Main {
    public static void main(String[] args) {

        HttpServerThread serverThread = new HttpServerThread();
        serverThread.run();
    }
}
