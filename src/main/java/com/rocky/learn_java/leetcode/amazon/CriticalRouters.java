package com.rocky.learn_java.leetcode.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AWS wants to increase the reliability of their network by upgrading crucial data center routers. Each data center has a single router that is connected to every other data center through a direct link or some other data center.
 *
 * To increase the fault tolerance of the network, AWS wants to identify routers which would result in a disconnected network if they went down and replace then with upgraded versions.
 *
 * Write an algorithm to identify all such routers.
 *
 * Input:
 *
 * The input to the function/method consists of three arguments:
 *
 * numRouters, an integer representing the number of routers in the data center.
 * numLinks, an integer representing the number of links between the pair of routers.
 * Links, the list of pair of integers - A, B representing a link between the routers A and B. The network will be connected.
 * Output:
 *
 * Return a list of integers representing the routers with need to be connected to the network all the time.
 *
 * Example:
 *
 * Input:
 *
 * numRouters = 7
 * numLinks = 7
 * Links = [[0,1], [0, 2], [1, 3], [2, 3], [2, 5], [5, 6], [3,4]]
 *
 * Output:
 *
 * [2, 3, 5]
 *
 * Explanation:
 *
 * On disconnecting router 2, a packet may be routed either to the routers- 0, 1, 3, 4 or the routers - 5, 6, but not to all.
 *
 * On disconnecting router 3, a packet may be routed either to the routers - 0,1,2,5,6 or to the router - 4, but not to all.
 *
 * On disconnecting router 5, a packet may be routed either to the routers - 0,1,2,3,4 or to the router - 6, but not to all.
 *
 * Related problems:
 *
 * Critical Connections
 */
public class CriticalRouters {
    class Graph {
        int n;
        List<Integer>[] adjacencyLists;
        Graph(int n, List<List<Integer>> connections){
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

    public List<Integer> criticalConnections(int n, List<List<Integer>> connections) {
        Graph g = new Graph(n, connections);
        Sequence seq = new Sequence();
        boolean[] visited = new boolean[n]; // "is visited" can be replaced by checking dfsSeq[i] == 0
        int[] low = new int[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        int[] dfsSeq = new int[n];
        List<Integer> results = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            if(!visited[i]) {
                dfsFind(i, g, seq, visited, low, parent, dfsSeq, results);
            }
        }

        return results;
    }

    private void dfsFind(int p, Graph g, Sequence seq, boolean[] visited, int[] low, int[] parent, int[] dfsSeq, List<Integer> results) {
        int children = 0;
        visited[p] = true;
        low[p] = dfsSeq[p] = ++seq.seq;
        for(int adj: g.adjacencyLists[p]) {
            if(!visited[adj]) {
                parent[adj] = p;
                children++;
                dfsFind(adj, g, seq, visited, low, parent, dfsSeq, results);
                low[p] = Math.min(low[adj], low[p]);
                // p is root of DFS tree and has two or more children.
                if(parent[p] == -1 && children > 1) {
                    results.add(p);
                }
                // p is not root and is the low value of one child, Very Important to be clear of these 2 condition
                if(parent[p] != -1 && low[adj] >= dfsSeq[p]) {
                    results.add(p);
                }
            } else if( adj != parent[p]){
                low[p] = Math.min(low[p], dfsSeq[adj]);
            }
        }
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] edges = {{1, 2}, {1, 3}, {3, 4}, {1, 4}, {4, 5}};


        n = 6;
        edges = new int[][]{{1, 2}, {1, 3}, {2, 3}, {2, 4}, {2, 5}, {4, 6}, {5, 6}};


        n = 4;
        edges = new int[][]{{0,1},{1,2},{2,0},{1,3}};
        CriticalRouters solution = new CriticalRouters();
        System.out.println(solution.criticalConnections(n, Arrays.stream(edges).map(edge -> {
            List<Integer> newEdge = new ArrayList<>();
            for (int i : edge) {
                newEdge.add(i);
            }
            return newEdge;
        }).collect(Collectors.toList())));


    }
}
