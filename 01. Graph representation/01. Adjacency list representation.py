from __future__ import annotations


class GraphAdjList:
    def __init__(self) -> None:
        self.adj_list: dict[any,list[any]] = {}  # Hash table of vertex to array of vertices

    def add_vertex(self, u: any) -> None:
        if u not in self.adj_list:
            self.adj_list[u] = []

    def delete_vertex(self, u: any) -> None:
        if u not in self.adj_list:
            return
        del self.adj_list[u]
        v: any
        for v in self.adj_list:
            u: any
            if u in self.adj_list[v]:
                self.adj_list[v].remove(u)

    def add_edge(self, u: any, v: any) -> None:
        if u not in self.adj_list:
            self.add_vertex(u)
        if v not in self.adj_list:
            self.add_vertex(v)
        self.adj_list[u].append(v)
        self.adj_list[v].append(u)

    def remove_edge(self, u: any, v: any) -> None:
        if u in self.adj_list and v in self.adj_list[u] and v in self.adj_list and u in self.adj_list[v]:
            self.adj_list[u].remove(v)
            self.adj_list[v].remove(u)

    def edge_exists(self, u: any, v: any) -> bool:
        return u in self.adj_list and v in self.adj_list[u]

    def get_vertices(self) -> list[any]:
        return list(self.adj_list.keys())

    def get_neighbors(self, u: any) -> list[any]:
        if u not in self.adj_list:
            return []
        return self.adj_list[u]

    def initialize(self, vertices: list[any], edges: list[tuple[any,any]]) -> None:
        u: any
        for u in vertices:
            self.add_vertex(u)
        src: any; dest: any
        for src, dest in edges:
            self.add_edge(src, dest)


if __name__ == '__main__':
    graph: GraphAdjList[str] = GraphAdjList()
    graph.initialize(['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'],
                     [('A', 'B'), ('A', 'C'), ('A', 'D'), ('B', 'E'), ('B', 'F'),
                      ('C', 'G'), ('D', 'H'), ('D', 'I')])

    print(graph.adj_list)
    print(graph.get_neighbors('A'))
    print(graph.edge_exists('A', 'B'))
    print(graph.edge_exists('A', 'E'))
    graph.remove_edge('A', 'B')
    print(graph.edge_exists('A', 'B'))
    graph.add_edge('A', 'B')
    print(graph.edge_exists('A', 'B'))
    graph.delete_vertex('A')
    print(graph.adj_list)

    """Output:
    {
        'A': ['B', 'C', 'D'], 
        'B': ['A', 'E', 'F'], 
        'C': ['A', 'G'], 
        'D': ['A', 'H', 'I'], 
        'E': ['B'], 
        'F': ['B'], 
        'G': ['C'], 
        'H': ['D'], 
        'I': ['D']
    }

    ['B', 'C', 'D']

    True

    False

    False

    True
    
    {
        'B': ['E', 'F'], 
        'C': ['G'], 
        'D': ['H', 'I'], 
        'E': ['B'], 
        'F': ['B'], 
        'G': ['C'], 
        'H': ['D'], 
        'I': ['D']
    }"""