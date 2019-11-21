package com.rocky.learn_java.leetcode.amazon;

import java.util.Arrays;

import junit.framework.Assert;

/**
 * Given an undirected graph with n nodes labeled 1..n. Some of the nodes are already connected. 
 * The i-th edge connects nodes edges[i][0] and edges[i][1] together. 
 * Your task is to augment this set of edges with additional edges to connect all the nodes. 
 * Find the minimum cost to add new edges between the nodes such that all the nodes are accessible from each other.

	Input:
	
	n, an int representing the total number of nodes.
	edges, a list of integer pair representing the nodes already connected by an edge.
	newEdges, a list where each element is a triplet representing the pair of nodes between which an edge can be added and the cost of addition, respectively (e.g. [1, 2, 5] means to add an edge between node 1 and 2, the cost would be 5).
	Example 1:
	
	Input: n = 6, edges = [[1, 4], [4, 5], [2, 3]], newEdges = [[1, 2, 5], [1, 3, 10], [1, 6, 2], [5, 6, 5]]
	Output: 7
	Explanation:
	There are 3 connected components [1, 4, 5], [2, 3] and [6].
	We can connect these components into a single component by connecting node 1 to node 2 and node 1 to node 6 at a minimum cost of 5 + 2 = 7.
 *
 */

public class MinimumSpanningTree {
	
	public int minCostToConnect(int n, int[][] edges, int[][] newEdges) {
		int cost = 0;
		
		UnionFind uf = new UnionFind(n + 1);
		
		for(int i = 0; i < edges.length; i++) {
			uf.union(edges[i][0], edges[i][1]);
		}
		
		Arrays.sort(newEdges, (a, b) -> a[2] - b[2]);
		
		for(int i = 0; i< newEdges.length && uf.count() > 2; i++) {
			if(!uf.connected(newEdges[i][0], newEdges[i][1])) {
				uf.union(newEdges[i][0], newEdges[i][1]);
				cost += newEdges[i][2];
			}
		}
		System.out.println(Arrays.toString(uf.parent));
		System.out.println("--------------------------------------------------------");
		//System.out.println(Arrays.toString(uf.rank));
		return cost;
	}
	
	public static void main(String[] args) {
		int n = 6;
		int[][] edges = {{1, 4}, {4, 5}, {2, 3}};
		int[][] newEdges = {{1, 2, 5}, {1, 3, 10}, {1, 6, 2}, {5, 6, 5}};
		MinimumSpanningTree mst = new MinimumSpanningTree();
		int cost = mst.minCostToConnect(n, edges, newEdges);
		Assert.assertEquals(7, cost);
		System.out.println(cost);
	}
	
	
	//带路径压缩的并查集, union-find实现，压缩后find和union操作的时间复杂度都是于O(1)，缺点是无法保存点到点的路径
	private static class UnionFind {
		int[] parent;
//		int[] rank;
		int groupCount;
		
		public UnionFind(int n) {
			this.groupCount = n;
			parent = new int[n];
//			rank = new int[n];
			for(int i = 0; i < n; i++) {
				parent[i] = i;
//				rank[i] = 0;
			}
		}
		
		public int find(int p) {
			int pRoot = parent[p];
			while(pRoot != parent[pRoot]) {
				parent[pRoot] = parent[parent[pRoot]];
				pRoot = parent[pRoot];
				
			}
			return pRoot;
		}
		
		public boolean connected(int p, int q) {
			return find(p) == find(q);
		}
		
		public void union(int p, int q) {
			if(connected(p, q)) {
				return;
			}
			
			int pRoot = find(p);
			int qRoot = find(q);
			
			if(p>q) {
				parent[pRoot] = parent[qRoot];
			} else {
				parent[qRoot] = parent[pRoot];
//				if(rank[pRoot] == rank[qRoot]) {
//					rank[pRoot]++;
//				}
			}
			groupCount--;
		}
		
		public int count() {
			return this.groupCount;
		}
	}
}
