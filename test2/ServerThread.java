import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerThread extends Thread{
    private Socket socket;
    BufferedReader br = null;
    PrintStream ps = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //一个客户端的输出流对象
            ps = new PrintStream(socket.getOutputStream());
            String line = null;
            while((line = br.readLine()) != null) {
                //如果消息以ChatProtocol.USER_ROND开始，并以其结束
                //则可以确定读到的是用户登录的用户名
                if(line.startsWith(ChatProtocol.USER_ROND) &&
                        line.endsWith(ChatProtocol.USER_ROND)) {
                    String userName = getRealMsg(line);
                    //用户名不允许重复
                    if(Server.clients.map.containsKey(userName)) {
                        System.out.println("用户名重复");
                        ps.println(ChatProtocol.NAME_EER);
                    } else {
                        System.out.println(""+userName+" 登录成功，你可以开始聊天了！");
                        ps.println(ChatProtocol.LOGIN_SUCCESS);
                        //将用户名和输出流对象组成的键值关联对存入前面经过改造的map
                        Server.clients.map.put(userName, ps);

                    }
                } //如果消息以ChatProtocol.PRIVATE_ROND开头并以ChatProtocol.PRIVATE_ROND结尾
                //则可以确定是私聊信息
                else if (line.startsWith(ChatProtocol.PRIVATE_ROND ) &&
                        line.endsWith(ChatProtocol.PRIVATE_ROND)) {
                    String userAndMsg = getRealMsg(line);

                    //以SPILT_SIGN分割字符串，前半是用户名，后半是聊天信息
                    String user = userAndMsg.split(ChatProtocol.SPLIT_SIGN)[0];
                    String msg = userAndMsg.split(ChatProtocol.SPLIT_SIGN)[1];
                    //根据用户名在map中找出输出流对象，进行私聊信息发送
                    Server.clients.map.get(user).println("私聊信息 来自 "+Server.clients.getKeyByValue(ps)+" : " + msg);

                }
                else if (line.startsWith(ChatProtocol.MSG_ROND)&&line.endsWith(ChatProtocol.MSG_ROND)){
                    String userAndmsg=getRealMsg(line);
                    String msg =userAndmsg.split(ChatProtocol.SPLIT_SIGN)[0];
//                    String msg=userAndmsg.split(ChatProtocol.SPLIT_SIGN)[1];
//                    String msg = getRealMsg(line);
                    for(PrintStream clientPs :  Server.clients.valueSet()) {
                        clientPs.println("群发信息 来自 "+Server.clients.getKeyByValue(ps)+" : " + msg);
                    }
                }
            }
        } catch (IOException e) {
            //e.printStackTrace();
            Server.clients.removeByValue(ps);
            System.out.println(Server.clients.map.size());
            try {
                if (br != null) {
                    br.close();
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
    }

    private String getRealMsg(String line) {
        return line.substring(ChatProtocol.PROTOCOL_LEN, line.length() - ChatProtocol.PROTOCOL_LEN);
    }

}
