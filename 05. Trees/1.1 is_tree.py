from __future__ import annotations


def dfs(graph: dict[any,list[any]], vertex: any, visited: set[any], edges: list[tuple[any,any]]) -> None:
    visited.add(vertex)
    neighbor: any
    for neighbor in graph[vertex]:
        edges.append((vertex, neighbor))
        if neighbor not in visited:
            dfs(graph, neighbor, visited, edges)


def is_tree(graph: dict[any,list[any]]) -> bool:
    visited: set[any] = set()
    edges: list[tuple[any,any]] = []
    start: any = list(graph.keys())[0]
    dfs(graph, start, visited, edges)
    return len(visited) == len(graph) and len(edges) == 2*(len(graph)-1)


if __name__ == '__main__':
    graph_1: dict[str,list[str]] = {
        'A': ['B'],
        'B': ['A', 'G'],
        'C': ['F'],
        'D': ['H'],
        'E': ['F'],
        'F': ['C', 'E', 'G'],
        'G': ['B', 'F', 'I', 'K'],
        'H': ['D', 'K'],
        'I': ['G', 'J'],
        'J': ['I'],
        'K': ['G', 'H']
    }

    graph_2: dict[str,list[str]] = {
        'A': ['B'],
        'B': ['A', 'G'],
        'C': ['F'],
        'D': ['H'],
        'E': ['F'],
        'F': ['C', 'E', 'G'],
        'G': ['B', 'F', 'I'],
        'H': ['D', 'K'],
        'I': ['G', 'J'],
        'J': ['I'],
        'K': ['H']
    }

    graph_3: dict[str,list[str]] = {
        'A': ['B'],
        'B': ['A', 'G'],
        'C': ['F', 'E'],
        'D': ['H'],
        'E': ['F', 'C'],
        'F': ['C', 'E', 'G'],
        'G': ['B', 'F', 'I', 'K'],
        'H': ['D', 'K'],
        'I': ['G', 'J'],
        'J': ['I'],
        'K': ['G', 'H']
    }

    print('Graph 1 is tree:', is_tree(graph_1))
    print('Graph 2 is tree:', is_tree(graph_2))
    print('Graph 3 is tree:', is_tree(graph_3))

    # Output:
    # Graph 1 is tree: True
    # Graph 2 is tree: False
    # Graph 3 is tree: False