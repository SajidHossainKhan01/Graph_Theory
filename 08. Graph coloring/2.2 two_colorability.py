from __future__ import annotations
from collections import defaultdict
from queue import Queue


def bfs(graph: dict[any,list[any]], vertex: any, color_map: dict[any,int]) -> bool:
    queue: Queue[any] = Queue()
    queue.put(vertex)
    color_map[vertex] = 0
    while not queue.empty():
        vertex: any = queue.get()
        neighbor: any
        for neighbor in graph[vertex]:
            if neighbor not in color_map:
                color_map[neighbor] = 1-color_map[vertex]
                queue.put(neighbor)
            elif color_map[neighbor] == color_map[vertex]:
                return False
    return True


# 3 variants are possible:
# Variant 1: Knowing if an undirected graph is bipartite:
def is_bipartite(graph: dict[any,list[any]]) -> bool:
    color_map: dict[any,int] = {}
    vertex: any
    for vertex in graph:
        if vertex not in color_map:
            if not bfs(graph, vertex, color_map):
                return False
    return True


# Variant 2: Coloring a graph with 2 colors (if possible, otherwise null will be returned):
def two_coloring(graph: dict[any,list[any]]) -> dict[any,int] | None:
    color_map: dict[any,int] = {}
    for vertex in graph:
        if vertex not in color_map:
            if not bfs(graph, vertex, color_map):
                return None
    return color_map


# Variant 3: Partitioning vertices of a bipartite graph into two disjoint independent sets (if possible, otherwise null will be returned):
def partition_bipartite(graph: dict[any,list[any]]) -> tuple[list[any],list[any]] | tuple[None,None]:
    color_map: dict[any,int] = {}
    vertex: any
    for vertex in graph:
        if vertex not in color_map:
            if not bfs(graph, vertex, color_map):
                return None, None
    first_set = [u for u in graph if color_map[u] == 0]
    second_set = [u for u in graph if color_map[u] == 1]
    return first_set, second_set


if __name__ == '__main__':
    bipgraph: dict[any,list[any]] = {
        'A': [1, 5],
        'B': [1, 2, 3, 6],
        'C': [2],
        'D': [4, 5, 6],
        1: ['A', 'B'],
        2: ['C'],
        3: ['B'],
        4: ['D'],
        5: ['A', 'D'],
        6: ['D']
    }

    print(is_bipartite(bipgraph))
    # Output: True

    print(two_coloring(bipgraph))
    # Output: {'A': 0, 1: 1, 5: 1, 'B': 0, 'D': 0, 3: 1, 6: 1, 4: 1, 'C': 0, 2: 1}

    print(partition_bipartite(bipgraph))
    # Output: (['A', 'B', 'C', 'D'], [1, 2, 3, 4, 5, 6])