import java.io.*;

/**
 * Created by lenovo on 2018/7/17.
 */
public class UploadServer {
    // 接收文件
    public void receiveFile(DataInputStream dis) throws IOException {
        // 局部变量
        String name="";
        FileOutputStream out = null;

        // 业务逻辑
        // 第一步：获取文件名
        name = dis.readUTF().trim();

        // 如果只有文件名，则直接存到指定文件夹下
        System.out.println("要下载的文件为："+name);
        out = new FileOutputStream("down\\" + name);

        // 第二步：将输入流中的其他内容写入到文件
        long length = dis.readLong();
        byte[] b = new byte[(int)length];
        dis.read(b);
        out.write(b);

        out.flush();
        out.close();
        System.out.println("下载结束");
    }

    public void receiveDir(DataInputStream dis,int size) throws IOException{
        // 局部变量
        String name="";
        FileOutputStream out = null;

        // 业务逻辑
        for(int j = 0; j < size; j ++) {
            // 第一步：获取文件名，或文件路径
            name = dis.readUTF().trim();
            System.out.println("要下载的文件为："+name);

            // 得到文件从上传文件夹开始的路径
            String relativePath = "down\\" + name;
            File dir = new File(relativePath);
            File pardir = dir.getParentFile();
            boolean flag = false;

            // 建立文件路径上不存在的文件夹
            while (!flag) {
                if (!pardir.exists()) {
                    flag = pardir.mkdirs();
                } else
                    flag = true;
            }
            out = new FileOutputStream(relativePath);

            // 第二步：将输入流中的其他内容写入到文件
            long length = dis.readLong();
            byte[] b = new byte[(int)length];
            dis.read(b);
            out.write(b);
            out.flush();
        }

        out.close();
        System.out.println("下载结束");
    }

}
