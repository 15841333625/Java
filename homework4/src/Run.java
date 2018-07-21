
import java.util.*;

public class Run {
    public static LinkedList<Node> listnode=new LinkedList<Node>();
	public static void main(String[] args) throws Exception {
		Scanner sc=new Scanner(System.in);
		Node n = null;
        if(sc.hasNext())
        {
        	String s=sc.nextLine();
        	String[] split=s.split(" ");
        	n=new Node(split[0],Integer.parseInt(split[1]),split[2]);
        	/*UdpSend t1=new UdpSend(n);
        	UdpReceive t2=new UdpReceive(n);*/
        }
        while(true)
        {
        	UdpSend t1=new UdpSend(n);
        	UdpReceive t2=new UdpReceive(n);
        	new Thread(t1).start();
        	new Thread(t2).start();
            Dijkstra.getNode(n.linknext,n.getname());
        }
	}

}
