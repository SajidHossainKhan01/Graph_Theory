from __future__ import annotations


def dfs(graph: dict[any,list[any]], vertex: any, end: any, visited: set[any]) -> bool:
    if vertex == end:
        return True
    visited.add(vertex)
    neighbor: any
    for neighbor in graph[vertex]:
        if neighbor not in visited:
            if dfs(graph, neighbor, end, visited):
                return True
    return False


def path_exists(n: int, edges: list[tuple[int,int]], start: int, end: int) -> bool:
    graph = [[] for _ in range(n)]
    src: int; dest: int
    for src, dest in edges:
        graph[src].append(dest)
        graph[dest].append(src)
    visited: set[int] = set()
    return dfs(graph, start, end, visited)


if __name__ == '__main__':
    n = 9
    edges = [[0, 1], [0, 2], [2, 4], [3, 5], [5, 6], [5, 7], [6, 8]]

    start = 0
    end = 6
    print(f'Path exists from {start} to {end}: {path_exists(n, edges, start, end)}')

    start = 3
    end = 6
    print(f'Path exists from {start} to {end}: {path_exists(n, edges, start, end)}')

    # Output:
    # False
    # True
