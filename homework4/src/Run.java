
import java.util.*;

// ����������
public class Run {
	public static void main(String[] args) throws Exception {
		Scanner sc=new Scanner(System.in);
		Node n = null;
		// ��ʼ�����
        if(sc.hasNext())
        {
        	String s=sc.nextLine();
        	String[] split=s.split(" ");
        	n=new Node(split[0],Integer.parseInt(split[1]),split[2]);
        }

		UdpSend t1=new UdpSend(n);           //�����ڽ�㴫��ڵ���Ϣ
		Thread tt=new Thread(t1);

		UdpReceive t2=new UdpReceive(n);     // �������ڽڵ�Ľڵ���Ϣ����������Ϣ
		Thread tt2=new Thread(t2);

		Runnable r1 = new SendHeartBeat(n);   //������������Ϣ
		Thread tt3 = new Thread(r1);

		Runnable r2 = new CheckError(n);      //����Ƿ�ʱ���ڵ���ֹ���
		Thread tt4 = new Thread(r2);

		Dijkstra dj=new Dijkstra(n);          //������·����Ϣ
		Thread ddj=new Thread(dj);

		Interrupt ir=new Interrupt(ddj);       //����Ӽ�������ĳ���������������dijkstra���
		Thread iir=new Thread(ir);
		
        tt.start();
        tt2.start();
        tt3.start();
        tt4.start();
        ddj.start();
        iir.start();
	}

}
