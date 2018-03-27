package java_study;

/**
 * Created by lenovo on 2018/3/26.
 */
class Tag{
    Tag(int i){
        System.out.println("tag" + i);
    }
}
class Rock {
    Tag t1 = new Tag(1);
    Rock() {
        System.out.println("Creating Rock");
    }
    Rock(int i){
        System.out.println(i + " Rock");
    }
    Tag t2 = new Tag(2);
    Rock(int i, int j){
        this(i);                          //调用Rock(int i)构造器
        System.out.println(j);
        t3 = new Tag(33);
    }
    void copy(Rock r){
        System.out.println("copy");
    }
    Tag t3 = new Tag(3);
}
public class SimpleConstructor {
    public static void main(String[] args){
        Rock r = new Rock();
        Rock r2 = new Rock(1,2);
        r.copy(r2);
    }
}
