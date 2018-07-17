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
                    // ���������
                    OutputStream out = s.getOutputStream();

                    out.write(flag);

                    // �ϴ��ļ����ļ���
                    if(flag == 1)
                        uploadFile(filePath, out);
                    else if(flag == 2)
                        uploadDirectory(filePath, out);
                    else
                        System.out.println("��Ч���룡");

                    out.close();

                } catch ( IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // �ϴ��ļ�
    public void uploadFile(String path, OutputStream out) throws IOException {
        FileInputStream fis = null;
        byte[] buf=new byte[1024];
        String fileName="";

        //ҵ���߼�
        //1.д���ļ���
        fileName=path.substring(path.lastIndexOf('\\')+1);
        System.out.println("��Ҫ�ϴ����ļ���Ϊ��"+fileName);
        out.write(fileName.getBytes());
        out.write('\n');

        //2.д���ļ�����
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
