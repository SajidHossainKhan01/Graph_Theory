from __future__ import annotations


def reconstruct(nxt: list[list[int]], idx: dict[any,int], src: any, dest: any) -> list[any]:
    if nxt[idx[src]][idx[dest]] is None:
        return []
    label: dict[int,any] = {idx[u]: u for u in idx}
    path: list[any] = [src]
    temp: int = idx[src]
    while temp != idx[dest]:
        temp = nxt[temp][idx[dest]]
        path.append(label[temp])
    return path


def floyd_warshall(graph: dict[any,list[tuple[any,float]]]) -> tuple[list[list[float]],list[list[int]],dict[any,int]] | tuple[None,None,None]:
    vertices: list[any] = list(graph.keys())
    idx: dict[any,int] = {vertices[i]: i for i in range(len(vertices))}
    n: int = len(vertices)
    dist: list[list[float]] = [[float('inf')]*n for _ in range(n)]
    nxt: list[list[int]] = [[None]*n for _ in range(n)]
    u: any
    for u in graph:
        v: any; w: float
        for v, w in graph[u]:
            dist[idx[u]][idx[v]] = w
            nxt[idx[u]][idx[v]] = idx[v]
    i: int; j: int; k: int
    for i in range(n):
        dist[i][i] = 0
        nxt[i][i] = i
    for k in range(n):
        for i in range(n):
            for j in range(n):
                if dist[i][k]+dist[k][j] < dist[i][j]:
                    dist[i][j] = dist[i][k]+dist[k][j]
                    nxt[i][j] = nxt[i][k]
    for i in range(n):
        if dist[i][i] < 0:
            return None, None, None
    return dist, nxt, idx
  

if __name__ == '__main__':
    graph: dict[str,list[tuple[str,float]]] = {
        'A': [('B', 18), ('C', -4), ('E', 3)],
        'B': [],
        'C': [('B', 16), ('E', -2), ('D', 14)],
        'D': [('B', 6)],
        'E': [('D', 5)]
    }
    dist: list[list[float]]; nxt: list[list[int]]; idx: dict[str,int]
    dist, nxt, idx = floyd_warshall(graph)
    print('Distances:')
    print(*dist, sep='\n')

    # Output:
    # [0,   5,  -4,  -1, -6]
    # [inf, 0,  inf, inf, inf]
    # [inf, 9,  0,   3,   -2]
    # [inf, 6,  inf, 0,   inf]
    # [inf, 11, inf, 5,   0]

    src, dest = 'A', 'E'
    print(f'\nShortest path from {src} to {dest}:')
    print(reconstruct(nxt, idx, src, dest))

    # Output:
    # Shortest path from A to E:
    # ['A', 'C', 'E']