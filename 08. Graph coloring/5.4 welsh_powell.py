from __future__ import annotations


def welsh_powell(graph: dict[any,list[any]]):
    order: list[any] = list(graph.keys())
    order.sort(key=lambda u: len(graph[u]), reverse=True)
    color_map: dict[any,int] = {}
    color: int = 0
    while len(color_map) < len(graph):
        for vertex in order:
            if vertex not in color_map and all(neighbor not in color_map or color_map[neighbor] != color for neighbor in graph[vertex]):
                color_map[vertex] = color
        color += 1
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

    print(welsh_powell(graph))
    # Output: {'E': 0, 'H': 0, 'C': 0, 'A': 1, 'D': 1, 'I': 1, 'B': 2, 'F': 2, 'G': 2}
    