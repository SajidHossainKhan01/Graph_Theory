from __future__ import annotations


def rec(graph: list[list[float]], tour: list[int], cost: float, min_tour: float, min_cost: list[int]) -> tuple[float,list[int]]:
    n: int = len(graph)
    if len(tour) == n:
        tour.append(tour[0])
        cost += graph[tour[-2]][tour[-1]]
        if cost < min_cost:
            min_cost = cost
            min_tour = tour.copy()
        cost -= graph[tour[-2]][tour[-1]]
        tour.pop()
        return min_cost, min_tour
    for i in range(n):
        if i not in tour:
            tour.append(i)
            cost += graph[tour[-2]][tour[-1]]
            if cost < min_cost:
                min_cost, min_tour = rec(graph, tour, cost, min_tour, min_cost)
            cost -= graph[tour[-2]][tour[-1]]
            tour.pop()
    return min_cost, min_tour


def tsp_backtracking(graph: list[list[float]]) -> tuple[float,list[int]]:
    start: int = 0
    tour: list[int] = [start]
    cost: float = 0
    min_tour: list[int] = None
    min_cost: float = float('inf')
    min_cost, min_tour = rec(graph, tour, cost, min_tour, min_cost)
    return min_cost, min_tour


if __name__ == '__main__':

    points: list[tuple[int,int]] = [(1, 1), (2, 5), (2, 9), (4, 0), (5, 8), (5, 11), (6, 4), (9, 2), (10, 11), (12, 8)]
    graph: list[list[float]] = [[round(((x1-x2)**2+(y1-y2)**2)**0.5, 2) for x2, y2 in points] for x1, y1 in points]
    min_cost: float; min_tour: list[int]
    min_cost, min_tour = tsp_backtracking(graph)
    print(f'Minimum cost: {min_cost}')
    print(f'Minimum tour: {min_tour}')
