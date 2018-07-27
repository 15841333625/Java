
import java.util.*;

/**
 * 监控邻结点心跳包，超时则判断该邻结点故障
 */
public class CheckError implements Runnable{
   public Node n;
   public CheckError(Node nn)
   {
	   n=nn;
   }
   public void run() {
	   // 时刻监控
	   while(true)
	   {
		   // 获取当前时间
		   Date d=new Date();
		   long time=d.getTime();
		   synchronized (n.linknext) {
			   // 遍历邻结点map
			   for(int i=0;i<n.getnumber();i++)
			   {
				   if(n.linknext.get(i).istrue==1&&n.linknext.get(i).getport()!=0)
				   {
					   synchronized (n.nextNode) {
						   //上次收到此节点的时间
						   long t=n.nextNode.get(n.linknext.get(i).getend());
						   // 若当前时间-上一次接收到该邻结点心跳包时间 > 三个发送心跳包的周期，则判断该邻结点故障
						   if(time-t>=750)
						   {
							   UdpReceive ur=new UdpReceive(n);
							   // 调用故障处理函数
							   ur.dealBrokenNode(n.linknext.get(i).getend());
						   }
					   }
				   }
			   }
		   }
	   }
   }
}
