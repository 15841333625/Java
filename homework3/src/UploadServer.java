import java.io.*;

/**
 * Created by lenovo on 2018/7/17.
 */
public class UploadServer {
    // �����ļ�
    public void receiveFile(InputStream dis) throws IOException {
        //�ֲ�����
        char ch;
        char[] nameArr=new char[256];
        byte[] buf=new byte[1024];
        String name="";
        FileOutputStream out = null;

        //ҵ���߼�
        //��һ������ȡ�ļ����������ļ������
        int i=0;
        while((ch=(char) dis.read())!='\n'){
            nameArr[i++]= ch;
        }
        //name=nameArr.toString();//��仰�޷����ַ�����ת��Ϊ�ַ�����������������
        name=new String(nameArr);
        System.out.println("Ҫ���ص��ļ�Ϊ��"+name);
        out = new FileOutputStream("down\\" + name.trim());
        //�ڶ��������������е���������д�뵽�ļ�
        int len;
        while((len=dis.read(buf))!=-1){
            out.write(buf,0,len);
        }

        out.flush();
        out.close();

    }

    // �����ļ���
    public void receiveDiretory(InputStream dis) throws IOException {

    }
}
