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


def dinic(graph: dict[any,list[tuple[any,float]]]) -> float:
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
    return total_flow


if __name__ == '__main__':

    graph_1: dict[any,list[tuple[any,float]]] = {
        's': [('A', 10), ('B', 10)],
        'A': [('C', 4), ('D', 8)],
        'B': [('D', 8)],
        'C': [('E', 6)],
        'D': [('F', 6), ('E', 8)],
        'E': [('F', 6), ('t', 8)],
        'F': [('t', 10)]
    }

    graph_2: dict[any,list[tuple[any,float]]] = {
        's': [('A', 10), ('B', 10), ('C', 10)],
        'A': [('B', 6), ('D', 8)],
        'B': [('D', 4), ('E', 7)],
        'C': [('F', 8)],
        'D': [('G', 6), ('H', 6)],
        'E': [('C', 7), ('D', 6), ('I', 4)],
        'F': [('E', 5), ('I', 6)],
        'G': [('H', 8), ('t', 10)],
        'H': [('E', 5), ('t', 10)],
        'I': [('H', 6), ('t', 8)]
    }

    print(f'Graph 1: Maximum flow = {dinic(graph_1)}')
    print(f'Graph 2: Maximum flow = {dinic(graph_2)}')

    # Output:
    # Graph 1: Maximum flow = 18
    # Graph 2: Maximum flow = 22 
