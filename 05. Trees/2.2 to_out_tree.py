from __future__ import annotations


class Node:
    def __init__(self, val: any):
        self.val: any = val
        self.children: list[Node] = []

    def display(self, indent: str ='') -> None:
        print(indent + str(self.val))
        child: Node
        for child in self.children:
            child.display(indent+'    ')


def dfs(graph: dict[any,list[any]], node: any, visited: set[any]) -> None:
    visited.add(node.val)
    neighbor: any
    for neighbor in graph[node.val]:
        if neighbor not in visited:
            neighbor_node: Node = Node(neighbor)
            node.children.append(neighbor_node)
            dfs(graph, neighbor_node, visited)


def to_out_tree(graph: dict[any,list[any]], root_val: any) -> Node:  # We assume that graph is a valid tree
    root: Node = Node(root_val)
    visited: set[any] = set()
    dfs(graph, root, visited)
    return root


graph: dict[str,list[str]] = {
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

to_out_tree(graph, 'G').display()

# Output:
# G
#     B
#        A
#     F
#         C
#         E
#     I
#         J
#     K
#         H
#             D
