import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int SERVER_PORT = 8888;
    public static ChatMap<String, PrintStream> clients = new ChatMap();
    public void init() {
        try {
            ServerSocket ss = new ServerSocket(SERVER_PORT) ;
            while(true) {
                Socket socket = ss.accept();
                new ServerThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Server server = new Server();
        server.init();
    }
}
