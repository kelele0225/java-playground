package com.rocky.learn_java.leetcode.amazon;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// also known as Articulation Point problem
public class CriticalRoutersIndexStartWith1 {

    int discoverSeqCounter = 0;
    int vertexNumber;
    List<Integer>[] adjacencyLists;

    public CriticalRoutersIndexStartWith1(int n, int[][] edges) {
        this.vertexNumber = n;
        adjacencyLists = new ArrayList[n + 1];
        for(int i = 0; i < n + 1; i++) {
            adjacencyLists[i] = new ArrayList<>();
        }

        for(int i = 0; i < edges.length; i++) {
            this.addEdge(edges[i][0], edges[i][1]);
        }
    }

    public void addEdge(int a, int b) {
        adjacencyLists[a].add(b);
        adjacencyLists[b].add(a);
    }

    public List<Integer> findArticulationPoints() {
        LinkedList<Integer> criticalPoints = new LinkedList<>();

        int[] low = new int[vertexNumber + 1];
        int[] parent = new int[vertexNumber + 1];
        Arrays.fill(parent, -1);
        int[] discoverSeq = new int[vertexNumber + 1];
        boolean[] visited = new boolean[vertexNumber+ 1];
        for(int i = 0; i < this.vertexNumber; i++) {
            if(visited[i]) continue;
            dfs(i, criticalPoints, low, parent, discoverSeq, visited);
        }

        return criticalPoints;
    }

    private void dfs(int p, LinkedList<Integer> criticalPoints, int[] low, int[] parent, int[] discoverSeq, boolean[] visited) {
        int children = 0;
        visited[p] = true;
        low[p] = discoverSeq[p] = ++discoverSeqCounter;
        for(int v: adjacencyLists[p]) {
            if(!visited[v]){
                parent[v] = p;
                children++;
                dfs(v, criticalPoints, low, parent, discoverSeq, visited);
                low[p] = Math.min(low[p], low[v]);
                if(parent[p] == -1) {
                    if(children > 1) {
                        criticalPoints.add(p);
                    }
                } else if(low[v] >= discoverSeq[p]) {
                    criticalPoints.add(p);
                }
            } else if(v != parent[p]){
                low[p] = Math.min(low[p], discoverSeq[v]);
            }
        }
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] edges = {{1, 2}, {1, 3}, {3, 4}, {1, 4}, {4, 5}};

        System.out.println(new CriticalRoutersIndexStartWith1(n, edges).findArticulationPoints());
        assertEquals("[4, 1]", new CriticalRoutersIndexStartWith1(n, edges).findArticulationPoints().toString());

        n = 6;
        edges = new int[][]{{1, 2}, {1, 3}, {2, 3}, {2, 4}, {2, 5}, {4, 6}, {5, 6}};

        System.out.println(new CriticalRoutersIndexStartWith1(n, edges).findArticulationPoints());
        assertEquals("[2]", new CriticalRoutersIndexStartWith1(n, edges).findArticulationPoints().toString());

        n = 4;
        edges = new int[][]{{0,1},{1,2},{2,0},{1,3}};
        System.out.println(new CriticalRoutersIndexStartWith1(n, edges).findArticulationPoints());

    }
}
