from __future__ import annotations
from collections import defaultdict


def find_start(graph: dict[any,list[any]]) -> any:
    out_deg: dict[any,int] = defaultdict(lambda: 0)
    in_deg: dict[any,int] = defaultdict(lambda: 0)
    u: int
    for u in graph:
        v: int
        for v in graph[u]:
            out_deg[u] += 1
            in_deg[v] += 1
    start: any = list(graph.keys())[0]
    u: any
    for u in graph:
        if out_deg[u]-in_deg[u] == 1:
            start = u
            break
    return start


def dfs(graph: dict[any,list[any]], vertex: any, out_deg: dict[any,int], output: list[any]) -> None:
    while out_deg[vertex] > 0:
        out_deg[vertex] -= 1
        neighbor: any = graph[vertex][out_deg[vertex]]
        dfs(graph, neighbor, out_deg, output)
    output.append(vertex)
        
        
def hierholzer(graph: dict[any,list[any]]) -> list[any]:  # assumes that the graph is semi-Eulerian
    out_deg: dict[any,int] = {u: len(graph[u]) for u in graph}
    start: any = find_start(graph)
    output: list[any] = []
    dfs(graph, start, out_deg, output)
    output.reverse()
    return output


def find_itinerary(tickets: list[list[str,str]]) -> list[str]:
    graph: dict[str,list[str]] = defaultdict(lambda: [])
    start: str; end: str
    for start, end in tickets:
        graph[start].append(end)
    u: str
    for u in graph:
        graph[u].sort(reverse=True)
    order: list[str] = hierholzer(graph)
    return order


if __name__ == '__main__':
    tickets: list[list[str,str]] = [["JFK", "ATL"], ["JFK", "SFO"], ["ATL", "JFK"], ["ATL", "MUC"], ["ATL", "LHR"], ["MUC", "LHR"], ["LHR", "ATL"], ["LHR", "SFO"], ["SFO", "ATL"]] 

    print(find_itinerary(tickets))

    # Output:
    # ['JFK', 'ATL', 'JFK', 'SFO', 'ATL', 'LHR', 'ATL', 'MUC', 'LHR', 'SFO']
