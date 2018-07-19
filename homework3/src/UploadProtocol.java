import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by lenovo on 2018/7/17.
 */
public class UploadProtocol implements IOStrategy {
    public void service(Socket socket) {
        // ����洢�����ļ����ļ���·��
        System.out.println("�������·��");
        String downPath = "";
        Scanner sc = new Scanner(System.in);
        downPath = sc.next();

        UploadServer upls = new UploadServer(downPath);

        try {

            InputStream is = socket.getInputStream() ; // disʲôʱ��رգ�
            DataInputStream dis = new DataInputStream(is);
            int command = 0;

            while (!socket.isClosed()) {
                // ����Э�����1��ʾ�ͻ����ϴ���Ϊ�ļ���2��ʾ�ͻ����ϴ���Ϊ�ļ���
                command = dis.read();

                switch (command){
                    case 1:
                        upls.receiveFile(dis);
                        break;
                    case 2:
                        // ��ȡ�ļ������ļ��ĸ���
                        int length = dis.read();
                        upls.receiveDir(dis, length);
                        break;
                }

            }

            dis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
