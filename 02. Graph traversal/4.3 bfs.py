from __future__ import annotations
from queue import Queue
from graph_adj_list import *

def _bfs(graph: GraphAdjList[any], vertex: any, visited: set[any]) -> None:
    queue: Queue[any] = Queue()
    queue.put(vertex)
    visited.add(vertex)
    while not queue.empty():
        vertex: any = queue.get()
        print(vertex)
        neighbor: any
        for neighbor in graph.get_neighbors(vertex):
            if neighbor not in visited:
                queue.put(neighbor)
                visited.add(neighbor)


def bfs(graph: GraphAdjList[any]) -> None:
    visited: set[any] = set()
    vertex: any
    for vertex in graph.get_vertices():
        if vertex not in visited:
            _bfs(graph, vertex, visited)


if __name__ == '__main__':
    graph: GraphAdjList[any] = GraphAdjList()
    graph.adj_list = {
        'A': ['B', 'C', 'D'],
        'B': ['A', 'E', 'F'],
        'C': [],
        'D': ['H', 'I', 'J'],
        'E': ['B', 'K'],
        'F': ['B', 'L'],
        'H': ['D'],
        'I': ['D', 'O', 'J'],
        'J': ['D', 'I'],
        'K': ['E', 'P', 'L'],
        'L': ['K', 'Q', 'R', 'F'],
        'O': ['I'],
        'P': ['K'],
        'Q': ['L'],
        'R': ['L']
    }

    bfs(graph)

    """Output:
    A
    B
    C
    D
    E
    F
    H
    I
    J
    K
    L
    O
    P
    Q
    R"""

