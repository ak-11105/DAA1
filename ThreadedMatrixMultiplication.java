package daa;
import java.util.*;
public class ThreadedMatrixMultiplication {
	static void sequentialMultiplication(int[][] a,int[][] b,int n) {
		int[][] resultMatrix = new int[n][n];
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n; ++j) {
				for(int k = 0; k < n; ++k) {
					resultMatrix[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		System.out.println("Sequential Multiplication:");
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n; ++j) {
				System.out.print(resultMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	static void parallelMultiplication(int[][] a,int[][] b,int n) {
		int[][] resultMatrix = new int[n][n];
		
		Thread[][] threads = new Thread[n][n];
		
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n; ++j) {
				int row = i;
				int col = j;
				
				threads[row][col] = new Thread(() ->{
						resultMatrix[row][col] = 0;
						for(int k = 0; k <  n; ++k) {
							resultMatrix[row][col] += a[row][k] * b[k][col];
						}
					}
				);
				
				threads[row][col].start();
				
			}
		}
		
		
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n; ++j) {
				try {
					threads[i][j].join();
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("Parallel Multiplication:");
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n; ++j) {
				System.out.print(resultMatrix[i][j] + " ");
			}
			System.out.println();
		}
		
	}
	public static void main(String[] args) {
		int[][] matrixA = new int[][]{
		    {1, 2, 3},
		    {4, 5, 6},
		    {7, 8, 9}
		};
		int[][] matrixB = new int[][]{
		    {9, 8, 7},
		    {6, 5, 4},
		    {3, 2, 1}
		};
		
		int n = 3;
		
		long start = System.currentTimeMillis();
		sequentialMultiplication(matrixA,matrixB,n);
		long end = System.currentTimeMillis();
		System.out.println("Time required for Sequential Multiplication: " + (end - start));
		
		start = System.currentTimeMillis();
		parallelMultiplication(matrixA,matrixB,n);
		end = System.currentTimeMillis();
		System.out.println("Time required for Parallel Multiplication: " + (end - start));
	}
}
