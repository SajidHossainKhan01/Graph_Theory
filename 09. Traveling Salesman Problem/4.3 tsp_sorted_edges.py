from __future__ import annotations
from collections import defaultdict


class DisjointSet:
    def __init__(self, elems: list[any]):
        self.parent: dict[any,any] = {}
        self.size: dict[any,int] = {}
        elem: any
        for elem in elems:
            self.make_set(elem)

    def make_set(self, a: any) -> None:
        self.parent[a] = a
        self.size[a] = 1

    def find(self, a: any) -> any:
        if self.parent[a] == a:
            return a
        else:
            self.parent[a] = self.find(self.parent[a])
            return self.parent[a]

    def union(self, a: any, b: any) -> None:
        root_a = self.find(a)
        root_b = self.find(b)
        if root_a == root_b:
            return
        elif self.size[root_a] <= self.size[root_b]:
            self.parent[root_a] = root_b
            self.size[root_b] += self.size[root_a]
        else:
            self.parent[root_b] = root_a
            self.size[root_a] += self.size[root_b]


def tsp_sorted_edges(graph: list[list[float]]) -> tuple[float,list[int]]:
    n: int = len(graph)
    vertices: list[int] = list(range(n))
    edges: list[tuple[int,int]] = [(i, j) for i in range(n) for j in range(i+1, n)]
    edges.sort(key=lambda edge: graph[edge[0]][edge[1]])
    tour_graph: dict[int,list[tuple[int,float]]] = defaultdict(lambda: [])
    ds: DisjointSet[int] = DisjointSet(vertices)
    nb_edges: int = 0
    u: int; v: int
    for u, v in edges:
        w: float = graph[u][v]
        if ds.find(u) != ds.find(v) and len(tour_graph[u]) < 2 and len(tour_graph[v]) < 2:
            tour_graph[u].append((v, w))
            tour_graph[v].append((u, w))
            nb_edges += 1
            ds.union(u, v)
            if nb_edges == len(vertices)-1:
                break
    start: int; end: int
    start, end = [u for u in tour_graph if len(tour_graph[u]) == 1]
    tour_graph[start].append((end, graph[start][end]))
    tour_graph[end].append((start, graph[start][end]))
    tour: list[int] = [start]
    total_cost: float = 0
    vertex: int = start
    for _ in range(n):
        neighbor: int; w: float
        neighbor, w = tour_graph[vertex][0]
        total_cost += w
        tour.append(neighbor)
        tour_graph[vertex].remove((neighbor, w))
        tour_graph[neighbor].remove((vertex, w))
        vertex = neighbor
    return total_cost, tour


if __name__ == '__main__':
    points: list[tuple[float,float]] = [(1, 1), (2, 5), (2, 9), (4, 0), (5, 8), (5, 11), (6, 4), (9, 2), (10, 11), (12, 8)]
    graph: list[list[float]] = [[round(((x1-x2)**2+(y1-y2)**2)**0.5, 2) for x2, y2 in points] for x1, y1 in points]
    min_cost: float; min_tour: list[int]
    min_cost, min_tour = tsp_sorted_edges(graph)
    min_tour = [points[i] for i in min_tour]
    print(f'Shortest tour: {min_tour} (total length: {min_cost})')

    # Output: Shortest tour: [7, 6, 3, 0, 1, 2, 4, 5, 8, 9, 7] (total length: 40.84)
