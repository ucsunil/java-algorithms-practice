package com.practice.ds;

import java.util.LinkedList;
import java.util.List;

public class IntervalProblems {
	
	private static class Interval {
		int start, end;
		
		Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		Interval(Interval other) {
			start = other.start;
			end = other.end;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * Given two lists of intervals sorted in ascending order, combine them into
	 * one list in sorted order with no overlap.
	 * 
	 * @param intervalsA
	 * @param intervalsB
	 * @return
	 */
	public List<Interval> mergeIntervalLists(List<Interval> intervalsA, List<Interval> intervalsB) {
		LinkedList<Interval> result = new LinkedList<Interval>();
		int i = 0, j = 0;
		while(i < intervalsA.size() && j < intervalsB.size()) {
			Interval A = intervalsA.get(i);
			Interval B = intervalsB.get(j);
			if(A.start < B.start) {
				if(result.size() > 0 && A.start <= result.get(result.size()-1).end) {
					result.get(result.size()-1).end = Math.max(result.get(result.size()-1).end, A.end);
				} else {
					result.add(new Interval(A));
				}
				i++;
			} else if(B.start < A.start) {
				if(result.size() > 0 && B.start <= result.get(result.size()-1).end) {
					result.get(result.size()-1).end = Math.max(result.get(result.size()-1).end, B.end);
				} else {
					result.add(new Interval(B));
				}
				j++;
			} else { // means A.start = B.start
				if(result.size()> 0 && A.start <= result.get(result.size()-1).end) {
					result.get(result.size()-1).end = Math.max(result.get(result.size()-1).end, Math.max(A.end, B.end));
				} else {
					result.add(new Interval(A.start, Math.max(A.end, B.end)));
				}
				i++; j++;
			}
		}
		while(i < intervalsA.size()) {
			Interval A = intervalsA.get(i);
			if(result.size() > 0 && A.start <= result.get(result.size()-1).end) {
				result.get(result.size()-1).end = Math.max(result.get(result.size()-1).end, A.end);
			} else {
				result.add(new Interval(A));
			}
			i++;
		}
		while(j < intervalsB.size()) {
			Interval B = intervalsB.get(j);
			if(result.size() > 0 && B.start <= result.get(result.size()-1).end) {
				result.get(result.size()-1).end = Math.max(result.get(result.size()-1).end, B.end);
			} else {
				result.add(new Interval(B));
			}
			j++;
		}
		return result;
	}
	
	/**
	 * Merge intervals
	 */
	public List<Interval> mergeIntervals(List<Interval> intervals) {
		if(intervals == null || intervals.size() == 1)
			return intervals;
		
		intervals.sort((i1, i2) -> Integer.compare(i1.start, i2.start));
		int start = intervals.get(0).start;
		int end = intervals.get(0).end;
		List<Interval> result = new LinkedList<Interval>();
		
		for(Interval interval : intervals) {
			if(interval.start <= end) {
				end = Math.max(interval.end, end);
			} else { // disjoint intervals
				result.add(new Interval(start, end));
				start = interval.start;
				end = interval.end;
			}
		}
		result.add(new Interval(start, end));
		return result;
	}

}
