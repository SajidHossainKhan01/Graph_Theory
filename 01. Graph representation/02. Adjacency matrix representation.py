from __future__ import annotations


class GraphAdjMat:
    def __init__(self) -> None:
        self.adj_mat: list[list[any]] = []
        self.idx: dict[any,int] = {}
        self.label: dict[int,any] = {}

    def add_vertex(self, u: any) -> None:
        if u in self.idx:
            return
        for row in self.adj_mat:
            row.append(0)
        self.idx[u] = len(self.idx)
        self.label[len(self.label)] = u
        self.adj_mat.append([0]*(len(self.adj_mat)+1))

    def delete_vertex(self, u: any) -> None:
        if u not in self.idx:
            return
        self.adj_mat[self.idx[u]], self.adj_mat[-1] = self.adj_mat[-1], self.adj_mat[self.idx[u]]
        self.adj_mat.pop()
        row: list[any]
        for row in self.adj_mat:
            row[self.idx[u]], row[-1] = row[-1], row[self.idx[u]]
            row.pop()
        self.idx[self.label[len(self.adj_mat)]] = self.idx[u]
        self.label[self.idx[u]] = self.label[len(self.adj_mat)]
        del self.idx[u]
        del self.label[len(self.adj_mat)]

    def add_edge(self, u: any, v: any) -> None:
        if u not in self.idx:
            self.add_vertex(u)
        if v not in self.idx:
            self.add_vertex(v)
        self.adj_mat[self.idx[u]][self.idx[v]] = 1
        self.adj_mat[self.idx[v]][self.idx[u]] = 1

    def edge_exists(self, u: any, v: any) -> bool:
        if u not in self.idx or v not in self.idx:
            return False
        return self.adj_mat[self.idx[u]][self.idx[v]] == 1

    def remove_edge(self, u: any, v: any) -> None:
        if u not in self.idx or v not in self.idx:
            return
        self.adj_mat[self.idx[u]][self.idx[v]] = 0
        self.adj_mat[self.idx[v]][self.idx[u]] = 0

    def get_vertices(self) -> list[any]:
        return list(self.idx.keys())

    def get_neighbors(self, u: any) -> list[any]:
        neighbors: list[any] = []
        if u not in self.idx:
            return neighbors
        v: any
        for v in self.label:
            if self.adj_mat[self.idx[u]][v] == 1:
                neighbors.append(self.label[v])
        return neighbors

    def initialize(self, vertices: list[any], edges: list[tuple[any,any]]):
        u: any
        for u in vertices:
            self.add_vertex(u)
        src: any; dest: any
        for src, dest in edges:
            self.add_edge(src, dest)


if __name__ == '__main__':
    graph = GraphAdjMat()
    graph.initialize(['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'],
                     [('A', 'B'), ('A', 'C'), ('A', 'D'), ('B', 'E'), ('B', 'F'),
                      ('C', 'G'), ('D', 'H'), ('D', 'I')])
    print(*graph.adj_mat, sep='\n')
    print(graph.idx)
    print(graph.label)
    print(graph.get_neighbors('A'))
    print(graph.edge_exists('A', 'B'))
    print(graph.edge_exists('A', 'E'))
    graph.remove_edge('A', 'B')
    print(graph.edge_exists('A', 'B'))
    graph.add_edge('A', 'B')
    print(graph.edge_exists('A', 'B'))
    graph.delete_vertex('A')
    print(*graph.adj_mat, sep='\n')
    print(graph.idx)
    print(graph.label)

    """Output:
    [0, 1, 1, 1, 0, 0, 0, 0, 0]
    [1, 0, 0, 0, 1, 1, 0, 0, 0]
    [1, 0, 0, 0, 0, 0, 1, 0, 0]
    [1, 0, 0, 0, 0, 0, 0, 1, 1]
    [0, 1, 0, 0, 0, 0, 0, 0, 0]
    [0, 1, 0, 0, 0, 0, 0, 0, 0]
    [0, 0, 1, 0, 0, 0, 0, 0, 0]
    [0, 0, 0, 1, 0, 0, 0, 0, 0]
    [0, 0, 0, 1, 0, 0, 0, 0, 0]

    {'A': 0, 'B': 1, 'C': 2, 'D': 3, 'E': 4, 'F': 5, 'G': 6, 'H': 7, 'I': 8}

    {0: 'A', 1: 'B', 2: 'C', 3: 'D', 4: 'E', 5: 'F', 6: 'G', 7: 'H', 8: 'I'}

    ['B', 'C', 'D']

    True

    False

    False

    True

    [0, 0, 0, 1, 0, 0, 0, 0]
    [0, 0, 0, 0, 1, 1, 0, 0]
    [0, 0, 0, 0, 0, 0, 1, 0]
    [1, 0, 0, 0, 0, 0, 0, 1]
    [0, 1, 0, 0, 0, 0, 0, 0]
    [0, 1, 0, 0, 0, 0, 0, 0]
    [0, 0, 1, 0, 0, 0, 0, 0]
    [0, 0, 0, 1, 0, 0, 0, 0]

    {'B': 1, 'C': 2, 'D': 3, 'E': 4, 'F': 5, 'G': 6, 'H': 7, 'I': 0}

    {0: 'I', 1: 'B', 2: 'C', 3: 'D', 4: 'E', 5: 'F', 6: 'G', 7: 'H'}"""
            