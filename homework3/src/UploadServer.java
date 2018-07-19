import java.io.*;

/**
 * Created by lenovo on 2018/7/17.
 */
public class UploadServer {
    // �����ļ����ļ���
    private File down;

    public UploadServer(String downPath) {
        down = new File(downPath);
        if(!down.exists()) {
            down.mkdirs();
        }
    }
    // �����ļ�
    public void receiveFile(DataInputStream dis) throws Exception {
        // �ֲ�����
        String name="";
        FileOutputStream out = null;

        // ҵ���߼�
        // ��һ������ȡ�ļ���
        name = dis.readUTF().trim();

        // ���ֻ���ļ�������ֱ�Ӵ浽ָ���ļ�����
        System.out.println("Ҫ���յ��ļ�Ϊ��"+name);
        out = new FileOutputStream(down + "\\" + name);

        // �ڶ��������������е���������д�뵽�ļ�
        long length = dis.readLong();
        byte[] b = new byte[(int)length];
        dis.read(b);
        out.write(b);
        out.flush();
        out.close();

        // �ļ�����
        decode(new File(down + "\\" + name));

        System.out.println("���ս���");
    }

    public void receiveDir(DataInputStream dis,int size) throws Exception{
        // �ֲ�����
        String name="";
        FileOutputStream out = null;
        File dir = null;

        // ҵ���߼�
        for(int j = 0; j < size; j ++) {
            // ��һ������ȡ�ļ��������ļ�·��
            name = dis.readUTF().trim();
            System.out.println("Ҫ���յ��ļ�Ϊ��"+name);

            // �õ��ļ����ϴ��ļ��п�ʼ��·��
            String relativePath = down + "\\" + name;
            dir = new File(relativePath);
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
            out.close();

            // ���������ļ�����
            decode(dir);
        }
        System.out.println("���ս���");
    }

    // �����ļ�
    public void decode(File dir) throws Exception{
        if(dir.isDirectory()) {
            File[] flist = dir.listFiles();
            for(int i = 0; i < flist.length; i ++) {
                decode(flist[i]);
            }
        } else if(dir.isFile()) {
            // �����ļ��ݴ��� xxxdes.xxx �У�Ȼ��ɾ������ǰ�ļ�������
            String oldPath = dir.getAbsolutePath();
            String[] newPath = oldPath.split("\\.");
            String desFilePath = newPath[0] + "des." + newPath[1];
            File desFile = new File(desFilePath);

            DES.decrypt(dir.getAbsolutePath(), desFile.getAbsolutePath());

            // ɾ������ǰ�ļ������ܺ��ļ�����
            if(dir.delete()){
                desFile.renameTo(new File(oldPath));
            }

        }
    }

}
