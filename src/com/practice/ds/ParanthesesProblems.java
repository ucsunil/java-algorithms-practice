package com.practice.ds;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParanthesesProblems {

	public static void main(String[] args) {
		ParanthesesProblems pp = new ParanthesesProblems();
		String result = pp.stripParantheses(")()())");
		System.out.println(result);
		
		List<String> results = pp.removeInvalidParentheses(")()())");
		System.out.println(results);
		
		results = pp.getAllValidParanthesesStrings(")()())");
		System.out.println(results);
		
	}
	
	/**
	 * Given a string containing just the characters '(', ')', '{', '}', 
	 * '[' and ']', determine if the input string is valid.
	 * 
	 * Example:
	 * Input: "([)]"
	 * Output: false
	 * 
	 * Example:
	 * Input: "{[]}"
	 * Output: true
	 * 
	 * Example:
	 * Input: "()[]{}"
	 * Output: true
	 */
	public boolean isValidInput(String string) {
		Stack<Character> stack = new Stack<>();
		for(char s : string.toCharArray()) {
			if(s == '(')
				stack.push(')');
			else if(s == '[')
				stack.push('{');
			else if(s == ']') 
				stack.push('}');
			else if(stack.isEmpty() || stack.pop() != s) 
				return false;
		}
		return stack.isEmpty();
	}
	
	/**
	 * Given a number n, generate a string containing n pairs of
	 * parantheses.
	 */
	public List<String> generateParanthesesPairs(int n) {
		List<String> results = new ArrayList<String>();
		generateParanthesesPairs(results, n, "", 0, 0);
		return results;
	}
	
	public void generateParanthesesPairs(List<String> results, int n, String temp, int open, int close) {
		if(temp.length() == n*2) {
			results.add(temp);
			return;
		}
		if(open < n) {
			generateParanthesesPairs(results, n, temp+'(', open+1, close);
		}
		if(close < n) {
			generateParanthesesPairs(results, n, temp+')', open, close);
		}
	}
	
	/**
	 * Strip invalid parantheses
	 * 
	 * Given a string contianing parantheses, strip away the invalid parantheses
	 * so that the result string is a valid parantheses string. If multiple
	 * matches are possible just return one
	 */
	public String stripParantheses(String input) {
		return stripParantheses(input, 0, new char[] {'(', ')'});
	}
	
	public String stripParantheses(String input, int start_i, char[] parens) {
		int stack = 0;
		for(int i = start_i; i < input.length(); i++) {
			if(input.charAt(i) == parens[0]) stack++;
			if(input.charAt(i) == parens[1]) stack--;
			if(stack >= 0) continue;
			return stripParantheses(input.substring(0, i) + input.substring(i+1, input.length()), i, parens);
		}
		String reversed = new StringBuilder(input).reverse().toString();
		if(parens[0] == '(')
			return stripParantheses(reversed, 0, new char[] {')', '('});
		else 
			return reversed;
	}
	
	/**
	 * Remove invalid parantheses
	 * 
	 * Remove the minimum number of invalid parentheses in order to make the 
	 * input string valid. Return all possible results.
	 * Note: The input string may contain letters other than the parentheses '(' and ')'
	 */
	public List<String> removeInvalidParentheses(String input) {
		List<String> results = new ArrayList<>();
		removeInvalidParantheses(input, results, 0, 0, new char[] {'(', ')'});
		return results;
	}
	
	public void removeInvalidParantheses(String input, List<String> results, int last_i, int last_j, char[] parens) {
		int stack = 0;
		for(int i = last_i; i < input.length(); i++) {
			if(input.charAt(i) == parens[0]) stack++;
			if(input.charAt(i) == parens[1]) stack--;
			if(stack >= 0) continue;
			for(int j = last_j; j <= i; j++) {
				if(input.charAt(j) == parens[1] && (j == last_j || input.charAt(j-1) != parens[1]))
					removeInvalidParantheses(input.substring(0, j) + input.substring(j+1), results, i, j, parens);
			}
			return; // because all unnecessary parens[1] has been stripped
		}
		String reversed = new StringBuilder(input).reverse().toString();
		if(parens[0] == '(') { // means left to right is done
			removeInvalidParantheses(reversed, results, 0, 0, new char[] {')', '('});
		} else {
			results.add(reversed);
		}
	}
	
	/**
	 * Longest Valid Parantheses
	 * 
	 * Find the length of the longest substring that forms the longest valid parantheses
	 * string
	 */
	public int longestValidParantheses(String input) {
		Stack<Integer> stack = new Stack<Integer>();
		char[] array = input.toCharArray();
		
		for(int i = 0; i < array.length; i++) {
			if(array[i] == '(') {
				stack.push(i);
			} else {
				if(stack.isEmpty()) 
					stack.push(i);
				else if(input.charAt(stack.peek()) == ')')
					stack.push(i);
				else
					stack.pop();
			}
		}
		// Now stack only contains unmatched characters
		if(stack.isEmpty()) return input.length();
		int longest = 0, a = input.length();
		while(!stack.isEmpty()) {
			int b = stack.pop();
			longest = Math.max(longest, a - b - 1);
			a = b;
		}
		longest = Math.max(longest, a);
		return longest;
	}
	
	/**
	 * Get all valid parantheses strings
	 */
	public List<String> getAllValidParanthesesStrings(String input) {
		List<String> results = new ArrayList<String>();
		getAllValidParanthesesStrings(results, input, 0, new char[] {'(', ')'});
		return results;
	}
	
	public void getAllValidParanthesesStrings(List<String> results, String input, int start_i, char[] parens) {
		int stack = 0;
		for(int i = start_i; i < input.length(); i++) {
			if(input.charAt(i) == parens[0]) stack++;
			if(input.charAt(i) == parens[1]) stack--;
			if(stack > 0) 
				continue;
			else if(stack == 0) {
				if(parens[0] == '(')
					results.add(input.substring(0, i+1));
				else {
					String toInsert = new StringBuilder(input.substring(0, i+1)).reverse().toString();
					if(!results.contains(toInsert)) results.add(toInsert);
				}
				continue;
			} else {
				getAllValidParanthesesStrings(results, input.substring(0, i) + input.substring(i+1), i, parens);
				return;
			}
		}
		if(parens[0] == '(') {
			String reversed = new StringBuilder(input).reverse().toString();
			getAllValidParanthesesStrings(results, reversed, 0, new char[] {')', '('});
		}
	}
	
	/**
	 * Given a string containing open and close parantheses, find the minimum
	 * number of either open and or close paratheses that should be added to 
	 * make the parantheses pairs valid
	 * 
	 * )( -> 2
	 * )) -> 2
	 * () -> 0
	 * (() -> 1
	 */
	public int minimumNumberOfParens(String input) {
		Stack<Character> stack = new Stack<>();
		int count = 0;
		for(char c : input.toCharArray()) {
			if(c == '(') {
				stack.push(c);
			} else {
				if(!stack.isEmpty())
					stack.pop();
				else count++;
			}
		}
		return count + stack.size();
	}
	
}
