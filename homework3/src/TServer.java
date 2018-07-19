import java.util.*;
import java.io.*;

/**
 * Created by lenovo on 2018/7/17.
 */
public class TServer {// ������������
    public static void main(String[] args) {
        // �����������ļ�
        Properties props = new Properties();
        try {

            props.load(new FileInputStream("server.properties"));
            // ��������ļ��еĶ˿���Ϣ
            int port = Integer.parseInt(props.getProperty("port"));

            new NwServer(port, new ThreadSupport(new UploadProtocol()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
