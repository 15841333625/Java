import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by lenovo on 2018/7/17.
 */
public class NwServer { // NwServer.java����������������󣬲���������Socket����
    // ͨ��IOStrategy�ӿڴ��ݸ�ThreadSupport����
    public NwServer(int port, IOStrategy ios) { // ��������������߳���ִ��
        try {
            ServerSocket ss = new ServerSocket(port);
            ss.setSoTimeout(10000000);
            System.out.println("server is ready");
            while (true) {
                Socket socket = ss.accept(); // ���������������
                ios.service(socket); // ���������˵�socket���󴫵ݸ�
            } // ThreadSupport����
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
