package com.practice.ds;

public class StringsGridDP {

	public static void main(String[] args) {
		StringsGridDP sgdp = new StringsGridDP();
		String str = "bbbab";
		int longestPalindromicSubsequence = sgdp.longestPalindromicSubsequence(str);
		System.out.println("Longest palindromic subsequence: " + longestPalindromicSubsequence);
	}
	
	/**
	 * Bottom up DP solution
	 * 
	 * State transition:
	 * dp[i][j] = 1 for i == j
	 * dp[i][j] = 2 + dp[i+1][j-1] if str[i] == str[j]
	 * dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]) for all other cases
	 * 
	 * @param str
	 * @return
	 */
	public int longestPalindromicSubsequence(String str) {
		int[][] dp = new int[str.length()][str.length()];
		
		for(int i = str.length()-1; i >= 0; i--) {
			for(int j = i; j < str.length(); j++) {
				if(str.charAt(i) == str.charAt(j)) {
					if(i == j) {
						dp[i][j] = 1;
					} else {
						dp[i][j] = 2 + dp[i+1][j-1];
					}
				} else {
					dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
				}
			}
		}
		return dp[0][str.length()-1];
	}
	
	/**
	 * Longest palindromic substring
	 */
	public String longestPalindromicSubstring(String str) {
		String result = "";
		boolean[][] dp = new boolean[str.length()][str.length()];
		
		for(int i = str.length()-1; i >= 0; i--) {
			for(int j = i; j < str.length(); j++) {
				dp[i][j] = (str.charAt(i) == str.charAt(j)) && (j - i < 3 || dp[i+1][j-1]);
				if(result.length() < (j-i+1)) {
					result = str.substring(i, j+1);
				}
			}
		}
		return result;
	}
	
	/**
	 * Interleaving Strings
	 * 
	 * Given a three strings, find if the third string is formed by the interleaving
	 * of the first two strings.
	 */
	public boolean isInterleavingString(String s1, String s2, String s3) {
		if(s3.length() != s1.length() + s2.length()) return false;
		
		boolean[][] dp = new boolean[s1.length()+1][s2.length()+1];
		dp[0][0] = true;
		
		for(int i = 1; i <= s1.length(); i++) {
			dp[i][0] = dp[i-1][0] && s1.charAt(i-1) == s3.charAt(i-1);
		}
		
		for(int j = 1; j <= s2.length(); j++) {
			dp[0][j] = dp[0][j-1] && s2.charAt(j-1) == s3.charAt(j-1);
		}
		
		for(int i = 1; i <= s1.length(); i++) {
			for(int j = 1; j <= s2.length(); j++) {
				dp[i][j] = (dp[i-1][j] && s1.charAt(i-1) == s3.charAt(i+j-1)) || (dp[i][j-1] && s2.charAt(j-1) == s3.charAt(i+j-1));
			}
		}
		return dp[s1.length()][s2.length()];
	}
	
	/**
	 * Edit Distance
	 * 
	 * Given two strings s1 and s2, find the number of operations it takes
	 * to convert s1 to s2.
	 * 
	 * The corner case is easy to identify ie convert string to empty string
	 * dp[i][0] = i
	 * dp[0][j] = j
	 * 
	 * If word1[i] == word2[j], then dp[i][j] = dp[i-1][j-1]
	 * 
	 * There are three possibilities in other cases:
	 * 1. dp[i][j] = dp[i - 1][j - 1] + 1 (for replacement)
	 * 2. dp[i][j] = dp[i - 1][j] + 1 (for deletion)
	 * 3. dp[i][j] = dp[i][j - 1] + 1 (for insertion)
	 */
	public int minimumEditDistance(String word1, String word2) {
		int[][] dp = new int[word1.length()+1][word2.length()+1];
		
		for(int i = 1; i <= word1.length(); i++) 
			dp[i][0] = i;
		
		for(int j = 1; j <= word2.length(); j++)
			dp[0][j] = j;
		
		for(int i = 1; i <= word1.length(); i++) {
			for(int j = 1; j <= word2.length(); j++) {
				if(word1.charAt(i-1) == word2.charAt(j-1))
					dp[i][j] = dp[i-1][j-1];
				else
					dp[i][j] = 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
			}
		}
		
		return dp[word1.length()][word2.length()];
	}
	
	/**
	 * Given an input string and a pattern, implement wildcard pattern matching for
	 * '?' and '*'
	 */
	public boolean patternMatching(String input, String pattern) {
		boolean[][] dp = new boolean[input.length()+1][pattern.length()+1];
		dp[0][0] = true;
		for(int j = 1; j <= pattern.length(); j++)
			dp[0][j] = dp[0][j-1] && pattern.charAt(j-1) == '*';
		
		for(int i = 1; i <= input.length(); i++) {
			for(int j = 1; j <= pattern.length(); j++) {
				if(input.charAt(i-1) == pattern.charAt(j-1) || pattern.charAt(j-1) == '?') {
					dp[i][j] = dp[i-1][j-1];
				} else if(pattern.charAt(j-1) == '*') {
					dp[i][j] = dp[i-1][j] || dp[i][j-1]; // match with star or match without start
				}
			}
		}
		return dp[input.length()][pattern.length()];
	}
}
