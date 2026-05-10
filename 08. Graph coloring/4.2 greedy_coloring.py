from __future__ import annotations
from collections import defaultdict


def greedy_coloring(graph: dict[any,list[any]]) -> dict[any,int]:
    order: list[any] = list(graph.keys())
    color_map: dict[any,int] = defaultdict(lambda: -1)
    vertex: any
    for vertex in order:
        used: set[int] = set([color_map[neighbor] for neighbor in graph[vertex]])
        color: int = 0
        while color in used:
            color += 1
        color_map[vertex] = color
    return color_map


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

    print(greedy_coloring(graph))
    # Output: {'B': 1, 'C': 2, 'E': 2, 'H': 2, 'F': 1, 'A': 0, 'G': 1, 'D': 0, 'I': 0}