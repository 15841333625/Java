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
     * ���캯��
     * @param socket udp�׽���
     */
    public UdpNode(DatagramSocket socket) {
        this.socket = socket;
    }
    
    
    public void sendMessage(Node nn) {               //���ͽڵ���Ϣ��ת��Ϊ
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
     * ���ַ������͸����ڽ��
     * @param message Ҫ���͵��ַ���
     * ת��Ϊbyte����
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
     * ����������㷢��������Ϣ��
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
     * ��������
     * @param data    ������Ϣ��byte����
     * @param toPort  ������Ϣ�Ķ˿ں�
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
