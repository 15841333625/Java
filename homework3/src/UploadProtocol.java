import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by lenovo on 2018/7/17.
 */
public class UploadProtocol implements IOStrategy {
    public void service(Socket socket) {
        UploadServer upls = new UploadServer();

        try {

            InputStream dis = socket.getInputStream() ;
            int command = 0;

            while (true) {
                // ����Э�����1��ʾ�ͻ����ϴ���Ϊ�ļ���2��ʾ�ͻ����ϴ���Ϊ�ļ���
                command = dis.read();

                switch (command) {
                    case 1:
                        upls.receiveFile(dis);
                        break;
                    case 2:
                        upls.receiveDiretory(dis);
                        break;
                }

                dis.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
