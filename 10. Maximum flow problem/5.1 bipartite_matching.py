from __future__ import annotations
from collections import defaultdict
from queue import Queue


def dfs(residual: dict[any,dict[any,float]], u: any, sink: any, path: list[tuple[any,any]], visited: set[any], level: dict[any,int]) -> list[tuple[any,any]] | None:
    if u == sink:
        return path
    visited.add(u)
    v: any
    for v in residual[u]:
        if v not in visited and residual[u][v] > 0 and level[u] < level[v]:
            path.append((u, v))
            if dfs(residual, v, sink, path, visited, level):
                return path
            path.pop()
    return None


def get_level_map(residual: dict[any,dict[any,float]], source: any) -> dict[any,int]:
    queue: Queue[any] = Queue()
    queue.put(source)
    level: dict[any,int] = {source: 0}
    while not queue.empty():
        u: any = queue.get()
        v: any
        for v in residual[u]:
            if v not in level and residual[u][v] > 0:
                queue.put(v)
                level[v] = level[u]+1
    return level


def dinic(graph: dict[any,list[tuple[any,float]]]) -> dict[any,dict[any,float]]:
    residual: dict[any,dict[any,float]] = defaultdict(lambda: {})
    u: any; v: any; w: float
    for u in graph:
        for v, w in graph[u]:
            residual[u][v] = w
            residual[v][u] = 0
    total_flow: float = 0
    while True:
        level: dict[any,int] = get_level_map(residual, 's')
        if 't' not in level:
            break
        while True:
            path: list[tuple[any,any]] = dfs(residual, 's', 't', [], set(), level)
            if path is None:
                break
            bottleneck: float = min(residual[u][v] for u, v in path)
            total_flow += bottleneck
            u: any; v: any
            for u, v in path:
                residual[u][v] -= bottleneck
                residual[v][u] += bottleneck
    return residual


def bipartite_matching(set1: set[any], set2: set[any], edges: list[tuple[any,any]]) -> list[tuple[any,any]]:
    graph: dict[any,list[tuple[any,float]]] = defaultdict(lambda: [])
    u: any; v: any
    for u, v in edges:
        graph[u].append((v, 1))
    for u in set1:
        graph['s'].append((u, 1))
    for u in set2:
        graph[u].append(('t', 1))
    residual: dict[any,dict[any,float]] = dinic(graph)
    matching: list[tuple[any,any]] = []
    for u in set1:
        for v in residual[u]:
            if v != 's' and residual[u][v] == 0:
                matching.append((u, v))
    return matching
    

if __name__ == '__main__':
    
    set1: set[str] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'}
    set2: set[str] = {'1', '2', '3', '4', '5', '6', '7', '8'}
    edges: list[tuple[str,str]] = [('a', '1'), ('a', '4'), ('b', '1'), ('b', '2'), ('c', '2'), ('c', '5'), ('d', '1'), ('d', '3'), ('d', '6'), ('e', '3'), ('e', '5'), ('e', '6'), ('f', '6'), ('f', '7'), ('g', '5'), ('g', '8'), ('h', '8')]
    print(bipartite_matching(set1, set2, edges))

    # Output: [('b', '1'), ('h', '8'), ('d', '3'), ('f', '7'), ('g', '5'), ('c', '2'), ('a', '4'), ('e', '6')]
    