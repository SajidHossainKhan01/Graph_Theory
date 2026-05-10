import java.util.*;

public class SumOfDistancesInTree {
    /*
    def dist_to_all(graph: dict[int,list[int]], vertex: int) -> int:
    queue: Queue[tuple[int,int,int]] = Queue()
    queue.put((vertex, 0, None))
    total: int = 0
    while not queue.empty():
        vertex: int; level: int; parent: int
        vertex, level, parent = queue.get()
        total += level
        for neighbor in graph[vertex]:
            if neighbor != parent:
                queue.put((neighbor, level+1, vertex))
    return total


def fill_size_map(graph: dict[int,list[int]], vertex: int, size_map: dict[int,int], parent: int=None) -> int:
    size: int = 1
    neighbor: int
    for neighbor in graph[vertex]:
        if neighbor != parent:
            size += fill_size_map(graph, neighbor, size_map, vertex)
    size_map[vertex] = size
    return size


def fill_dist(graph: dict[int,list[int]], vertex: int, size_map: dict[int,int], dist: list[int], parent=None) -> None:
    if parent is not None:
        dist[vertex] = dist[parent] + len(graph) - 2*size_map[vertex]
    neighbor: int
    for neighbor in graph[vertex]:
        if neighbor != parent:
            fill_dist(graph, neighbor, size_map, dist, vertex)


def min_dist_each(graph: dict[int,list[int]]) -> list[int]:
    n: int = len(graph)
    root: int = 0
    size_map: dict[int,int] = {}
    dist: list[int] = [0]*n
    dist[root] = dist_to_all(graph, root)
    fill_size_map(graph, root, size_map)
    fill_dist(graph, root, size_map, dist)
    return dist


def sum_of_distances_in_tree(n: int, edges: list[list[int,int]]) -> list[int]:
    graph: dict[int,list[int]] = {u: [] for u in range(n)}
    u: int; v: int
    for u, v in edges:
        graph[u].append(v)
        graph[v].append(u)
    dist: list[int] = min_dist_each(graph)
    return dist


if __name__ == '__main__':
    n: int = 14
    edges: list[list[int,int]] = [[0, 1], [0, 3], [0, 10], [1, 2], [3, 4], [3, 5], [4, 6], [4, 7], [4, 8], [7, 9], [10, 11], [10, 12], [11, 13]]

    print(f'Sum of distances: {sum_of_distances_in_tree(n, edges)}')

    # Output:
    # Sum of distances: [29, 39, 51, 29, 33, 41, 45, 43, 45, 55, 35, 45, 47, 57]*/

    public static int distToAll(HashMap<Integer,ArrayList<Integer>> graph, int vertex) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{vertex, 0, -1});
        int total = 0;
        while (!queue.isEmpty()) {
            int[] polled = queue.poll();
            vertex = polled[0];
            int level = polled[1];
            int parent = polled[2];
            total += level;
            for (int neighbor : graph.get(vertex))
                if (neighbor != parent)
                    queue.add(new int[]{neighbor, level+1, vertex});
        }
        return total;
    }

    public static int fillSizeMap(HashMap<Integer,ArrayList<Integer>> graph, int vertex, HashMap<Integer,Integer> sizeMap, int parent) {
        int size = 1;
        for (int neighbor : graph.get(vertex))
            if (neighbor != parent)
                size += fillSizeMap(graph, neighbor, sizeMap, vertex);
        sizeMap.put(vertex, size);
        return size;
    }

    public static void fillDist(HashMap<Integer,ArrayList<Integer>> graph, int vertex, HashMap<Integer,Integer> sizeMap, int[] dist, int parent) {
        if (parent != -1)
            dist[vertex] = dist[parent] + graph.size() - 2*sizeMap.get(vertex);
        for (int neighbor : graph.get(vertex))
            if (neighbor != parent)
                fillDist(graph, neighbor, sizeMap, dist, vertex);
    }

    public static int[] minDistEach(HashMap<Integer,ArrayList<Integer>> graph) {
        int n = graph.size();
        int root = 0;
        HashMap<Integer,Integer> sizeMap = new HashMap<>();
        int[] dist = new int[n];
        dist[root] = distToAll(graph, root);
        fillSizeMap(graph, root, sizeMap, -1);
        fillDist(graph, root, sizeMap, dist, -1);
        return dist;
    }

    public static int[] sumOfDistancesInTree(int n, int[][] edges) {
        HashMap<Integer,ArrayList<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++)
            graph.put(i, new ArrayList<>());
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        int[] dist = minDistEach(graph);
        return dist;
    }

    public static void main(String[] args) {
        int n = 14;
        int[][] edges = {{0, 1}, {0, 3}, {0, 10}, {1, 2}, {3, 4}, {3, 5}, {4, 6}, {4, 7}, {4, 8}, {7, 9}, {10, 11}, {10, 12}, {11, 13}};
        
        System.out.println("Sum of distances: " + Arrays.toString(sumOfDistancesInTree(n, edges)));

        // Output:
        // Sum of distances: [29, 39, 51, 29, 33, 41, 45, 43, 45, 55, 35, 45, 47, 57]
    }
}
