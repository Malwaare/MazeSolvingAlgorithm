package main;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class Graph {

	public static final int INF = Integer.MAX_VALUE;
	private int[][] edges;
	private int startingNode = -1;
	private int endNode = -1;
	private int nodeCount;
	private int[] pointerMatrix;
	private int width;

	/**
	 * constructs a graph based on matrix
	 * 
	 * @param matrix
	 */
	public Graph(char[][] matrix) {
		width = matrix[0].length;
		nodeCount = matrix.length * matrix[0].length;
		LinkedBlockingQueue<Edge> edges = new LinkedBlockingQueue<>();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				// check if the element is not a wall
				if (matrix[i][j] != '#') {
					if (matrix[i][j] == 'S') {
						startingNode = mapFunction(i, j);
					}
					if (matrix[i][j] == 'X') {
						endNode = mapFunction(i, j);
					}
					// check left
					if (j - 1 >= 0 && matrix[i][j - 1] != '#') {
						edges.add(new Edge(mapFunction(i, j), mapFunction(i, j - 1)));
					}
					// check right
					if (j + 1 < matrix[i].length && matrix[i][j + 1] != '#') {
						edges.add(new Edge(mapFunction(i, j), mapFunction(i, j + 1)));
					}
					// check up
					if (i - 1 >= 0 && matrix[i - 1][j] != '#') {
						edges.add(new Edge(mapFunction(i, j), mapFunction(i - 1, j)));
					}
					// check down
					if (i + 1 < matrix.length && matrix[i + 1][j] != '#') {
						edges.add(new Edge(mapFunction(i, j), mapFunction(i + 1, j)));
					}
				}
			}
		}
		this.edges = new int[edges.size()][];
		int i = 0;
		while (!edges.isEmpty()) {
			this.edges[i] = new int[2];
			this.edges[i][0] = edges.peek().getFrom();
			this.edges[i][1] = edges.poll().getTo();
			i++;
		}
		pointerMatrix = new int[nodeCount + 1];
		int actual = 0;
		int prev = 0;
		for (i = 1; i < this.edges.length; i++) {
			if (this.edges[i][0] > actual) {
				prev = actual;
				actual = this.edges[i][0];
				for (int j = prev; j < actual; j++) {
					pointerMatrix[j + 1] = i;
				}
				pointerMatrix[actual + 1] = i;
			}
		}

		pointerMatrix[nodeCount] = this.edges.length;
		
	}

	public void dijkstra() {
		final int u = startingNode;
		final int v = endNode;
		int nodeCount = this.nodeCount;
		int[] tDoc = new int[nodeCount];
		int[] tDef = new int[nodeCount];
		int[] x = new int[nodeCount];

		for (int i = 0; i < nodeCount; i++) {
			tDoc[i] = INF;
			x[i] = -1;
		}
		tDoc[u] = 0;
		int r = u;
		tDef[r] = tDoc[r];
		
		// Dijkstra algorithm
		while (r != v) {
			for (int i = pointerMatrix[r]; i < pointerMatrix[r + 1]; i++) {
				int j = edges[i][1];
				if (tDef[j] == 0) {
					if (tDoc[j] > tDef[r] + 1) {
						tDoc[j] = tDef[r] + 1;
						x[j] = r;
					}
				}
			}
			int minimumNode = -1;
			int minimum = INF;
			for (int i = 0; i < nodeCount; i++) {
				if (tDef[i] == 0 && i != u) {
					if (tDoc[i] < minimum) {
						minimum = tDoc[i];
						minimumNode = i;
					}
				}
			}
			if (minimumNode != -1)
				tDef[minimumNode] = tDoc[minimumNode];
			r = minimumNode;
			if (r == -1)
				break;
		}
		if (r != -1) {
			Stack<Integer> path = new Stack<>();
			r = v;
			while (r != u) {
				path.push(r);
				r = x[r];
			}
			path.push(startingNode);
			// Print solution to console
			int from;
			int to;
			System.out.println("Solution: ");
			while (!path.isEmpty()) {
				from = path.pop();
				try {
					to = path.peek();
					System.out.print(translate(to, from) + ",");
				} catch (EmptyStackException e) {

				}
			}
		} else {
			System.out.println("Error");
		}
	}

	/**
	 * Function that assigns a number to node
	 */
	public int mapFunction(int i, int j) {
		return i * width + j;
	}

	private char translate(int mapFunction, int beforeMapFunction) {
		if (mapFunction - 1 == beforeMapFunction) {
			return 'r';
		} else if (mapFunction + 1 == beforeMapFunction) {
			return 'l';
		} else if (mapFunction + width == beforeMapFunction) {
			return 'u';
		} else
			return 'd';
	}

}
