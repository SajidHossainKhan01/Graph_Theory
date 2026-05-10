import java.util.*;

public class RottenOranges {

    public static int orangesRotting(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int nbOranges = 0;
        int nbRotten = 0;
        int maxTime = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] != 0)
                    nbOranges++;
                if (grid[i][j] == 2)
                    queue.add(new int[]{i, j, 0});
            }
        }
        if (nbOranges == 0)
            return 0;
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int i = curr[0];
            int j = curr[1];
            int time = curr[2];
            maxTime = Math.max(maxTime, time);
            nbRotten++;
            if (nbRotten == nbOranges)
                return maxTime;
            int neiI; int neiJ;
            for (int[] neighbor : new int[][]{{i+1, j}, {i-1, j}, {i, j+1}, {i, j-1}}) {
                neiI = neighbor[0];
                neiJ = neighbor[1];
                if (neiI >= 0 && neiI < n && neiJ >= 0 && neiJ < m && grid[neiI][neiJ] == 1) {
                    grid[neiI][neiJ] = 2;
                    queue.add(new int[]{neiI, neiJ, time+1});
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[][] grid = new int[][]{
            {1, 2, 1, 0, 0, 1},
            {1, 1, 0, 0, 1, 1},
            {0, 1, 0, 0, 2, 1},
            {1, 1, 1, 0, 1, 0}
        };
        
        System.out.println("Minimum number of minutes: " + orangesRotting(grid));

        // Minimum number of minutes: 4
    }
}
