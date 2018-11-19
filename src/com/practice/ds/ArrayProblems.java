package com.practice.ds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayProblems {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Given an array of integers, find the indices of any two
	 * integers that sum up to the given target
	 */
	public int[] twoSumIndices(int[] array, int target) {
		int[] result = new int[2];
		Map<Integer, Integer> map = new HashMap<>();
		for(int i = 0; i < array.length; i++) {
			if(map.containsKey(target - array[i])) {
				result[0] = i;
				result[1] = map.get(target - array[i]);
				return result;
			}
			map.put(array[i], i);
		}
		return result;
	}
	
	/**
	 * Given an array that contains a monotonically increasing and
	 * then decreasing sequence identify the index of the largest
	 * element.
	 * You can assume there are no duplicates in the array
	 */
	public int findMaxValueIndex(int[] values) {
		return findMaxValueIndex(values, 0, values.length-1);
	}
	
	public int findMaxValueIndex(int[] values, int start, int end) {
		if(start >= end) return start;
		int midIndex = (start + end)/2;
		if(values[midIndex] < values[midIndex+1])
			return findMaxValueIndex(values, midIndex+1, end);
		else 
			return findMaxValueIndex(values, start, midIndex-1);	
	}
	
	/**
	 * Same problem as before but without using recursion
	 */
	public int findMaxValueIndex02(int[] values) {
		int start = 0;
		int end = values.length;
		int midIndex = (start + end)/2;
		while(start < end) {
			if(values[midIndex] < values[midIndex+1]) {
				start = midIndex + 1;
			} else {
				end = midIndex - 1;
			}
			midIndex = (start + end)/2;
		}
		return midIndex;
	}
	
	/**
	 * Given an unsorted array, find length of the longest consecutive increasing sequence
	 * 
	 * The trick is to keep track of the sequence length and store that in the boundary 
	 * points of the sequence
	 */
	public int lengthOfLongestConsecutiveIncreasingSequence(int[] nums) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int result = 0;
		for(int num : nums) {
			if(!map.containsKey(num)) {
				int left = map.get(num-1) == null ? 0 : map.get(num-1);
				int right = map.get(num+1) == null ? 0 : map.get(num+1);
				int total = left + right + 1;
				// insert the value for num
				map.put(num, total);
				result = Math.max(total, result);
				// now update the edges
				map.put(num-left, total);
				map.put(num+right, total);
			} else {
				continue; // for duplicates
			}
		}
		return result;
	}
	
	/**
	 * Count of numbers smaller after current index
	 * Leetcode - 315
	 * 
	 * Given an array of integers, transform it to an array such
	 * that each index in the original array now contains the
	 * number of smaller elements after this index in the original
	 * array.
	 */
	private static class TreeNode {
		int value;
		int duplicates = 1;
		int count = 0; // count of nodes smaller than this node
		TreeNode(int value) {
			this.value = value;
		}
		TreeNode left, right;
	}
	
	public Integer[] countOfSmallerNumbersAfterSelf(int[] nums) {
		Integer[] result = new Integer[nums.length];
		TreeNode root = null;
		for(int i = nums.length-1; i >= 0; i--) {
			root = insertTreeNode(root, nums[i], result, i, 0);
		}
		return result;
	}
	
	// Idea is to construct a BST with the node tracking the number of smaller elements 
	// coming after it
	public TreeNode insertTreeNode(TreeNode root, int value, Integer[] result, int index, int smaller) {
		if(root == null) {
			root = new TreeNode(value);
			result[index] = smaller;
		} else if(root.value == value) {
			root.duplicates++;
			result[index] = smaller + root.count;
		} else if(root.value > value) { // need to set left node; increment current node's count by 1
			root.count++;
			root.left = insertTreeNode(root.left, value, result, index, smaller);
		}else { // need to set right node
			root.right = insertTreeNode(root.right, value, result, index, smaller + root.duplicates + root.count);
		}
		return root;
	}
	
}
