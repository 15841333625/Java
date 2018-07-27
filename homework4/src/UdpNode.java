
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


/**
 * Created by lenovo on 2018/7/21.
 * udp���ͣ���������
 */

public class UdpNode {
    private DatagramSocket socket;
    private DatagramPacket packet;

    /**
     * ���캯��
     * @param socket udp�׽���
     */
    public UdpNode(DatagramSocket socket) {
        this.socket = socket;
    }

    /**
     * ����byte[]��������
     * @param data   �洢Ҫ���͵���Ϣ
     * @param toPort Ҫ���͵��Ķ˿�
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
     * ����String��������
     * @param message Ҫ���͵��ַ���
     * ת��Ϊbyte����
     */
    public void sendMessage(String message, int toPort) {
        // ��Ҫ���͵� String ��������ת��Ϊ byte[] ����
        byte[] b = message.getBytes();

        sendMessage(b, toPort);
    }

    /**
     * ��������
     * @param data  �洢�������ݵ�byte����
     * @return      ���ؽ��ܵ��Ŀͷ��˵�ip��ַ�Ͷ˿�
     */
    public DatagramPacket receiveMessage(byte[] data) {
        try {
            packet = new DatagramPacket(data,data.length);
            socket.receive(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //���ؽ��ܵ��Ŀͷ��˵�ip��ַ�Ͷ˿�
        return packet;
    }
}
