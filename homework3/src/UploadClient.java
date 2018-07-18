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

    public void upload (DataOutputStream out) {
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
                    // 上传标志位
                    out.write(flag);

                    // 上传文件或文件夹
                    if(flag == 1)
                        uploadFile(filePath, out, 1, "");
                    else if(flag == 2)
                        uploadDirectory(filePath, out);
                    else
                        System.out.println("无效输入！");

                } catch ( Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 上传文件
    // path 为上传文件的绝对路径, out 为输出流, flag 为服务标志位, repath 表示文件相对上传文件夹的路径
    public void uploadFile(String path, DataOutputStream out, int flag, String repath) throws Exception {
        FileInputStream fis = null;
        String fileName="";
        File file = new File(path);

        //业务逻辑
        if(flag == 1) {
            //1.写入文件名
            fileName=path.substring(path.lastIndexOf('\\')+1);
            System.out.println("您要上传的文件名为："+fileName);
            out.writeUTF(fileName);

        } else if(flag == 2) {
            //1.写入文件夹路径;
            System.out.println("您要上传的文件为："+path);
            fileName = path;
            out.writeUTF(repath);
        }

        //2.文件加密，将加密后文件暂存在 xxxdes.xxx 文件中
        String[] newName = fileName.split("\\.");
        String desFileName = newName[0] + "des." + newName[1];
        File desFile = new File(desFileName);

        DES.encrypt(file.getAbsolutePath(), desFile.getAbsolutePath());

        //3.写入文件长度
        out.writeLong(desFile.length());

        //3.写入文件内容
        fis=new FileInputStream(desFile);
        byte[] b = new byte[(int)desFile.length()];
        fis.read(b);
        out.write(b);
        out.flush();

        fis.close();

        //4.删除加密后文件
        desFile.delete();
    }

    // 遍历文件夹
    public void traverseDir(File dir, List<File> files) {
        if(dir.isDirectory()) {
            File[] flist = dir.listFiles();
            for(int i = 0; i < flist.length; i ++) {
                traverseDir(flist[i], files);
            }
        } else {
            files.add(dir);
        }
    }

    public void uploadDirectory(String path, DataOutputStream out) throws Exception {
        File root = new File(path);
        String rootPath = root.getAbsolutePath();

        // 将文件夹中的所有文件保存在 files 中
        List<File> files = new ArrayList<>(20);
        traverseDir(root, files);

        out.write(files.size());

        // 遍历 files
        Iterator<File> it = files.iterator();
        while(it.hasNext()) {
            File file = it.next();
            // 得到从根文件夹开始的文件路径
            String relativePath = root.getName() + "\\" + file.getAbsolutePath().substring(rootPath.length() + 1);

            // 上传文件
            uploadFile(file.getAbsolutePath(), out, 2, relativePath);
        }
    }
}
