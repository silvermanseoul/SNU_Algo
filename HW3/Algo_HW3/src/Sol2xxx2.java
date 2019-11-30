// https://brenden.tistory.com/37


import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Edge implements Comparable<Edge> {
    int v1;
    int v2;
    int cost;
    Edge(int v1, int v2, int cost) {
        this.v1 = v1;
        this.v2 = v2;
        this.cost = cost;
    }
    @Override
    public int compareTo(Edge o) {
        if(this.cost < o.cost)
            return -1;
        else if(this.cost == o.cost)
            return 0;
        else
            return 1;
    }
}
public class Sol2xxx2 {
    public static int[] parent;
    public static ArrayList<Edge> edgeList;

    public static void union(int x, int y) {
        x = find(x);
        y = find(y);
        if(x != y)
            parent[y] = x;
    }

    public static int find(int x) {
        if(parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }
    public static boolean isSameParent(int x, int y) {
        x = find(x);
        y = find(y);
        if(x == y) return true;
        else return false;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int v = sc.nextInt();
        int e = sc.nextInt();

        edgeList = new ArrayList<Edge>();
        for(int i = 0; i < e; i++) {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();
            int cost = sc.nextInt();
            edgeList.add(new Edge(v1,v2,cost));
        }

        parent = new int[v+1];
        for(int i = 1; i <= v; i++) {
            parent[i] = i;
        }

        Collections.sort(edgeList);

        int sum = 0;
        for(int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
            if(!isSameParent(edge.v1, edge.v2)) {
                sum += edge.cost;
                union(edge.v1, edge.v2);
            }
        }

        System.out.println(sum);
    }
}
