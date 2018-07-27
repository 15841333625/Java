import java.io.*;
import java.util.*;
import java.net.*;

public class Node {
       private String name;            // 结点名
       private int nextnumber;         // 邻结点个数
       private int port;               // 该结点的端口号
       private DatagramSocket socket;  // udp套接字
       public final Map<String, Long> nextNode = new HashMap<>();   // 存储邻结点的map，<结点名，最后一次接收心跳包的时间>
       public final LinkedList<Edge> linknext = new LinkedList<>(); // 存储网络中的边的信息

       public Node(String n,int p)
       {
    	   name=n;
    	   port=p;
    	   nextnumber=0;
       }
       public Node(String n,int p,String fs) throws Exception
       {
    	   name=n;
    	   port=p;
    	   // 从初始文件中对数据
    	   File f=new File(fs);
    	   InputStream fis = new FileInputStream(f);
           // InputStreamReader 是字节流通向字符流的桥梁
  		   InputStreamReader isr = new InputStreamReader(fis);
           BufferedReader br = new BufferedReader(isr);
           nextnumber=Integer.parseInt(br.readLine());
           String s;
           while((s=br.readLine())!=null)
           {
               // 设置初始心跳包时间
               long max = 9223372036854775806L;  // long 类型的最大值
        	   String[] split=s.split(" ");
        	   double d=(double)Integer.parseInt(split[1]);
        	   Edge nn=new Edge(n,split[0],Integer.parseInt(split[2]),d,1);
        	   linknext.add(nn);
        	   nextNode.put(split[0], max);
           }
           // 建立本端口udp socket套接字
           socket = new DatagramSocket(port);
       }

       public String getname() {   return this.name; }
       public int getnumber() {   return nextnumber; }
       public int getport() {   return port; }
       public DatagramSocket getsocket() {   return socket;}
       public void setnumber(int n) {    nextnumber=n; }
}



