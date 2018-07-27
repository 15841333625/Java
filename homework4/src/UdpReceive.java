import java.net.DatagramSocket;
import java.util.Iterator;

/**
 * udp������Ϣ��ʵ�� Runnable �ӿ�
 */

public class UdpReceive implements Runnable{
    private DatagramSocket socket;  // udp�׽���
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
        while(true) {
            // ������Ϣ
            reveiveMessage();
        }
    }

    /**
     * ����������㷢��������Ϣ��
     */
    public void reveiveMessage() {
        // ��Ž��յ��� byte[]
        byte[] b = new byte[1024];
        // Udp��������
        UdpNode un = new UdpNode(socket);
        un.receiveMessage(b);

        // �����յ��� byte ����ת��Ϊ String ����
        String originData = new String(b);
        // �����յ����ַ����� " " �ָ�Ϊ�����Ӵ�
        String[] EdgeMessage = originData.trim().split(" ");

        // ������յ�����ϢΪ�����Ϣ����ת�������Ϣ������
        if(EdgeMessage[0].equals("data")) {
            addNodeMessage(EdgeMessage);
        }
        // ������յ�����Ϣ������������ת��������������
        else if(EdgeMessage[0].equals("heart")) {
            dealHeartbeat(EdgeMessage[1]);
        }
    }

    /**
     * �����յ��ıߵ���Ϣ�洢�� List ��
     * @param EdgeMessage �洢�ߵ���Ϣ���ַ���
     */
    private void addNodeMessage(String[] EdgeMessage) {
    	for(int i = 1; i < EdgeMessage.length; i ++)
    	{
	    	String [] message=EdgeMessage[i].split(",");	    	
	        // message[0] Ϊ��ʼ��㣬1 Ϊ��ֹ��㣬2 ΪȨ�أ��˿�Ĭ��Ϊ0��3Ϊ�������Ƿ����
	        Edge nn = new Edge(message[0], message[1], Double.parseDouble(message[2]),Integer.parseInt(message[3]));
	        // �����������Ч�����滻ԭ������
            // ͬ��n.linknext�������߳��޷�ͬʱ����n.linknext
            synchronized (n.linknext){
                if(nn.istrue==0)
                {
                    // ����ԭ�ȴ洢����Ϣ��List������Ч��Ϣɾ��
                    for(int j = 0; j < n.linknext.size(); j ++)
                    {
                        if(n.linknext.get(j).getstart().equals(nn.getstart())&&n.linknext.get(j).getend().equals(nn.getend())
                                &&n.linknext.get(j).istrue==1)
                            n.linknext.remove(j);
                    }
                }
                // ���û��������ߵ���Ϣ��洢
                if(!n.linknext.contains(nn))
                    n.linknext.add(nn);
            }
    	}
    }

    /**
     * ����������
     * @param message ����������Ϣ�������,����ʱ��
     */
    private void dealHeartbeat(String message)
    {
        // ���ڽ������������ݴ���map�У���������ϴη���ʱ��
        String[] heartMessage = message.split(",");
        String name = heartMessage[0], time = heartMessage[1];

        // �洢�ڽ�㷢����������ʱ��
        long t = Long.parseLong(time);

        // ͬ��nextNode�������߳��޷�ͬʱ����nextNode
        synchronized (n.nextNode) {
            if(n.nextNode.containsKey(name)) {      //�Ƿ�����ڼ�¼��
                n.nextNode.replace(name,t);         //�������
            }
        }

    }

    /**
     * ������Ͻ��
     * @param name �����ڽ��Ľ����
     */
    public void dealBrokenNode(String name) {
        // �ڴ洢�ڽ���map�н���ɾ�����ڱ�List�н�������Ϊ��Ч
        removeInNextNode(name);
        changeInEdgeList(name);
    }

    /**
     * ɾ���ڽ��map�еĽ��
     * @param name �����
     */
    private void removeInNextNode(String name) {
        synchronized (n.nextNode) {
            // ���������ɾ��
            if(n.nextNode.containsKey(name))
                n.nextNode.remove(name);
        }
    }

    /**
     * �ڱ�List�н�������Ϊ��Ч
     * @param name �����
     */
    private void changeInEdgeList(String name) {
        synchronized (n.linknext) {
            for(int i=0;i<n.linknext.size();i++)
            {
                Edge e=n.linknext.get(i);
                // ���һ���ߵĿ�ʼ�������������иù��Ͻ��������������Ϊ��Ч
                if(e.getstart().equals(name) || e.getend().equals(name)) {
                    e.istrue=0;
                }
            }
        }
    }
}
