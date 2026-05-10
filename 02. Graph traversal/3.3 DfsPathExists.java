import java.util.*;

public class DfsPathExists {
    public static boolean dfs(ArrayList<Integer>[] graph, int vertex, int end, HashSet<Integer> visited) {
        if (vertex == end)
            return true;
        visited.add(vertex);
        for (int neighbor : graph[vertex])
            if (!visited.contains(neighbor))
                if (dfs(graph, neighbor, end, visited))
                    return true;
        return false;
    }

    public static boolean pathExists(int n, int[][] edges, int start, int end) {
        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();
        for (int[] edge : edges) {
            int src = edge[0], dest = edge[1];
            graph[src].add(dest);
            graph[dest].add(src);
        }
        HashSet<Integer> visited = new HashSet<>();
        return dfs(graph, start, end, visited);
    }

    public static void main(String[] args) {
        int n = 9;
        int[][] edges = {{0, 1}, {0, 2}, {2, 4}, {3, 5}, {5, 6}, {5, 7}, {6, 8}};

        int start = 0;
        int end = 6;
        System.out.println("Path exists from " + start + " to " + end + ": " + pathExists(n, edges, start, end));

        start = 3;
        end = 6;
        System.out.println("Path exists from " + start + " to " + end + ": " + pathExists(n, edges, start, end));
        
        // Output:
        // false
        // true
    }
}
