from __future__ import annotations
from collections import defaultdict
import random
import fibheap as fh


def prim(mat: list[list[float]]) -> dict[int,list[tuple[int,float]]]:
    n = len(mat)
    mst: dict[int,list[tuple[int,float]]] = defaultdict(lambda: [])
    start: int = 0
    cost: dict[int,float] = {}
    parent: dict[int,int] = {}
    nodes: dict[int,fh.Node] = {}
    queue: fh.Fheap = fh.makefheap()
    u: int
    for u in range(n):
        cost[u] = 0 if u == start else float('inf')
        parent[u] = None
        nodes[u] = fh.Node((cost[u], u))
        queue.insert(nodes[u])
    while len(mst) < n:
        u: int = queue.extract_min().key[1]
        if u != start:
            mst[u].append((parent[u], cost[u]))
            mst[parent[u]].append((u, cost[u]))
        v: int; w: float
        for v in range(n):
            w = mat[u][v]
            if v not in mst and w < cost[v]:
                cost[v] = w
                parent[v] = u
                queue.decrease_key(nodes[v], (cost[v], v))
    return mst


def min_weight_perfect_matching(mat: list[list[any]], odds: list[any]) -> list[tuple[any,any,float]]:
    random.shuffle(odds)
    matching: list[tuple[any,any,float]] = []
    while odds:
        u: any = odds.pop()
        closest: any = None
        min_dist: float = float('inf')
        for v in odds:
            if u != v and mat[u][v] < min_dist:
                min_dist = mat[u][v]
                closest = v
        matching.append((u, closest, min_dist))
        odds.remove(closest)
    return matching


def dfs(graph: dict[any,list[tuple[any,float]]], vertex: any, deg: dict[any,int], output: list[any], edges: dict[tuple[any,any],int]) -> None:
    while deg[vertex] > 0:
        deg[vertex] -= 1
        neighbor: any = graph[vertex][deg[vertex]][0]
        if edges[tuple(sorted((vertex, neighbor)))] > 0:
            if vertex == neighbor:
                edges[tuple(sorted((vertex, neighbor)))] -= 1
            else:
                edges[tuple(sorted((vertex, neighbor)))] -= 2
            dfs(graph, neighbor, deg, output, edges)
    output.append(vertex)
        
        
def hierholzer(graph: dict[any,list[tuple[any,float]]]) -> list[any]:
    deg: dict[any,int] = {u: len(graph[u]) for u in graph}
    edges: dict[tuple[any,any],int] = defaultdict(lambda: 0)
    for u in graph:
        for v, w in graph[u]:
            edges[tuple(sorted((u, v)))] += 1
    start: any = 0
    output: list[any] = []
    dfs(graph, start, deg, output, edges)
    output.reverse()
    return output


def shortcutting(circuit: list[any]) -> list[any]:
    visited = set()
    output = []
    for vertex in circuit:
        if vertex not in visited:
            output.append(vertex)
            visited.add(vertex)
    output.append(circuit[0])
    return output


def tsp_christofides(mat: list[list[float]]) -> tuple[float,list[int]]:
    mst: dict[int,list[tuple[int,float]]] = prim(mat)
    odds: list[int] = [u for u in mst if len(mst[u]) % 2 == 1]
    matching: list[tuple[int,int,float]] = min_weight_perfect_matching(mat, odds)
    u: int; v: int; w: float
    for u, v, w in matching:
        mst[u].append((v, w))
        mst[v].append((u, w))
    eulerian: list[int] = hierholzer(mst)
    tour: list[int] = shortcutting(eulerian)
    cost: float = 0
    for i in range(len(tour)-1):
        cost += mat[tour[i]][tour[i+1]]
    return cost, tour


if __name__ == '__main__':
    points = [(10, 0), (9, -3), (1, 3), (6, 1), (4, 3.5), (5, 4.2), (5.8, 2.8), (7.5, 6.5), (9, 1), (10, 5), (0, 4.1), (8.8, 4.2), (7.6, 2.5)]
    graph = [[round(((x1-x2)**2+(y1-y2)**2)**0.5, 2) for x2, y2 in points] for x1, y1 in points]
    cost, tour = tsp_christofides(graph)
    print(f'Produced tour: {tour} (cost = {cost})')
    
    # Output:
    # Produced tour: [0, 1, 3, 6, 10, 2, 4, 5, 12, 11, 7, 9, 8, 0] (cost = 37.94)
