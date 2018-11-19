package com.practice.ds;

public class LinkedListProblems {
	
	private static class Node {
		int value;
		Node next;
		
		Node(int value) {
			this.value = value;
		}
	}

	public static void main(String[] args) {
		LinkedListProblems llp = new LinkedListProblems();
		
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		one.next = two;
		two.next = three;
		
//		Node start = llp.reverseLinkedList(one);
//		while(start != null) {
//			System.out.print(start.value + " ");
//			start = start.next;
//		}
		
		Node start = llp.reverseLinkedList(one, 1, 2);
		while(start != null) {
			System.out.print(start.value + " ");
			start = start.next;
		}
	}
	
	/**
	 * Given a linked list, detect if there is a cycle
	 * 
	 * @param start
	 * @return
	 */
	public boolean hasCycle(Node start) {
		Node walker = start;
		Node runner = start;
		
		while(walker.next != null && runner.next.next != null) {
			walker = walker.next;
			runner = runner.next.next;
			if(walker == runner) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Given a linked list, reverse the linked list
	 * @param head
	 * @return
	 */
	public Node reverseLinkedList(Node head) {
		Node dummy = new Node(0);
		dummy.next = head;
		while(head.next != null) {
			Node next = head.next;
			head.next = next.next;
			next.next = dummy.next;
			dummy.next = next;
		}
		return dummy.next;
	}
	
	/**
	 * Given a linked list, reverse the list between
	 * positions m and n (index starts at 0)
	 */
	public Node reverseLinkedList(Node head, int m, int n) {
		Node dummy = new Node(0);
		dummy.next = head;
		
		int k = 0; Node pre = null;
		while(k < m) {
			pre = head;
			head = head.next;
			k++;
		}
		
		while(k < n && head.next != null) {
			Node next = head.next;
			head.next = next.next;
			next.next = pre.next;
			pre.next = next;
		}
		return dummy.next;
		
		
	}

}
