from __future__ import annotations
from collections import defaultdict
import fibheap as fh



def dsatur(graph: dict[any,list[any]]) -> dict[any,int]:
    color_map: dict[any,int] = {}
    nei_colors: dict[any,set[any]] = defaultdict(lambda: set())
    queue: fh.Fheap = fh.makefheap()
    nodes: dict[any,fh.Node] = {}
    u: any
    for u in graph:
        nodes[u] = fh.Node((0, -len(graph[u]), u))
        queue.insert(nodes[u])
    while queue.minimum():
        u: any = queue.extract_min().key[2]
        color: int = 0
        while color in nei_colors[u]:
            color += 1
        color_map[u] = color
        v: any
        for v in graph[u]:
            nei_colors[v].add(color)
            if v not in color_map:
                queue.decrease_key(nodes[v], (-len(nei_colors[v]), -len(graph[v]), v))
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

    print(dsatur(graph))
    # Output: {'E': 0, 'H': 0, 'C': 0, 'A': 1, 'D': 1, 'I': 1, 'B': 2, 'F': 2, 'G': 2}
