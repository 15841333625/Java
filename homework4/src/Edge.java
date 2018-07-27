/**
 * ��
 */
public class Edge {
    private String startNode; // ��ʼ���
    private String endNode;   // ��ֹ���
    private int endport = 0;  // ��ֹ���Ķ˿�
    private double weight;    // Ȩ��
    public int istrue;

    /**
     * ���캯��
     * @param s ��ʼ���
     * @param e ��ֹ���
     * @param p ��ֹ���Ķ˿�
     * @param w Ȩ��
     */
    public Edge(String s, String e, int p, double w,int is)
    {
        this(s, e, w, is);
        endport = p;
        istrue=1;
    }

    /**
     * ���캯��
     * @param s ��ʼ���
     * @param e ��ֹ���
     * @param w Ȩ��
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
     * �Ƚ��������Ƿ����
     * @param o ��һ����
     * @return �����ʼ��㣬��ֹ����Ȩ������򷵻�true�����򷵻�false
     */
    public boolean equals(Object o) {
        Edge e = (Edge)o;
        
        return (this.startNode.equals(e.startNode))
                && (this.endNode.equals(e.endNode))
                && (this.weight == e.weight);
    }

}
