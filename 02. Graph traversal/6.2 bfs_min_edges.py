from __future__ import annotations
from queue import Queue


def min_edges(n: int, edges: list[int,int], start: int, end: int) -> int:
    graph: list[list[int]] = [[] for _ in range(n)]
    src: int; dest: int
    for src, dest in edges:
        graph[src].append(dest)
        graph[dest].append(src)
    queue: Queue[tuple[int,int]] = Queue()
    visited: set[int] = set()
    queue.put((start, 0))
    visited.add(start)
    while not queue.empty():
        vertex: int; level: int; vertex, level = queue.get()
        if vertex == end:
            return level
        neighbor: int
        for neighbor in graph[vertex]:
            if neighbor not in visited:
                queue.put((neighbor, level+1))
                visited.add(neighbor)
    return -1


if __name__ == '__main__':
    n: int = 6
    edges: list[tuple[int,int]] = [(0, 1), (0, 2), (0, 3), (1, 2), (1, 4), (2, 4), (3, 4), (3, 5), (4, 5)]
    start: int = 0
    end: int = 5
    print(min_edges(n, edges, start, end))

    # Output:
    # 2
