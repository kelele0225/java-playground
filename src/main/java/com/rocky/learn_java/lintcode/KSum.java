package com.rocky.learn_java.lintcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KSum {
    /**
     * @param A: An integer array
     * @param k: A positive integer (k <= length(A))
     * @param target: An integer
     * @return: An integer
     */
    public int kSum(int[] A, int k, int target) {
        // write your code here
        if(A == null || A.length < k) return 0;
        
        Arrays.sort(A);
        List<Integer> counter = new ArrayList<>();
        
        kSumDfs(A, k, target, 0, counter);
        
        return counter.size();
    }
    
    private void kSumDfs(int[] A, int k, int target, int start, List<Integer> counter){
        if(k < 0 || target < 0) return;
        if(k == 0 && target == 0) {
            counter.add(1);
            return;
        }
        
        for(int i = start; i < A.length; i++){
            kSumDfs(A, k - 1, target - A[i], i + 1, counter);
        }
    }
	
    public static void main(String[] args) {
    	KSum ksum = new KSum();
    	int[] input = {1,3,4,5,6,8,11,14,16,19,20,23,25,28,30,33,34,36,39,41,43,45,46,48,51,53,55,56,58,59,60};
    	System.out.println(ksum.kSum(input, 26, 811));
    }
}
