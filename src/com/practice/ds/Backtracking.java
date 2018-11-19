package com.practice.ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Backtracking {

	public static void main(String[] args) {
		int[] array = {1, 1, 2};
		Backtracking bt = new Backtracking();
		System.out.println(bt.uniquePermutations(array));
		
		String str = "abb";
		System.out.print(bt.partitionPalindromes(str));
	}
	
	/**
	 * Given an set, find all possible subsets for the set
	 */
	public List<List<Integer>> allSubsets(int[] array) {
		List<List<Integer>> results = new ArrayList<>();
		Arrays.sort(array);
		allSubsets(results, new ArrayList<Integer>(), array, 0);
		return results;
	}
	
	public void allSubsets(List<List<Integer>> results, List<Integer> temp, int[] array, int start) {
		results.add(new ArrayList<>(temp));
		for(int i = start; i < array.length; i++) {
			temp.add(array[i]);
			allSubsets(results, temp, array, i+1);
			temp.remove(temp.size()-1);
		}
	}
	
	/**
	 * Given a collection that contains duplicates, find all possible
	 * subsets for the collection. The result should not contain
	 * duplicates.
	 */
	public List<List<Integer>> allSubsetsSkipDuplicates(int[] array) {
		List<List<Integer>> results = new ArrayList<>();
		Arrays.sort(array);
		allSubsetsSkipDuplicates(results, new ArrayList<Integer>(), array, 0);
		return results;
	}
	
	public void allSubsetsSkipDuplicates(List<List<Integer>> results, List<Integer> temp, int[] array, int start) {
		results.add(new ArrayList<Integer>());
		for(int i = start; i < array.length; i++) {
			if(i > start && array[i] == array[i-1]) continue; // to skip duplicates
			temp.add(array[i]);
			allSubsetsSkipDuplicates(results, temp, array, start+1);
			temp.remove(temp.size()-1);
		}
	}
	
	
	/**
	 * Given a set that does not contain duplicates, find the different combinations
	 * (reuse allowed) that sum up to a certain target value.
	 * 
	 * Running time complexity is O(2^N)
	 * @param array
	 * @param target
	 * @return
	 */
	public List<List<Integer>> combinationSum(int[] array, int target) {
		List<List<Integer>> results = new ArrayList<>();
		Arrays.sort(array);
		combinationSum(array, results, new ArrayList<>(), target, 0);
		return results;
	}
	
	public void combinationSum(int[] array, List<List<Integer>> results, List<Integer> temp, int remaining, int start) {
		if(remaining < 0) 
			return;
		if(remaining == 0) {
			results.add(new ArrayList<Integer>(temp));
			return;
		}
		for(int i = start; i < array.length; i++) {
			temp.add(array[i]);
			combinationSum(array, results, temp, remaining-array[i], i); // you can add duplicates
			temp.remove(temp.size()-1);
		}
	}
	
	/**
	 * Given a collection that may contain duplicates, find the different combinations
	 * that sum up to a certain target while ensuring that each combination only
	 * contains unique numbers.
	 */
	public List<List<Integer>> combinationSumWithoutDuplicates(int[] array, int target) {
		List<List<Integer>> results = new ArrayList<>();
		Arrays.sort(array);
		combinationSumWithDuplicates(results, new ArrayList<Integer>(), array, target, 0);
		return results;
	}
	
	public void combinationSumWithDuplicates(List<List<Integer>> results, List<Integer> temp, int[] array, int remaining, int start) {
		if(remaining < 0)
			return;
		else if(remaining == 0) {
			results.add(new ArrayList<Integer>(temp));
			return;
		} else {
			for(int i = start; i < array.length; i++) {
				if(i > start && array[i] == array[i-1]) continue;
				temp.add(array[i]);
				combinationSumWithDuplicates(results, temp, array, remaining-array[i], i+1); // i+1 because duplicates are not allowed
				temp.remove(temp.size()-1);
			}
		}
	}
	
	/**
	 * Given a set of integers, find all the valid permutations
	 * of the set
	 */
	public List<List<Integer>> permutations(int[] array) {
		List<List<Integer>> results = new ArrayList<>();
		permutations(results, new ArrayList<Integer>(), array);
		return results;
	}
	
	public void permutations(List<List<Integer>> results, List<Integer> temp, int[] array) {
		if(temp.size() == array.length) {
			results.add(new ArrayList<Integer>(temp));
		} else {
			for(int i = 0; i < array.length; i++) {
				if(temp.contains(array[i])) continue; // skip if element is added
				temp.add(array[i]);
				permutations(results, temp, array);
				temp.remove(temp.size()-1);
			}
		}
	}
	
	/**
	 * Given a collection of numbers, return all unique permutations
	 * of the numbers.
	 */
	public List<List<Integer>> uniquePermutations(int[] array) {
		List<List<Integer>> results = new ArrayList<>();
		boolean[] used = new boolean[array.length];
		Arrays.sort(array);
		uniquePermutations(results, new ArrayList<Integer>(), array, used);
		return results;
	}
	
	public void uniquePermutations(List<List<Integer>> results, List<Integer> temp, int[] array, boolean[] used) {
		if(temp.size() == array.length) {
			results.add(new ArrayList<Integer>(temp));
		} else {
			for(int i = 0; i < array.length; i++) {
				// used[i-1] -> this combination has already been seen 
				// when previous is unused, means  it was used previously and
				// you are starting with the duplicate
				if(used[i] || (i > 0 && array[i] == array[i-1] && !used[i-1])) continue;
				used[i] = true;
				temp.add(array[i]);
				uniquePermutations(results, temp, array, used);
				used[i] = false;
				temp.remove(temp.size()-1);
			}
		}
	}
	
	/**
	 * Given a string, partition the string into all possible palindromes
	 */
	public List<List<String>> partitionPalindromes(String string) {
		List<List<String>> results = new ArrayList<>();
		partitionPalindromes(results, new ArrayList<String>(), string, 0);
		return results;
	}
	
	public void partitionPalindromes(List<List<String>> results, List<String> temp, String string, int start) {
		if(start == string.length()) {
			results.add(new ArrayList<String>(temp));
		} else {
			for(int i = start; i < string.length(); i++) {
				if(isPalindrome(string, start, i)) {
					temp.add(string.substring(start, i+1));
					partitionPalindromes(results, temp, string, i+1);
					temp.remove(temp.size()-1);
				}
			}
		}
	}
	
	private boolean isPalindrome(String str, int low, int high) {
		while(low < high) {
			if(str.charAt(low++) != str.charAt(high--)) return false;
		}
		return true;
	}
	
	/**
	 * Given a target sum, find all possible combinations of k numbers given
	 * that only numbers 1 - 9 can be used and the ccombination should be
	 * unique.
	 */
	public List<List<Integer>> combinationSumKNumbers(int target, int k) {
		List<List<Integer>> results = new ArrayList<>();
		combinationSumKNumbers(results, new ArrayList<Integer>(), target, k, 0);
		return results;
	}
	
	// The first if condition will help to optimize and not go deeper
	// than is necessary
	public void combinationSumKNumbers(List<List<Integer>> results, List<Integer> temp, int remaining, int k, int start) {
		if(remaining < 0 || (temp.size() == k && remaining != 0)) {
			return;
		} else if(temp.size() == k && remaining == 0) {
			results.add(new ArrayList<Integer>(temp));
		} else {
			for(int i = start; i < 10; i++) {
				temp.add(i);
				combinationSumKNumbers(results, temp, remaining-i, k, i+1);
				temp.remove(temp.size()-1);
			}
		}
	}
	
	/**
	 * Given two integers n and k, find all the combinations of size k that can
	 * be formed with numbers from 1...n
	 */
	public List<List<Integer>> combinationsSizeK(int n, int k) {
		List<List<Integer>> results = new ArrayList<>();
		combinationsSizeK(results, new ArrayList<Integer>(), n, k, 1);
		return results;
	}
	
	public void combinationsSizeK(List<List<Integer>> results, List<Integer> temp, int n, int k, int start) {
		if(temp.size() == k) {
			results.add(new ArrayList<Integer>(temp));
		} else {
			for(int i = start; i <= n; i++) {
				temp.add(i);
				combinationsSizeK(results, temp, n, k, start+1);
				temp.remove(temp.size()-1);
			}
		}
	}

}
