
import java.util.*;

 //ʵ�ִӿ���̨������룬����������·��

public class Interrupt implements Runnable{
	private Thread temp;
	//��ʼ��
	public Interrupt(Thread t)
	{
		temp=t;
	}
    public void run()
    {
    	while(true)
    	{
			//��������
    		Scanner sc=new Scanner(System.in);
    	    String s=sc.nextLine();
			//���dijkstra,�������
    	    temp.interrupt();
    	}
    }
}
