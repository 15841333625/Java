import java.io.*;
import java.util.*;
import java.net.*;

public class Node {
       private String name;
       private int nextnumber;
       private int port;
       private DatagramSocket socket;
       public  LinkedList<Edge> linknext=new LinkedList();
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
    	   File f=new File(fs);
    	   InputStream fis = new FileInputStream(f);
  		   InputStreamReader isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁
           BufferedReader br = new BufferedReader(isr);
           nextnumber=Integer.parseInt(br.readLine());
           String s;
           while((s=br.readLine())!=null)
           {
        	   String[] split=s.split(" ");
        	   double d=(double)Integer.parseInt(split[1]);
        	   Edge nn=new Edge(n,split[0],Integer.parseInt(split[2]),d);
        	   linknext.add(nn);
           }
           // 建立本端口udp socket套接字
           socket = new DatagramSocket(port);
       }
       
       public LinkedList<Edge> getnext()
       {   return linknext;}
       public String getname()
       {   return this.name; }
       public int getnumber()
       {   return nextnumber; }
       public int getport()
       {   return port; }
       public DatagramSocket getsocket()
       {   return socket;}
       public void setname(String n)
       {    name=n;  }
       public void setnumber(int n)
       {    nextnumber=n; }
       public void setport(int p)
       {    port=p; }
       public void sendMessage() {
           String message = "";

           List<String> nextmessage = new ArrayList<>(10);
           Iterator<Edge> it = linknext.iterator();
           while(it.hasNext()) {
               Edge n = it.next();
               String s = name + "," + n.getend() + "," + n.getweight();
               nextmessage.add(s);
           }

           Iterator<String> sit = nextmessage.iterator();
           while(sit.hasNext()) {
               String s = sit.next();
               message = message + " " + s;
           }

           sendMessage(message);
       }

       /**
        * 将字符串发送给相邻结点
        * @param message 要发送的字符串
        */
       private void sendMessage(String message) {
           byte[] b = message.getBytes();

           UdpNode un = new UdpNode(socket);

           Iterator<Edge> nit = linknext.iterator();
           while (nit.hasNext()) {

               Edge nn = nit.next();
               if(nn.getport() != 0) {
                   un.sendMessage(b, nn.getport());
               }
           }
       }

       /**
        * 接收其他结点发送来的信息，
        */
       public void reveiveMessage() {
           byte[] b = new byte[1024];
           UdpNode un = new UdpNode(socket);

           un.receiveMessage(b);

           String originData = new String(b);
           String[] EdgeMessage = originData.trim().split(" ");

           for(int i = 0; i < EdgeMessage.length; i ++) {
               String[] message = EdgeMessage[i].trim().split(",");
               Edge nn = new Edge(message[0], message[1], 0, Integer.parseInt(message[2]));
               linknext.add(nn);
           }
       }
}

