

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {
    private static final int SERVER_PORT = 8888;
    private Socket socket;
    private PrintStream ps;
    private BufferedReader brServer;
    private BufferedReader keyIn;

    public void init() {
        try {
            keyIn = new BufferedReader(new InputStreamReader(System.in));
            socket = new Socket("127.0.0.1", SERVER_PORT);
            ps = new PrintStream(socket.getOutputStream());
            brServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String tip = "";
            while(true) {
                String userName = JOptionPane.showInputDialog(tip + "输入用户名");
                ps.println(ChatProtocol.USER_ROND + userName + ChatProtocol.USER_ROND);

                //服务器端响应
                String result = brServer.readLine();
                if(result.equals(ChatProtocol.NAME_EER)) {
                    tip = "用户名重复，请重新输入";
                    continue;
                }
                if(result.equals(ChatProtocol.LOGIN_SUCCESS)) {
                    System.out.println("登录成功，账号： "+ userName);
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        new ClientThread(brServer).start();
    }


    private void readAndSend() {
        try {
            String line = null;
            while((line = keyIn.readLine()) != null) {
                //如果发送的消息中带有冒号，且以//开头，则认为是私聊信息
//                System.out.println(line);
                if(line.indexOf(":") > 0 && line.startsWith("@")) {
                    line = line.substring(1);
                    ps.println(ChatProtocol.PRIVATE_ROND
                            + line.split(":")[0]
                            + ChatProtocol.SPLIT_SIGN
                            + line.split(":")[1]
                            + ChatProtocol.PRIVATE_ROND);
                } else if (line.startsWith("@@")){
                    line=line.substring(2);
//                    System.out.println("测试全体"+line);
                    ps.println(ChatProtocol.MSG_ROND
                            + line.split(":")[0]
                            +ChatProtocol.SPLIT_SIGN
//                            +line.split(":")[1]
                            +ChatProtocol.MSG_ROND);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void closeRs() {
        try {
            if (keyIn != null) {
                keyIn.close();
            }

            if (brServer != null) {
                brServer.close();
            }

            if (ps != null) {
                ps.close();
            }

            if (socket != null) {
                socket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.init();
        client.readAndSend();
    }
}