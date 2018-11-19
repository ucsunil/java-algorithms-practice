package com.practice.ds;

import java.util.ArrayList;
import java.util.List;

public class MatrixProblems {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public List<Integer> spiralOrderMatrix(int[][] matrix) {
		List<Integer> list = new ArrayList<Integer>();
		if(matrix == null || matrix.length == 0) return list;
		
		int rowBegin = 0, colBegin = 0;
		int rowEnd = matrix.length-1, colEnd = matrix[0].length-1;
		
		while(rowBegin <= rowEnd && colBegin <= colEnd) {
			for(int i = colBegin; i <= colEnd; i++)
				list.add(matrix[rowBegin][i]);
			rowBegin++;
			
			for(int j = rowBegin; j <= rowEnd; j++) 
				list.add(matrix[j][colEnd]);
			colEnd--;
			
			// Going left
			if(rowBegin <= rowEnd) { // so we don't go over same row again
				for(int i = colEnd; i >= colBegin; i--)
					list.add(matrix[rowEnd][i]);
			}
			rowEnd--;
			
			// Going up
			if(colBegin <= colEnd) { // so we don't go up an already traversed column
				for(int j = rowEnd; j >= rowBegin; j--)
					list.add(matrix[j][colBegin]);
			}
			colBegin++;
			
		}
		return list;
	}

}
