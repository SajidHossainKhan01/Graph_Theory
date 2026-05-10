from __future__ import annotations


class DisjointSet:
    def __init__(self, elems: list[any]):
        self.parent: dict[any,any] = {}
        self.size: dict[any,int] = {}
        elem: any
        for elem in elems:
            self.make_set(elem)

    def make_set(self, a: any) -> None:
        self.parent[a] = a
        self.size[a] = 1

    def find(self, a: any) -> any:
        if self.parent[a] == a:
            return a
        else:
            self.parent[a] = self.find(self.parent[a])
            return self.parent[a]

    def union(self, a: any, b: any) -> None:
        root_a = self.find(a)
        root_b = self.find(b)
        if root_a == root_b:
            return
        elif self.size[root_a] <= self.size[root_b]:
            self.parent[root_a] = root_b
            self.size[root_b] += self.size[root_a]
        else:
            self.parent[root_b] = root_a
            self.size[root_a] += self.size[root_b]


# Approach 1: Brute force solution, trying all paths
def dfs(graph: dict[int,list[tuple[int,int]]], vertex: int, end: int, limit: int, path: set[int]) -> bool:
    path.add(vertex)
    if vertex == end:
        return True
    neighbor: int; weight: int
    for neighbor, weight in graph[vertex]:
        if neighbor not in path and weight < limit:
            if dfs(graph, neighbor, end, limit, path):
                return True
    path.remove(vertex)
    return False


def distance_limited_paths_exist_brute_force(n, edges, queries) -> list[bool]:
    graph: dict[int,list[tuple[int,int]]] = {i: [] for i in range(n)}
    u: int; v: int; w: int
    for u, v, w in edges:
        graph[u].append((v, w))
        graph[v].append((u, w))
    output: list[bool] = []
    u: int; v: int; limit: int
    for u, v, limit in queries:
        path: set[int] = set()
        output.append(dfs(graph, u, v, limit, path))
    return output


# Approach 2: Edge cleaning, removing edges with weight >= limit for each query
def can_reach(graph: dict[int,list[int]], vertex: int, end: int, visited: set[int]) -> bool:
    if vertex == end:
        return True
    visited.add(vertex)
    neighbor: int
    for neighbor in graph[vertex]:
        if neighbor not in visited:
            if can_reach(graph, neighbor, end, visited):
                return True
    return False


def distance_limited_paths_exist_edge_cleaning(n: int, edges: list[list[int,int,int]], queries: list[list[int,int,int]]) -> list[bool]:
    output = []
    start: int; end: int; limit: int
    for start, end, limit in queries:
        graph: dict[int,list[int]] = {i: [] for i in range(n)}
        u: int; v: int; w: int
        for u, v, w in edges:
            if w < limit:
                graph[u].append(v)
                graph[v].append(u)
        visited: set[int] = set()
        output.append(can_reach(graph, start, end, visited))
    return output


# Approach 3: Disjoint set, incrementally building a disjoint set of connected components as we add edges
def distance_limited_paths_exist_disjoint_set(n: int, edges: list[list[int,int,int]], queries: list[list[int,int,int]]) -> list[bool]:
    output: list[bool] = [False]*len(queries)
    queries: list[list[int,int,int,int]] = [queries[i] + [i] for i in range(len(queries))]
    edges.sort(key=lambda e: e[2])
    queries.sort(key=lambda q: q[2])
    ds: DisjointSet[int] = DisjointSet(list(range(n)))
    i: int = 0
    start: int; end: int; limit: int; pos: int
    for start, end, limit, pos in queries:
        while i < len(edges) and edges[i][2] < limit:
            ds.union(edges[i][0], edges[i][1])
            i += 1
        output[pos] = ds.find(start) == ds.find(end)
    return output


if __name__ == '__main__':
    n = 6
    edges = [[0, 1, 3], [0, 1, 8], [0, 2, 10], [0, 3, 12], [1, 3, 6], [2, 3, 8], [2, 5, 16], [3, 4, 15], [3, 5, 7], [4, 5, 12]]
    queries = [[0, 4, 5], [0, 4, 11], [0, 4, 16], [1, 5, 8], [2, 5, 13]]

    print('Approach 1:', distance_limited_paths_exist_brute_force(n, edges, queries))
    print('Approach 2:', distance_limited_paths_exist_edge_cleaning(n, edges, queries))
    print('Approach 3:', distance_limited_paths_exist_disjoint_set(n, edges, queries))

    # Output:
    # Approach 1: [False, False, True, True, True]
    # Approach 2: [False, False, True, True, True]
    # Approach 3: [False, False, True, True, True]
