/**
 * Created by lenovo on 2018/7/17.
 */
public class TServer {// ������������
    public static void main(String[] args) {
        new NwServer(4321, new ThreadSupport(new UploadProtocol()));
    }
}
