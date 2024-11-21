package daa;
import java.util.*;
public class NQueen {
	static Scanner sc = new Scanner(System.in);
	static public boolean isSafe(int row,int col,int[][] board,int n,HashMap<Integer,Boolean> rowMap,HashMap<Integer,Boolean> upperDiagonal,HashMap<Integer,Boolean> lowerDiagonal) {
		if(rowMap.getOrDefault(row,false) == true)
			return false;
		if(lowerDiagonal.getOrDefault(row+col,false) == true)
			return false;
		if(upperDiagonal.getOrDefault((n-1) + (col - row),false) == true)
			return false;
		return true;
	}
	static public void nQueen(int col,int board[][],int n,HashMap<Integer,Boolean> rowMap,HashMap<Integer,Boolean> upperDiagonal,HashMap<Integer,Boolean> lowerDiagonal) {
		if (col >= n) {
            printBoard(board, n);
            return;
        }
		for(int row = 0; row < n; ++row) {
			if(isSafe(row,col,board,n,rowMap,upperDiagonal,lowerDiagonal)) {
				board[row][col] = 1;
				rowMap.put(row,true);
				lowerDiagonal.put(row+col,true);
				upperDiagonal.put((n-1) + (col - row),true);
				nQueen(col + 1,board,n,rowMap,upperDiagonal,lowerDiagonal);
				upperDiagonal.put((n-1) + (col - row),false);
				lowerDiagonal.put(row+col,false);
				rowMap.put(row,false);
				board[row][col] = 0;
			}
		}
	}
	static public void printBoard(int[][] board, int n) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
	public static void main(String[] args) {
		System.out.println("Enter the number of Queens:");
		int n = sc.nextInt();
		int[][] board = new int[n][n];
		HashMap <Integer,Boolean> rowMap = new HashMap<>();
		HashMap <Integer,Boolean> upperDiagonal = new HashMap<>();
		HashMap <Integer,Boolean> lowerDiagonal = new HashMap<>();
		int col = 0;
		nQueen(col,board,n,rowMap,upperDiagonal,lowerDiagonal);
	}
}
