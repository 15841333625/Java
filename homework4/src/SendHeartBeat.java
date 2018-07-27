
// 发送心跳包给相邻接点
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

                    // 每隔一段时间重新搜索路径
                    Thread.sleep(period);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // 记录发送心跳包的时间
                long time = (new Date()).getTime();

                // 心跳包格式为 heart + 发送结点名 + 发送时间
                String s = "heart " + n.getname() + "," + Long.toString(time);

                // 发送给邻结点
                UdpSend.sendDataMessage(n, s, socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

