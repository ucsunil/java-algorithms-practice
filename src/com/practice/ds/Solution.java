package com.practice.ds;

class Solution {
	public static class TreeNode {
		 int val;
		 TreeNode left;
		 TreeNode right;
		 TreeNode(int x) { val = x; }
	}
	
	public static void main(String[] args) {
		Solution sol = new Solution();
		TreeNode one = new TreeNode(1);
		TreeNode two = new TreeNode(2);
		TreeNode three = new TreeNode(3);
		TreeNode four = new TreeNode(4);
		// thr
	}
	
    TreeNode error1, error2;
    public void recoverTree(TreeNode root) {
        identifyErrorNodesInBST(root);
		int temp = error1.val;
		error1.val = error2.val;
		error2.val = temp;
	}
    
    TreeNode prev;
	public void identifyErrorNodesInBST(TreeNode root) {
		if(root == null) return;
		identifyErrorNodesInBST(root.left);
		if(prev != null) {
			if(prev.val >= root.val) {
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
