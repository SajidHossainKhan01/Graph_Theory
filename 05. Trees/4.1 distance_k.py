from __future__ import annotations
from queue import Queue


class Node:
    def __init__(self, val: any):
        self.val: any = val
        self.children: list[Node] = []

    def display(self, indent: str ='') -> None:
        print(indent + str(self.val))
        child: Node
        for child in self.children:
            child.display(indent+'    ')


def get_parent_map(root: Node) -> dict[Node,Node]:
    parent_map: dict[Node,Node] = {root: None}
    queue: Queue[Node] = Queue()
    queue.put(root)
    while not queue.empty():
        node: Node = queue.get()
        child: Node
        for child in node.children:
            parent_map[child] = node
            queue.put(child)
    return parent_map


def distance_k(root: Node, start: Node, k: int) -> list[any]:
    parent_map: dict[Node,Node] = get_parent_map(root)
    output: list[any] = []
    queue: Queue[tuple[Node,int]] = Queue()
    queue.put((start, 0))
    visited: set[Node] = set()
    visited.add(start)
    while not queue.empty():
        node: Node; level: int
        node, level = queue.get()
        if level == k:
            output.append(node.val)
        neighbors: list[Node] = node.children + [parent_map[node]]
        neighbor: Node
        for neighbor in neighbors:
            if level < k and neighbor is not None and neighbor not in visited:
                visited.add(neighbor)
                queue.put((neighbor, level+1))
    return output


if __name__ == '__main__':
    nodes: dict[str,Node] = {u: Node(u) for u in 'ABCDEFGHIJKLMNOPQRST'}
    children_map: dict[str,list[str]] = {
        'A': ['B', 'C', 'D'],
        'B': ['E', 'F'],
        'C': ['G'],
        'D': ['H', 'I', 'J'],
        'E': ['K'],
        'F': ['L', 'M'],
        'G': [],
        'H': ['N'],
        'I': [],
        'J': ['O'],
        'K': [],
        'L': ['P', 'Q'],
        'M': ['R'],
        'N': [],
        'O': ['S', 'T'],
        'P': [],
        'Q': [],
        'R': [],
        'S': [],
        'T': []
    }
    node: str
    for node in children_map:
        child: str
        for child in children_map[node]:
            nodes[node].children.append(nodes[child])
    root: Node; start: Node; k: int
    root, start, k = nodes['A'], nodes['B'], 3

    print('Input:')
    root.display()

    print(distance_k(root, start, k))

    """Input:
        A
            B
                E        
                    K    
                F        
                    L    
                        P
                        Q
                    M
                        R
            C
                G
            D
                H
                    N
                I
                J
                    O
                        S
                        T"""
    # Output: ['P', 'Q', 'R', 'G', 'H', 'I', 'J']