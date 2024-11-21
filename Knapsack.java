package daa;
import java.util.*;
public class Knapsack {
	static Scanner sc = new Scanner(System.in);
	static public int knapsackUsingDp(int[] weights,int[] values,int n,int M) {
		int[][] dp = new int[n+1][M+1];
		for(int i = 0; i <= n; ++i) {
			for(int j = 0; j <= M; ++j) {
				if(i == 0 || j == 0) {
					dp[i][j] = 0;
				}
				else if(weights[i - 1] <= j) {
					dp[i][j] = Math.max(dp[i-1][j],values[i - 1] + dp[i-1][j- weights[i - 1]]);
				}
				else {
					dp[i][j] = dp[i-1][j];
				}
			}
		}
		return dp[n][M];
	}
	static public void main(String[] args) {
		System.out.println("Enter the Capacity of Knapsack:");
		int M = sc.nextInt();
		System.out.println("Enter the number of weights:");
		int n = sc.nextInt();
		int[] weights = new int[n];
		int[] values = new int[n];
		System.out.println("Enter the weights:");
		for(int i = 0 ; i < n; ++i) {
			weights[i] = sc.nextInt();
		}
		System.out.println("Enter the values:");
		for(int i = 0 ; i < n; ++i) {
			values[i] = sc.nextInt();
		}
		System.out.println("The maximum value is " + knapsackUsingDp(weights,values,n,M)); 
	}
}
