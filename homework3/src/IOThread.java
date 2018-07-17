import java.net.*;

/**
 * Created by lenovo on 2018/7/17.
 */
public class IOThread extends Thread {
    private Socket socket; // ִ�кͿͻ��˵�ͨ��Э��
    private IOStrategy ios; // iosָ��һ��Э�����

    public IOThread(Socket socket, IOStrategy ios) {
        this.socket = socket;
        this.ios = ios;
    }

    public void run() {
        ios.service(socket); // ִ��Э��
    }
}
