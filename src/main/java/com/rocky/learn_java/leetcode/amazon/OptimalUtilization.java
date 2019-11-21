package com.rocky.learn_java.leetcode.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OptimalUtilization {
	
	public List<int[]> solution(int[][] a, int[][] b, int target){
		List<int[]> ret = new ArrayList<>();
		Arrays.sort(a, (i, j) -> i[1] - j[1]);
		Arrays.sort(b, (i, j) -> i[1] - j[1]);
		
		int m = a.length;
		int n = b.length;
		
		int left = 0;
		int right = n - 1;
		int maxSum = Integer.MIN_VALUE;
		
		while(left < m && right >= 0) {
			int sum = a[left][1] + b[right][1];
			if(sum > target) {
				right--;
			} else {
				if(maxSum <= sum) {
					if(maxSum < sum) {
						maxSum = sum;
						ret.clear();
					}
					ret.add(new int[] {a[left][0], b[right][0]});
					int index = right - 1;
					while(index>=0 && b[index][1] == b[index + 1][1]) {
						ret.add(new int[]{a[left][0], b[index][0]});
						index--;
					}
				}
				left++;
			}

		}
		return ret;
	}

	public static void main(String ... args) {
		int[][] a = {{1,2},{2,4},{3,6}};
		int[][] b = {{1,2}};
		
		OptimalUtilization ou = new OptimalUtilization();
		List<int[]> ret = ou.solution(a, b, 7);
		ret.stream().forEach(i -> System.out.println(i[0] + "-" + i[1]));
	}
}
