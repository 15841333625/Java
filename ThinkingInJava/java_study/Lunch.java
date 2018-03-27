package java_study;

/**
 * Created by lenovo on 2018/3/27.
 */
class Soup {
    private Soup() {}
    //可以通过静态方法创造Soup对象
    public static Soup makeSoup() {
        return new Soup();
    }
    private static Soup ps1 = new Soup();
    public static Soup access() {
        return ps1;
    }
    public void f() {}
}

class Sandwich {
    void f() {
        new Lunch();
    }
}

//每个文件中只能有一个public的类
public class Lunch {
    void test() {
        //Soup priv1 = new Soup();        无法编译通过
        Soup priv2 = Soup.makeSoup();
        Sandwich f1 = new Sandwich();
        Soup.access();
    }
}
