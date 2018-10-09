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
    // 存放语法
    public List<String> gramma = new ArrayList<>();

    // 存放终结符
    private List<String> finalchar = new ArrayList<>();

    public firstCol() {
        // 添加终结符
        finalchar.add("-");
        finalchar.add("*");
        finalchar.add("(");
        finalchar.add(")");
        finalchar.add("id");

        // 输入语法
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
     * 获取一个非终结符的First集
     * @param unfinal 非终结符Map
     * @param key 要获取集合的非终结符
     * @return First集合
     */
    public List<String> getFirstCollection(Map<String, String[]> unfinal, String key) {
        // 存放返回的First集合
        List<String> result = new ArrayList<>();

        // 遍历Map，找到需要获取First集的非终结符
        for (Map.Entry<String, String[]> entry : unfinal.entrySet()) {
            if(key.equals(entry.getKey())) {
                // 获得该终结符可以推出的表达式字符串数组
                String[] values = entry.getValue();
                // 遍历所有表达式数组，获取第一个字符
                for(int i = 0; i < values.length; i ++) {
                    String str = values[i].substring(0,1);
                    // 如果是终结符，则添加到First集合中
                    // 判断是否是"id"
                    if(values[i].substring(0,1).equals("i") && values[i].substring(1,2).equals("d"))
                        str = "id";
                    if(finalchar.contains(str)) {
                        result.add(str);
                    } else if(str.equals("e")) { // 如果是空，则添加到First集合中
                        result.add(str);
                    } else{  // 如果是非终结符，则将其First集合合并
                        // 标志，判断是否需要在First集合中加入e
                        boolean bflag = true;
                        // 遍历表达式，获得每一个非终结符
                        for(int j = 0; j < values[i].length(); j ++) {
                            String unfin = values[i].substring(j, j + 1);
                            if(values[i].substring(j+1,j+2).equals("'")) {
                                unfin = values[i].substring(j, j + 2);
                                j ++;
                            }

                            // 获得每一个非终结符的First集合
                            List<String> another = getFirstCollection(unfinal, unfin);
                            // 如果集合中没有e,则结果的First集合中也没有e
                            if(!another.contains("e"))
                                bflag = false;
                            // 如果是表达式中的第一个非终结符，则合并First集合
                            if(j == 0) {
                                result.addAll(another);
                            }
                        }
                        // 如果表达式中的每一个非终结符的First集合中都有e,则所求的First集合中也有e
                        if(bflag)
                            result.add("e");
                    }
                }
            }
        }
        return result;
    }

    /**
     * 返回所有非终结符的First集
     * @return 所有First集的集合
     */
    public List<List<String>> getAllFirstCollection() {
        List<List<String>> result = new ArrayList<>();

        // 获得所有非终结符和它们推出的表达式的Map
        Map<String, String[]> unfinal = new HashMap<>();
        Iterator it = gramma.iterator();
        while(it.hasNext()){
            String g = it.next().toString();
            // -> 左边为非终结符
            String[] gs = g.split("->");
            // 获得推出的表达式字符串数组
            String[] fs = gs[1].split("\\|");
            unfinal.put(gs[0], fs);
        }

        // 获得每一个非终结符的First集合，最后将该非终结符添加进去
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
