/**
 * Created by lenovo on 2018/7/17.
 */
public interface IOStrategy {
    public void service(java.net.Socket socket);  //对传入的socket对象进行处理
}
