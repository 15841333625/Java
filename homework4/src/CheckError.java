
import java.util.*;

/**
 * ����ڽ������������ʱ���жϸ��ڽ�����
 */
public class CheckError implements Runnable{
   public Node n;
   public CheckError(Node nn)
   {
	   n=nn;
   }
   public void run() {
	   // ʱ�̼��
	   while(true)
	   {
		   // ��ȡ��ǰʱ��
		   Date d=new Date();
		   long time=d.getTime();
		   synchronized (n.linknext) {
			   // �����ڽ��map
			   for(int i=0;i<n.getnumber();i++)
			   {
				   if(n.linknext.get(i).istrue==1&&n.linknext.get(i).getport()!=0)
				   {
					   synchronized (n.nextNode) {
						   //�ϴ��յ��˽ڵ��ʱ��
						   long t=n.nextNode.get(n.linknext.get(i).getend());
						   // ����ǰʱ��-��һ�ν��յ����ڽ��������ʱ�� > �������������������ڣ����жϸ��ڽ�����
						   if(time-t>=750)
						   {
							   UdpReceive ur=new UdpReceive(n);
							   // ���ù��ϴ�����
							   ur.dealBrokenNode(n.linknext.get(i).getend());
						   }
					   }
				   }
			   }
		   }
	   }
   }
}
