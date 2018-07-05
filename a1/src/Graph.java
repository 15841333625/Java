/**
 * Created by lenovo on 2018/7/5.
 */
public class Graph {
    private int[][] graph;
    public static int N = 10000;

    public Graph(int[][] g) {
        graph = g;
    }

    public void dijkstra(int vertex) {
        int num = graph[0].length;
        // 前驱结点数组
        int[] prenode = new int[num];
        // 最短距离数组
        int[] mindist = new int[num];
        // 该结点是否已经找到最短路径
        boolean[] find = new boolean[num];

        int vnear = 0;

        for(int i = 0; i < mindist.length; i ++) {
            prenode[i] = i;
            mindist[i] = graph[vertex][i];
            find[i] = false;
        }

        find[vertex] = true;

        for(int i = 1; i < graph.length; i ++) {
            // 每次循环求得距离vertex最近的结点vnear和最短距离min
            int min = N;
            for (int j = 0; j < graph.length; j ++) {
                if (!find[j] && mindist[j] < min) {
                    min = mindist[j];
                    vnear = j;
                }
            }
            find[vnear] = true;

            //根据vnear修正vertex到其他所有结点的前驱结点及距离
            for (int k = 0; k < graph.length; k ++) {
                if(!find[k] && (min + graph[vnear][k]) < mindist[k]) {
                    prenode[k] = vnear;
                    mindist[k] = min + graph[vnear][k];
                }
            }
        }

        for (int i = 0; i < num; i ++) {
            if(mindist[i] != N)
                System.out.println("v" + vertex + "->v" + i + ", s = " + mindist[i]);
            else
                System.out.println("v" + vertex + "->v" + i + ", s = null");
        }
    }

    public static void main(String[] arg) {
        int N = Graph.N;
        int[][] g = {
                {N, 2, 5, 1, N, N},
                {2, N, 3, 2, N, N},
                {5, 3, N, 3, 1, 5},
                {1, 2, 3, N, 1, N},
                {N, N, 1, 1, N, 2},
                {N, N, 5, N, 2, N}
        };
        Graph graph = new Graph(g);
        for(int i = 0; i < 6; i ++) {
            graph.dijkstra(i);
            System.out.println("-----------------------------");
        }
    }
}
