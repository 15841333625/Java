import java.net.*;
import java.io.*;

/**
 * Created by lenovo on 2018/7/16.
 */
public class TClient {
    public static void main(String[] args) throws Exception {
        int port = 4321; // Ҳ���Դ������ļ��м�������˿�����.
        String host = "localhost";
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
    }
}
