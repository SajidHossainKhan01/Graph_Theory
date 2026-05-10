from __future__ import annotations
from collections import defaultdict
from queue import Queue


def dfs(residual: dict[any,dict[any,float]], u: any, sink: any, path: list[tuple[any,any]], visited: set[any], level: dict[any,int]) -> list[tuple[any,any]] | None:
    if u == sink:
        return path
    visited.add(u)
    v: any
    for v in residual[u]:
        if v not in visited and residual[u][v] > 0 and level[u] < level[v]:
            path.append((u, v))
            if dfs(residual, v, sink, path, visited, level):
                return path
            path.pop()
    return None


def get_level_map(residual: dict[any,dict[any,float]], source: any) -> dict[any,int]:
    queue: Queue[any] = Queue()
    queue.put(source)
    level: dict[any,int] = {source: 0}
    while not queue.empty():
        u: any = queue.get()
        v: any
        for v in residual[u]:
            if v not in level and residual[u][v] > 0:
                queue.put(v)
                level[v] = level[u]+1
    return level


def dinic(graph: dict[any,list[tuple[any,float]]]) -> float:
    residual: dict[any,dict[any,float]] = defaultdict(lambda: {})
    u: any; v: any; w: float
    for u in graph:
        for v, w in graph[u]:
            residual[u][v] = w
            residual[v][u] = 0
    total_flow: float = 0
    while True:
        level: dict[any,int] = get_level_map(residual, 's')
        if 't' not in level:
            break
        while True:
            path: list[tuple[any,any]] = dfs(residual, 's', 't', [], set(), level)
            if path is None:
                break
            bottleneck: float = min(residual[u][v] for u, v in path)
            total_flow += bottleneck
            u: any; v: any
            for u, v in path:
                residual[u][v] -= bottleneck
                residual[v][u] += bottleneck
    return total_flow


def bipartite_matching(set1: set[any], set2: set[any], edges: list[tuple[any,any]]) -> int:
    graph: dict[any,list[tuple[any,float]]] = defaultdict(lambda: [])
    u: any; v: any
    for u, v in edges:
        graph[u].append((v, 1))
    for u in set1:
        graph['s'].append((u, 1))
    for u in set2:
        graph[u].append(('t', 1))
    return dinic(graph)


def max_students(seats: list[list[str]]) -> int:
    m: int; n: int
    m, n = len(seats), len(seats[0])
    seats_set: set[tuple[int,int]] = set()
    i: int; j: int
    for i in range(m):
        for j in range(n):
            if seats[i][j] == '.':
                seats_set.add((i, j))
    set1: set[tuple[int,int]] = set([(i, j) for (i, j) in seats_set if j%2 == 0])
    set2: set[tuple[int,int]] = set([(i, j) for (i, j) in seats_set if j%2 == 1])
    edges: list[tuple[int,int]] = []
    for (i, j) in seats_set:
        neighbors = [(i-1, j-1), (i-1, j+1), (i, j-1), (i, j+1)]
        for (ni, nj) in neighbors:
            if (ni, nj) in seats_set:
                edges.append(((i, j), (ni, nj)) if (i, j) in set1 else ((ni, nj), (i, j)))
    return len(seats_set)-bipartite_matching(set1, set2, edges)


if __name__ == '__main__':

    seats: list[list[str]] = [
        [".",".",".","#",".",".","#"],
        [".","#","#",".","#",".","#"],
        ["#","#","#",".",".","#","."],
        ["#",".",".","#","#",".","#"]
    ]

    print(f'We can place at most {max_students(seats)} students')

    # Output: We can place at most 8 students
