from __future__ import annotations
from collections import defaultdict
import fibheap as fh


def prim(graph: dict[any,list[tuple[any,float]]]) -> dict[any,list[tuple[any,float]]]:
    mst: dict[any,list[tuple[any,float]]] = defaultdict(lambda: [])
    start: any = list(graph.keys())[0]
    cost: dict[any,float] = {}
    parent: dict[any,any] = {}
    nodes: dict[any,fh.Node] = {}
    queue: fh.Fheap = fh.makefheap()
    u: any
    for u in graph:
        cost[u] = 0 if u == start else float('inf')
        parent[u] = None
        nodes[u] = fh.Node((cost[u], u))
        queue.insert(nodes[u])
    while len(mst) < len(graph):
        u: any = queue.extract_min().key[1]
        if u != start:
            mst[u].append((parent[u], cost[u]))
            mst[parent[u]].append((u, cost[u]))
        v: any; w: float
        for v, w in graph[u]:
            if v not in mst and w < cost[v]:
                cost[v] = w
                parent[v] = u
                queue.decrease_key(nodes[v], (cost[v], v))
    return mst


if __name__ == '__main__':
    graph: dict[str,list[tuple[str,float]]] = {
        'H': [('I', 1), ('J', 4), ('G', 4)],
        'I': [('H', 1), ('J', 3), ('G', 5), ('E', 9)],
        'C': [('D', 2), ('A', 3), ('B', 4), ('E', 9), ('F', 9)],
        'D': [('C', 2), ('B', 2), ('E', 8), ('G', 9)],
        'B': [('D', 2), ('C', 4), ('A', 6), ('G', 9)],
        'A': [('C', 3), ('B', 6), ('F', 9)],
        'J': [('I', 3), ('H', 4), ('E', 10), ('F', 18)],
        'G': [('H', 4), ('I', 5), ('E', 7), ('B', 9), ('D', 9)],
        'E': [('G', 7), ('F', 8), ('D', 8), ('C', 9), ('I', 9), ('J', 10)],
        'F': [('E', 8), ('C', 9), ('A', 9), ('J', 18)]
    }

    print(prim(graph))
    # Output: 
    # {
    #    'I': [('H', 1), ('J', 3)], 
    #    'H': [('I', 1), ('G', 4)],
    #    'J': [('I', 3)], 
    #    'G': [('H', 4), ('E', 7)], 
    #    'E': [('G', 7), ('F', 8), ('D', 8)],
    #    'F': [('E', 8)],
    #    'D': [('E', 8), ('C', 2), ('B', 2)], 
    #    'C': [('D', 2), ('A', 3)],
    #    'B': [('D', 2)],
    #    'A': [('C', 3)]
    # }