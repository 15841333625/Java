import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpReceive implements Runnable{

	private DatagramPacket packet;
    private DatagramSocket socket;
    public Node n;
    
    UdpReceive(Node nn)
    {
    	socket=nn.getsocket();
    	n=nn;
    }
    
	/**
     * ����������㷢��������Ϣ��
     */
    public void run() {
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
