package com.rocky.learn_java.leetcode.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopNCompetitors {
	public static void main(String[] args) {
        System.out.println("Hello World!");
        
        int numCompetitors = 6;
        int topNCompetitors = 10;
        String[] competitors = {"newshop", "shopnow", "afshion", "fashionbeats", "mymarket", "tcellular"};
        int numReviews = 6;
        String[] reviews = {"newshop is afshion providing good services in the city; everyone should use newshop", "best services by newshop", "fashionbeats has great services in the city", "i am proud to have fashionbeats", "mymarket has awesome services", "Thanks Newshop for the quick delivery afshion"};

        /*
        intuition: Top N frequently used words
        - store the competitors into map, along with their frequent count
        - loop through reviews
            - convert the review to lowercase, and split by space
            - if a word is not a competitor then avoid
            - if a word is being used already for a review then avoid
            - else increase the count of the competitor
        - Create a PriorityQueue to find the N top elements, and provided logic to sort
        - Create an array, and fill up with the N top elements
        */
                
        List<String> result = new TopNCompetitors().getTopCompetitors(numCompetitors, topNCompetitors, competitors, numReviews, reviews);
        //Assert.assertEquals("[newshop, fashionbeats]", result.toString());
        System.out.println(result);
    }
	
	public List<String> getTopCompetitors(int numCompetitors, int topNCompetitors
            , String[] competitors, int numReviews, String[] reviews) {
		Map<String, Integer> reviewCountMap = new HashMap<>();
		this.buildReviewCountMap(numCompetitors, competitors, reviews, reviewCountMap);

		List<String> ans = new ArrayList<>();
		//自己实现MinHeap
//		MinHeap<Node> minHeap = new MinHeap<>(topNCompetitors);
//		for(Map.Entry<String, Integer> entry: reviewCountMap.entrySet()) {
//			minHeap.offer(new Node(entry.getKey(), entry.getValue()));
//		}
//		for(int i = minHeap.size - 1; i >= 0; i--) {
//			ans.add(((Node)minHeap.heap[i]).competitor);
//		}
		
		//use PriorityQueue as minheap
		PriorityQueue<Map.Entry<String, Integer>> queue = new PriorityQueue<>((a, b) -> 
			a.getValue() == b.getValue() ? b.getKey().compareTo(a.getKey()) : a.getValue() - b.getValue()
		);

		// add competitor <-> reviewCount pair into minheap, and poll the heap peek while heap size > N
		for(Map.Entry<String, Integer> entry: reviewCountMap.entrySet()) {
			queue.offer(entry);
			if(queue.size() > topNCompetitors) {
				queue.poll();
			}
		}
		
        for(int i=topNCompetitors-1; i>=0 && !queue.isEmpty(); i--) {
            Map.Entry<String, Integer> entry = queue.poll();
            ans.add(entry.getKey());
        }
        
        
        Collections.reverse(ans);
		
		return ans;
	}
	
	private void buildReviewCountMap(int numCompetitors, String[] competitors, String[] reviews, Map<String, Integer> reviewCountMap) {
		Arrays.stream(competitors).forEach(compeitor -> reviewCountMap.put(compeitor.toLowerCase(), 0));
		
		Arrays.stream(reviews).forEach(review -> {
			for(String word: review.toLowerCase().split(" ")) {
				// 用hashmap containsKey的做法是一个优化，不需要再遍历每一个competitor去做字符比较，时间复杂度从O（L*M)降到了O（L） L是review长度，M是competitor个数
				if(reviewCountMap.containsKey(word)) { 
					reviewCountMap.put(word, reviewCountMap.get(word) + 1);
					break;
				}
			}
		});
		
	}
	
	private static class Node implements Comparable<Node> {
		String competitor;
		int reviews;
		public Node(String competitor, int reviews) {
			this.competitor = competitor;
			this.reviews = reviews;
		}
		
		public int compareTo(Node b) {
			if(this.reviews == b.reviews) {
				return b.competitor.compareTo(competitor);
			}
			return this.reviews - b.reviews;
		}
	}
	
	private static class MinHeap<E extends Comparable> {
		int size;
		int capacity;
		Object[] heap;
		public MinHeap(int capacity) {
			this.size = 0;
			this.capacity = capacity;
			heap = new Object[capacity];
		}
		
		private void offer(E e) {
			if(size == capacity) {
				heap[0] = e;
				shiftDown(0);
			} else {
				heap[size] = e;
				shiftUp(size);
				size++;
			}
		}
		
		private void shiftDown(int index) {
			if(index * 2 + 1 >= size) {
				return;
			}
			int minChildIndex = index * 2 + 1;
			if(index * 2 + 2 < size) {
				minChildIndex = ((E)heap[index * 2 + 1]).compareTo((E)heap[index * 2 + 2]) < 0 ? index * 2 + 1 : index * 2 + 2;
			}
			E minChild = (E)heap[minChildIndex];
			E self = (E)heap[index];
			if(self.compareTo(minChild) > 0) {
				heap[index] = minChild;
				heap[minChildIndex] = self;
				this.shiftDown(minChildIndex);
			}
		}
		
		private void shiftUp(int index) {
			if(index == 0) return;
			int parentIndex = (index - 1) / 2;
			E parent = (E)heap[parentIndex];
			E self = (E)heap[index];
			if(self.compareTo(parent) < 0) {
				heap[parentIndex] = self;
				heap[index] = parent;
				shiftUp(parentIndex);
			}
			
		}
	}
}
