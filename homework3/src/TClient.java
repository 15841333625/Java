import java.net.*;

/**
 * Created by lenovo on 2018/7/16.
 */
public class TClient {
    public static void main(String[] args) throws Exception {
        int port = 4321; // 也可以从属性文件中加载这个端口数据.
        String host = "localhost";
        if (args.length == 2) {
            port = Integer.parseInt(args[1]);
            host = args[0];
        }
        Socket s = new Socket(host, port); // 创建Socket对象

        UploadClient uplc = new UploadClient(s);

        uplc.upload();


        s.close(); // 本次通信结束，关闭Socket对象
    }
}
