
// ���������������ڽӵ�
import java.net.DatagramSocket;
import java.util.Date;

public class SendHeartBeat implements Runnable{
    private static DatagramSocket socket;
    public static Node n;

	public SendHeartBeat (Node nn)
	{
		n=nn;
		socket=nn.getsocket();
	}
	
    public void run() {
        try {
            while (true) {
                try {
                	int period = Property.readIntProperty("Node.properties", "heartPeriod");

                    // ÿ��һ��ʱ����������·��
                    Thread.sleep(period);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // ��¼������������ʱ��
                long time = (new Date()).getTime();

                // ��������ʽΪ heart + ���ͽ���� + ����ʱ��
                String s = "heart " + n.getname() + "," + Long.toString(time);

                // ���͸��ڽ��
                UdpSend.sendDataMessage(n, s, socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

