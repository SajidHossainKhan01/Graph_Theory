from __future__ import annotations
from math import sqrt


def get_neighbors(n: int, r: int, c: int):
    neighbors: list[tuple[int,int]] = []
    neighbors.extend([(i, c) for i in range(n) if i != r])
    neighbors.extend([(r, j) for j in range(n) if j != c])
    i: int; j: int
    for i in range(int(r//sqrt(n)*sqrt(n)), int(r//sqrt(n)*sqrt(n)+sqrt(n))):
        for j in range(int(c//sqrt(n)*sqrt(n)), int(c//sqrt(n)*sqrt(n)+sqrt(n))):
            if (i, j) != (r, c):
                neighbors.append((i, j))
    return neighbors


def rec(grid: list[list[int]], r: int, c: int) -> bool:
    n: int = len(grid)
    if r == n:
        return True
    used: set[int] = set([grid[i][j] for i, j in get_neighbors(n, r, c)])
    next_r: int; next_c: int
    next_r, next_c = (r, c+1) if c < n-1 else (r+1, 0)
    if grid[r][c] != 0:
        return rec(grid, next_r, next_c)
    color: int
    for color in range(1, n+1):
        if color not in used:
            grid[r][c] = color
            if rec(grid, next_r, next_c):
                return True
    grid[r][c] = 0
    return False


def sudoku_solver(grid: list[list[int]]) -> list[list[int]]:
    rec(grid, 0, 0)
    return grid


if __name__ == '__main__':
    grid: list[list[int]] = [
        [0, 0, 4, 0, 5, 0, 0, 0, 0],
        [9, 0, 0, 7, 3, 4, 6, 0, 0],
        [0, 0, 3, 0, 2, 1, 0, 4, 9],
        [0, 3, 5, 0, 9, 0, 4, 8, 0],
        [0, 9, 0, 0, 0, 0, 0, 3, 0],
        [0, 7, 6, 0, 1, 0, 9, 2, 0],
        [3, 1, 0, 9, 7, 0, 2, 0, 0],
        [0, 0, 9, 1, 8, 2, 0, 0, 3],
        [0, 0, 0, 0, 6, 0, 1, 0, 0]
    ]

    sudoku_solver(grid)
    print(*grid, sep='\n')
    # Output:
    # [2, 6, 4, 8, 5, 9, 3, 1, 7]
    # [9, 8, 1, 7, 3, 4, 6, 5, 2]
    # [7, 5, 3, 6, 2, 1, 8, 4, 9]
    # [1, 3, 5, 2, 9, 7, 4, 8, 6]
    # [8, 9, 2, 5, 4, 6, 7, 3, 1]
    # [4, 7, 6, 3, 1, 8, 9, 2, 5]
    # [3, 1, 8, 9, 7, 5, 2, 6, 4]
    # [6, 4, 9, 1, 8, 2, 5, 7, 3]
    # [5, 2, 7, 4, 6, 3, 1, 9, 8]
    {"threads":[{"position":0,"start":0,"end":1013,"connection":"idle"},{"position":1014,"start":1014,"end":2025,"connection":"open"}],"url":"https://att-c.udemycdn.com/2023-02-12_20-29-59-408f309c645e0f85815e825ef9f6850b/original.py?response-content-disposition=attachment%3B+filename%3Dsudoku_solver.py&Expires=1693145340&Signature=bmhxNkviOcj-KaUaEb~6wjM35oK-GzRAhgB6IyxGitsfxFke8cIESk4JhVJiokI-iiSJkP6Pox5y0KgTb5bmQQZz0lgua~nVZYhqK-KN4UgCvMVnlbaNMCKWw7t46mcdMyxyaQwccUPNzVBOuynUnLZcPfKrq0vp8CwEM31P4B38p5PJHyp4kVrb05vrhgHno7ltqFAoDKEEIkQB2Y0nk8Ok3s0px-vJDnboJQ4~mm6BiFWDZt3h66jIVYRG7Y9gHZ8IQll3INBit35TZ7dsoLWkn4YvZDcxPxiWLgWye1mIWbR3WnVrdaItSqDRQU1OeDyNxaA04-EKmurvdo6ixA__&Key-Pair-Id=APKAITJV77WS5ZT7262A","method":"GET","port":443,"downloadSize":2025,"headers":{"content-type":"binary/octet-stream","content-length":"2025","connection":"close","date":"Sun, 27 Aug 2023 09:41:18 GMT","x-amz-replication-status":"COMPLETED","last-modified":"Sun, 12 Feb 2023 20:30:02 GMT","etag":"\"544b1ecb934415e83bc6e8bd5f82b3ba\"","x-amz-server-side-encryption":"AES256","x-amz-meta-qqfilename":"sudoku_solver.py","x-amz-version-id":"beh_jaWkwwxLfMlNa7a085y29ZeG.WDX","content-disposition":"attachment; filename=sudoku_solver.py","accept-ranges":"bytes","server":"AmazonS3","x-cache":"Miss from cloudfront","via":"1.1 5e828cc6ff056cb59ec35c3467ec45f4.cloudfront.net (CloudFront)","x-amz-cf-pop":"AMS1-C1","x-amz-cf-id":"ZcHH81sumeaJSqBHPi8cARLgUl1GD66UK_ajKD9eEW4DgfumSKULRA==","x-cdn":"cf-cloudfront","vary":"Origin"}}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            