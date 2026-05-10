import java.util.*;

public class EdgeLengthLimited {

    // Approach 1: Brute force solution, trying all paths
    public static boolean dfs(HashMap<Integer,ArrayList<int[]>> graph, int vertex, int end, int limit, HashSet<Integer> path) {
        path.add(vertex);
        if (vertex == end)
            return true;
        for (int[] tuple : graph.get(vertex)) {
            int neighbor = tuple[0];
            int weight = tuple[1];
            if (!path.contains(neighbor) && weight < limit) {
                if (dfs(graph, neighbor, end, limit, path))
                    return true;
            }
        }
        path.remove(vertex);
        return false;
    }

    public static boolean[] edgeLengthLimitedPathsExistBruteForce(int n, int[][] edges, int[][] queries) {
        HashMap<Integer,ArrayList<int[]>> graph = new HashMap<>();
        for (int i = 0; i < n; i++)
            graph.put(i, new ArrayList<int[]>());
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            graph.get(u).add(new int[]{v, w});
            graph.get(v).add(new int[]{u, w});
        }
        boolean[] output = new boolean[queries.length];
        int i = 0;
        for (int[] query : queries) {
            int u = query[0], v = query[1], limit = query[2];
            HashSet<Integer> path = new HashSet<Integer>();
            output[i++] = dfs(graph, u, v, limit, path);
        }
        return output;
    }

    // Approach 2: Edge cleaning, removing edges with weight >= limit for each query
    public static boolean canReach(HashMap<Integer,ArrayList<Integer>> graph, int vertex, int end, HashSet<Integer> visited) {
        if (vertex == end)
            return true;
        visited.add(vertex);
        for (int neighbor : graph.get(vertex)) {
            if (!visited.contains(neighbor)) {
                if (canReach(graph, neighbor, end, visited))
                    return true;
            }
        }
        return false;
    }

    public static boolean[] edgeLengthLimitedPathsExistEdgeCleaning(int n, int[][] edges, int[][] queries) {
        boolean output[] = new boolean[queries.length];
        int i = 0;
        for (int[] query : queries) {
            int start = query[0], end = query[1], limit = query[2];
            HashMap<Integer,ArrayList<Integer>> graph = new HashMap<>();
            for (int j = 0; j < n; j++)
                graph.put(j, new ArrayList<Integer>());
            for (int[] edge : edges) {
                int u = edge[0], v = edge[1], w = edge[2];
                if (w < limit) {
                    graph.get(u).add(v);
                    graph.get(v).add(u);
                }
            }
            HashSet<Integer> visited = new HashSet<Integer>();
            output[i++] = canReach(graph, start, end, visited);
        }
        return output;
    }

    // Approach 3: Disjoint set, incrementally building a disjoint set of connected components as we add edges
    public static boolean[] edgeLengthLimitedPathsExistDisjointSet(int n, int[][] edges, int[][] queries) {
        boolean output[] = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++)
            queries[i] = new int[]{queries[i][0], queries[i][1], queries[i][2], i};
        Arrays.sort(edges, (e1, e2) -> e1[2] - e2[2]);
        Arrays.sort(queries, (q1, q2) -> q1[2] - q2[2]);
        ArrayList<Integer> vertices = new ArrayList<Integer>();
        for (int i = 0; i < n; i++)
            vertices.add(i);
        DisjointSet<Integer> ds = new DisjointSet<>(vertices);
        int i = 0;
        for (int[] query : queries) {
            int start = query[0], end = query[1], limit = query[2], pos = query[3];
            while (i < edges.length && edges[i][2] < limit) {
                ds.union(edges[i][0], edges[i][1]);
                i++;
            }
            output[pos] = ds.find(start) == ds.find(end);
        }
        return output;
    }

    public static void main(String[] args) {
        int n = 6;
        int[][] edges = new int[][]{{0, 1, 3}, {0, 1, 8}, {0, 2, 10}, {0, 3, 12}, {1, 3, 6}, {2, 3, 8}, {2, 5, 16}, {3, 4, 15}, {3, 5, 7}, {4, 5, 12}};
        int[][] queries = new int[][]{{0, 4, 5}, {0, 4, 11}, {0, 4, 16}, {1, 5, 8}, {2, 5, 13}};

        System.out.println("Approach 1: " + Arrays.toString(edgeLengthLimitedPathsExistBruteForce(n, edges, queries)));
        System.out.println("Approach 2: " + Arrays.toString(edgeLengthLimitedPathsExistEdgeCleaning(n, edges, queries)));
        System.out.println("Approach 3: " + Arrays.toString(edgeLengthLimitedPathsExistDisjointSet(n, edges, queries)));

        // Output:
        // Approach 1: [false, false, true, true, true]
        // Approach 2: [false, false, true, true, true]
        // Approach 3: [false, false, true, true, true]
    }
}
