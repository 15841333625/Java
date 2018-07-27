import java.net.*;
import java.util.*;

/**
 * udp发送信息，实现 Runnable 接口
 */
public class UdpSend implements Runnable{
    private DatagramSocket socket; // udp套接字
    public Node n;
    
    
    public UdpSend(Node nn) {
    	this.socket=nn.getsocket();
    	n=nn;
    }
       
    public void run() {
        while(true) {
            sendMessage();
            try {
                // 从配置文件中读取出间隔时间
                int period = Property.readIntProperty("Node.properties", "period");
                // 间隔一段时间后重新发送信息
                Thread.sleep(period);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将结点信息转化为string，发送结点信息
     */
    public void sendMessage() {
        // 要发送的结点信息，data 表示数据为结点信息
        String message = "data";

        // nextmessage 存储边信息转换成的的字符串，边的属性分割符为","       
        List<String> nextmessage = new ArrayList<>(10);
        for(int i=0;i<n.linknext.size();i++)
        {
        	Edge ne=n.linknext.get(i);
        	String s = ne.getstart() + "," + ne.getend() + "," + ne.getweight()+","+ne.istrue;
            nextmessage.add(s);
        }

        // 将所有边的信息合并为一个字符串，边与边的分隔符为" "
        for(int i=0;i<nextmessage.size();i++)
        {
        	String s=nextmessage.get(i);
        	message = message + " " + s;
        }

        sendDataMessage(n, message, socket);
    }

    /**
     * 将路径字符串发送给相邻结点
     * @param message 要发送的字符串
     * 转化为byte数组
     */
    public static void sendDataMessage(Node n, String message, DatagramSocket socket) {
        // 遍历所有边，前number个边为临边，其endNode为临界点
        for(int i=0;i<n.getnumber();i++)
        {
            // 如果该边为有效边则发送信息
        	Edge nn=n.linknext.get(i);
        	if(nn.istrue==1)
        	{
        		 UdpNode un = new UdpNode(socket);
                 un.sendMessage(message, nn.getport());
        	}
        }

    }

}




