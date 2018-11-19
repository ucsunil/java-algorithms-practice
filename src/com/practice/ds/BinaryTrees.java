package com.practice.ds;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BinaryTrees {
	
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
	 * Given a binary tree, print the right side view
	 * 
	 * @param current
	 * @param result
	 * @param level
	 */
	public void btPrintRightSideView(Node current, List<Integer> result, int level) {
		if(current == null) return;
		
		if(level == result.size()) {
			result.add(current.value);
		}
		btPrintRightSideView(current.right, result, level+1);
		btPrintRightSideView(current.left, result, level+1);
	}
	
	/**
	 * Given a binary tree, print the left side view
	 * 
	 */
	public void btPrintLeftSideView(Node current, List<Integer> result, int level) {
		if(current == null) return;
		
		if(level == result.size()) {
			result.add(current.value);
		}
		btPrintLeftSideView(current.left, result, level+1);
		btPrintLeftSideView(current.right, result, level+1);		
	}
	
	/**
	 * Given two nodes P and Q, find the lowest common ancestor.
	 * 
	 * @param root
	 * @param p
	 * @param q
	 * @return
	 */
	public Node lowestCommonAncestor(Node root, Node p, Node q) {
		if(root == p || root == q || root == null) {
			return root;
		}
		
		Node left = lowestCommonAncestor(root.left, p, q);
		Node right = lowestCommonAncestor(root.right, p, q);
		
		if(left != null && right != null) 
			return root;
		
		return left == null ? right : left;
	}
	
	/**
	 * Given a binary tree, return it's height
	 */
	public int heightOfBinaryTree(Node root) {
		if(root == null) return 0;
		
		return 1 + Math.max(heightOfBinaryTree(root.left), heightOfBinaryTree(root.right));
	}
	
	/**
	 * Given two binary trees, find if they are the same
	 */
	public boolean isSameTree(Node root1, Node root2) {
		if(root1 == null && root2 == null) return true;
		if(root1.value != root2.value) return false;
		return isSameTree(root1.left, root2.left) && isSameTree(root1.right, root2.right);
	}
	
	/**
	 * A tournament tree is a binary tree where the parent is the minimum
	 * of it's two children. Given a tournament tree, find the second 
	 * minimum value in the tree. Each node in the tree will have exactly
	 * zero or two children and the values are all unique.
	 */
	public int secondMinTournamentTree(Node root) {
		if(root.left == null && root.right == null) return Integer.MAX_VALUE;
		
		if(root.value == root.right.value) {
			return Math.min(root.left.value, secondMinTournamentTree(root.right));
		} else { // Implies root.value = root.left.value
			return Math.min(root.right.value, secondMinTournamentTree(root.left));
		}
	}
	
	/**
	 * Given a binary tree, create a linked list for the nodes in each level
	 */
	public void createLevelLinkedLists(List<LinkedList<Integer>> lists, Node root, int level) {
		if(root == null) return;
		
		if(lists.size() == level)
			lists.add(new LinkedList<Integer>());
		lists.get(level).add(root.value);
		
		createLevelLinkedLists(lists, root.left, level+1);
		createLevelLinkedLists(lists, root.right, level+1);
	}
	
	/**
	 * Given a binary tree, create a linked list for the nodes in each level. The lists 
	 * should contain the values in a zig zag manner
	 */
	public void createZigZagLevelLinkedLists(List<LinkedList<Integer>> lists, Node root, int level) {
		if(root == null) return;
		
		if(lists.size() == level)
			lists.add(new LinkedList<Integer>());
		if(level%2 == 0) 
			lists.get(level).add(root.value);
		else
			lists.get(level).add(0, root.value);
		
		createZigZagLevelLinkedLists(lists, root.left, level+1);
		createZigZagLevelLinkedLists(lists, root.right, level+1);
	}
	
	/**
	 * Given a binary tree, print all the root to leaf paths
	 */
	public List<String> binaryTreePaths(Node root) {
		List<String> answer = new ArrayList<>();
		if(root != null) 
			binaryTreePaths(root, "", answer);
		return answer;
	}
	
	public void binaryTreePaths(Node node, String path, List<String> answer) {
		if(node.left == null && node.right == null) answer.add(path + node.value);
		if(node.left != null) binaryTreePaths(node.left, path + node.value + "->", answer);
		if(node.right != null) binaryTreePaths(node.right, path + node.value + "->", answer);
	}
	
	/**
	 * Same problem as above with a slight variation that you have to return the
	 * nodes. Use backtracking for this.
	 */
	public List<List<Node>> binaryTreePathsWithNodes(Node root) {
		List<List<Node>> paths = new ArrayList<>();
		if(root == null) return paths;
		binaryTreePathsWithNodes(paths, new ArrayList<Node>(), root);
		return paths;
	}
	
	public void binaryTreePathsWithNodes(List<List<Node>> paths, List<Node> path, Node node) {
		path.add(node);
		if(node.left == null && node.right == null) {
			paths.add(new ArrayList<>(path));
		} else {
			if(node.left != null) {
				binaryTreePathsWithNodes(paths, path, node.left);
				path.remove(path.size()-1);
			}
			if(node.right != null) {
				binaryTreePathsWithNodes(paths, path, node.right);
				path.remove(path.size()-1);
			}
		}
	}
	
	/**
	 * Diameter of binary tree -> This is the longest path between two
	 * nodes of the binary tree
	 */
	int maxPathLength = 0;
	public int maxPathLength(Node root) {
		maxDepth(root);
		return maxPathLength;
	}
	
	public int maxDepth(Node root) {
		if(root == null) return 0;
		
		int left = maxDepth(root.left);
		int right = maxDepth(root.right);
		
		maxPathLength = Math.max(maxPathLength, left+right);
		return 1 + Math.max(left, right);
	}
	
	/**
	 * Maximum binary tree.
	 * 
	 * Given an array without any duplicates, construct the maximum binary
	 * tree from the array. The root is the maximum number in the array. The
	 * numbers to the left of the root form the left subtree and the numbers
	 * to the right for the root form the right subtree. Recursively apply the 
	 * same rule as before where the root is always the largest node in the
	 * array
	 */
	public Node maximumBinaryTree(int[] array) {
		if(array == null) return null;
		return maximumBinaryTree(array, 0, array.length-1);
	}
	
	public Node maximumBinaryTree(int[] array, int start, int end) {
		if(start > end) return null;
		
		int max = Integer.MIN_VALUE; int maxPos = -1;
		for(int i = start; i <= end; i++) {
			if(array[i] > max) {
				max = array[i];
				maxPos = i;
			}
		}
		Node root = new Node(max);
		root.left = maximumBinaryTree(array, start, maxPos-1);
		root.right = maximumBinaryTree(array, maxPos+1, end);
		return root;
	}
	
	/**
	 * Given a binary tree, find the maximum width of the tree
	 * 
	 * The solution is based on the fact that if you serialize a binary tree to an array, the left
	 * child is at index 2*i and the right child is at index 2*i+1 (index starting from 1). So you 
	 * keep 2 lists (start and end) which track the position (or order) of the first non null left 
	 * and right child. At each level you have to calculate the max at that level and the max on the 
	 * left and right child levels.
	 */
	public int maximumWidthofBinaryTree(Node root) {
		return maximumWidthOfBinaryTree(root, 0, 1, new ArrayList<Integer>(), new ArrayList<Integer>());
	}
	
	public int maximumWidthOfBinaryTree(Node root, int level, int order, List<Integer> start, List<Integer> end) {
		if(root == null) return 0;
		
		if(start.size() == level) {
			start.add(order);
			end.add(order);
		} else { // level has already been encountered before; so leave start alone and update end
			end.set(level, order);
		}
		int currWidth = end.get(level) - start.get(level) + 1; // max width at current level
		int left = maximumWidthOfBinaryTree(root.left, level+1, 2*order, start, end); // max width on left subtree
		int right = maximumWidthOfBinaryTree(root.right, level+1, 2*order+1, start, end); // max width on right subtree
		return Math.max(currWidth, Math.max(left, right));
	}
	
	/**
	 * Binary tree pruning
	 * 
	 * Given a binary tree whose nodes only have values 1 or 0, return the tree
	 * where each subtree not containing a 1 has been removed
	 */
	public Node pruneBinaryTree(Node root) {
		if(root == null) return null;
		
		root.left = pruneBinaryTree(root.left);
		root.right = pruneBinaryTree(root.right);
		if(root.left == null && root.right == null && root.value == 0) return null;
		return root;
	}
	
	/**
	 * Longest consecutive sequence in binary tree
	 * Path needs to be from parent to child
	 */
	int longest = 0;
	public int longestConsecutiveSequence(Node root) {
		if(root == null) return 0;
		longestConsecutiveSequence(root, root.value, 0);
		return longest;
	}
	
	public void longestConsecutiveSequence(Node root, int expectedValue, int currMax) {
		if(root == null)
			return;
		if(root.value == expectedValue) currMax++;
		else currMax = 1;
		
		longest = Math.max(longest, currMax);
		longestConsecutiveSequence(root.left, root.value+1, currMax);
		longestConsecutiveSequence(root.right, root.value+1, currMax);
	}
	
	/**
	 * Given a binary tree, find if it is symmetric about the root
	 */
	public boolean isBinaryTreeSymmetric(Node root) {
		if(root != null) return isBinaryTreeSymmetric(root.left, root.right);
		return true;
	}
	
	public boolean isBinaryTreeSymmetric(Node left, Node right) {
		if(left == null || right == null) return left == right;
		if(left.value != right.value) return false;
		
		return isBinaryTreeSymmetric(left.left, right.right) && isBinaryTreeSymmetric(left.right, right.left);
	}

}
