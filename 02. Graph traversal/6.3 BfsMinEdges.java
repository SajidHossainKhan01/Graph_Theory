import java.util.*;

public class BfsMinEdges {

    public static int minEdges(int n, int[][] edges, int start, int end) {
        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();
        for (int[] edge : edges) {
            int src = edge[0], dest = edge[1];
            graph[src].add(dest);
            graph[dest].add(src);
        }
        Queue<int[]> queue = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();
        queue.add(new int[]{start, 0});
        visited.add(start);
        while (!queue.isEmpty()) {
            int[] popped = queue.poll();
            int vertex = popped[0], level = popped[1];
            if (vertex == end)
                return level;
            for (int neighbor : graph[vertex]) {
                if (!visited.contains(neighbor)) {
                    queue.add(new int[]{neighbor, level+1});
                    visited.add(neighbor);
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int n = 6;
        int[][] edges = {{0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 4}, {2, 4}, {3, 4}, {3, 5}, {4, 5}};
        int start = 0, end = 5;
        System.out.println(minEdges(n, edges, start, end));

        // Output:
        // 2
    }
}
