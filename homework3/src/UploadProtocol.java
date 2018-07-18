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

            InputStream is = socket.getInputStream() ; // dis什么时候关闭？
            DataInputStream dis = new DataInputStream(is);
            int command = 0;

            while (!socket.isClosed()) {
                // 接受协议命令，1表示客户端上传的为文件，2表示客户端上传的为文件夹
                command = dis.read();

                switch (command){
                    case 1:
                        upls.receiveFile(dis);
                        break;
                    case 2:
                        // 获取文件夹下文件的个数
                        int length = dis.read();
                        upls.receiveDir(dis, length);
                        break;
                }

            }

            dis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
