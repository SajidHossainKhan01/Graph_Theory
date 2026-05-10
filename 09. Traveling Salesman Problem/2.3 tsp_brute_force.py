from __future__ import annotations


def generate_permutations(arr: list[int]) -> list[list[int]]:
    if len(arr) == 1:
        return [arr]
    permutations: list[list[int]] = []
    for i in range(len(arr)):
        extracted_element = arr[i]
        remaining_elements = arr[:i]+arr[i+1:]
        smaller_permutations = generate_permutations(remaining_elements)
        for permutation in smaller_permutations:
            permutation.insert(0, extracted_element)
            permutations.append(permutation)
    return permutations


def tsp_brute_force(graph: list[list[float]]) -> tuple[float,list[int]]:
    n: int = len(graph)
    vertices: list[int] = list(range(n))
    permutations: list[list[int]] = generate_permutations(vertices)
    min_cost: float = float('inf')
    min_tour: list[int] = []
    for permutation in permutations:
        permutation.append(permutation[0])
        cost: float = 0
        for i in range(n):
            cost += graph[permutation[i]][permutation[i+1]]
        if cost < min_cost:
            min_cost = cost
            min_tour = permutation
    return min_cost, min_tour


if __name__ == '__main__':

    points = [(1, 1), (2, 5), (2, 9), (4, 0), (5, 8), (5, 11), (6, 4), (9, 2), (10, 11), (12, 8)]
    graph = [[round(((x1-x2)**2+(y1-y2)**2)**0.5, 2) for x2, y2 in points] for x1, y1 in points]
    min_cost: float; min_tour: list[int]
    min_cost, min_tour = tsp_brute_force(graph)
    print(f'Minimum cost: {min_cost}')
    print(f'Minimum path: {min_tour}')
    