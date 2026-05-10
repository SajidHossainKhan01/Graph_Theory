from __future__ import annotations
from graph_adj_list import *


def _dfs(graph: GraphAdjList[any], vertex: any, visited: set[any]) -> None:
    print(vertex)
    visited.add(vertex)
    neighbor: any
    for neighbor in graph.get_neighbors(vertex):
        if neighbor not in visited:
            _dfs(graph, neighbor, visited)


def dfs(graph: GraphAdjList[any]) -> None:
    visited: set[any] = set()
    vertex: any
    for vertex in graph.get_vertices():
        if vertex not in visited:
            _dfs(graph, vertex, visited)


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

    dfs(graph)

    """Output:
    A
    B
    E
    K
    P
    L
    Q
    R
    F
    C
    D
    H
    I
    O
    J"""
    