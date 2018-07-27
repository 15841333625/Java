import java.util.*;

public class Dijkstra implements Runnable{
	
	  public Node n;
	  //���캯��
	  public Dijkstra(Node nn)
	  {
		  n=nn;
	  }
	  public void run()
	  {
		  while(true)
		  {
			  System.out.println("���������ַ�");
			  try {
                  // �������ļ��ж�ȡ�����ʱ��
                  int period = Property.readIntProperty("Node.properties", "DjPeriod");

                  // ÿ��һ��ʱ����������·��
                  Thread.sleep(period);
			  }
			  // �����׳� InterruptedException
              catch (Exception e) {
				  // TODO Auto-generated catch block
				  //e.printStackTrace();
			  }
			  getNode(n.linknext,n);
		  }
	  }
	  public static double max=1000.0;                              //�������ֵ
	  public static ArrayList<Node> nodelist=new ArrayList<Node>();    //�������нڵ�
	  public static void getNode(LinkedList<Edge> list,Node n)      //����ά����
	  {
		  nodelist.clear();
		  //�жϽڵ��Ƿ��Ѿ�������������
		  boolean flag = true;
		  //������ڵ����
		  Node nt = new Node(n.getname(),n.getport());
		  nodelist.add(nt);

		  for(int i=0;i<list.size();i++)
		  {
			  flag=true;
			  for(int k=0;k<nodelist.size();k++)
			  {
				  if((nodelist.get(k).getname().equals(list.get(i).getend())&&list.get(i).istrue==1)||list.get(i).istrue==0)  //�ж��Ƿ��Ѿ�����
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
		  double[][] vertex=new double [nodelist.size()][nodelist.size()];   //��ά����
		  for(int k=0;k<nodelist.size();k++)   //��ʼ��
		  {
			  for(int g=0;g<nodelist.size();g++)
			  {
				  if(k==g)
					  vertex[k][g]=0.0;
				  else
					  vertex[k][g]=max;
			  }
		  }
		  //�������Ϣ
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
	        double[] prenode = new double[NUM];// ǰ���ڵ�����
	        double[] mindist = new double[NUM];// ��̾�������
	        // �ýڵ��Ƿ��Ѿ��ҵ����·��
	        boolean[] find = new boolean[NUM];
	         
	        int vnear = 0;
	         
	        for (int i = 0; i < mindist.length; i++) {    //��ʼ��
	            prenode[i] = 0;
	            mindist[i] = Graph[vs][i];
	            find[i] = false;
	        }
	 
	        find[vs] = true;
	 
	        for (int v = 1; v < Graph.length; v++) {
	        	double min = max;
	            for (int j = 0; j < Graph.length; j++) {   
	                if (!find[j] && mindist[j] < min) {
	                    min = mindist[j];        //��̾���
	                    vnear = j;
	                }
	            }
	            find[vnear] = true;      //�ҵ���vnear�����·��
	 
	            for (int k = 0; k < Graph.length; k++) {
	                if (!find[k] && (min + Graph[vnear][k]) < mindist[k]) {   //Ѱ��ǰ��
	                    prenode[k] = vnear;                    //�ҵ����޸�ǰ���ڵ�
	                    mindist[k] = min + Graph[vnear][k];    //�ҵ���Сֵ
	                }
	            }
	        }
	        
	        for (int i = 1; i < NUM; i++) {
	        	String f=nodelist.get(vs).getname();   //��ʼ�ڵ�
		        String s=nodelist.get(i).getname();    //Ҫ����ڵ�
		        String c=nodelist.get((int) prenode[i]).getname();  //ǰ���ڵ�
		        int num=(int)prenode[i];
                String sc=c+s;    //����·��
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