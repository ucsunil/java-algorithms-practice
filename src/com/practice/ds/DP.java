package com.practice.ds;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DP {

	public static void main(String[] args) {
		DP dp = new DP();
		int[] coins = {1, 2, 5};
		System.out.println("Min. coins to make 11: " + dp.coinChangeMinCoins(coins, 11));
		System.out.println("Min. coins to make 11 (method 02): " + dp.coinChangeMinCoins02(coins, 11));
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
	
}
