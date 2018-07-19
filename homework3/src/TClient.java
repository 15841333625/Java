import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Created by lenovo on 2018/7/16.
 */
public class TClient {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();

        try {
            // 获取客户端配置文件
            props.load(new FileInputStream("client.properties"));

            // 获取配置文件中的主机和端口号
            String host = props.getProperty("host");
            int port = Integer.parseInt(props.getProperty("port"));

            if (args.length == 2) {
                port = Integer.parseInt(args[1]);
                host = args[0];
            }
            Socket s = new Socket(host, port); // 创建Socket对象

            UploadClient uplc = new UploadClient(s);
            OutputStream ou = s.getOutputStream();// 定义输出流
            DataOutputStream out = new DataOutputStream(ou);

            uplc.upload(out);

            out.close(); // 关闭流对象
            s.close(); // 本次通信结束，关闭Socket对象

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
