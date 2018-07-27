/**
 * 边
 */
public class Edge {
    private String startNode; // 起始结点
    private String endNode;   // 终止结点
    private int endport = 0;  // 终止结点的端口
    private double weight;    // 权重
    public int istrue;

    /**
     * 构造函数
     * @param s 起始结点
     * @param e 终止结点
     * @param p 终止结点的端口
     * @param w 权重
     */
    public Edge(String s, String e, int p, double w,int is)
    {
        this(s, e, w, is);
        endport = p;
        istrue=1;
    }

    /**
     * 构造函数
     * @param s 起始结点
     * @param e 终止结点
     * @param w 权重
     */
    public Edge(String s, String e, double w, int is)
    {
        startNode = s;
        endNode = e;
        weight = w;
        istrue=is;
    }

    public void setweight(double w)
    {
        weight=w;
    }
    public String getstart()
    {
        return startNode;
    }
    public String getend()
    {
        return endNode;
    }
    public int getport()
    {
        return endport;
    }
    public double getweight()
    {
        return weight;
    }

    /**
     * 比较两条边是否相等
     * @param o 另一条边
     * @return 如果起始结点，终止结点和权重相等则返回true，否则返回false
     */
    public boolean equals(Object o) {
        Edge e = (Edge)o;
        
        return (this.startNode.equals(e.startNode))
                && (this.endNode.equals(e.endNode))
                && (this.weight == e.weight);
    }

}
