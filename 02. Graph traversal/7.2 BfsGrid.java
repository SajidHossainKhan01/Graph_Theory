import java.util.*;

public class BfsGrid {

    public static void gridBfs(int[][] grid, int i, int j) {
        int n = grid.length, m = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i, j});
        grid[i][j] = 2;
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            i = cell[0];
            j = cell[1];
            grid[i][j] = 2;
            System.out.println(i + " " + j);
            int[][] adjacentCells = {{i+1, j}, {i-1, j}, {i, j+1}, {i, j-1}};
            for (int[] nextCell : adjacentCells) {
                int nextI = nextCell[0], nextJ = nextCell[1];
                if (nextI >= 0 && nextI < n && nextJ >= 0 && nextJ < m && grid[nextI][nextJ] == 0) {
                    queue.add(new int[]{nextI, nextJ});
                    grid[nextI][nextJ] = 2;
                }
            }
        }
        for (i = 0; i < n; i++)
            for (j = 0; j < m; j++)
                if (grid[i][j] == 2)
                    grid[i][j] = 0;
    }

    public static void main(String[] args) {
        int[][] grid = {{0, 0, 0, 0, 1, 0, 0, 0},
                        {0, 1, 1, 0, 1, 1, 1, 0},
                        {0, 1, 0, 1, 0, 0, 1, 0},
                        {0, 1, 0, 1, 1, 0, 1, 1},
                        {0, 1, 0, 1, 1, 0, 1, 0},
                        {0, 1, 1, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 1, 0, 1, 0}};
        gridBfs(grid, 2, 0);

        /* Output:
        2 0
        3 0
        1 0
        4 0
        0 0
        5 0
        0 1
        6 0
        0 2
        7 0
        6 1
        0 3
        7 1
        6 2
        1 3
        7 2
        6 3
        7 3
        6 4
        6 5
        7 5
        */
    }
}
