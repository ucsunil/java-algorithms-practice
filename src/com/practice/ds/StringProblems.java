package com.practice.ds;

import java.util.HashMap;
import java.util.Map;

public class StringProblems {

	public static void main(String[] args) {
		StringProblems sp = new StringProblems();
		String s1 = "ADOBECODEBANC";
		String s2 = "ABC";
		String minWindow = sp.minimumWindow(s1, s2);
		System.out.println(minWindow);
	}
	
	/**
	 * Implement the strstr() function. Find the first occurrence of
	 * the needle in the haystack.
	 * @param haystack
	 * @param needle
	 * @return
	 */
	public int findNeedleInHayStack(String haystack, String needle) {
		for(int i = 0; ; i++) {
			for(int j = 0; ; j++) {
				if(j == needle.length()) return i;
				if(i+j == haystack.length()) return -1; // means end of string has been reached
				if(needle.charAt(j) != haystack.charAt(i+j)) break;
			}
		}
	}
	
	/**
	 * Minimum window substring
	 * 
	 * Given a string s2, find the minimum window substring in the string s1
	 * that contains all the characters in s2
	 */
	public String minimumWindow(String s1, String s2) {
		int begin = 0, end = 0, length = Integer.MAX_VALUE, head = 0;
		Map<Character, Integer> map = new HashMap<>();
		for(char c : s2.toCharArray()) {
			if(map.containsKey(c)) {
				map.put(c, map.get(c)+1);
			} else {
				map.put(c, 1);
			}
		}
		int counter = s2.length();
		while(end < s1.length()) {
			if(map.containsKey(s1.charAt(end))) {
				if(map.get(s1.charAt(end)) > 0) counter--; //so that no more than necessary is counted
				map.put(s1.charAt(end), map.get(s1.charAt(end))-1);				
			}
			end++;
			while(counter == 0) {
				if(end-begin < length) {
					length = end - begin;
					head = begin;
				}
				if(map.containsKey(s1.charAt(begin))) {
					// we put the number of occurrences back into the
					map.put(s1.charAt(begin), map.get(s1.charAt(begin))+1);
					if(map.get(s1.charAt(begin)) > 0) // implies past this index s2 can no longer be formed; so break and increase range
						counter++;					
				}
				begin++;
			}
		}
		return length == Integer.MAX_VALUE ? "" : s1.substring(head, head+length);
	}
	
	/**
	 * Given two strings s1 and s2, find if s2 contains s1 or a permutation of
	 * s1 as a substring. All characters are lower case.
	 * 
	 * Leetcode 567
	 * 
	 * The solution is to maintain a map over the sliding window. As each character
	 * in s2 comes into the sliding window, decrease the number of occurrences and
	 * as each character leaves the sliding window, increase it.
	 */
	public boolean checkInclusion(String s1, String s2) {
		int length1 = s1.length();
		int length2 = s2.length();
		int[] map = new int[26];
		
		for(int i = 0; i < length1; i++) {
			map[s1.charAt(i) - 'a']++;
			map[s2.charAt(i) - 'a']--;
		}
		if(isAllZeros(map)) return true;
		
		for(int i = length1; i < length2; i++) {
			map[s2.charAt(i) - 'a']--;
			map[s2.charAt(i-length1) - 'a']++;
			if(isAllZeros(map)) return true;
		}
		return false;
	}
	
	private boolean isAllZeros(int[] map) {
		for(int i : map) {
			if(i != 0) return false;
		}
		return true;
	}
	
	/**
	 * Given a string, find the longest substring without repeating
	 * characters
	 */
	public String longestSubstringWithoutRepeatingChars(String input) {
		int current = 0, start = 0, longest = Integer.MIN_VALUE;
		Map<Character, Integer> map = new HashMap<>();
		while(current < input.length()) {
			if(map.containsKey(input.charAt(current))) {
				if(map.size() > longest) {
					longest = map.size();
					start = longest - current;
				}
				current = map.get(input.charAt(current));
				map.clear();
			} else {
				map.put(input.charAt(current), current);
			}
			current++;
		}
		if(map.size() > longest) { // this check is done once current reaches end
			longest = map.size();
			start = longest - current;
		}
		return input.substring(start, start+longest);
	}
	
	/**
	 * Compressed string
	 * 
	 * Given a string compress it
	 * AAAbbbc -> A3B3c1
	 * Aab -> A1a1b1
	 */
	

}
