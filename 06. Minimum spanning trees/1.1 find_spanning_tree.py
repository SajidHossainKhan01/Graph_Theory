from __future__ import annotations
from collections import defaultdict


def dfs(graph: dict[any,list[any]], vertex: any, visited: set[any], spanning_tree: dict[any,list[any]]) -> None:
    visited.add(vertex)
    neighbor: any
    for neighbor in graph[vertex]:
        if neighbor not in visited:
            spanning_tree[vertex].append(neighbor)
            spanning_tree[neighbor].append(vertex)
            dfs(graph, neighbor, visited, spanning_tree)
            
            
def find_spanning_tree(graph: dict[any,list[any]]) -> dict[any,list[any]]:
    spanning_tree: dict[any,list[any]] = defaultdict(lambda: [])
    visited: set[any] = set()
    arbitrary_vertex: any = list(graph.keys())[0]
    dfs(graph, arbitrary_vertex, visited, spanning_tree)
    return spanning_tree


if __name__ == "__main__":
    graph: dict[str,list[str]] = {
        'A': ['B', 'C', 'F'],
        'B': ['A', 'C', 'D', 'G'],
        'C': ['A', 'B', 'D', 'E', 'F'],
        'D': ['B', 'C', 'E', 'G'],
        'E': ['C', 'D', 'F', 'G', 'I', 'J'],
        'F': ['A', 'C', 'E', 'J'],
        'G': ['B', 'D', 'E', 'H', 'I'],
        'H': ['G', 'I', 'J'],
        'I': ['E', 'G', 'H'],
        'J': ['E', 'F', 'H']
    }

    spanning_tree: dict[str,list[str]] = find_spanning_tree(graph)
    print(spanning_tree)

    """Output:
    {
        'A': ['B'],
        'B': ['A', 'C'], 
        'C': ['B', 'D'], 
        'D': ['C', 'E'], 
        'E': ['D', 'F'], 
        'F': ['E', 'J'], 
        'J': ['F', 'H'], 
        'H': ['J', 'G'], 
        'G': ['H', 'I'], 
        'I': ['G']
    }"""
