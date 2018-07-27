
import java.util.*;

// 运行主程序
public class Run {
	public static void main(String[] args) throws Exception {
		Scanner sc=new Scanner(System.in);
		Node n = null;
		// 初始化结点
        if(sc.hasNext())
        {
        	String s=sc.nextLine();
        	String[] split=s.split(" ");
        	n=new Node(split[0],Integer.parseInt(split[1]),split[2]);
        }

		UdpSend t1=new UdpSend(n);           //向相邻结点传输节点信息
		Thread tt=new Thread(t1);

		UdpReceive t2=new UdpReceive(n);     // 接受相邻节点的节点信息和心跳包信息
		Thread tt2=new Thread(t2);

		Runnable r1 = new SendHeartBeat(n);   //发送心跳包信息
		Thread tt3 = new Thread(r1);

		Runnable r2 = new CheckError(n);      //检查是否超时，节点出现故障
		Thread tt4 = new Thread(r2);

		Dijkstra dj=new Dijkstra(n);          //输出最短路径信息
		Thread ddj=new Thread(dj);

		Interrupt ir=new Interrupt(ddj);       //如果从键盘输入某个数，则立刻输出dijkstra结果
		Thread iir=new Thread(ir);
		
        tt.start();
        tt2.start();
        tt3.start();
        tt4.start();
        ddj.start();
        iir.start();
	}

}
