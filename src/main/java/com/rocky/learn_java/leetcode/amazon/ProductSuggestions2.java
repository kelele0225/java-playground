package com.rocky.learn_java.leetcode.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Description: Implement a function to return product suggestions using
 * products from a product repository after each character is typed by the
 * customer in the search bar. If there are more than THREE acceptable products,
 * return the product name that is first in the alphabetical order. Only return
 * product suggestions after the customer has entered two characters. Product
 * suggestions must start with the characters already typed. Both the repository
 * and the customer query should be compared in a CASE-INSENSITIVE way.
 * 
 * Input: The input to the method/function consist of three arguments:
 * 
 * numProducts, an integer representing the number of various products in
 * Amazon's product repository; repository, a list of unique strings
 * representing the various products in Amazon's product repository;
 * customerQuery, a string representing the full search query of the customer.
 * Output: Return a list of a list of strings, where each list represents the
 * product suggestions made by the system as the customer types each character
 * of the customerQuery. Assume the customer types characters in order without
 * deleting/removing any characters.
 * 
 * Example: Input: numProducts = 5 repository = ["mobile", "mouse", "moneypot",
 * "monitor", "mousepad"] customerQuery = "mouse"
 * 
 * Output: [["mobile", "moneypot", "monitor"], ["mouse", "mousepad"], ["mouse",
 * "mousepad"], ["mouse", "mousepad"]]
 * 
 * Explanation: The chain of words that will generate in the search box will be
 * mo, mou, mous and mouse, and each line from output shows the suggestions of
 * "mo", "mou", "mous" and "mouse", respectively in each line. For the
 * suggestions that are generated for "mo", the matches that will be generated
 * are: ["mobile", "mouse", "moneypot", "monitor", "mousepad"]. Alphabetically,
 * they will be reordered to ["mobile", "moneypot", "monitor", "mouse",
 * "mousepad"]. Thus, the suggestions are ["mobile", "moneypot", "monitor"]
 * 
 * @author wanglei
 *
 */
public class ProductSuggestions2 {

	class Trie {
		HashMap<Character, Trie> childs;
		PriorityQueue<String> repoQueue;
		Trie() {
			this.childs = new HashMap<>();
			this.repoQueue = new PriorityQueue<>((a, b) -> b.compareTo(a));
		}

		PriorityQueue<String> search(String str) {
			if(str == null || str.length() ==0) {
				return null;
			}
			Trie pointer = this;
			for(char c: str.toCharArray()) {
				Trie cur = pointer.childs.get(c);

				if(cur == null) {
					return null;
				}
				pointer = cur;
			}
			return pointer.repoQueue;
		}
	}

	private void buildTrie(List<String> repository, Trie root) {
		repository.forEach(repo -> {
			repo = repo.toLowerCase();
			Trie pointer = root;
			for(char c: repo.toCharArray()) {
				if(pointer.childs.get(c) == null) {
					pointer.childs.put(c, new Trie());
				}
				Trie next = pointer.childs.get(c);
				next.repoQueue.offer(repo);
				if(next.repoQueue.size() > 3) {
					next.repoQueue.poll();
				}
				pointer = next;
			}
		});
	}

	public List<List<String>> suggestions(int numProducts, List<String> repository, String customerQuery) {
		Trie root = new Trie();
		this.buildTrie(repository, root);
		List<List<String>> suggestions = new ArrayList<>();
		customerQuery = customerQuery.toLowerCase();
		for(int i = 2; i <= customerQuery.length(); i++) {
			PriorityQueue<String> repoQueue = root.search(customerQuery.substring(0, i));
			if(repoQueue != null) {
				PriorityQueue<String> repoQueueCopy = new PriorityQueue<>(repoQueue);
				List<String> suggestion = new ArrayList<>();
				while(repoQueueCopy.isEmpty() == false) {
					suggestion.add(repoQueueCopy.poll());
				}
				Collections.reverse(suggestion);
				suggestions.add(suggestion);
			}
		}
		return suggestions;
	}

	public static void main(String[] args) {

		List<String> repository = new ArrayList<>();
		repository.add("mobile");
		repository.add("mOUse");
		repository.add("moNeypot");
		repository.add("monitor");
		repository.add("MOUSEPAD");
		List<List<String>> l = new ProductSuggestions2().suggestions(5, repository, "MOUse");
		for (List<String> ls : l) {
			System.out.println(Arrays.toString(ls.toArray()));
		}
	}
}
