from __future__ import annotations
from queue import Queue


# Approach 1: Depth-first search
def dfs(graph: list[list[int]], vertex: int, visited: set[int]) -> None:
    visited.add(vertex)
    for neighbor in graph[vertex]:
        if neighbor not in visited:
            dfs(graph, neighbor, visited)


def can_visit_all_rooms_dfs(rooms: list[list[int]]) -> bool:
    visited: set[int] = set()
    dfs(rooms, 0, visited)
    return len(visited) == len(rooms)


# Approach 2: Breadth-first search
def can_visit_all_rooms_bfs(rooms: list[list[int]]) -> bool:
    queue: Queue[int] = Queue()
    queue.put(0)
    visited: set[int] = {0}
    while not queue.empty():
        vertex: int = queue.get()
        for neighbor in rooms[vertex]:
            if neighbor not in visited:
                queue.put(neighbor)
                visited.add(vertex)
    return len(visited) == len(rooms)


if __name__ == '__main__':
    print('Example 1:')
    rooms: list[list[int]] = [[1, 2], [2, 3], [3], [4], [2]]
    print(f'Can visit all rooms (dfs): {can_visit_all_rooms_dfs(rooms)}')
    print(f'Can visit all rooms (bfs): {can_visit_all_rooms_bfs(rooms)}')   

    print('\nExample 2:')
    rooms: list[list[int]] = [[1, 2], [2, 3], [3], [], [2, 4]]
    print(f'Can visit all rooms (dfs): {can_visit_all_rooms_dfs(rooms)}')
    print(f'Can visit all rooms (bfs): {can_visit_all_rooms_bfs(rooms)}')

    # Output:
    # Example 1:
    # Can visit all rooms (dfs): True
    # Can visit all rooms (bfs): True

    # Example 2:
    # Can visit all rooms (dfs): False
    # Can visit all rooms (bfs): False
