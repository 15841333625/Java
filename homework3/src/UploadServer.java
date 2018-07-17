import java.io.*;

/**
 * Created by lenovo on 2018/7/17.
 */
public class UploadServer {
    // 接收文件
    public void receiveFile(InputStream dis) throws IOException {
        //局部变量
        char ch;
        char[] nameArr=new char[256];
        byte[] buf=new byte[1024];
        String name="";
        FileOutputStream out = null;

        //业务逻辑
        //第一步：获取文件名，构造文件输出流
        int i=0;
        while((ch=(char) dis.read())!='\n'){
            nameArr[i++]= ch;
        }
        //name=nameArr.toString();//这句话无法将字符数组转换为字符串，需用下面的语句
        name=new String(nameArr);
        System.out.println("要下载的文件为："+name);
        out = new FileOutputStream("down\\" + name.trim());
        //第二步：将输入流中的其他内容写入到文件
        int len;
        while((len=dis.read(buf))!=-1){
            out.write(buf,0,len);
        }

        out.flush();
        out.close();

    }

    // 接收文件夹
    public void receiveDiretory(InputStream dis) throws IOException {

    }
}
