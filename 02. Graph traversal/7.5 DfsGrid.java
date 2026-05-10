public class DfsGrid {
    
    public static void dfs(int[][] grid, int i, int j) {
        int n = grid.length, m = grid[0].length;
        grid[i][j] = 2;
        System.out.println(i + " " + j);
        int[][] adjacentCells = {{i+1, j}, {i-1, j}, {i, j+1}, {i, j-1}};
        for (int[] cell : adjacentCells) {
            int nextI = cell[0], nextJ = cell[1];
            if (nextI >= 0 && nextI < n && nextJ >= 0 && nextJ < m && grid[nextI][nextJ] == 0)
                dfs(grid, nextI, nextJ);
        }
    }

    public static void gridDfs(int[][] grid, int i, int j) {
        dfs(grid, i, j);
        for (i = 0; i < grid.length; i++)
            for (j = 0; j < grid[0].length; j++)
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
        gridDfs(grid, 2, 0);

        /* Output:
        2 0
        3 0
        4 0
        5 0
        6 0
        7 0
        7 1
        6 1
        6 2
        7 2
        7 3
        6 3
        6 4
        6 5
        7 5
        1 0
        0 0
        0 1
        0 2
        0 3
        1 3
        */
    }
}
