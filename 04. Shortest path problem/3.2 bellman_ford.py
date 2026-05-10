from __future__ import annotations


def reconstruct(prev: dict[any,any], dest: any) -> list[any]:
    path: list[any] = [dest]
    while prev[dest]:
        dest = prev[dest]
        path.append(dest)
    return path[::-1]


def get_edges(graph: dict[any,list[tuple[any,float]]]) -> list[tuple[any,any,float]]:
    edges: list[tuple[any,any,float]] = []
    u: any
    for u in graph:
        v: any; w: float
        for v, w in graph[u]:
            edges.append((u, v, w))
    return edges


def bellman_ford(graph: dict[any,list[tuple[any,float]]], src: any) -> tuple[dict[any,float],dict[any,any]] | tuple[None,None]:
    n: int = len(graph)
    edges: list[tuple[any,any,float]] = get_edges(graph)
    dist: dict[any,float] = {v: 0 for v in graph}
    prev: dict[any,any] = {v: None for v in graph}
    v: any
    for v in dist:
        dist[v] = 0 if v == src else float('inf')
    k: int
    for k in range(1, n):
        changed: bool = False
        u: any; v: any; w: float
        for u, v, w in edges:
            if dist[u]+w < dist[v]:
                dist[v] = dist[u]+w
                prev[v] = u
                changed = True
        if not changed:
            break
    u: any; v: any; w: float
    for u, v, w in edges:
        if dist[u]+w < dist[v]:
            return None, None
    return dist, prev


if __name__ == '__main__':
    # Graph with no negative cycles:
    print('Graph 1:')
    graph: dict[str,list[tuple[str,float]]] = {
        'A': [('B', 3), ('C', 12)],
        'B': [('C', 6), ('D', 2), ('E', 11)],
        'C': [('E', 3)],
        'D': [('E', 10), ('F', 5)],
        'E': [('A', -6)],
        'F': [('E', -2), ('G', 6), ('H', 13), ('B', 9)],
        'G': [('H', 4)],
        'H': [('F', -5)]
    }
    src: str = 'A'
    dist, prev = bellman_ford(graph, 'A')
    if dist is not None:
        for v in graph:
            print(f'sp from {src} to {v}: {reconstruct(prev, v)} (weight: {dist[v]})')
    else:
        print('The graph contains negative cycles!')


    # Graph with negative cycles:
    print('\nGraph 2:')
    graph: dict[str,list[tuple[str,float]]] = {
        'A': [('B', 3), ('C', 12)],
        'B': [('C', 6), ('D', 2), ('E', 11)],
        'C': [('E', 3)],
        'D': [('E', 10), ('F', 5)],
        'E': [('A', -6), ('D', -6)],
        'F': [('E', -2), ('G', 6), ('H', 13), ('B', 9)],
        'G': [('H', 4)],
        'H': [('F', -5)]
    }
    src: str = 'A'
    dist, prev = bellman_ford(graph, 'A')
    if dist is not None:
        for v in graph:
            print(f'sp from {src} to {v}: {reconstruct(prev, v)} (weight: {dist[v]})')
    else:
        print('The graph contains negative cycles!')

    # Output:
    # Graph 1:
    # sp from A to A: ['A'] (weight: 0)
    # sp from A to B: ['A', 'B'] (weight: 3)
    # sp from A to C: ['A', 'B', 'C'] (weight: 9)
    # sp from A to D: ['A', 'B', 'D'] (weight: 5)
    # sp from A to E: ['A', 'B', 'D', 'F', 'E'] (weight: 8)
    # sp from A to F: ['A', 'B', 'D', 'F'] (weight: 10)    
    # sp from A to G: ['A', 'B', 'D', 'F', 'G'] (weight: 16)
    # sp from A to H: ['A', 'B', 'D', 'F', 'G', 'H'] (weight: 20)

    # Graph 2:
    # The graph contains negative cycles!
