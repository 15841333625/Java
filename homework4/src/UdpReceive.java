import java.net.DatagramSocket;
import java.util.Iterator;

/**
 * udp接收信息，实现 Runnable 接口
 */

public class UdpReceive implements Runnable{
    private DatagramSocket socket;  // udp套接字
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
        while(true) {
            // 接收信息
            reveiveMessage();
        }
    }

    /**
     * 接收其他结点发送来的信息，
     */
    public void reveiveMessage() {
        // 存放接收到的 byte[]
        byte[] b = new byte[1024];
        // Udp接收数据
        UdpNode un = new UdpNode(socket);
        un.receiveMessage(b);

        // 将接收到的 byte 数据转换为 String 类型
        String originData = new String(b);
        // 将接收到的字符串以 " " 分隔为几个子串
        String[] EdgeMessage = originData.trim().split(" ");

        // 如果接收到的信息为结点信息，跳转到结点信息处理函数
        if(EdgeMessage[0].equals("data")) {
            addNodeMessage(EdgeMessage);
        }
        // 如果接收到的信息是心跳包，跳转到心跳包处理函数
        else if(EdgeMessage[0].equals("heart")) {
            dealHeartbeat(EdgeMessage[1]);
        }
    }

    /**
     * 将接收到的边的信息存储在 List 中
     * @param EdgeMessage 存储边的信息的字符串
     */
    private void addNodeMessage(String[] EdgeMessage) {
    	for(int i = 1; i < EdgeMessage.length; i ++)
    	{
	    	String [] message=EdgeMessage[i].split(",");	    	
	        // message[0] 为起始结点，1 为终止结点，2 为权重，端口默认为0，3为这条边是否存在
	        Edge nn = new Edge(message[0], message[1], Double.parseDouble(message[2]),Integer.parseInt(message[3]));
	        // 如果这条边无效，则替换原先数据
            // 同步n.linknext，两个线程无法同时访问n.linknext
            synchronized (n.linknext){
                if(nn.istrue==0)
                {
                    // 遍历原先存储边信息的List，将无效信息删除
                    for(int j = 0; j < n.linknext.size(); j ++)
                    {
                        if(n.linknext.get(j).getstart().equals(nn.getstart())&&n.linknext.get(j).getend().equals(nn.getend())
                                &&n.linknext.get(j).istrue==1)
                            n.linknext.remove(j);
                    }
                }
                // 如果没存过这条边的信息则存储
                if(!n.linknext.contains(nn))
                    n.linknext.add(nn);
            }
    	}
    }

    /**
     * 处理心跳包
     * @param message 心跳包的信息，结点名,发送时间
     */
    private void dealHeartbeat(String message)
    {
        // 将邻结点的心跳包数据存在map中，结点名：上次发送时间
        String[] heartMessage = message.split(",");
        String name = heartMessage[0], time = heartMessage[1];

        // 存储邻结点发送心跳包的时间
        long t = Long.parseLong(time);

        // 同步nextNode，两个线程无法同时访问nextNode
        synchronized (n.nextNode) {
            if(n.nextNode.containsKey(name)) {      //是否存在于记录中
                n.nextNode.replace(name,t);         //有则更新
            }
        }

    }

    /**
     * 处理故障结点
     * @param name 故障邻结点的结点名
     */
    public void dealBrokenNode(String name) {
        // 在存储邻结点的map中将其删除，在边List中将其设置为无效
        removeInNextNode(name);
        changeInEdgeList(name);
    }

    /**
     * 删除邻结点map中的结点
     * @param name 结点名
     */
    private void removeInNextNode(String name) {
        synchronized (n.nextNode) {
            // 如果存在则删除
            if(n.nextNode.containsKey(name))
                n.nextNode.remove(name);
        }
    }

    /**
     * 在边List中将边设置为无效
     * @param name 结点名
     */
    private void changeInEdgeList(String name) {
        synchronized (n.linknext) {
            for(int i=0;i<n.linknext.size();i++)
            {
                Edge e=n.linknext.get(i);
                // 如果一条边的开始结点或结束结点中有该故障结点则将这条边设置为无效
                if(e.getstart().equals(name) || e.getend().equals(name)) {
                    e.istrue=0;
                }
            }
        }
    }
}
