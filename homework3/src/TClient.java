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
            // ��ȡ�ͻ��������ļ�
            props.load(new FileInputStream("client.properties"));

            // ��ȡ�����ļ��е������Ͷ˿ں�
            String host = props.getProperty("host");
            int port = Integer.parseInt(props.getProperty("port"));

            if (args.length == 2) {
                port = Integer.parseInt(args[1]);
                host = args[0];
            }
            Socket s = new Socket(host, port); // ����Socket����

            UploadClient uplc = new UploadClient(s);
            OutputStream ou = s.getOutputStream();// ���������
            DataOutputStream out = new DataOutputStream(ou);

            uplc.upload(out);

            out.close(); // �ر�������
            s.close(); // ����ͨ�Ž������ر�Socket����

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
