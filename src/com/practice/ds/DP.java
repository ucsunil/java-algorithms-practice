package com.practice.ds;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class DP {

	public static void main(String[] args) {
		DP dp = new DP();
		int[] coins = {1, 2, 5};
		System.out.println("Min. coins to make 11: " + dp.coinChangeMinCoins(coins, 11));
		System.out.println("Min. coins to make 11 (method 02): " + dp.coinChangeMinCoins02(coins, 11));
		
		int[] nums = {1, 2, 5};
		System.out.println("Combinations to make 3 (expected = 3): " + dp.combinationsSumTopDown(nums, 3));
		System.out.println("Combinations to make 3 (expected = 3): " + dp.combinationsSumBottomUp(nums, 3));
		
		System.out.println("Possible combinations to make 3 (expected = 2): " + dp.possibleCoinCombinations(nums, 3));
		
		nums = new int[]{2, 4, 6};
		System.out.println("Different ways to make 6 (expected = 2): " + dp.targetSum(nums, 6));
	}

	/**
	 * Given an array (no duplicates) and a target sum, find the total number of combinations
	 * that sum up to the given target. The output combinations may contain duplicates and
	 * different sequences are counted as different combinations. So [1, 1, 3] and [1, 3, 1]
	 * are two possible combinations.
	 */
	public int combinationsSumTopDown(int[] nums, int target) {
		int[] dp = new int[target+1];
		Arrays.fill(dp, -1);
		dp[0] = 1; // empty combination
		return combinationsSunTopDown(nums, target, dp);
	}
	
	public int combinationsSunTopDown(int[] nums, int target, int[] dp) {
		if(dp[target] != -1) {
			return dp[target]; //already calculated
		}
		
		int result = 0;
		for(int i = 0; i < nums.length; i++) {
			if(target >= nums[i]) {
				result = result + combinationsSunTopDown(nums, target-nums[i], dp);
			}
		}
		dp[target] = result;
		return result;
	}
	
	/**
	 * Same combination sum problem as above but with bottom up solution. This is a
	 * much easier solution.
	 */
	public int combinationsSumBottomUp(int[] nums, int target) {
		int[] dp = new int[target+1];
		dp[0] = 1;
		for(int i = 1; i < dp.length; i++) {
			for(int j = 0; j < nums.length; j++) {
				if(i - nums[j] >= 0) {
					dp[i] = dp[i] + dp[i-nums[j]];
				}
			}
		}
		return dp[target];
	}
	
	/**
	 * Given coins of certain denominations and a target amount, find the minimum number of 
	 * coins required to make the target amount. You may assume you have an infinite supply
	 * of each coin.
	 * 
	 * The idea is to start from zero and calculate the amount you can form with a single
	 * coin of each denomination. Then you move to the next denomination that was formed
	 * in the previous step and do the same while ensuring the minimum coins needed to form
	 * the amount is retained. Keep going all the way until you hit target.
	 *  
	 * @param coins
	 * @param target
	 * @return
	 */
	public int coinChangeMinCoins(int[] coins, int target) {
		int[] dp = new int[target+1];
		Arrays.fill(dp, -1);
		dp[0] = 0;
		for(int i = 0; i < dp.length; i++) {
			for(int coin : coins) {
				if(dp[i] == -1) continue; // this value could not be formed anyway
				if(i+coin <= target) {
					if(dp[i+coin] == -1) {
						dp[i+coin] = dp[i] + 1;
					} else {
						dp[i+coin] = Math.min(dp[i]+1, dp[i+coin]);
					}
				}
			}
//			for(int j = 0; j < dp.length; j++) 
//				System.out.print(dp[j] + " ");
//			System.out.println();
		}
		return dp[target];
	}
	
	public int coinChangeMinCoins02(int[] coins, int target) {
		int[] dp = new int[target+1];
		Arrays.fill(dp, -1);
		dp[0] = 0;
		for(int coin : coins) {
			for(int i = 1; i < dp.length; i++) {
				if(i-coin >= 0) {
					if(dp[i-coin] == -1) continue;
					if(dp[i] == -1) {
						dp[i] = 1+dp[i-coin];
					} else {
						dp[i] = Math.min(1+dp[i-coin], dp[i]);
					}					
				}
			}
//			for(int j = 0; j < dp.length; j++) 
//				System.out.print(dp[j] + " ");
//			System.out.println();
		}
		return dp[target];
	}
	
	/**
	 * Frog Jump
	 * 
	 * Given the positions of stones across a river, find if a frog can jump from start
	 * to end. Each jump can only be k-1, k, k+1 where k was the previous jump. The first 
	 * stone is always at 0 and the first jump is always one
	 */
	public boolean canJumpToEnd(int[] stones) {
		if(stones.length == 0) return true;
		
		Map<Integer, Set<Integer>> map = new HashMap<>();
		for(int i = 0; i < stones.length; i++)
			map.put(stones[i], new HashSet<Integer>());
		
		map.get(0).add(1);
		for(int i = 0; i < stones.length; i++) {
			int stone = stones[i];
			for(int dist : map.get(stone)) {
				int reach = stone + dist;
				if(reach == stones[stones.length-1]) return true;
				if(map.get(reach) != null) { // means stone exists in array
					map.get(reach).add(dist);
					if(dist-1 > 0) map.get(reach).add(dist-1);
					map.get(reach).add(dist+1);
				}
			}
		}
		return false;
	}
	
	/**
	 * Given coins of different denominations, find the different ways you can make
	 * up the target sum. You have unlimited coins of each denomination.
	 * @param coins
	 * @param sum
	 * @return
	 */
	public int possibleCoinCombinations(int[] coins, int target) {
		int[] dp = new int[target+1];
		Arrays.fill(dp, 0);
		dp[0] = 1;
		
		for(int coin : coins) {
			for(int i = 0; i <= target; i++) {
				if(i+coin <= target)
					dp[i+coin] = dp[i] + dp[i+coin];
			}
		}
		
		return dp[target];
	}
	
	/**
	 * Target Sum - Leetcode 494
	 * 
	 * You are given a list of non-negative integers, a1, a2, ..., an, 
	 * and a target, S. Now you have 2 symbols + and -. For each integer, 
	 * you should choose one from + and - as its new symbol. 
	 * 
	 * Find out how many ways to assign symbols to make sum of integers equal to target S.
	 * 
	 * This question can be rephrased as split the original list into two sublists such
	 * that one sublist contains all positive elements and the other contains all negative
	 * elements such that their combined sum equals target.
	 * 
	 * sum(P) - sum(N) = target
	 * sum(P) - sum(N) + sum(P) + sum(N) = target + sum(P) + sum(N)
	 * 2 * sum(P) = target + sum(list)
	 * sum(P) = (target + sum(list))/2
	 * 
	 * The problem now becomes find the number of ways to sum up to sum(P)
	 */
	public int targetSum(int[] nums, int target) {
		int sum = 0;
		for(int num : nums) {
			sum = sum + num;
		}
		return ((target + sum) % 2 > 0 || sum < 0) ? 0 : targetSumWays(nums, target); 
	}
	
	public int targetSumWays(int[] nums, int target) {
		int[] dp = new int[target + 1];
		dp[0] = 1;
		
		for(int num : nums) {
			for(int i = target; i >= num; i--) { // automatically takes care of when num > target
				System.out.println("i-num = " + (i-num));
				dp[i] = dp[i] + dp[i-num];
			}
		}
		return dp[target];
	}
	
	/**
	 * Given an array of nums, find the different ways you can sum up to a
	 * target sum assuming you can only use an entry once and no duplicates
	 * are present in input
	 */
	public int targetSumWaysNoDuplicates(int[] nums, int target) {
		Arrays.sort(nums); // important considering the for loop limit
		int[] dp = new int[target+1];
		dp[0] = 1;
		
		for(int num : nums) {
			for(int i = 0; i < Math.min(dp.length, num); i++) {
				if(dp[i] != 0) {
					if(i+num < dp.length) dp[i + num] = 1 + dp[i + num];
				}
			}
		}
		return dp[target];
	}
	
	/**
	 * Minmum Refueling Stops (Leetcode 871)
	 * 
	 * Find the minimum number of stops a car would have to make to refuel
	 * to reach a given target distance. Assume the car uses 1 unit of gas 
	 * for 1 unit of distance. You are given an array of stations where
	 * station[i][0] is the distance of the station from start and station[i][1]
	 * is the amount of gas in the station.
	 * 
	 * At each station the car can transfer all the gas in the station to itself
	 */
	public int minimumRefuelingStops(int target, int startFuel, int[][] stations) {
		long[] dp = new long[stations.length + 1];
		dp[0] = startFuel;
		
		for(int i = 0; i < stations.length; i++) {
			// you want to iterate backwards to go back to the earliest 
			// position from where you can reach this station
			for(int t = i; t >= 0 && dp[t] >= stations[i][0]; t--) { // if dp[t] >= s[i][0] -> we can refuel
				dp[t+1] = Math.max(dp[t+1], dp[t] + stations[i][1]);
			}			
		}
		
		for(int i = 0; i < dp.length; i++) {
			if(dp[i] >= target) return i; // minimum number of stops
		}
		return -1;
	}

}
