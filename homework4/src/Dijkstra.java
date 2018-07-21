import java.util.*;

public class Dijkstra{
	  public static int max=1000;
	  public static ArrayList<Node> nodelist=new ArrayList<Node>();
	  public static void getNode(LinkedList<Edge> list,String name)
	  {
		  double max=100.0;
		  int nodenum=0;
		  boolean flag=true;
		  System.out.println(list.size());
		  for(int i=0;i<list.size();i++)
		  {
			  for(int j=0;j<list.size();j++)
			  {
				  if(list.get(i).getend().equals(list.get(j).getend()))
					  break;
				  else
				  {
					  for(int k=0;k<nodelist.size();k++)
					  {
						  if(nodelist.get(k).getport()==list.get(j).getport())
						  {
							  flag=false;
							  System.out.println(111);
							  break;  
						  }
					  }
					  if(flag==true)
					  {
						  Node n=new Node(list.get(j).getend(),list.get(j).getport());
						  nodelist.add(n);
					  }
					  else
						  break;
				  }
				  }
		  }
		  System.out.println(nodelist.size());
		  double[][] vertex=new double [nodelist.size()][nodelist.size()];
		  for(int k=0;k<nodelist.size();k++)
		  {
			  for(int g=0;g<nodelist.size();g++)
			  {
				  if(k==g)
					  vertex[k][g]=0.0;
				  else
					  vertex[k][g]=max;
			  }
		  }
		  for(int i=0;i<list.size();i++)
		  {
			  for(int k=0;k<nodelist.size();k++)
			  {
				  for(int g=0;g<nodelist.size();g++)
				  {
					  if(list.get(i).getstart().equals(nodelist.get(k).getname())&&list.get(i).getend().equals(nodelist.get(g).getname()))
						  vertex[k][g]=list.get(i).getweight();
				  }
			  }
		  }
		  int vs = 0;
		  for(int i=0;i<nodelist.size();i++)
		  {
			  if(name.equals(nodelist.get(i).getname()))
			  {
				  vs=i;
				  break;
			  }
		  }
		  dijk(vs,vertex);
	  }
		  
		  /*for(int i=0;i<nodenum;i++)
		  {
			  for(int j=0;j<nodenum;j++)
			  {
				  char s=(char)(i+65);
				  char c=(char)(j+65);
				  if()
			  }
		  }*/
		  
		  /*double[][] vertex=new double [listnode.size()][listnode.size()];
		  for(int i=0;i<listnode.size();i++)
		  {
			  for(int j=0;j<listnode.size();j++)
			  {
				  String n=listnode.get(j).getname();
				  for(int k=0;k<listnode.get(i).getnext().size();k++)
				  {
					  if(n==listnode.get(i).getnext().get(k).getname())
						  vertex[i][j]=listnode.get(i).getnext().get(k).getweight();
				  }
			  }
		  }
		  for(int i=0;i<listnode.size();i++)
		  {
			  vertex[i][i]=0;
		  }
		  for(int i=0;i<listnode.size();i++)
		  {
			  dijk(i,vertex,listnode);
		  }*/
	  //}
	  public static void dijk(int vs, double[][] Graph) {
	        int NUM = Graph[0].length; 
	        double[] prenode = new double[NUM];// 前驱节点数组
	        double[] mindist = new double[NUM];// 最短距离数组
	        // 该节点是否已经找到最短路径
	        boolean[] find = new boolean[NUM];
	         
	        int vnear = 0;
	         
	        for (int i = 0; i < mindist.length; i++) {
	            prenode[i] = i;
	            mindist[i] = Graph[vs][i];
	            find[i] = false;
	        }
	 
	        find[vs] = true;
	 
	        for (int v = 1; v < Graph.length; v++) {

	        	double min = max;
	            for (int j = 0; j < Graph.length; j++) {
	                if (!find[j] && mindist[j] < min) {
	                    min = mindist[j];
	                    vnear = j;
	                }
	            }
	            find[vnear] = true;
	 
	            for (int k = 0; k < Graph.length; k++) {
	                if (!find[k] && (min + Graph[vnear][k]) < mindist[k]) {
	                    prenode[k] = vnear;
	                    mindist[k] = min + Graph[vnear][k];
	                }
	            }
	        }
	        
	        for (int i = 0; i < NUM; i++) {
	        	String f=nodelist.get(vs).getname();
	        	if(i!=vs)
	        	{
		        	String s=nodelist.get(i).getname();
		        	String c=nodelist.get((int)prenode[i]).getname();
		            System.out.println("least-cost path to node " + s + ": " + f + s + " and the cost is " + mindist[i]);
		        }
	    }
	  }
}