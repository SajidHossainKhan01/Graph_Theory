from __future__ import annotations
from queue import Queue


def grid_bfs(grid: list[list[int]], i: int, j: int) -> None:
    n:int; m: int
    n, m = len(grid), len(grid[0])
    queue: Queue[tuple[int,int]] = Queue()
    queue.put((i, j))
    grid[i][j] = 2
    while not queue.empty():
        i: int; j: int
        i, j = queue.get()
        print(i, j)
        adjacent_cells: list[tuple[int,int]] = [(i+1, j), (i-1, j), (i, j+1), (i, j-1)]
        next_i: int; next_j: int
        for next_i, next_j in adjacent_cells:
            if 0 <= next_i < n and 0 <= next_j < m and grid[next_i][next_j] == 0:
                queue.put((next_i, next_j))
                grid[next_i][next_j] = 2


if __name__ == '__main__':
    grid: list[list[int]] = [[0, 0, 0, 0, 1, 0, 0, 0],
                            [0, 1, 1, 0, 1, 1, 1, 0],
                            [0, 1, 0, 1, 0, 0, 1, 0],
                            [0, 1, 0, 1, 1, 0, 1, 1],
                            [0, 1, 0, 1, 1, 0, 1, 0],
                            [0, 1, 1, 1, 1, 1, 0, 0],
                            [0, 0, 0, 0, 0, 0, 1, 0],
                            [0, 0, 0, 0, 1, 0, 1, 0]]
    grid_bfs(grid, 2, 0)

    """Output:
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
    7 5"""
