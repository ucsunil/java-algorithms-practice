package com.practice.ds;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Grids {
	
	private static final int[][] NUMS_01 = {
			{9, 9, 4},
			{6, 6, 8},
			{2, 1, 1}
	};

	public static void main(String[] args) {
		Grids grids = new Grids();
		int[][] grid_01 = {{0, 0, 0, -1, -1},
							{0, 0, -1, -1, 0},
							{0, 0, 0, 0, 0}};
		boolean[][] boolean_01 = new boolean[grid_01.length][grid_01[0].length];
		System.out.println("Does path exist (expected: true) = " + grids.doesPathExistDFS(grid_01, 0, 0, 1, 4, boolean_01));
		boolean_01 = new boolean[grid_01.length][grid_01[0].length];
		System.out.println("Can ball roll to finish DFS(expected: true) = " + grids.canBallRollToDestDFS(grid_01, 0, 0, 1, 4, boolean_01));
		System.out.println("Can ball roll to finish BFS(expected: true) = " + grids.canBallRollToDestBFS(grid_01, new int[] {0, 0}, new int[] {1, 4}));
	
		System.out.println("Shortest distance for ball to roll to finish (DFS): " + grids.canBallRollToDestShortestDistanceDFS(grid_01, new int[] {0, 0}, new int[] {1, 4}));
		System.out.println("Shortest distance for ball to roll to finish (BFS): " + grids.canBallRollToDestShortestDistanceBFS(grid_01, new int[] {0, 0}, new int[] {1, 4}));
		
		System.out.print("Longest increasing sequence (expected: 4): " + grids.longestIncreasingPath(NUMS_01));
	}
	
	/**
	 * Given a grid with obstacles (grid[x][y] = -1), find out if a path exists from given
	 * start coordinates to given end coordinates
	 * 
	 */
	public boolean doesPathExistDFS(int[][] grid, int posx, int posy, int endx, int endy, boolean[][] visited) {
		if(posx < 0 || posy < 0 || posx >= grid.length || posy >= grid[0].length)  // means going out of bounds
			return false;
		if(grid[posx][posy] == -1 || visited[posx][posy]) // either traversed or encountered a block
			return false;
		if(posx == endx && posy == endy) 
			return true;
		visited[posx][posy] = true;
		return doesPathExistDFS(grid, posx+1, posy, endx, endy, visited) ||
				doesPathExistDFS(grid, posx, posy+1, endx, endy, visited) ||
				doesPathExistDFS(grid, posx-1, posy, endx, endy, visited) ||
				doesPathExistDFS(grid, posx, posy-1, endx, endy, visited);
	}
	
	/**
	 * Given a ball in a grid which has obstacles, find if the ball can roll to the given
	 * end position. Once the ball starts rolling, it cannot change directions until it
	 * hits either an obstacle or the end of the board
	 */
	public boolean canBallRollToDestDFS(int[][] grid, int x, int y, int endx, int endy, boolean[][] started) {
		if(x < 0 || y < 0 || x >= grid.length || y >= grid[0].length) 
			return false;
		if(grid[x][y] == -1 || started[x][y]) // means either already started from here or obstacle
			return false;
		if(x == endx && y == endy)
			return true;
		started[x][y] = true;
		
		int down = x, up = x, right = y, left = y;
		while(down+1 < grid.length && grid[down+1][y] != -1) {
			down++;
			if(down == endx && y == endy) return true; // in case destination is in the path
		}
		if(canBallRollToDestDFS(grid, down, y, endx, endy, started)) return true;
		
		while(right+1 < grid[0].length && grid[x][right+1] != -1) {
			right++;
			if(x == endx && right == endy) return true;
		}
		if(canBallRollToDestDFS(grid, x, right, endx, endy, started)) return true;
		
		while(up-1 >= 0 && grid[up-1][y] != -1) {
			up--;
			if(up == endx && y == endy) return true;
		}
		if(canBallRollToDestDFS(grid, up, y, endx, endy, started)) return true;
		
		while(left-1 >= 0 && grid[x][left-1] != -1) {
			left--;
			if(x == endx && left == endy) return true; 
		}
			
		if(canBallRollToDestDFS(grid, x, left, endx, endy, started)) return true;
		
		return false;
	}
	
	/**
	 * Same problem as before but using BFS
	 */
	public boolean canBallRollToDestBFS(int[][] grid, int[] start, int[] end) {		
		Queue<int[]> queue = new LinkedList<>();		
		boolean[][] started = new boolean[grid.length][grid[0].length];
		queue.add(start);
		
		while(!queue.isEmpty()) {
			start = queue.poll();
			if(start[0] == end[0] && start[1] == end[1]) return true;
			
			if(started[start[0]][start[1]]) continue;
			else started[start[0]][start[1]] = true;
			
			int down = start[0], up = start[0], left = start[1], right = start[1];
			while(down+1 < grid.length && grid[down+1][start[1]] != -1) {
				down++;
				if(down == end[0] && start[1] == end[1]) return true;
			}				
			queue.add(new int[] {down, start[1]});
			
			while(right+1 < grid[0].length && grid[start[0]][right+1] != -1) {
				right++;
				if(start[0] == end[0] && right == end[1]) return true;
			}
			queue.add(new int[] {start[0], right});
			
			while(up-1 >= 0 && grid[up-1][start[1]] != -1) {
				up--;
				if(up == end[0] && start[1] == end[1]) return true;
			}
			queue.add(new int[] {up, start[1]});
			
			while(left-1 >= 0 && grid[start[0]][left-1] != 0) {
				left--;
				if(start[0] == end[0] && left == end[1]) return true;
			}
			queue.add(new int[] {start[0], left});
		}
		return false;
	}
	
	/**
	 * Given a ball in a grid which has obstacles, find the least number of
	 * squares the ball would have to cross to reach the destination. Note
	 * that once the ball starts rolling, it can only change direction when it
	 * hits an obstacle
	 * 
	 * If there is no way for the ball to stop at the destination, return -1
	 */
	public int canBallRollToDestShortestDistanceDFS(int[][] grid, int[] start, int[] end) {
		int[][] distance = new int[grid.length][grid[0].length];
		for(int[] row : distance) {
			Arrays.fill(row, Integer.MAX_VALUE);
		}
		distance[start[0]][start[1]] = 0;
		canBallRollToDestShortestDistanceDFS(grid, start, distance);
		return distance[end[0]][end[1]] == Integer.MAX_VALUE ? -1 : distance[end[0]][end[1]];
	}
	
	public void canBallRollToDestShortestDistanceDFS(int[][] grid, int[] start, int[][] distance) {
		int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
		for(int[] dir : dirs) {
			int x = start[0];
			int y = start[1];
			int count = 0;
			
			while(x+dir[0] >= 0 && y+dir[1] >= 0 && x+dir[0] < grid.length && y+dir[1] < grid[0].length && grid[x+dir[0]][y+dir[1]] != -1) {
				x = x + dir[0];
				y = y + dir[1];
				count++;
			}
			if(distance[start[0]][start[1]] + count < distance[x][y]) {
				distance[x][y] = distance[start[0]][start[1]] + count;
				canBallRollToDestShortestDistanceDFS(grid, new int[] {x, y}, distance);
			}
		}
	}
	
	/**
	 * Same problem as before but with BFS
	 */
	public int canBallRollToDestShortestDistanceBFS(int[][] grid, int[] start, int[] end) {
		int[][] distance = new int[grid.length][grid[0].length];
		for(int[] row : distance) {
			Arrays.fill(row, Integer.MAX_VALUE);
		}
		distance[start[0]][start[1]] = 0;
		int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
		Queue<int[]> queue = new LinkedList<>();
		queue.add(start);
		while(!queue.isEmpty()) {
			int[] s = queue.poll();
			for(int[] dir : dirs) {
				int x = s[0];
				int y = s[1];
				int count = 0;
				while(x+dir[0] >=0 && y+dir[1] >= 0 && x+dir[0] < grid.length && y+dir[1] < grid[0].length && grid[x+dir[0]][y+dir[1]] != -1) {
					x = x + dir[0];
					y = y + dir[1];
					count++;
				}
				if(distance[s[0]][s[1]]+count < distance[x][y]) {
					distance[x][y] = distance[s[0]][s[1]] + count;
					queue.add(new int[] {x,y});
				}
			}
		}
		return distance[end[0]][end[1]] == Integer.MAX_VALUE ? -1 : distance[end[0]][end[1]];
	}
	
	/**
	 * Given a grid of 0s and 1s, where 1s represent the islands, find the number of islands
	 * in the grid
	 */
	public int numIslands(int[][] grid) {
		int numIslands = 0;
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				if(grid[i][j] == 1) {
					numIslands++;
					shrinkIsland(grid, i, j);
				}
			}
		}
		return numIslands;
	}
	
	public void shrinkIsland(int[][] grid, int startx, int starty) {
		if(grid[startx][starty] == 0 || startx < 0 || starty < 0 || startx >= grid.length || starty >= grid[0].length) 
			return;
		
		grid[startx][starty] = 0;
		shrinkIsland(grid, startx+1, starty);
		shrinkIsland(grid, startx, starty+1);
		shrinkIsland(grid, startx-1, starty);
		shrinkIsland(grid, startx, starty-1);
	}
	
	/**
	 * Longest increasing path in a matrix
	 */
	int longest = 1;
	public int longestIncreasingPath(int[][] grid) {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				int[][] cache = new int[grid.length][grid[0].length];
				dfsLongestIncreasingPath(grid, i, j, i, j, cache);
			}
		}
		return longest;
	}
	
	public void dfsLongestIncreasingPath(int[][] grid, int currx, int curry, int prevx, int prevy, int[][] cache) {
		if(currx < 0 || currx >= grid.length || curry < 0 || curry >= grid[0].length) // out of bounds
			return;
		if(currx == prevx && curry == prevy)
			cache[currx][curry] = 1; // start condition
		else if(grid[currx][curry] > grid[prevx][prevy] && 1+cache[prevx][prevy] > cache[currx][curry]) {// update condition
			cache[currx][curry] = 1 + cache[prevx][prevy];
			longest = Math.max(longest, cache[currx][curry]);
		} else { // path is non increasing or longer path is already found
//			grid[currx][curry] <= grid[prevx][prevy] || 1+cache[prevx][prevy] <= cache[currx][curry]
			return;
		}
				
		dfsLongestIncreasingPath(grid, currx+1, curry, currx, curry, cache);
		dfsLongestIncreasingPath(grid, currx, curry+1, currx, curry, cache);
		dfsLongestIncreasingPath(grid, currx-1, curry, currx, curry, cache);
		dfsLongestIncreasingPath(grid, currx, curry-1, currx, curry, cache);
	}
	
	/**
	 * Longest increasing path in a matrix
	 * 
	 * Same problem as above but with slightly different approach
	 */
	public int longestIncreasingPath02(int[][] grid) {
		int[][] cache = new int[grid.length][grid[0].length];
		int longest = 1;
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				int max = dfsLongestIncreasingPath02(grid, i, j, cache);
				longest = Math.max(longest, max);
			}
		}
		return longest;
	}
	
	public int dfsLongestIncreasingPath02(int[][] grid, int i, int j, int[][] cache) {
		if(cache[i][j] != 0) return cache[i][j];
		int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {-1, -1}};
		int longest = 1;
		for(int[] dir : dirs) {
			int x = i + dir[0];
			int y = j + dir[1];
			if(x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] <= grid[i][j])
				continue;
			int length = 1 + dfsLongestIncreasingPath02(grid, x, y, cache);
			longest = Math.max(longest, length);
		}
		cache[i][j] = longest;
		return cache[i][j];
	}
	
	/**
	 * Swim in rising water
	 * Leetcode - 778
	 * 
	 * The water level in each cell at time t is the higher of starting level or
	 * time t.
	 * Starting at position (0,0), find if you can reach bottom right assuming you
	 * can only travel in the cardinal directions and only if the water level in the
	 * destination cell is lower than water level in current cell
	 * 
	 * Find the time it takes to reach the end
	 */
	public int swimInRisingWater(int[][] grid) {
		boolean[][] visited = new boolean[grid.length][grid[0].length];
		int time = 0;
		while(!visited[grid.length][grid[0].length]) {
			visited = new boolean[grid.length][grid[0].length];
			swimInRisingWaterDFS(grid, 0, 0, time, visited);
			time++;
		}
		return time-1; // because of the time++ (easy to miss)
	}
	
	public void swimInRisingWaterDFS(int[][] grid, int x, int y, int time, boolean[][] visited) {
		if(visited[x][y] || x < 0 || y < 0 || x >= grid.length || y >= grid.length) return;
		if(grid[x][y] > time) return; // cannot swim as water level here is higher
		visited[x][y] = true;
		int[][] dirs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
		
		for(int[] dir : dirs) {
			swimInRisingWaterDFS(grid, x+dir[0], y+dir[0], time, visited);
		}
		
	}

}
