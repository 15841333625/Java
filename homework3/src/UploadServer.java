import java.io.*;

/**
 * Created by lenovo on 2018/7/17.
 */
public class UploadServer {
    // �����ļ�
    public void receiveFile(DataInputStream dis) throws IOException {
        // �ֲ�����
        String name="";
        FileOutputStream out = null;

        // ҵ���߼�
        // ��һ������ȡ�ļ���
        name = dis.readUTF().trim();

        // ���ֻ���ļ�������ֱ�Ӵ浽ָ���ļ�����
        System.out.println("Ҫ���ص��ļ�Ϊ��"+name);
        out = new FileOutputStream("down\\" + name);

        // �ڶ��������������е���������д�뵽�ļ�
        long length = dis.readLong();
        byte[] b = new byte[(int)length];
        dis.read(b);
        out.write(b);

        out.flush();
        out.close();
        System.out.println("���ؽ���");
    }

    public void receiveDir(DataInputStream dis,int size) throws IOException{
        // �ֲ�����
        String name="";
        FileOutputStream out = null;

        // ҵ���߼�
        for(int j = 0; j < size; j ++) {
            // ��һ������ȡ�ļ��������ļ�·��
            name = dis.readUTF().trim();
            System.out.println("Ҫ���ص��ļ�Ϊ��"+name);

            // �õ��ļ����ϴ��ļ��п�ʼ��·��
            String relativePath = "down\\" + name;
            File dir = new File(relativePath);
            File pardir = dir.getParentFile();
            boolean flag = false;

            // �����ļ�·���ϲ����ڵ��ļ���
            while (!flag) {
                if (!pardir.exists()) {
                    flag = pardir.mkdirs();
                } else
                    flag = true;
            }
            out = new FileOutputStream(relativePath);

            // �ڶ��������������е���������д�뵽�ļ�
            long length = dis.readLong();
            byte[] b = new byte[(int)length];
            dis.read(b);
            out.write(b);
            out.flush();
        }

        out.close();
        System.out.println("���ؽ���");
    }

}
