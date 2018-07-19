import java.util.*;
import java.io.*;

/**
 * Created by lenovo on 2018/7/17.
 */
public class TServer {// 服务器主程序
    public static void main(String[] args) {
        // 服务器配置文件
        Properties props = new Properties();
        try {

            props.load(new FileInputStream("server.properties"));
            // 获得配置文件中的端口信息
            int port = Integer.parseInt(props.getProperty("port"));

            new NwServer(port, new ThreadSupport(new UploadProtocol()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
