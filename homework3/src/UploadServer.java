import java.io.*;

/**
 * Created by lenovo on 2018/7/17.
 */
public class UploadServer {
    // 接收文件
    public void receiveFile(DataInputStream dis) throws Exception {
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

        // 文件解密
        decode(new File("down\\" + name));

        System.out.println("下载结束");
    }

    public void receiveDir(DataInputStream dis,int size) throws Exception{
        // 局部变量
        String name="";
        FileOutputStream out = null;
        File dir = null;

        // 业务逻辑
        for(int j = 0; j < size; j ++) {
            // 第一步：获取文件名，或文件路径
            name = dis.readUTF().trim();
            System.out.println("要下载的文件为："+name);

            // 得到文件从上传文件夹开始的路径
            String relativePath = "down\\" + name;
            dir = new File(relativePath);
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
            out.close();

            // 第三步：文件解密
            decode(dir);
        }


        System.out.println("下载结束");
    }

    // 解密文件
    public void decode(File dir) throws Exception{
        if(dir.isDirectory()) {
            File[] flist = dir.listFiles();
            for(int i = 0; i < flist.length; i ++) {
                decode(flist[i]);
            }
        } else if(dir.isFile()) {
            // 解密文件暂存在 xxxdes.xxx 中，然后删除解密前文件，改名
            String oldPath = dir.getAbsolutePath();
            String[] newPath = oldPath.split("\\.");
            String desFilePath = newPath[0] + "des." + newPath[1];
            File desFile = new File(desFilePath);

            DES.decrypt(dir.getAbsolutePath(), desFile.getAbsolutePath());

            // 删除解密前文件，解密后文件改名
            if(dir.delete()){
                desFile.renameTo(new File(oldPath));
            }

        }
    }

}
