import java.io.*;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.*;

/**
 * Created by lenovo on 2018/7/18.
 */

/**
 * saveDesKey生成密钥，存放在DesKey.xml中
 * encrypt和decrypt根据DesKey.xml中的密钥，来加密和解密文件
 */
public class DES {
    private static String keyfileName = "DesKey.xml";
    /**
     * <p> DES解密文件
     * @param file 需要解密的文件
     * @param dest 解密后的文件
     * @throws Exception
     */
    public static void decrypt(String file, String dest) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(dest);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = is.read(buffer)) >= 0) {
            cos.write(buffer, 0, r);
        }
        cos.close();
        out.close();
        is.close();
    }
    /**
     * <p>DES加密文件
     * @param file 源文件
     * @param destFile 加密后的文件
     * @throws Exception
     */
    public static void encrypt(String file, String destFile) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(destFile);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
    }
    private static Key getKey() {
        Key kp = null;
        try {
            String fileName = keyfileName;
            InputStream is = new FileInputStream(fileName);
            ObjectInputStream oos = new ObjectInputStream(is);
            kp = (Key) oos.readObject();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kp;
    }
    public static void main(String[] args) throws Exception {
        DES.saveDesKey();
        DES.encrypt("d:\\test\\a.txt", "ades.txt");
        DES.decrypt("ades.txt","a.txt");
        //desinput.txt 经过加密和解密后生成的 desinput2.txt 应该与源文件一样
    }
    /**
     * <p> 生成KEY，并保存
     */
    public static void saveDesKey(){
        try {
            SecureRandom sr = new SecureRandom();
            //为我们选择的DES算法生成一个KeyGenerator对象
            KeyGenerator kg = KeyGenerator.getInstance ("DES" );
            kg.init (sr);
            FileOutputStream fos = new FileOutputStream(keyfileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            //生成密钥
            Key key = kg.generateKey();
            oos.writeObject(key);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
