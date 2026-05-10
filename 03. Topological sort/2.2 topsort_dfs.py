from __future__ import annotations


def dfs(graph: dict[any,list[any]], vertex: any, visited: set[any], stack: list[any], path: set[any]) -> bool:
    visited.add(vertex)
    path.add(vertex)
    neighbor: any
    for neighbor in graph[vertex]:
        if neighbor in path or (neighbor not in visited and not dfs(graph, neighbor, visited, stack, path)):
            return False
    stack.append(vertex)
    path.remove(vertex)
    return True


def topological_sort(graph: dict[any,list[any]]) -> list[any]:
    visited: set[any] = set()
    path: set[any] = set()
    stack: list[any] = []
    vertex: any
    for vertex in graph:
        if vertex not in visited:
            if not dfs(graph, vertex, visited, stack, path):
                return []
    ordering: list[any] = []
    while stack:
        ordering.append(stack.pop())
    return ordering


if __name__ == '__main__':
    graph: dict[int,list[int]] = {
        0: [3, 6],
        1: [3],
        2: [4, 5],
        3: [6, 7],
        4: [3, 7, 8],
        5: [4, 8],
        6: [7, 9],
        7: [10],
        8: [11],
        9: [10, 12],
        10: [12, 14],
        11: [14],
        12: [],
        13: [14],
        14: []
    }

    print(topological_sort(graph))
    # Output: [13, 2, 5, 4, 8, 11, 1, 0, 3, 6, 9, 7, 10, 14, 12]
    