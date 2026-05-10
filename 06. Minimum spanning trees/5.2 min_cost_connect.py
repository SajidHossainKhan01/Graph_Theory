from __future__ import annotations
from collections import defaultdict
import fibheap as fh


def manhattan(p0: tuple[int,int], p1: tuple[int,int]) -> float:
    return abs(p0[0]-p1[0]) + abs(p0[1]-p1[1])


def min_cost_connect(points: list[list[int,int]]) -> float:
    points: list[tuple[int,int]] = [tuple(point) for point in points]
    total_cost: float = 0
    mst: dict[tuple[int,int],list[tuple[tuple[int,int],float]]] = defaultdict(lambda: [])
    start: tuple[int,int] = points[0]
    cost: dict[tuple[int,int],float] = {}
    parent: dict[tuple[int,int],tuple[int,int]] = {}
    nodes: dict[tuple[int,int],fh.Node] = {}
    queue: fh.Fheap = fh.makefheap()
    u: tuple[int,int]
    for u in points:
        cost[u] = 0 if u == start else float('inf')
        parent[u] = None
        nodes[u] = fh.Node((cost[u], u))
        queue.insert(nodes[u])
    while len(mst) < len(points):
        u: tuple[int,int] = queue.extract_min().key[1]
        if u != start:
            mst[u].append((parent[u], cost[u]))
            mst[parent[u]].append((u, cost[u]))
            total_cost += cost[u]
        v: tuple[int,int]
        for v in points:
            w: float = manhattan(u, v)
            if v not in mst and w < cost[v]:
                cost[v] = w
                parent[v] = u
                queue.decrease_key(nodes[v], (cost[v], v))
    return total_cost


if __name__ == '__main__':
    points: list[list[int,int]] = [[0, 0], [2, 2], [3, 10], [5, 2], [7, 0]]
    print(min_cost_connect(points))
    # Output: 20