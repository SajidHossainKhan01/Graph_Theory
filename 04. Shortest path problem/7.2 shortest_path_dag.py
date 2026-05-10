from __future__ import annotations
from collections import defaultdict
from queue import Queue


def topological_sort(graph: dict[any,list[tuple[any,float]]]) -> list[any]:
    indegree: dict[any,int] = defaultdict(lambda: 0)
    u: any
    for u in graph:
        v: any; w: float
        for v, w in graph[u]:
            indegree[v] += 1
    queue: Queue[any] = Queue()
    vertex: any
    for vertex in graph:
        if indegree[vertex] == 0:
            queue.put(vertex)
    ordering: list[any] = []
    while not queue.empty():
        vertex: any = queue.get()
        ordering.append(vertex)
        neighbor: any; weight: float
        for neighbor, weight in graph[vertex]:
            indegree[neighbor] -= 1
            if indegree[neighbor] == 0:
                queue.put(neighbor)
    if len(ordering) < len(graph):
        return []
    return ordering


def reconstruct(prev: dict[any,any], dest: any) -> list[any]:
    if not prev[dest]:
        return None
    path: list[any] = [dest]
    while prev[dest]:
        dest = prev[dest]
        path.append(dest)
    return path[::-1]


# Approach 1: min of in-neighbors
def get_transpose(graph: dict[any,list[tuple[any,float]]]) -> dict[any,list[tuple[any,float]]]:
    tgraph: dict[any,list[tuple[any,float]]] = defaultdict(lambda: [])
    for u in graph:
        for v, w in graph[u]:
            tgraph[v].append((u, w))
    return tgraph


def shortest_path_1(graph: dict[any,list[tuple[any,float]]], src: any) -> tuple[dict[any,float],dict[any,any]]:
    ordering: list[any] = topological_sort(graph)
    if not ordering:
        return None, None
    tgraph: dict[any,list[tuple[any,float]]] = get_transpose(graph)
    dist: dict[any,float] = defaultdict(lambda: float('inf'))
    prev: dict[any,any] = defaultdict(lambda: None)
    dist[src] = 0
    v: any
    for v in ordering:
        if v != src and len(tgraph[v]) > 0:
            u: any; w: float
            u, w = min(tgraph[v], key=lambda x: dist[x[0]] + x[1])
            dist[v] = dist[u]+w
            prev[v] = u
    return dist, prev


# Approach 2: relaxing edges
def shortest_path_2(graph: dict[any,list[tuple[any,float]]], src: any) -> tuple[dict[any,float],dict[any,any]]:
    ordering: list[any] = topological_sort(graph)
    if not ordering:
        return None, None
    dist: dict[any,float] = defaultdict(lambda: float('inf'))
    prev: dict[any,any] = defaultdict(lambda: None)
    dist[src] = 0
    u: any
    for u in ordering:
        v: any; w: float
        for v, w in graph[u]:
            if dist[u]+w < dist[v]:
                dist[v] = dist[u]+w
                prev[v] = u
    return dist, prev


if __name__ == '__main__':
    graph: dict[str,list[tuple[str,float]]] = {
        'A': [('B', 2), ('D', 4), ('E', 5)],
        'B': [('E', 6)],
        'C': [('E', 1), ('F', 4)],
        'D': [('E', 3), ('H', 7)],
        'E': [('H', 2)],
        'F': [('I', 3)],
        'G': [('H', 3), ('J', 8)],
        'H': [('I', 4), ('K', 4), ('L', 9)],
        'I': [('L', 1)],
        'J': [('K', 2)],
        'K': [],
        'L': []
    }

    print('Approach 1:')
    src: str = 'D'
    dist: dict[any,float]; prev: dict[any,any]
    dist, prev = shortest_path_1(graph, src)
    print('dist:', dist)
    print('prev:', prev)
    dest: str = 'L'
    print(f'sp from {src} to {dest}: {reconstruct(prev, dest)}')

    src: str = 'D'
    print('\nApproach 2:')
    dist: dict[any,float]; prev: dict[any,any]
    dist, prev = shortest_path_2(graph, src)
    print('dist:', dist)
    print('prev:', prev)
    dest: str = 'L'
    print(f'sp from {src} to {dest}: {reconstruct(prev, dest)}')

    """Output:
    Approach 1:
    dist: {'D': 0, 'A': inf, 'B': inf, 'C': inf, 'E': 3, 'F': inf, 'G': inf, 'H': 5, 'I': 9, 'J': inf, 'K': 9, 'L': 10}
    prev: {'E': 'D', 'H': 'E', 'I': 'H', 'K': 'H', 'L': 'I'}
    sp from D to L: ['D', 'E', 'H', 'I', 'L']
    
    Approach 2:
    dist: {'D': 0, 'A': inf, 'B': inf, 'E': 3, 'C': inf, 'F': inf, 'H': 5, 'I': 9, 'G': inf, 'J': inf, 'K': 9, 'L': 10}
    prev: {'E': 'D', 'H': 'E', 'I': 'H', 'K': 'H', 'L': 'I'}
    sp from D to L: ['D', 'E', 'H', 'I', 'L']"""
