from __future__ import annotations
from collections import defaultdict
from queue import Queue


def topological_sort(graph: dict[any,list[any]]) -> list[any]:
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
    return ordering


def find_recipes(recipes: list[str], ingredients: list[list[str]], supplies: list[str]) -> list[str]:
    ingredients: dict[str,set[str]] = {recipes[i]: set(ingredients[i]) for i in range(len(ingredients))}
    supplies: set[str] = set(supplies)
    graph: dict[str,list[str]] = {recipe: [] for recipe in recipes}
    u: str
    for u in recipes:
        v: str
        for v in recipes:
            if u in ingredients[v]:
                graph[u].append(v)
    ordering: list[str] = topological_sort(graph)
    prepared: list[str] = []
    recipe: str
    for recipe in ordering:
        if ingredients[recipe].issubset(supplies):
            prepared.append(recipe)
            supplies.add(recipe)
    return prepared


if __name__ == '__main__':
    recipes: list[str] = ["chicken burger", "buns", "crispy chicken"]
    ingredients: list[str] = [["buns", "crispy chicken", "lettuce", "cheese"], ["yeast", "flour"], ["chicken", "breadcrumbs"]]
    supplies: list[str] = ["yeast", "flour", "cheese", "breadcrumbs", "milk", "lettuce", "chicken"]

    print('Possible recipes are:', find_recipes(recipes, ingredients, supplies))
    # Output: Possible recipes are: ['buns', 'crispy chicken', 'chicken burger']