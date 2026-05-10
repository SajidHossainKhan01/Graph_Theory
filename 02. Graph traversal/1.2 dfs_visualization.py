import matplotlib.pyplot as plt
import networkx as nx
import random
import matplotlib.animation as animation
import string


def generate_random_graph(n=None, p=None):
    if n is None:
        n = random.randint(8, 25)
    if p is None:
        p = random.random()/4 + 0.25
    graph = nx.Graph()
    vertices = string.ascii_uppercase[:n]
    graph.add_nodes_from(vertices)
    for i in range(n):
        for j in range(i+1, n):
            if random.random() < p:
                graph.add_edge(vertices[i], vertices[j])
    return graph


def dfs(graph, vertex, visited, events):
    visited.add(vertex)
    for neighbor in graph[vertex]:
        if neighbor not in visited:
            events.append(('visit', (vertex, neighbor)))
            dfs(graph, neighbor, visited, events)
            events.append(('backtrack', vertex))


def get_events(graph):
    events = []
    visited = set()
    vertices = list(graph.nodes())
    random.shuffle(vertices)
    for vertex in vertices:
        if vertex not in visited:
            events.append(('launch', vertex))
            dfs(graph, vertex, visited, events)
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
    event_type, elems = events[frame]
    if event_type == 'launch':
        node = elems
        indicator.center = [pos[node][0], pos[node][1]+0.05]
        graph.nodes[node]['visited'] = True
        nx.draw_networkx_nodes(graph, pos, nodelist=[node], node_color='b', node_size=500)
    elif event_type == 'visit':
        src, dest = elems
        graph.nodes[dest]['visited'] = True
        indicator.center = [pos[dest][0], pos[dest][1]+0.05]
        nx.draw_networkx_nodes(graph, pos, nodelist=[dest], node_color='b', node_size=500)
        nx.draw_networkx_edges(graph, pos, edgelist=[(src, dest)], edge_color='b', width=5)
    elif event_type == 'backtrack':
        node = elems
        indicator.center = [pos[node][0], pos[node][1]+0.05]
        nx.draw_networkx_nodes(graph, pos, nodelist=[node], node_color='b', node_size=500)


if __name__ == '__main__':
    graph = generate_random_graph()
    pos = nx.spring_layout(graph)
    events = get_events(graph)
    indicator = plt.Circle(pos[events[0][1]], 0.02, color='g', zorder=float('inf'))
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