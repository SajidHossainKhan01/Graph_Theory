from queue import Queue
import matplotlib.pyplot as plt
import networkx as nx
import random
import matplotlib.animation as animation
import string


def generate_random_graph(n=None, p=None):
    if n is None:
        n = random.randint(8, 25)
    if p is None:
        p = random.random()/4 + 0.1
    graph = nx.Graph()
    vertices = string.ascii_uppercase[:n]
    graph.add_nodes_from(vertices)
    for i in range(n):
        for j in range(i+1, n):
            if random.random() < p:
                graph.add_edge(vertices[i], vertices[j])
    return graph


def draw_graph(graph):
    pos = nx.spring_layout(graph)
    nx.draw_networkx_nodes(graph, pos, node_size=500, node_color='w')
    nx.draw_networkx_edges(graph, pos)
    nx.draw_networkx_labels(graph, pos, font_size=16)
    plt.show()


def bfs(graph, vertex, visited, events):
    queue = Queue()
    queue.put(vertex)
    visited.add(vertex)
    while not queue.empty():
        vertex = queue.get()
        added = set()
        for neighbor in graph[vertex]:
            if neighbor not in visited:
                queue.put(neighbor)
                visited.add(neighbor)
                added.add(neighbor)
        events.append((vertex, added))


def get_events(graph):
    events = []
    visited = set()
    vertices = list(graph.nodes())
    random.shuffle(vertices)
    for vertex in vertices:
        if vertex not in visited:
            bfs(graph, vertex, visited, events)
    return events


def init():
    nx.draw_networkx_nodes(graph, pos, node_color='r', node_size=500)
    nx.draw_networkx_edges(graph, pos, edge_color='black')
    nx.draw_networkx_labels(graph, pos, font_size=14)
    return


def update(frame):
    global pos, graph, events, indicator
    if frame >= len(events):
        return
    vertex, neighbors = events[frame]
    indicator.center = [pos[vertex][0], pos[vertex][1]+0.05]
    graph.nodes[vertex]['visited'] = True
    nx.draw_networkx_nodes(graph, pos, nodelist=[vertex], node_color='b', node_size=500)
    nx.draw_networkx_edges(graph, pos, edgelist=[(vertex, neighbor) for neighbor in neighbors], edge_color='b', width=5)
    nx.draw_networkx_nodes(graph, pos, nodelist=neighbors, node_color='#075993', node_size=500)


if __name__ == '__main__':
    graph = generate_random_graph()
    pos = nx.spring_layout(graph)
    events = get_events(graph)
    indicator = plt.Circle(pos[events[0][0]], 0.02, color='g', zorder=float('inf'))
    fig, ax = plt.subplots()
    ax.set_aspect("equal")
    ax.add_artist(indicator)
    try:
        fig_manager = plt.get_current_fig_manager()
        fig_manager.window.showMaximized()
    except:
        pass
    ani = animation.FuncAnimation(fig, update, interval=400, init_func=init, blit=False)
    plt.show()