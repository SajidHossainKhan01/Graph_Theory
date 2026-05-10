from __future__ import annotations
from collections import defaultdict


def rec(graph: dict[any,list[any]], k: int, vertices: list[any], i: int, color_map: dict[any,int]) -> bool:
    if i == len(vertices):
        return True
    used: set[int] = set([color_map[neighbor] for neighbor in graph[vertices[i]]])
    color: int
    for color in range(k):
        if color not in used:
            color_map[vertices[i]] = color
            if rec(graph, k, vertices, i+1, color_map):
                return True
    color_map[vertices[i]] = -1
    return False


# Variant 1: Checks if the graph can be colored with k colors
def is_k_colorable(graph: dict[any,list[any]], k: any) -> bool:
    vertices: list[any] = list(graph.keys())
    color_map: dict[any,int] = defaultdict(lambda: -1)
    return rec(graph, k, vertices, 0, color_map)


# Variant 2: Colors the graph with at most k colors (if possible, otherwise puts -1 for each vertex in the color map)
def k_coloring(graph: dict[any,list[any]], k: int) -> dict[any,int]:
    vertices: list[any] = list(graph.keys())
    color_map: dict[any,int] = defaultdict(lambda: -1)
    rec(graph, k, vertices, 0, color_map)
    return color_map


def chromatic_number(graph: dict[any,list[any]]) -> int:
    k: int = 1
    while k <= len(graph):
        if is_k_colorable(graph, k):
            return k
        k += 1
    return -1


if __name__ == '__main__':
    graph: dict[str,list[str]] = {
        'A': ['B', 'C', 'E', 'H', 'F'],
        'B': ['A', 'C', 'E'],
        'C': ['A', 'B'],
        'D': ['G', 'H', 'E'],
        'E': ['A', 'F', 'G', 'B', 'I', 'D'],
        'F': ['E', 'I', 'A'],
        'G': ['D', 'E', 'H'],
        'H': ['D', 'G', 'A', 'I'],
        'I': ['E', 'F', 'H']
    }

    print(is_k_colorable(graph, 2))
    # Output: False

    print(is_k_colorable(graph, 3))
    # Output: True

    print(k_coloring(graph, 3))
    # Output: {'B': 1, 'C': 2, 'E': 2, 'H': 2, 'F': 1, 'A': 0, 'G': 1, 'D': 0, 'I': 0})

    print(chromatic_number(graph))
    # Output: 3