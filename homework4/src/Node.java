import java.io.*;
import java.util.*;
import java.net.*;

public class Node {
       private String name;            // �����
       private int nextnumber;         // �ڽ�����
       private int port;               // �ý��Ķ˿ں�
       private DatagramSocket socket;  // udp�׽���
       public final Map<String, Long> nextNode = new HashMap<>();   // �洢�ڽ���map��<����������һ�ν�����������ʱ��>
       public final LinkedList<Edge> linknext = new LinkedList<>(); // �洢�����еıߵ���Ϣ

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
    	   // �ӳ�ʼ�ļ��ж�����
    	   File f=new File(fs);
    	   InputStream fis = new FileInputStream(f);
           // InputStreamReader ���ֽ���ͨ���ַ���������
  		   InputStreamReader isr = new InputStreamReader(fis);
           BufferedReader br = new BufferedReader(isr);
           nextnumber=Integer.parseInt(br.readLine());
           String s;
           while((s=br.readLine())!=null)
           {
               // ���ó�ʼ������ʱ��
               long max = 9223372036854775806L;  // long ���͵����ֵ
        	   String[] split=s.split(" ");
        	   double d=(double)Integer.parseInt(split[1]);
        	   Edge nn=new Edge(n,split[0],Integer.parseInt(split[2]),d,1);
        	   linknext.add(nn);
        	   nextNode.put(split[0], max);
           }
           // �������˿�udp socket�׽���
           socket = new DatagramSocket(port);
       }

       public String getname() {   return this.name; }
       public int getnumber() {   return nextnumber; }
       public int getport() {   return port; }
       public DatagramSocket getsocket() {   return socket;}
       public void setnumber(int n) {    nextnumber=n; }
}



