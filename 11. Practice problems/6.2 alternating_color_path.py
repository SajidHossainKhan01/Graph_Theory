from __future__ import annotations
from queue import Queue


def bfs(graph: dict[int,list[tuple[int,str]]], output: list[int]) -> None:
    queue: Queue[tuple[int,int,str]] = Queue()
    queue.put((0, 0, ''))
    visited: set[tuple[int,str]] = {(0, '')}
    while not queue.empty():
        vertex: int; level: int; prev_edge_c: str
        vertex, level, prev_edge_c = queue.get()
        output[vertex] = min(output[vertex], level)
        neighbor: int; edge_c: str
        for neighbor, edge_c in graph[vertex]:
            if (neighbor, edge_c) not in visited and edge_c != prev_edge_c:
                visited.add((neighbor, edge_c))
                queue.put((neighbor, level+1, edge_c))


def shortest_paths(n: int, red_edges: list[list[int,int]], blue_edges: list[list[int,int]]) -> list[int]:
    graph: dict[int,list[tuple[int,str]]] = {i: [] for i in range(n)}
    u: int; v: int
    for u, v in red_edges:
        graph[u].append((v, 'red'))
    for u, v in blue_edges:
        graph[u].append((v, 'blue'))
    output: list[int] = [float('inf')]*n
    bfs(graph, output)
    output = [-1 if x == float('inf') else x for x in output]
    return output


if __name__ == '__main__':
    n: int = 6
    red_edges: list[list[int,int]] = [[0, 1], [1, 2], [1, 3], [2, 3], [3, 4]]
    blue_edges: list[list[int,int]] = [[1, 2], [2, 3], [2, 4], [3, 1]]

    print(f'Shortest distances: {shortest_paths(n, red_edges, blue_edges)}')

    # Output:
    # Shortest distances: [0, 1, 2, 3, 6, -1]
