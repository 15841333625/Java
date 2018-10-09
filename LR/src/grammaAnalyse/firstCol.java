package grammaAnalyse;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by lenovo on 2018/10/9.
 */

/**
 *   E->TE'
     E'->-TE'|e
     T->FT'
     T'->*FT'|e
     F->(E)|id
 */
public class firstCol {
    // ����﷨
    public List<String> gramma = new ArrayList<>();

    // ����ս��
    private List<String> finalchar = new ArrayList<>();

    public firstCol() {
        // ����ս��
        finalchar.add("-");
        finalchar.add("*");
        finalchar.add("(");
        finalchar.add(")");
        finalchar.add("id");

        // �����﷨
        boolean flag = true;
        while(flag) {
            Scanner scan = new Scanner(System.in);
            String read = scan.nextLine();
            if(read.equals("end")) {
                flag = false;
            } else {
                gramma.add(read);
            }
        }
    }

    /**
     * ��ȡһ�����ս����First��
     * @param unfinal ���ս��Map
     * @param key Ҫ��ȡ���ϵķ��ս��
     * @return First����
     */
    public List<String> getFirstCollection(Map<String, String[]> unfinal, String key) {
        // ��ŷ��ص�First����
        List<String> result = new ArrayList<>();

        // ����Map���ҵ���Ҫ��ȡFirst���ķ��ս��
        for (Map.Entry<String, String[]> entry : unfinal.entrySet()) {
            if(key.equals(entry.getKey())) {
                // ��ø��ս�������Ƴ��ı��ʽ�ַ�������
                String[] values = entry.getValue();
                // �������б��ʽ���飬��ȡ��һ���ַ�
                for(int i = 0; i < values.length; i ++) {
                    String str = values[i].substring(0,1);
                    // ������ս��������ӵ�First������
                    // �ж��Ƿ���"id"
                    if(values[i].substring(0,1).equals("i") && values[i].substring(1,2).equals("d"))
                        str = "id";
                    if(finalchar.contains(str)) {
                        result.add(str);
                    } else if(str.equals("e")) { // ����ǿգ�����ӵ�First������
                        result.add(str);
                    } else{  // ����Ƿ��ս��������First���Ϻϲ�
                        // ��־���ж��Ƿ���Ҫ��First�����м���e
                        boolean bflag = true;
                        // �������ʽ�����ÿһ�����ս��
                        for(int j = 0; j < values[i].length(); j ++) {
                            String unfin = values[i].substring(j, j + 1);
                            if(values[i].substring(j+1,j+2).equals("'")) {
                                unfin = values[i].substring(j, j + 2);
                                j ++;
                            }

                            // ���ÿһ�����ս����First����
                            List<String> another = getFirstCollection(unfinal, unfin);
                            // ���������û��e,������First������Ҳû��e
                            if(!another.contains("e"))
                                bflag = false;
                            // ����Ǳ��ʽ�еĵ�һ�����ս������ϲ�First����
                            if(j == 0) {
                                result.addAll(another);
                            }
                        }
                        // ������ʽ�е�ÿһ�����ս����First�����ж���e,�������First������Ҳ��e
                        if(bflag)
                            result.add("e");
                    }
                }
            }
        }
        return result;
    }

    /**
     * �������з��ս����First��
     * @return ����First���ļ���
     */
    public List<List<String>> getAllFirstCollection() {
        List<List<String>> result = new ArrayList<>();

        // ������з��ս���������Ƴ��ı��ʽ��Map
        Map<String, String[]> unfinal = new HashMap<>();
        Iterator it = gramma.iterator();
        while(it.hasNext()){
            String g = it.next().toString();
            // -> ���Ϊ���ս��
            String[] gs = g.split("->");
            // ����Ƴ��ı��ʽ�ַ�������
            String[] fs = gs[1].split("\\|");
            unfinal.put(gs[0], fs);
        }

        // ���ÿһ�����ս����First���ϣ���󽫸÷��ս����ӽ�ȥ
        for (Map.Entry<String, String[]> entry : unfinal.entrySet()) {
            List<String> rlist = getFirstCollection(unfinal, entry.getKey());
            rlist.add(entry.getKey());
            result.add(rlist);
        }

        return result;
    }

    public static void main(String[] args) {
        firstCol f = new firstCol();

        List<List<String>> list = f.getAllFirstCollection();

        for(int i = 0; i < list.size(); i ++) {
            List<String> l = list.get(i);

            System.out.print(l.get(l.size()-1) + "first: ");
            for(int j = 0; j < l.size() - 1; j ++) {
                System.out.print(l.get(j) + "  ");
            }
            System.out.println("");
        }
    }
}
