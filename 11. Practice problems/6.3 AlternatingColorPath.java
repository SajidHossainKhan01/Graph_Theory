import java.util.*;

public class AlternatingColorPath {
    
    // We will represent "red" color by 0, "blue" color by 1, and "uncolored" by -1, to be able to use int[] type as a tuple
    public static void bfs(HashMap<Integer,ArrayList<int[]>> graph, int[] output) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0, -1});
        HashSet<String> visited = new HashSet<>();
        visited.add("0,-1");
        while (!queue.isEmpty()) {
            int[] polled = queue.poll();
            int vertex = polled[0], level = polled[1], prevEdgeC = polled[2];
            output[vertex] = Math.min(output[vertex], level);
            for (int[] tuple : graph.get(vertex)) {
                int neighbor = tuple[0], edgeC = tuple[1];
                if (!visited.contains(neighbor+","+edgeC) && edgeC != prevEdgeC) {
                    visited.add(neighbor+","+edgeC);
                    queue.add(new int[]{neighbor, level+1, edgeC});
                }
            }
        }
    }

    public static int[] shortestPaths(int n, int[][] red_edges, int[][] blue_edges) {
        HashMap<Integer,ArrayList<int[]>> graph = new HashMap<>();
        for (int i = 0; i < n; i++)
            graph.put(i, new ArrayList<int[]>());
        for (int[] edge : red_edges) {
            int u = edge[0], v = edge[1];
            graph.get(u).add(new int[]{v, 0});
        }
        for (int[] edge : blue_edges) {
            int u = edge[0], v = edge[1];
            graph.get(u).add(new int[]{v, 1});
        }
        int[] output = new int[n];
        Arrays.fill(output, Integer.MAX_VALUE);
        bfs(graph, output);
        for (int i = 0; i < n; i++)
            if (output[i] == Integer.MAX_VALUE)
                output[i] = -1;
        return output;
    }

    public static void main(String[] args) {
        int n = 6;
        int[][] redEdges = {{0, 1}, {1, 2}, {1, 3}, {2, 3}, {3, 4}};
        int[][] blueEdges = {{1, 2}, {2, 3}, {2, 4}, {3, 1}};
        int[] output = shortestPaths(n, redEdges, blueEdges);
        
        System.out.println("Shortest distances: " + Arrays.toString(output));

        // Output:
        // Shortest distances: [0, 1, 2, 3, 6, -1]
    }
}
