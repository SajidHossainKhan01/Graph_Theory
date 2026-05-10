import java.util.*;

public class TwoColorability {

    public static<T> boolean bfs(Map<T, List<T>> graph, T vertex, Map<T, Integer> color) {
        Queue<T> queue = new LinkedList<>();
        queue.add(vertex);
        color.put(vertex, 0);
        while (!queue.isEmpty()) {
            vertex = queue.poll();
            for (T neighbor : graph.get(vertex)) {
                if (!color.containsKey(neighbor)) {
                    color.put(neighbor, 1 - color.get(vertex));
                    queue.add(neighbor);
                } else if (color.get(neighbor) == color.get(vertex)) {
                    return false;
                }
            }
        }
        return true;
    }

    // 3 variants are possible:
    // Variant 1: Knowing if an undirected graph is bipartite:
    public static<T> boolean isBipartite(Map<T, List<T>> graph) {
        Map<T, Integer> color = new HashMap<>();
        for (T vertex : graph.keySet()) {
            if (!color.containsKey(vertex))
                if (!bfs(graph, vertex, color))
                    return false;
        }
        return true;
    }

    // Variant 2: Coloring a graph with 2 colors (if possible, otherwise null will be returned):
    public static<T> Map<T, Integer> twoColoring(Map<T, List<T>> graph) {
        Map<T, Integer> color = new HashMap<>();
        for (T vertex : graph.keySet()) {
            if (!color.containsKey(vertex))
                if (!bfs(graph, vertex, color))
                    return null;
        }
        return color;
    }

    // Variant 3: Partitioning vertices of a bipartite graph into two disjoint independent sets (if possible, otherwise null will be returned):
    public static<T> ArrayList<ArrayList<T>> partitionBipartite(Map<T, List<T>> graph) {
        Map<T, Integer> color = new HashMap<>();
        for (T vertex : graph.keySet()) {
            if (!color.containsKey(vertex))
                if (!bfs(graph, vertex, color))
                    return null;
        }
        ArrayList<T> firstSet = new ArrayList<>();
        ArrayList<T> secondSet = new ArrayList<>();
        for (T vertex : graph.keySet()) {
            if (color.get(vertex) == 0)
                firstSet.add(vertex);
            else
                secondSet.add(vertex);
        }
        ArrayList<ArrayList<T>> result = new ArrayList<>();
        result.add(firstSet);
        result.add(secondSet);
        return result;
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", List.of("1", "5"));
        graph.put("B", List.of("1", "2", "3", "6"));
        graph.put("C", List.of("2"));
        graph.put("D", List.of("4", "5", "6"));
        graph.put("1", List.of("A", "B"));
        graph.put("2", List.of("C"));
        graph.put("3", List.of("B"));
        graph.put("4", List.of("D"));
        graph.put("5", List.of("A", "D"));
        graph.put("6", List.of("B", "D"));

        System.out.println(isBipartite(graph));
        // Output: True

        System.out.println(twoColoring(graph));
        // Output: {A=0, 1=1, B=0, 2=1, 3=1, C=0, D=0, 4=1, 5=1, 6=1}

        System.out.println(partitionBipartite(graph));
        // Output: [[A, B, C, D], [1, 2, 3, 4, 5, 6]]
    }
}