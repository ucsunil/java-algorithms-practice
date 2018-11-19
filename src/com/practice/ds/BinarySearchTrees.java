package com.practice.ds;

public class BinarySearchTrees {
	
	private static class Node {
		int value;
		Node left, right;
		
		Node(int value) {
			this.value = value;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Given two nodes P and Q, find the lowest common ancestor.
	 */
	public Node lowestCommonAncestor(Node root, Node p, Node q) {
		if(root.value < p.value && root.value < q.value) {
			return lowestCommonAncestor(root.right, p, q);
		} else if(root.value > p.value && root.value > q.value) {
			return lowestCommonAncestor(root.left, p, q);
		} else {
			return root;
		}
	}
	
	/**
	 * Find the minimum difference between two nodes in a BST.
	 */
	int minDifference = Integer.MAX_VALUE;
	Node previous = null;
	public int minDifferenceBST(Node root) {
		minDifferenceBSTInorder(root);
		return minDifference;
	}
	
	public void minDifferenceBSTInorder(Node root) {
		if(root == null) return;
		minDifferenceBSTInorder(root.left);
		if(previous != null)
			minDifference = Math.min(minDifference, root.value - previous.value);
		previous = root;
		minDifferenceBSTInorder(root.right);
	}
	
	/**
	 * Fix the BST
	 * 
	 * Given that two nodes in a BST are incorrectly placed, fix
	 * the BST
	 */
	Node error1, error2;
	public Node fixTheBST(Node root) {
		identifyErrorNodesInBST(root);
		int temp = error1.value;
		error1.value = error2.value;
		error2.value = temp;
		return root;
	}
	
	Node prev;
	public void identifyErrorNodesInBST(Node root) {
		if(root == null) return;
		identifyErrorNodesInBST(root.left);
		if(prev != null) {
			if(prev.value >= root.value) {
				if(error1 == null)
					error1 = prev;
				else
					error2 = root;
			}
		}
		prev = root;
		identifyErrorNodesInBST(root.right);		
	}
}
