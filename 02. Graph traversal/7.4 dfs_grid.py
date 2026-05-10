from __future__ import annotations


def dfs(grid: list[list[int]], i: int, j: int) -> None:
    n: int; m: int
    n, m = len(grid), len(grid[0])
    grid[i][j] = 2 
    print(i, j)
    adjacent_cells: list[tuple[int,int]] = [(i+1, j), (i-1, j), (i, j+1), (i, j-1)]
    next_i: int; next_j: int
    for next_i, next_j in adjacent_cells:
        if 0 <= next_i < n and 0 <= next_j < m and grid[next_i][next_j] == 0:
            dfs(grid, next_i, next_j)


def grid_dfs(grid: list[list[int]], i: int, j: int) -> None:
    dfs(grid, i, j)
    i: int
    for i in range(len(grid)):
        j: int
        for j in range(len(grid[0])):
            if grid[i][j] == 2:
                grid[i][j] = 0


if __name__ == '__main__':
    grid: list[list[int]] = [[0, 0, 0, 0, 1, 0, 0, 0],
                            [0, 1, 1, 0, 1, 1, 1, 0],
                            [0, 1, 0, 1, 0, 0, 1, 0],
                            [0, 1, 0, 1, 1, 0, 1, 1],
                            [0, 1, 0, 1, 1, 0, 1, 0],
                            [0, 1, 1, 1, 1, 1, 0, 0],
                            [0, 0, 0, 0, 0, 0, 1, 0],
                            [0, 0, 0, 0, 1, 0, 1, 0]]
    grid_dfs(grid, 2, 0)

    """Output:
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
    1 3"""
