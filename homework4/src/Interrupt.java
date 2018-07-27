
import java.util.*;

 //实现从控制台随机输入，立刻输出最短路径

public class Interrupt implements Runnable{
	private Thread temp;
	//初始化
	public Interrupt(Thread t)
	{
		temp=t;
	}
    public void run()
    {
    	while(true)
    	{
			//随意输入
    		Scanner sc=new Scanner(System.in);
    	    String s=sc.nextLine();
			//打断dijkstra,立即输出
    	    temp.interrupt();
    	}
    }
}
