package main;

public class Maze {

	private char[][] matrix;
	
	public Maze(char[][] matrix) {
		this.matrix = matrix;
	}

	public void findShortestPath() {
		Graph graph = new Graph(matrix);
		graph.dijkstra();
	}
	
	public char[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(char[][] matrix) {
		this.matrix = matrix;
	}
	
}
