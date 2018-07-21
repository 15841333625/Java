
import java.net.*;
import java.util.*;

public class UdpSend implements Runnable{
	private DatagramPacket packet;
    private DatagramSocket socket;
    public Node n;
    
    
    public UdpSend(Node nn) {
    	this.socket=nn.getsocket();
    	n=nn;
    }
       
    public void run() {               //���ͽڵ���Ϣ��ת��Ϊstring
        String message = "";

        List<String> nextmessage = new ArrayList<>(10);
        Iterator<Edge> it = n.linknext.iterator();
        while(it.hasNext()) {
            Edge ne = it.next();
            String s = ne.getstart() + "," + ne.getend() + "," + ne.getweight();
            nextmessage.add(s);
        }

        Iterator<String> sit = nextmessage.iterator();
        while(sit.hasNext()) {
            String s = sit.next();
            message = message + " " + s;
        }

        sendMessage(message);
    }

    /**
     * ���ַ������͸����ڽ��
     * @param message Ҫ���͵��ַ���
     * ת��Ϊbyte����
     */
    private void sendMessage(String message) {
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
       
    public void sendMessage(byte[] data, int toPort){
        try {

            packet = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), toPort);
            socket.send(packet);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
