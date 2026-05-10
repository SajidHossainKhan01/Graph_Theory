from __future__ import annotations
from collections import defaultdict


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


def kruskal(graph: dict[any,list[tuple[any,float]]]) -> dict[any,list[tuple[any,float]]]:
    vertices: list[any] = list(graph.keys())
    edges: list[tuple[any,any,float]] = []
    visited: set[any] = set()
    u: any
    for u in graph:
        visited.add(u)
        v: any; w: float
        for v, w in graph[u]:
            if v not in visited:
                edges.append((u, v, w))
    edges.sort(key=lambda e: e[2])
    mst: dict[any,list[tuple[any,float]]] = defaultdict(lambda: [])
    ds: DisjointSet[any] = DisjointSet(vertices)
    nb_edges: int = 0
    u: any; v: any; w: float
    for u, v, w in edges:
        if ds.find(u) != ds.find(v):
            mst[u].append((v, w))
            mst[v].append((u, w))
            nb_edges += 1
            ds.union(u, v)
            if nb_edges == len(vertices) - 1:
                break
    return mst


if __name__ == '__main__':
    graph: dict[str,list[tuple[str,float]]] = {
        'A': [('C', 3), ('B', 6), ('F', 9)],
        'B': [('D', 2), ('C', 4), ('A', 6), ('G', 9)],
        'C': [('D', 2), ('A', 3), ('B', 4), ('E', 9), ('F', 9)],
        'D': [('C', 2), ('B', 2), ('E', 8), ('G', 9)],
        'E': [('G', 7), ('F', 8), ('D', 8), ('C', 9), ('I', 9), ('J', 10)],
        'F': [('E', 8), ('C', 9), ('A', 9), ('J', 18)],
        'G': [('H', 4), ('I', 5), ('E', 7), ('B', 9), ('D', 9)],
        'H': [('I', 1), ('J', 4), ('G', 4)],
        'I': [('H', 1), ('J', 3), ('G', 5), ('E', 9)],
        'J': [('I', 3), ('H', 4), ('E', 10), ('F', 18)]
    }

    print(kruskal(graph))
    # Output: {
    #   'H': [('I', 1), ('G', 4)],
    #   'I': [('H', 1), ('J', 3)],
    #   'B': [('D', 2)], 'D': [('B', 2), ('C', 2), ('E', 8)],
    #   'C': [('D', 2), ('A', 3)],
    #   'A': [('C', 3)],
    #   'J': [('I', 3)], 'G': [('H', 4), ('E', 7)],
    #   'E': [('G', 7), ('D', 8), ('F', 8)],
    #   'F': [('E', 8)]
    # }