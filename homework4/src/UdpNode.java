import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lenovo on 2018/7/20.
 */
public class UdpNode implements Runnable{
    private DatagramPacket packet;
    private DatagramSocket socket;

    /**
     * 构造函数
     * @param socket udp套接字
     */
    public UdpNode(DatagramSocket socket) {
        this.socket = socket;
    }
    
    
    public void sendMessage(Node nn) {               //发送节点信息，转化为
        String message = "";

        List<String> nextmessage = new ArrayList<>(10);
        Iterator<Edge> it = nn.linknext.iterator();
        while(it.hasNext()) {
            Edge n = it.next();
            String s = n.getstart() + "," + n.getend() + "," + n.getweight();
            nextmessage.add(s);
        }

        Iterator<String> sit = nextmessage.iterator();
        while(sit.hasNext()) {
            String s = sit.next();
            message = message + " " + s;
        }

        sendMessage(message,nn);
    }

    /**
     * 将字符串发送给相邻结点
     * @param message 要发送的字符串
     * 转化为byte数组
     */
    private void sendMessage(String message,Node n) {
        byte[] b = message.getBytes();

        UdpNode un = new UdpNode(socket);

        Iterator<Edge> nit = n.linknext.iterator();
        while (nit.hasNext()) {

            Edge nn = nit.next();
            if(nn.getport() != 0) {
                un.sendMessage(b, nn.getport());
            }
        }
    }

    /**
     * 接收其他结点发送来的信息，
     */
    public void reveiveMessage(Node n) {
        byte[] b = new byte[1024];
        //UdpNode un = new UdpNode(socket);

        receiveMessage(b);

        String originData = new String(b);
        String[] EdgeMessage = originData.trim().split(" ");

        for(int i = 0; i < EdgeMessage.length; i ++) {
            String[] message = EdgeMessage[i].trim().split(",");
            Edge nn = new Edge(message[0], message[1], 0, Integer.parseInt(message[2]));
            n.linknext.add(nn);
        }
    }

	/**
     * 发送数据
     * @param data    数据信息的byte数组
     * @param toPort  接收信息的端口号
     */
    public void sendMessage(byte[] data, int toPort){
        try {

            packet = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), toPort);
            socket.send(packet);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收数据
     * @param data  存储接收数据的byte数组
     * @return      返回接受到的客服端的ip地址和端口
     */
    public DatagramPacket receiveMessage(byte[] data) {
        try {

            packet = new DatagramPacket(data,data.length);
            socket.receive(packet);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回接受到的客服端的ip地址和端口
        return packet;
    }
}
