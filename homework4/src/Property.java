import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by lenovo on 2018/7/21.
 * 读取配置文件中的信息
 */
public class Property {
    /**
     * 读取某个配置文件中某个属性的信息
     * @param file 配置文件的文件名
     * @param key  要读取的属性的名称
     * @return 返回int类型属性值
     * @throws IOException 可能抛出IOException
     */
    static public int readIntProperty( String file, String key ) throws IOException{
        Properties props = new Properties();
        props.load(new FileInputStream(file));
        return Integer.parseInt(props.getProperty(key));
    }
}

