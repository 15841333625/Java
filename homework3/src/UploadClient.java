import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created by lenovo on 2018/7/17.
 */
public class UploadClient {
    Socket s;

    public UploadClient(Socket s) {
        this.s = s;
    }

    public void upload () {
        // 标志位，0表示停止上传，1表示上传的为文件，2表示上传的为文件夹
        int flag = 0;
        while(true) {
            System.out.print("0. 退出 \n1. 上传文件\n2. 上传文件夹\n");
            System.out.println("---------------------------------------------");
            Scanner sc = new Scanner(System.in);
            flag = sc.nextInt();
            if(flag == 0)
                break;
            else {
                // 获得要上传的文件或文件夹路径
                String filePath;
                System.out.println("输入文件绝对路径：");
                sc = new Scanner(System.in);
                filePath = sc.next().trim();

                try {
                    // 定义输出流
                    OutputStream out = s.getOutputStream();

                    out.write(flag);

                    // 上传文件或文件夹
                    if(flag == 1)
                        uploadFile(filePath, out);
                    else if(flag == 2)
                        uploadDirectory(filePath, out);
                    else
                        System.out.println("无效输入！");

                    out.close();

                } catch ( IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 上传文件
    public void uploadFile(String path, OutputStream out) throws IOException {
        FileInputStream fis = null;
        byte[] buf=new byte[1024];
        String fileName="";

        //业务逻辑
        //1.写入文件名
        fileName=path.substring(path.lastIndexOf('\\')+1);
        System.out.println("您要上传的文件名为："+fileName);
        out.write(fileName.getBytes());
        out.write('\n');

        //2.写入文件内容
        fis=new FileInputStream(path);
        int len;
        while((len=fis.read(buf))!=-1){
            out.write(buf, 0, len);
        }
        out.flush();

        fis.close();
    }

    public void uploadDirectory(String path, OutputStream out) throws IOException {
        File root = new File(path);

        
    }
}
