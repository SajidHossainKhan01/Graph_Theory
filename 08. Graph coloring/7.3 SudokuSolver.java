import java.util.*;
import java.lang.Math;

public class SudokuSolver {

    public static ArrayList<int[]> getNeighbors(int n, int r, int c) {
        ArrayList<int[]> neighbors = new ArrayList<int[]>();
        for (int i = 0; i < n; i++) {
            if (i != r) {
                int[] arr = new int[] {i, c};
                neighbors.add(arr);
            }
        }
        for (int j = 0; j < n; j++) {
            if (j != c) {
                int[] arr = new int[] {r, j};
                neighbors.add(arr);
            }
        }
        int blockStartRow = (int)(Math.floor(r/Math.sqrt(n)) * Math.sqrt(n));
        int blockEndRow = (int)(Math.floor(r/Math.sqrt(n)) * Math.sqrt(n) + Math.sqrt(n));
        int blockStartCol = (int)(Math.floor(c/Math.sqrt(n)) * Math.sqrt(n));
        int blockEndCol = (int)(Math.floor(c/Math.sqrt(n)) * Math.sqrt(n) + Math.sqrt(n));
        for (int i = blockStartRow; i < blockEndRow; i++) {
            for (int j = blockStartCol; j < blockEndCol; j++) {
                if (i != r && j != c) {
                    int[] arr = new int[] {i, j};
                    neighbors.add(arr);
                }
            }
        }
        return neighbors;
    }

    public static boolean rec(int[][] grid, int r, int c) {
        int n = grid.length;
        if (r == n)
            return true;
        HashSet<Integer> used = new HashSet<>();
        for (int[] neighbor : getNeighbors(n, r, c)) {
            int row = neighbor[0];
            int col = neighbor[1];
            used.add(grid[row][col]);
        }
        int nextR = r;
        int nextC = c+1;
        if (c == n-1) {
            nextR = r+1;
            nextC = 0;
        }
        if (grid[r][c] != 0)
            return rec(grid, nextR, nextC);
        for (int color = 1; color <= n; color++) {
            if (!used.contains(color)) {
                grid[r][c] = color;
                if (rec(grid, nextR, nextC))
                    return true;
            }
        }
        grid[r][c] = 0;
        return false;
    }

    public static int[][] sudokuSolver(int[][] grid) {
        rec(grid, 0, 0);
        return grid;
    }

    public static void main(String[] args) {
        int[][] grid = new int[][] {
            {0, 0, 4, 0, 5, 0, 0, 0, 0},
            {9, 0, 0, 7, 3, 4, 6, 0, 0},
            {0, 0, 3, 0, 2, 1, 0, 4, 9},
            {0, 3, 5, 0, 9, 0, 4, 8, 0},
            {0, 9, 0, 0, 0, 0, 0, 3, 0},
            {0, 7, 6, 0, 1, 0, 9, 2, 0},
            {3, 1, 0, 9, 7, 0, 2, 0, 0},
            {0, 0, 9, 1, 8, 2, 0, 0, 3},
            {0, 0, 0, 0, 6, 0, 1, 0, 0}
        };

        int[][] solvedGrid = sudokuSolver(grid);
        for (int i = 0; i < solvedGrid.length; i++) {
            for (int j = 0; j < solvedGrid.length; j++)
                System.out.print(solvedGrid[i][j] + " ");
            System.out.println();
        }
        // Output:
        // 2 6 4 8 5 9 3 1 7 
        // 9 8 1 7 3 4 6 5 2 
        // 7 5 3 6 2 1 8 4 9
        // 1 3 5 2 9 7 4 8 6
        // 8 9 2 5 4 6 7 3 1
        // 4 7 6 3 1 8 9 2 5
        // 3 1 8 9 7 5 2 6 4
        // 6 4 9 1 8 2 5 7 3
        // 5 2 7 4 6 3 1 9 8
    }
}