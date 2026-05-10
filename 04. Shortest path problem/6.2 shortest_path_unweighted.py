from __future__ import annotations
from queue import Queue


def reconstruct(prev: dict[any,any], dest: any) -> list[any]:
    if not prev[dest]:
        return None
    path: list[any] = [dest]
    while prev[dest]:
        dest = prev[dest]
        path.append(dest)
    return path[::-1]


def sssp_undirected(graph: dict[any,list[any]], src: any) -> tuple[dict[any,float],dict[any,any]]:
    dist: dict[any,int] = {u: float('inf') for u in graph}
    dist[src] = 0
    prev: dict[any,any] = {u: None for u in graph}
    queue: Queue[tuple[any,int]] = Queue()
    queue.put((src, 0))
    visited: set[any] = {src}
    while not queue.empty():
        vertex: any; level: int
        vertex, level = queue.get()
        neighbor: any
        for neighbor in graph[vertex]:
            if neighbor not in visited:
                prev[neighbor] = vertex
                dist[neighbor] = level+1
                visited.add(neighbor)
                queue.put((neighbor, level+1))
    return dist, prev


if __name__ == '__main__':
    graph: dict[str,list[str]] = {
        'A': ['B', 'C', 'D'],
        'B': ['A', 'C', 'I'],
        'C': ['A', 'B', 'F'],
        'D': ['A', 'G'],
        'E': ['C', 'H'],
        'F': ['C', 'H', 'J'],
        'G': ['C', 'D', 'I'],
        'H': ['C', 'E', 'F', 'I'],
        'I': ['B', 'G', 'K'],
        'J': ['F', 'K'],
        'K': ['I', 'J']
    }

    src: str = 'A'
    dist: dict[any,float]; prev: dict[any,any]
    dist, prev = sssp_undirected(graph, src)
    print('dist:', dist)
    print('prev:', prev)
    dest: str = 'K'
    print(f'sp from {src} to {dest}: {reconstruct(prev, dest)} (length: {dist[dest]})')
