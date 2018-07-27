import java.net.*;
import java.util.*;

/**
 * udp������Ϣ��ʵ�� Runnable �ӿ�
 */
public class UdpSend implements Runnable{
    private DatagramSocket socket; // udp�׽���
    public Node n;
    
    
    public UdpSend(Node nn) {
    	this.socket=nn.getsocket();
    	n=nn;
    }
       
    public void run() {
        while(true) {
            sendMessage();
            try {
                // �������ļ��ж�ȡ�����ʱ��
                int period = Property.readIntProperty("Node.properties", "period");
                // ���һ��ʱ������·�����Ϣ
                Thread.sleep(period);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * �������Ϣת��Ϊstring�����ͽ����Ϣ
     */
    public void sendMessage() {
        // Ҫ���͵Ľ����Ϣ��data ��ʾ����Ϊ�����Ϣ
        String message = "data";

        // nextmessage �洢����Ϣת���ɵĵ��ַ������ߵ����Էָ��Ϊ","       
        List<String> nextmessage = new ArrayList<>(10);
        for(int i=0;i<n.linknext.size();i++)
        {
        	Edge ne=n.linknext.get(i);
        	String s = ne.getstart() + "," + ne.getend() + "," + ne.getweight()+","+ne.istrue;
            nextmessage.add(s);
        }

        // �����бߵ���Ϣ�ϲ�Ϊһ���ַ���������ߵķָ���Ϊ" "
        for(int i=0;i<nextmessage.size();i++)
        {
        	String s=nextmessage.get(i);
        	message = message + " " + s;
        }

        sendDataMessage(n, message, socket);
    }

    /**
     * ��·���ַ������͸����ڽ��
     * @param message Ҫ���͵��ַ���
     * ת��Ϊbyte����
     */
    public static void sendDataMessage(Node n, String message, DatagramSocket socket) {
        // �������бߣ�ǰnumber����Ϊ�ٱߣ���endNodeΪ�ٽ��
        for(int i=0;i<n.getnumber();i++)
        {
            // ����ñ�Ϊ��Ч��������Ϣ
        	Edge nn=n.linknext.get(i);
        	if(nn.istrue==1)
        	{
        		 UdpNode un = new UdpNode(socket);
                 un.sendMessage(message, nn.getport());
        	}
        }

    }

}




