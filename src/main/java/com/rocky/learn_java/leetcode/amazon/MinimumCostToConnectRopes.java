package com.rocky.learn_java.leetcode.amazon;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class MinimumCostToConnectRopes {

	/**
	 * 
	Given n ropes of different lengths, we need to connect these ropes into one rope. 
	We can connect only 2 ropes at a time. 
	The cost required to connect 2 ropes is equal to sum of their lengths. 
	The length of this connected rope is also equal to the sum of their lengths. 
	This process is repeated until n ropes are connected into a single rope. 
	Find the min possible cost required to connect all ropes.
	
	Example 1:
	
	Input: ropes = [8, 4, 6, 12]
	Output: 58
	Explanation: The optimal way to connect ropes is as follows
	1. Connect the ropes of length 4 and 6 (cost is 10). Ropes after connecting: [8, 10, 12]
	2. Connect the ropes of length 8 and 10 (cost is 18). Ropes after connecting: [18, 12]
	3. Connect the ropes of length 18 and 12 (cost is 30).
	Total cost to connect the ropes is 10 + 18 + 30 = 58
	Example 2:
	
	Input: ropes = [20, 4, 8, 2]
	Output: 54
	Example 3:
	
	Input: ropes = [1, 2, 5, 10, 35, 89]
	Output: 224
	Example 4:
	
	Input: ropes = [2, 2, 3, 3]
	Output: 20
	 */
	
	public int solution(int[] ropes) {
		if(ropes == null || ropes.length == 0) return 0;
		
		
		//Queue<int[]> q = new LinkedList<>();
		PriorityQueue<Integer> priorityQ = new PriorityQueue<>();
		for(int i: ropes) {
			priorityQ.offer(i);
		}
		
		int cost = 0;
		
		while(priorityQ.isEmpty() == false) {
			int oneConnectCost = priorityQ.poll();
			oneConnectCost += priorityQ.poll();
			cost += oneConnectCost;
			if(priorityQ.isEmpty()) {
				break;
			}
			priorityQ.offer(oneConnectCost);
		}
		return cost;
		
	}
	
	public static void main(String[] args) {
		MinimumCostToConnectRopes mctcs = new MinimumCostToConnectRopes();
		int[] sticks1 = {8, 4, 6, 12};
		int target1 = 58;
		
		System.out.println(mctcs.solution(sticks1) == target1);
		int[] sticks2 = {1, 2, 5, 10, 35, 89};
		int target2 = 224;
		
		System.out.println(mctcs.solution(sticks1) == target1);
		int[] sticks3 = {2, 2, 3, 3};
		int target3 = 10;
		
		System.out.println(mctcs.solution(sticks1) == target1);
		
	}
}
