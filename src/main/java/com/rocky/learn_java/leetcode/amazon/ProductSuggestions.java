package com.rocky.learn_java.leetcode.amazon;

import java.util.*;

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
public class ProductSuggestions {

	public static class Trie {
		Trie[] letters;
		PriorityQueue<String> repositoryLexicalQueue;
		boolean wordEnd;

		public Trie() {
			letters = new Trie[26];
			repositoryLexicalQueue = new PriorityQueue<>(Collections.reverseOrder());
			wordEnd = false;
		}
	}

	private static void buildTrie(List<String> repository, Trie root) {
		for (String product : repository) {
			product = product.toLowerCase();
			Trie triePointer = root;
			for (int i = 0; i < product.length(); i++) {
				int c = product.charAt(i) - 'a';
				if (triePointer.letters[c] == null) {
					triePointer.letters[c] = new Trie();
				}
				triePointer = triePointer.letters[c];
				triePointer.repositoryLexicalQueue.add(product);
				if (triePointer.repositoryLexicalQueue.size() > 3) {
					triePointer.repositoryLexicalQueue.poll();
				}
			}
			triePointer.wordEnd = true;
		}
	}

	public static List<List<String>> suggestions(int numProducts, List<String> repository, String customerQuery) {
		Trie root = new Trie();

		buildTrie(repository, root);

		List<List<String>> productSuggestions = new ArrayList<>();
		Trie triePointer = root;

		customerQuery = customerQuery.toLowerCase();
		for (int i = 0; i < customerQuery.length(); i++) {
			int c = customerQuery.charAt(i) - 'a';
			if (triePointer.letters[c] == null) {
				break;
			}
			triePointer = triePointer.letters[c];
			if (i > 0 && triePointer.repositoryLexicalQueue.size() != 0) {
				List<String> suggestions = new ArrayList<>();
				while (triePointer.repositoryLexicalQueue.isEmpty() == false) {
					suggestions.add(triePointer.repositoryLexicalQueue.poll());
				}
				Collections.reverse(suggestions);
				productSuggestions.add(suggestions);
			}
		}
		return productSuggestions;
	}

	public static void main(String[] args) {

		List<String> repository = new ArrayList<>();
		repository.add("mobile");
		repository.add("mOUse");
		repository.add("moNeypot");
		repository.add("monitor");
		repository.add("MOUSEPAD");
		List<List<String>> l = suggestions(5, repository, "MOUse");
		for (List<String> ls : l) {
			System.out.println(Arrays.toString(ls.toArray()));
		}
	}
}
