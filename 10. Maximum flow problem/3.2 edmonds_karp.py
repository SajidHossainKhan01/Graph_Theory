from __future__ import annotations
from collections import defaultdict
from queue import Queue


def bfs(residual: dict[any,dict[any,float]], source: any, sink: any) -> list[tuple[any,any]] | None:
    queue: Queue[any] = Queue()
    queue.put(source)
    prev: dict[any,any] = {source: None}
    while not queue.empty():
        u: any = queue.get()
        if u == sink:
            path = []
            while prev[u] is not None:
                path.append((prev[u], u))
                u = prev[u]
            path.reverse()
            return path
        v: any
        for v in residual[u]:
            if v not in prev and residual[u][v] > 0:
                queue.put(v)
                prev[v] = u
    return None


def edmonds_karp(graph: dict[any,list[tuple[any,float]]]) -> float:
    residual: dict[any,dict[any,float]] = defaultdict(lambda: {})
    u: any; v: any; w: float
    for u in graph:
        for v, w in graph[u]:
            residual[u][v] = w
            residual[v][u] = 0
    total_flow: float = 0
    while True:
        path: list[tuple[any,float]] = bfs(residual, 's', 't')
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

    print(f'Graph 1: Maximum flow = {edmonds_karp(graph_1)}')
    print(f'Graph 2: Maximum flow = {edmonds_karp(graph_2)}')

    # Output:
    # Graph 1: Maximum flow = 18
    # Graph 2: Maximum flow = 22 
