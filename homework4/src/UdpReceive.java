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
     * 接收其他结点发送来的信息，
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
