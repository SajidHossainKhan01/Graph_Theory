from __future__ import annotations
from bellman_ford import *
from dijkstra import *


def reconstruct(prev: dict[any,any], src: any, dest: any) -> list[any]:
    if src == dest:
        return [src]
    elif prev[src][dest] is None:
        return []
    path: list[any] = [dest]
    vertex: any = dest
    while prev[src][vertex] is not None:
        vertex: any = prev[src][vertex]
        path.append(vertex)
    return path[::-1]
  

def johnson(graph: dict[any,tuple[any,float]]) -> tuple[dict[any,dict[any,float]],dict[any,dict[any,any]]] | tuple[None,None]:
    graph: dict[any,tuple[any,float]] = {u: graph[u].copy() for u in graph}
    graph['q'] = []
    u: any
    for u in graph:
        graph['q'].append((u, 0))
    h: dict[any,float] | None; prev: dict[any,dict[any,any]] | None
    h, prev = bellman_ford(graph, 'q')
    if h is None:
        return None, None
    u: any
    for u in graph:
        i: int
        for i in range(len(graph[u])):
            v: any; w: float
            v, w = graph[u][i]
            graph[u][i] = (v, w+h[u]-h[v])
    del graph['q']
    dist: dict[any,float] = {}
    prev: dict[any,any] = {}
    u: any
    for u in graph:
        dist[u], prev[u] = dijkstra(graph, u)
    for u in graph:
        v: any
        for v in graph:
            dist[u][v] += h[v]-h[u]
    return dist, prev
  

if __name__ == '__main__':
    graph: dict[str,list[tuple[str,float]]] = {
        'A': [('B', 18), ('C', -4), ('E', 3)],
        'B': [],
        'C': [('B', 16), ('E', -2), ('D', 14)],
        'D': [('B', 6)],
        'E': [('D', 5)]
    }
    dist: dict[any,dict[any,float]]; prev: dict[any,dict[any,any]]
    dist, prev = johnson(graph)
    for u in dist:
        print(f'{u} : {dist[u]}')

    # Output:
    # A : {'A': 0, 'B': 5, 'C': -4, 'D': -1, 'E': -6}
    # B : {'A': inf, 'B': 0, 'C': inf, 'D': inf, 'E': inf}
    # C : {'A': inf, 'B': 9, 'C': 0, 'D': 3, 'E': -2}
    # D : {'A': inf, 'B': 6, 'C': inf, 'D': 0, 'E': inf}
    # E : {'A': inf, 'B': 11, 'C': inf, 'D': 5, 'E': 0}

    src: str; dest: str
    src, dest = 'A', 'E'
    print(f'Shortest path from {src} to {dest}:')
    print(reconstruct(prev, src, dest))
    # Output:
    # Shortest path from A to E:
    # ['A', 'C', 'E']