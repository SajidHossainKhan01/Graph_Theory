from __future__ import annotations


def dfs(graph: dict[any,list[any]], vertex: any, path: list[any], in_path: set[any]) -> list[any] | None:
    path.append(vertex)
    in_path.add(vertex)
    neighbor: any
    for neighbor in graph[vertex]:
        if len(path) == len(graph) and neighbor == path[0]:
            path.append(path[0])
            return path
        elif neighbor not in in_path:
            result: list[any] = dfs(graph, neighbor, path, in_path)
            if result:
                return result
    path.pop()
    in_path.remove(vertex)
    return None


def hamiltonian_cycle_backtracking(graph: dict[any,list[any]]) -> list[any] | None:
    start: any = list(graph.keys())[0]
    path: list[any] = []
    in_path: set[any] = set()
    return dfs(graph, start, path, in_path)


if __name__ == '__main__':
    graph: dict[str,list[str]] = {
        'A': ['B', 'C', 'D'],
        'B': ['A', 'C', 'E'],
        'C': ['A', 'B', 'D', 'E', 'F'],
        'D': ['A', 'C', 'E', 'F'],
        'E': ['B', 'C', 'D', 'F'],
        'F': ['C', 'D', 'E']
    }

    print(hamiltonian_cycle_backtracking(graph))

    # Output:
    # ['A', 'B', 'C', 'E', 'F', 'D', 'A']
