import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by lenovo on 2018/7/21.
 * ��ȡ�����ļ��е���Ϣ
 */
public class Property {
    /**
     * ��ȡĳ�������ļ���ĳ�����Ե���Ϣ
     * @param file �����ļ����ļ���
     * @param key  Ҫ��ȡ�����Ե�����
     * @return ����int��������ֵ
     * @throws IOException �����׳�IOException
     */
    static public int readIntProperty( String file, String key ) throws IOException{
        Properties props = new Properties();
        props.load(new FileInputStream(file));
        return Integer.parseInt(props.getProperty(key));
    }
}

