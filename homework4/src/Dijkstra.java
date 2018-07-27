import java.util.*;

public class Dijkstra implements Runnable{
	
	  public Node n;
	  //构造函数
	  public Dijkstra(Node nn)
	  {
		  n=nn;
	  }
	  public void run()
	  {
		  while(true)
		  {
			  System.out.println("输入任意字符");
			  try {
                  // 从配置文件中读取出间隔时间
                  int period = Property.readIntProperty("Node.properties", "DjPeriod");

                  // 每隔一段时间重新搜索路径
                  Thread.sleep(period);
			  }
			  // 可能抛出 InterruptedException
              catch (Exception e) {
				  // TODO Auto-generated catch block
				  //e.printStackTrace();
			  }
			  getNode(n.linknext,n);
		  }
	  }
	  public static double max=1000.0;                              //设置最大值
	  public static ArrayList<Node> nodelist=new ArrayList<Node>();    //存下所有节点
	  public static void getNode(LinkedList<Edge> list,Node n)      //建二维数组
	  {
		  nodelist.clear();
		  //判断节点是否已经在数组链表中
		  boolean flag = true;
		  //将传入节点放入
		  Node nt = new Node(n.getname(),n.getport());
		  nodelist.add(nt);

		  for(int i=0;i<list.size();i++)
		  {
			  flag=true;
			  for(int k=0;k<nodelist.size();k++)
			  {
				  if((nodelist.get(k).getname().equals(list.get(i).getend())&&list.get(i).istrue==1)||list.get(i).istrue==0)  //判断是否已经存下
				  {
					  flag=false;
					  break;
				  }
			  }
			  if(flag==true)
			  {
				  Node n1=new Node(list.get(i).getend(),list.get(i).getport());
				  nodelist.add(n1);
			  }
		  }
		  double[][] vertex=new double [nodelist.size()][nodelist.size()];   //二维数组
		  for(int k=0;k<nodelist.size();k++)   //初始化
		  {
			  for(int g=0;g<nodelist.size();g++)
			  {
				  if(k==g)
					  vertex[k][g]=0.0;
				  else
					  vertex[k][g]=max;
			  }
		  }
		  //填入边信息
		  for(int i=0;i<list.size();i++)
		  {
			  for(int k=0;k<nodelist.size();k++)
			  {
				  for(int g=0;g<nodelist.size();g++)
				  {
					  if(list.get(i).getstart().equals(nodelist.get(k).getname())&&list.get(i).getend().equals(nodelist.get(g).getname())&&list.get(i).istrue==1)
						  vertex[k][g]=list.get(i).getweight();
				  }
			  }
		  }
		  dijk(0,vertex);
	  }
	  public static void dijk(int vs, double[][] Graph) {
	        int NUM = Graph[0].length; 
	        double[] prenode = new double[NUM];// 前驱节点数组
	        double[] mindist = new double[NUM];// 最短距离数组
	        // 该节点是否已经找到最短路径
	        boolean[] find = new boolean[NUM];
	         
	        int vnear = 0;
	         
	        for (int i = 0; i < mindist.length; i++) {    //初始化
	            prenode[i] = 0;
	            mindist[i] = Graph[vs][i];
	            find[i] = false;
	        }
	 
	        find[vs] = true;
	 
	        for (int v = 1; v < Graph.length; v++) {
	        	double min = max;
	            for (int j = 0; j < Graph.length; j++) {   
	                if (!find[j] && mindist[j] < min) {
	                    min = mindist[j];        //最短距离
	                    vnear = j;
	                }
	            }
	            find[vnear] = true;      //找到到vnear的最短路径
	 
	            for (int k = 0; k < Graph.length; k++) {
	                if (!find[k] && (min + Graph[vnear][k]) < mindist[k]) {   //寻找前驱
	                    prenode[k] = vnear;                    //找到，修改前驱节点
	                    mindist[k] = min + Graph[vnear][k];    //找到最小值
	                }
	            }
	        }
	        
	        for (int i = 1; i < NUM; i++) {
	        	String f=nodelist.get(vs).getname();   //起始节点
		        String s=nodelist.get(i).getname();    //要到达节点
		        String c=nodelist.get((int) prenode[i]).getname();  //前驱节点
		        int num=(int)prenode[i];
                String sc=c+s;    //存下路径
		        for(int j=0;j<NUM;j++)
		        {
			        if(c.equals(f))
			        {
			        	break;
			        }
			        else
			        {
			        	num=(int)prenode[num];
			        	c=nodelist.get(num).getname();
			        	sc=c+sc;
			      
			        }
		        }
		        if(mindist[i]!=1000)
			    System.out.println("least-cost path to node " + nodelist.get(i).getname() + ": "  + sc + " and the cost is " + mindist[i]);
	    }
	        System.out.println();
	  }
}