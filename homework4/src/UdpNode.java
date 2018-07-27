
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


/**
 * Created by lenovo on 2018/7/21.
 * udp发送，接收数据
 */

public class UdpNode {
    private DatagramSocket socket;
    private DatagramPacket packet;

    /**
     * 构造函数
     * @param socket udp套接字
     */
    public UdpNode(DatagramSocket socket) {
        this.socket = socket;
    }

    /**
     * 发送byte[]类型数据
     * @param data   存储要发送的信息
     * @param toPort 要发送到的端口
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
     * 发送String类型数据
     * @param message 要发送的字符串
     * 转化为byte数组
     */
    public void sendMessage(String message, int toPort) {
        // 将要发送的 String 类型数据转换为 byte[] 类型
        byte[] b = message.getBytes();

        sendMessage(b, toPort);
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
