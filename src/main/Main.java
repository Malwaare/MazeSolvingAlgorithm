package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args){

		//Input File path
        Scanner sc = new Scanner(System.in);  
        System.out.print("Enter path to the maze: ");
		String filePath = sc.nextLine();
        sc.close();
        
      try{
		Maze maze = loadMaze(filePath);
		maze.findShortestPath();
      }
      catch(FileNotFoundException e)
      {
    	System.out.println("File not found");
      }
		
	}

	/**
	 *  Method to fill matrix of maze from .txt form
	 * @param inputFile
	 * @return matrix for Dijkstra algorithm
	 * @throws FileNotFoundException
	 */
	public static Maze loadMaze(String inputFile) throws FileNotFoundException {
		File file = new File(inputFile);
		Scanner sc = new Scanner(file);
		
		// Load number of lines
		char[][] matrix;
		int lines = 0;
		while (sc.hasNext()) {
			lines++;
			sc.nextLine();
		}
		sc.close();
		matrix = new char[lines][];
		sc = new Scanner(file);
		String line;
		
		//Fill the matrix
		for(int i = 0; i < matrix.length; i++) {
			line = sc.nextLine();
			matrix[i] = new char[line.length()];
			for(int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = line.charAt(j);
			}
		}
		sc.close();
		
		// Print maze to console
		System.out.println("Input maze: ");
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[i].length;j++) {
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		}
		
		
		return new Maze(matrix);
	}

}
