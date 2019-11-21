package com.rocky.learn_java.leetcode.amazon;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// also known as Articulation Point problem
public class CriticalEdgesInGraph {

    class Graph2 {
        int n;
        List<Integer>[] adjacencyLists;
        Graph2(int n, List<List<Integer>> connections){
            this.n = n;
            adjacencyLists = new ArrayList[n];
            for(int i = 0; i < n; i++) {
                adjacencyLists[i] = new ArrayList<Integer>();
            }
            connections.forEach(connection -> adjacencyLists[connection.get(0)].add(connection.get(1)));
            connections.forEach(connection -> adjacencyLists[connection.get(1)].add(connection.get(0)));
        }
    }
    class Sequence {
        int seq = 0;
    }
    public List<List<Integer>> findCriticalEdges(int n, List<List<Integer>> connections) {
        Graph2 g = new Graph2(n, connections);
        Sequence seq = new Sequence();
        boolean[] visited = new boolean[n];
        int[] low = new int[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        int[] dfsSeq = new int[n];
        List<List<Integer>> results = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            if(!visited[i]) {
                dfsFind(i, g, seq, visited, low, parent, dfsSeq, results);
            }
        }
        return results;
    }

    private void dfsFind(int p, Graph2 g, Sequence seq, boolean[] visited, int[] low, int[] parent, int[] dfsSeq, List<List<Integer>> results) {
        int children = 0;
        visited[p] = true;
        low[p] = dfsSeq[p] = ++seq.seq;
        for(int adj: g.adjacencyLists[p]) {
            if(!visited[adj]) {
                parent[adj] = p;
                children++;
                dfsFind(adj, g, seq, visited, low, parent, dfsSeq, results);
                low[p] = Math.min(low[adj], low[p]);

                if(low[adj] > dfsSeq[p]) {
                    results.add(Arrays.asList(p, adj));
                }
            } else if(adj != parent[p]){
                low[p] = Math.min(low[p], dfsSeq[adj]);
            }
        }
    }



    public static void main(String[] args) {
        int n = 5;
        int[][] edges = {{0, 1}, {0, 2}, {2, 3}, {0, 3}, {3, 4}};

        CriticalEdgesInGraph solution = new CriticalEdgesInGraph();
        System.out.println(solution.findCriticalEdges(n, Arrays.stream(edges).map(edge -> {
            List<Integer> newEdge = new ArrayList<>();
            for (int i : edge) {
                newEdge.add(i);
            }
            return newEdge;
        }).collect(Collectors.toList())));
//        assertEquals("[[0, 1], [3, 4]]", new CriticalEdgesInGraph().findCriticalEdges(n, Arrays.stream(edges).map(edge -> {
//            List<Integer> newEdge = new ArrayList<>();
//            for (int i : edge) {
//                newEdge.add(i);
//            }
//            return newEdge;
//        }).collect(Collectors.toList())).toString());

        n = 6;
        edges = new int[][]{{0, 1}, {0, 2}, {1, 2}, {1, 3}, {1, 4}, {3, 5}, {4, 5}};

        System.out.println(new CriticalEdgesInGraph().findCriticalEdges(n, Arrays.stream(edges).map(edge -> {
            List<Integer> newEdge = new ArrayList<>();
            for (int i : edge) {
                newEdge.add(i);
            }
            return newEdge;
        }).collect(Collectors.toList())));
//        assertEquals("[]", new CriticalEdgesInGraph().findCriticalEdges(n, Arrays.stream(edges).map(edge -> {
//            List<Integer> newEdge = new ArrayList<>();
//            for (int i : edge) {
//                newEdge.add(i);
//            }
//            return newEdge;
//        }).collect(Collectors.toList())).toString());
    }
}
