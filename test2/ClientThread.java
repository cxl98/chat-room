
import java.io.BufferedReader;
import java.io.IOException;

public class ClientThread extends Thread {

    BufferedReader br = null;

    public ClientThread(BufferedReader brServer) {
        this.br = brServer;
    }

    public ClientThread() {

    }

    public void run() {
        try {
            String line = null;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}