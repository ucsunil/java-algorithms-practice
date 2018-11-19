package com.practice.ds;

public class MathProblems {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Given an integer, reverse the integer
	 * @param x
	 * @return
	 */
	public int reverseInteger(int x) {
		int result = 0;
		while(x != 0) {
			int tail = x%10;
			int newResult = result*10 + tail;
			if((newResult-tail)%10 != result) // to handle overflow cases
				return 0;
			x = x/10;
		}
		return result;
	}
}
