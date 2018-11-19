package com.practice.ds;

public class GridsDP {

	public static void main(String[] args) {
		GridsDP gdp = new GridsDP();
		int[] inums = {3, 1, 5, 8};
		int result = gdp.burstBalloons(inums);
		System.out.println("Max coins from bursting balloons: " + result);
	}
	
	/**
	 * Classic 0/1 knapsack problem. Given a list of weights and values,
	 * find the maximum value that you can fill in a knapsack of certain
	 * weight capacity.
	 * 
	 * Basic idea is to minimize weight while maximizing value
	 * @param wt
	 * @param vals
	 * @return
	 */
	public int knapsackProblem(int[] wt, int[] vals, int capacity) {
		int[][] dp = new int[wt.length+1][capacity+1];
		
		for(int i = 0; i <= wt.length; i++) {
			for(int j = 0; j <= capacity; j++) {
				if(i == 0 || j == 0) dp[i][j] = 0;
				
				if(j >= wt[i-1]) {
					dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-wt[i-1]] + vals[i-1]);
				} else {
					dp[i][j] = dp[i-1][j];
				}
			}
		}
		return dp[wt.length][capacity];
	}
	
	/**
	 * Burst Balloons
	 * 
	 * Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on 
	 * it represented by array nums. You are asked to burst all the balloons. If the you burst 
	 * balloon i you will get nums[left] * nums[i] * nums[right] coins. Here left and right 
	 * are adjacent indices of i. After the burst, the left and right then becomes adjacent.
	 * 
	 * Find the maximum coins you can collect by bursting the balloons wisely.
	 */
	public int burstBalloons(int[] inums) {
		int x = inums.length + 2;
		int[] nums = new int[x];
		nums[0] = nums[x-1] = 1; // assign the balloons to the end
		
		for(int i = 0; i < inums.length; i++) {
			nums[i+1] = inums[i];
		}
		int[][] dp = new int[x][x];
		
		for(int k = 2; k < x; k++) { // k is (size-1) of the array being considered
			for(int left = 0; left < x-k; left++) { // left edge of the subproblem
				int right = left + k; // right edge of the subproblem
				for(int i = left+1; i < right; i++) { // i is the balloon being burst
					dp[left][right] = Math.max(dp[left][right], nums[left]*nums[i]*nums[right] + dp[left][i] + dp[i][right]);
				}
			}
		}
		return dp[0][x-1];
	}
	
	/**
	 * Given a robot is in the top left corner of a grid, find the number
	 * of unique possible paths to the bottom right of the grid assuming
	 * the robot can only move right or down
	 */
	public int uniquePaths(int[][] grid) {
		int[][] dp = new int[grid.length][grid[0].length];
		for(int i = 0; i < grid.length; i++) {
			dp[i][0] = 1; // left column
		}
		for(int j = 0; j < grid[0].length; j++) {
			dp[0][j] = 1; // top row
		}
		for(int i = 1; i < grid.length; i++) {
			for(int j = 1; j < grid[0].length; j++) {
				dp[i][j] = dp[i-1][j] + dp[i][j-1];
			}
		}
		return dp[grid.length-1][grid[0].length-1];
	}

}
