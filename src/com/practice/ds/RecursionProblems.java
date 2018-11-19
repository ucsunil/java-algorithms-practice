package com.practice.ds;

import java.util.ArrayList;
import java.util.List;

public class RecursionProblems {

	public static void main(String[] args) {
		RecursionProblems recursion = new RecursionProblems();
		System.out.print("Number of valid queen positions for N=8: " + recursion.NQueenPositions(8).size());
	}
	
	public List<Integer[]> NQueenPositions(int N) {
		List<Integer[]> results = new ArrayList<>();
		Integer[] column = new Integer[N];
		NQueenPositions(results, N, 0, column);
		return results;
	}
	
	public void NQueenPositions(List<Integer[]> results, int N, int currentRow, Integer[] column) {
		if(currentRow == N) {
			results.add(column.clone());
		} else {
			for(int i = 0; i < N; i++) {
				if(isQueenPositionValid(currentRow, i, column)) {
					column[currentRow] = i;
					NQueenPositions(results, N, currentRow+1, column);
				}
			}
		}		
	}
	
	public boolean isQueenPositionValid(int row, int col, Integer[] column) {
		for(int i = 0; i < row; i++) {
			if(column[i] == col) return false;
			if(i + column[i] == row + col) return false;
			if(i - column[i] == row - col) return false;
		}
		return true;
	}
	
	public interface NestedInteger {
		// @return true if this NestedInteger holds a single integer, rather than a nested list.
		public boolean isInteger();
		
		// @return the single integer that this NestedInteger holds, if it holds a single integer
		// Return null if this NestedInteger holds a nested list
		public Integer getInteger();
		
		// @return the nested list that this NestedInteger holds, if it holds a nested list
		// Return null if this NestedInteger holds a single integer
		public List<NestedInteger> getList();
	}
	
	/**
	 * Given a list of nested weights, find the inverse depth sum
	 * @param list
	 * @return
	 */
	public int nestedListWeightSumInverse(List<NestedInteger> list) {
		if(list == null || list.size() == 0) return 0;
		List<List<Integer>> holder = new ArrayList<>();
		nestedListWeightSumInverse(holder, list, 1);
		int result = 0;
		for(int i = 0, j = holder.size(); i < holder.size(); i++, j--) {
			List<Integer> temp = holder.get(i);
			for(int x : temp) {
				result = result + (x*j);
			}
		}
		return result;
	}
	
	public void nestedListWeightSumInverse(List<List<Integer>> holder, List<NestedInteger> list, int level) {
		if(holder.size() < level)
			holder.add(new ArrayList<Integer>());
		for(NestedInteger ni : list) {
			if(ni.isInteger())
				holder.get(level-1).add(ni.getInteger());
			else
				nestedListWeightSumInverse(holder, ni.getList(), level+1);
		}
	}

}
