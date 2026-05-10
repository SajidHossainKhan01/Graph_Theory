from __future__ import annotations
from collections import defaultdict
from queue import Queue


def topological_sort(graph: dict[any, list[any]]) -> list[any]:
    indegree: dict[any,int] = defaultdict(lambda: 0)
    u: any
    for u in graph:
        v: any
        for v in graph[u]:
            indegree[v] += 1
    queue: Queue[any] = Queue()
    vertex: any
    for vertex in graph:
        if indegree[vertex] == 0:
            queue.put(vertex)
    ordering: list[any] = []
    while not queue.empty():
        vertex: any = queue.get()
        ordering.append(vertex)
        neighbor: any
        for neighbor in graph[vertex]:
            indegree[neighbor] -= 1
            if indegree[neighbor] == 0:
                queue.put(neighbor)
    if len(ordering) < len(graph):
        return []
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
    # Output: [0, 1, 2, 13, 5, 4, 3, 8, 6, 11, 7, 9, 10, 12, 14]
    