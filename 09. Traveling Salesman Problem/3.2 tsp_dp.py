from __future__ import annotations


def tsp(mat: list[list[float]], s: int, A: int, memo: dict[tuple[int,int],float], nxt: dict[tuple[int,int],int]) -> float:
    if (s, A) in memo:
        return memo[(s, A)]
    if not A:
        return mat[s][0]
    else:
        memo[(s, A)], nxt[(s, A)] = min([(tsp(mat, i, A^(1<<i), memo, nxt)+mat[s][i], i) for i in range(len(mat)) if A&(1<<i)])
        return memo[(s, A)]


def tsp_dp(mat: list[list[float]]) -> tuple[float,list[int]]:
    n: int = len(mat)
    s: int = 0
    A: int = (1<<n) - 1
    A ^= 1
    memo: dict[tuple[int,int],float] = {}
    nxt: dict[tuple[int,int],int] = {}
    min_cost: float = tsp(mat, s, A, memo, nxt)
    min_tour: list[int] = [s]
    while A:
        s = nxt[(s, A)]
        min_tour.append(s)
        A ^= 1<<s
    min_tour.append(0)
    return min_cost, min_tour


if __name__ == '__main__':

    points: list[tuple[float,float]] = [(1, 1), (2, 5), (2, 9), (4, 0), (5, 8), (6, 3), (7, 7), (8, 2), (9, 6), (10, 10), (11, 4), (12, 9)]
    graph: list[list[float]] = [[round(((x1-x2)**2+(y1-y2)**2)**0.5, 2) for x2, y2 in points] for x1, y1 in points]  # adjacency matrix with Euclidean distance

    min_cost, min_tour = tsp_dp(graph)
    min_tour = [points[i] for i in min_tour]
    print(f'Shortest tour: {min_tour} (total length: {min_cost})')

    # Output: Shortest tour: [(1, 1), (4, 0), (6, 3), (8, 2), (11, 4), (9, 6), (12, 9), (10, 10), (7, 7), (5, 8), (2, 9), (2, 5), (1, 1)] (total length: 39.69)
