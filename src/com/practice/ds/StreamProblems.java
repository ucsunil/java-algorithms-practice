package com.practice.ds;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * A stream is an array that can only be consumed once. You do not have a way to find
 * the length of the stream.
 */
public class StreamProblems {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Median of data stream
	 * 
	 * The idea is to use a max heap to store the numbers lower than the median
	 * and to use a min heap to store numbers higher than the median. The size
	 * difference between the two heaps is at most 1.
	 */
	public double medianOfDataStream(int[] stream) {
		PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
		PriorityQueue<Integer> minHeap = new PriorityQueue<>();
		
		for(int x : stream) {
			maxHeap.add(x);
			minHeap.add(maxHeap.poll());
			
			if(maxHeap.size() < minHeap.size())
				maxHeap.add(minHeap.poll());
		}
		if(maxHeap.size() == minHeap.size()) {
			return (double) (maxHeap.peek() + minHeap.peek())/2;
		} else {
			return maxHeap.peek();
		}
	}
	
	/**
	 * Given a data stream of 0 and 1, find the length of longest consecutive
	 * series of 1s that can be formed by flipping k bits (of 0s to 1s)
	 */
	public int longestConsecutiveSequenceFlipKBits(int[] stream, int k) {
		Queue<Integer> q = new LinkedList<Integer>();
		int currSequence = 0, maxLength = 0;
		for(int i = 0; i < stream.length; i++) {
			if(stream[i] == 1) {
				currSequence++;
			} else {
				q.add(i);
				currSequence++;
				if (q.size() > k) {
					int index = q.poll();
					currSequence = currSequence - index - 1;
				}
			}
			maxLength = Math.max(currSequence, maxLength);
		}
		return maxLength;
	}

}
