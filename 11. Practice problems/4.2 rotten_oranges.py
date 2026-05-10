from __future__ import annotations
from queue import Queue


def oranges_rotting(grid: list[list[int]]) -> int:
    n: int; m: int
    n, m = len(grid), len(grid[0])
    queue: Queue[tuple[int,int,int]] = Queue()
    nb_oranges: int = 0
    nb_rotten: int = 0
    max_time: int = 0
    i: int; j: int
    for i in range(n):
        for j in range(m):
            if grid[i][j] != 0:
                nb_oranges += 1
            if grid[i][j] == 2:
                queue.put((i, j, 0))
    if nb_oranges == 0:
        return 0
    while not queue.empty():
        i: int; j: int; time: int
        i, j, time = queue.get()
        max_time = max(max_time, time)
        nb_rotten += 1
        if nb_rotten == nb_oranges:
            return max_time
        nei_i: int; nei_j: int
        for nei_i, nei_j in [(i+1, j), (i-1, j), (i, j+1), (i, j-1)]:
            if 0 <= nei_i < n and 0 <= nei_j < m and grid[nei_i][nei_j] == 1:
                grid[nei_i][nei_j] = 2
                queue.put((nei_i, nei_j, time + 1))
    return -1


if __name__ == '__main__':
    grid: list[list[int]] = [
        [1, 2, 1, 0, 0, 1],
        [1, 1, 0, 0, 1, 1],
        [0, 1, 0, 0, 2, 1],
        [1, 1, 1, 0, 1, 0]
    ]

    print(f'Minimum number of minutes: {oranges_rotting(grid)}')

    # Minimum number of minutes: 4
