package daa;
import java.util.*;
public class TspUsingLCBB {
    static int N; // Number of cities
    static final int INF = (int) 1e5; // A large value representing infinity

    // Function to calculate the reduced matrix and return the reduction cost
    static int reduceMatrix(int[][] mat) {
        int reductionCost = 0;

        // Reduce each row
        for (int i = 0; i < N; i++) {
            int min = INF;
            for (int j = 0; j < N; j++) {
                min = Math.min(min, mat[i][j]);
            }
            if (min != INF && min > 0) {
                for (int j = 0; j < N; j++) {
                    if (mat[i][j] != INF) {
                        mat[i][j] -= min;
                    }
                }
                reductionCost += min;
            }
        }

        // Reduce each column
        for (int i = 0; i < N; i++) {
            int min = INF;
            for (int j = 0; j < N; j++) {
                min = Math.min(min, mat[j][i]);
            }
            if (min != INF && min > 0) {
                for (int j = 0; j < N; j++) {
                    if (mat[j][i] != INF) {
                        mat[j][i] -= min;
                    }
                }
                reductionCost += min;
            }
        }

        return reductionCost;
    }

    // Function to solve TSP using Branch and Bound
    static void solveTSP(int[][] mat) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0])); // Min-heap
        List<Integer> optimalPath = new ArrayList<>();

        // Copy the matrix to avoid modifying the original
        int[][] rootMat = new int[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(mat[i], 0, rootMat[i], 0, N);
        }

        int rootCost = reduceMatrix(rootMat); // Initial cost after reducing the matrix
        pq.add(new int[]{rootCost, 0, -1, 0}); // {cost, currentVertex, parentVertex, level}

        int minCost = INF;
        int[] bestState = null;
        int[][] bestMat = null;

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int currCost = curr[0];
            int currVertex = curr[1];
            int parentVertex = curr[2];
            int level = curr[3];

            // If current cost exceeds the minimum cost found, skip processing this branch
            if (currCost >= minCost) continue;

            // Check if this is the last level
            if (level == N - 1) {
                if (currCost < minCost) {
                    minCost = currCost;
                    bestState = curr;
                    bestMat = rootMat;
                }
                continue;
            }

            // Explore children nodes
            for (int i = 0; i < N; i++) {
                if (rootMat[currVertex][i] != INF) {
                    int[][] childMat = new int[N][N];
                    for (int x = 0; x < N; x++) {
                        System.arraycopy(rootMat[x], 0, childMat[x], 0, N);
                    }

                    // Set constraints in the child matrix
                    for (int k = 0; k < N; k++) {
                        childMat[currVertex][k] = INF;
                        childMat[k][i] = INF;
                    }
                    childMat[i][0] = INF;

                    int newCost = currCost + rootMat[currVertex][i];
                    newCost += reduceMatrix(childMat);
                    pq.add(new int[]{newCost, i, currVertex, level + 1});
                }
            }
        }

        // Output the result
        System.out.println("The optimal cost of TSP is : " + minCost);
        System.out.print("The optimal path is : ");
        if (bestState != null) {
            int vertex = bestState[1];
            while (vertex != -1) {
                optimalPath.add(vertex);
                vertex = bestState[2]; // Trace parent
            }
            for (int city : optimalPath) {
                System.out.print(city + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of cities (N): ");
        N = sc.nextInt();

        int[][] mat = new int[N][N];
        System.out.println("Enter the cost matrix (use a large number like 100000 to represent INF):");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                mat[i][j] = sc.nextInt();
            }
        }

        solveTSP(mat);

        sc.close();
    }
}