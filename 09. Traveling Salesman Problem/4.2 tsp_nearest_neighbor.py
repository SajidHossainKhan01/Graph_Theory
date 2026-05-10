from __future__ import annotations


def tsp_nearest_neighbor(graph: list[list[float]], start: int=0) -> tuple[float,list[int]]:
    n: int = len(graph)
    vertex: int = start
    tour: list[int] = [start]
    visited: set[int] = set()
    visited.add(start)
    total_cost: float = 0
    for _ in range(n-1):
        min_cost: float = float('inf')
        min_neighbor: int = -1
        neighbor: int
        for neighbor in range(n):
            if neighbor not in visited and graph[vertex][neighbor] < min_cost:
                min_cost = graph[vertex][neighbor]
                min_neighbor = neighbor
        total_cost += min_cost
        tour.append(min_neighbor)
        visited.add(min_neighbor)
        vertex = min_neighbor
    total_cost += graph[tour[-1]][tour[0]]
    tour.append(tour[0])
    return total_cost, tour


def tsp_repetitive_nearest_neighbor(graph: list[list[float]]) -> tuple[float,list[int]]:
    n: int = len(graph)
    min_cost: float = float('inf')
    min_tour: list[float] = []
    for start in range(n):
        total_cost: float; tour: list[int]
        total_cost, tour = tsp_nearest_neighbor(graph, start)
        if total_cost < min_cost:
            min_cost = total_cost
            min_tour = tour
    return min_cost, min_tour


if __name__ == '__main__':
    points: list[tuple[float,float]] = [(1, 1), (2, 5), (2, 9), (4, 0), (5, 8), (5, 11), (6, 4), (9, 2), (10, 11), (12, 8)]
    graph: list[list[float]] = [[round(((x1-x2)**2+(y1-y2)**2)**0.5, 2) for x2, y2 in points] for x1, y1 in points]
    min_cost: float; min_path: list[int]
    min_cost, min_path = tsp_repetitive_nearest_neighbor(graph)
    min_tour = [points[i] for i in min_path]
    print(f'Shortest tour: {min_tour} (total length: {min_cost})')

    # Output: Shortest tour: [(1, 1), (4, 0), (6, 4), (9, 2), (12, 8), (10, 11), (5, 11), (5, 8), (2, 9), (2, 5), (1, 1)] (total length: 40.839999999999996)
    