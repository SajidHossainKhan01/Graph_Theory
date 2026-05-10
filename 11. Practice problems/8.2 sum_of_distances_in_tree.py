from __future__ import annotations
from queue import Queue


def dist_to_all(graph: dict[int,list[int]], vertex: int) -> int:
    queue: Queue[tuple[int,int,int]] = Queue()
    queue.put((vertex, 0, None))
    total: int = 0
    while not queue.empty():
        vertex: int; level: int; parent: int
        vertex, level, parent = queue.get()
        total += level
        for neighbor in graph[vertex]:
            if neighbor != parent:
                queue.put((neighbor, level+1, vertex))
    return total


def fill_size_map(graph: dict[int,list[int]], vertex: int, size_map: dict[int,int], parent: int=None) -> int:
    size: int = 1
    neighbor: int
    for neighbor in graph[vertex]:
        if neighbor != parent:
            size += fill_size_map(graph, neighbor, size_map, vertex)
    size_map[vertex] = size
    return size


def fill_dist(graph: dict[int,list[int]], vertex: int, size_map: dict[int,int], dist: list[int], parent=None) -> None:
    if parent is not None:
        dist[vertex] = dist[parent] + len(graph) - 2*size_map[vertex]
    neighbor: int
    for neighbor in graph[vertex]:
        if neighbor != parent:
            fill_dist(graph, neighbor, size_map, dist, vertex)


def min_dist_each(graph: dict[int,list[int]]) -> list[int]:
    n: int = len(graph)
    root: int = 0
    size_map: dict[int,int] = {}
    dist: list[int] = [0]*n
    dist[root] = dist_to_all(graph, root)
    fill_size_map(graph, root, size_map)
    fill_dist(graph, root, size_map, dist)
    return dist


def sum_of_distances_in_tree(n: int, edges: list[list[int,int]]) -> list[int]:
    graph: dict[int,list[int]] = {u: [] for u in range(n)}
    u: int; v: int
    for u, v in edges:
        graph[u].append(v)
        graph[v].append(u)
    dist: list[int] = min_dist_each(graph)
    return dist


if __name__ == '__main__':
    n: int = 14
    edges: list[list[int,int]] = [[0, 1], [0, 3], [0, 10], [1, 2], [3, 4], [3, 5], [4, 6], [4, 7], [4, 8], [7, 9], [10, 11], [10, 12], [11, 13]]

    print(f'Sum of distances: {sum_of_distances_in_tree(n, edges)}')

    # Output:
    # Sum of distances: [29, 39, 51, 29, 33, 41, 45, 43, 45, 55, 35, 45, 47, 57]
