
public class Edge {
    private String startNode;
    private String endNode;
    private int endport=0;
    private double weight;
    public Edge(String s, String e, int p, double w)
    {
        startNode=s;
        endNode=e;
        endport=p;
        weight=w;
    }
    public Edge(String s, String e, double w)
    {
        startNode=s;
        endNode=e;
        endport=0;
        weight=w;
    }
    public void setport(int p)
    {
        endport=p;
    }
    public void setstart(String s)
    {
        startNode=s;
    }
    public void setweight(double w)
    {
        weight=w;
    }
    public void setend(String n)
    {
        endNode=n;
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

}
