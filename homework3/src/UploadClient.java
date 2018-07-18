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
        // ��־λ��0��ʾֹͣ�ϴ���1��ʾ�ϴ���Ϊ�ļ���2��ʾ�ϴ���Ϊ�ļ���
        int flag = 0;
        while(true) {
            System.out.print("0. �˳� \n1. �ϴ��ļ�\n2. �ϴ��ļ���\n");
            System.out.println("---------------------------------------------");
            Scanner sc = new Scanner(System.in);
            flag = sc.nextInt();
            if(flag == 0)
                break;
            else {
                // ���Ҫ�ϴ����ļ����ļ���·��
                String filePath;
                System.out.println("�����ļ�����·����");
                sc = new Scanner(System.in);
                filePath = sc.next().trim();

                try {
                    // �ϴ���־λ
                    out.write(flag);

                    // �ϴ��ļ����ļ���
                    if(flag == 1)
                        uploadFile(filePath, out, 1, "");
                    else if(flag == 2)
                        uploadDirectory(filePath, out);
                    else
                        System.out.println("��Ч���룡");

                } catch ( Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // �ϴ��ļ�
    // path Ϊ�ϴ��ļ��ľ���·��, out Ϊ�����, flag Ϊ�����־λ, repath ��ʾ�ļ�����ϴ��ļ��е�·��
    public void uploadFile(String path, DataOutputStream out, int flag, String repath) throws Exception {
        FileInputStream fis = null;
        String fileName="";
        File file = new File(path);

        //ҵ���߼�
        if(flag == 1) {
            //1.д���ļ���
            fileName=path.substring(path.lastIndexOf('\\')+1);
            System.out.println("��Ҫ�ϴ����ļ���Ϊ��"+fileName);
            out.writeUTF(fileName);

        } else if(flag == 2) {
            //1.д���ļ���·��;
            System.out.println("��Ҫ�ϴ����ļ�Ϊ��"+path);
            fileName = path;
            out.writeUTF(repath);
        }

        //2.�ļ����ܣ������ܺ��ļ��ݴ��� xxxdes.xxx �ļ���
        String[] newName = fileName.split("\\.");
        String desFileName = newName[0] + "des." + newName[1];
        File desFile = new File(desFileName);

        DES.encrypt(file.getAbsolutePath(), desFile.getAbsolutePath());

        //3.д���ļ�����
        out.writeLong(desFile.length());

        //3.д���ļ�����
        fis=new FileInputStream(desFile);
        byte[] b = new byte[(int)desFile.length()];
        fis.read(b);
        out.write(b);
        out.flush();

        fis.close();

        //4.ɾ�����ܺ��ļ�
        desFile.delete();
    }

    // �����ļ���
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

        // ���ļ����е������ļ������� files ��
        List<File> files = new ArrayList<>(20);
        traverseDir(root, files);

        out.write(files.size());

        // ���� files
        Iterator<File> it = files.iterator();
        while(it.hasNext()) {
            File file = it.next();
            // �õ��Ӹ��ļ��п�ʼ���ļ�·��
            String relativePath = root.getName() + "\\" + file.getAbsolutePath().substring(rootPath.length() + 1);

            // �ϴ��ļ�
            uploadFile(file.getAbsolutePath(), out, 2, relativePath);
        }
    }
}
