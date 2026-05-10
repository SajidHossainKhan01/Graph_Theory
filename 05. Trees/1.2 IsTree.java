import java.util.*;

public class IsTree {
    public static<T> void dfs(HashMap<T, ArrayList<T>> graph, T vertex, HashSet<T> visited, ArrayList<Edge<T>> edges) {
        visited.add(vertex);
        for (T neighbor : graph.get(vertex)) {
            edges.add(new Edge<>(vertex, neighbor));
            if (!visited.contains(neighbor))
                dfs(graph, neighbor, visited, edges);
        }
    }

    public static<T> boolean isTree(HashMap<T, ArrayList<T>> graph) {
        HashSet<T> visited = new HashSet<>();
        ArrayList<Edge<T>> edges = new ArrayList<>();
        T start = graph.keySet().iterator().next();
        dfs(graph, start, visited, edges);
        return visited.size() == graph.size() && edges.size() == 2*(graph.size()-1);
    }

    public static void main(String[] args) {
        HashMap<String, ArrayList<String>> graph1 = new HashMap<>();
        graph1.put("A", new ArrayList<>(Arrays.asList("B")));
        graph1.put("B", new ArrayList<>(Arrays.asList("A", "G")));
        graph1.put("C", new ArrayList<>(Arrays.asList("F")));
        graph1.put("D", new ArrayList<>(Arrays.asList("H")));
        graph1.put("E", new ArrayList<>(Arrays.asList("F")));
        graph1.put("F", new ArrayList<>(Arrays.asList("C", "E", "G")));
        graph1.put("G", new ArrayList<>(Arrays.asList("B", "F", "I", "K")));
        graph1.put("H", new ArrayList<>(Arrays.asList("D", "K")));
        graph1.put("I", new ArrayList<>(Arrays.asList("G", "J")));
        graph1.put("J", new ArrayList<>(Arrays.asList("I")));
        graph1.put("K", new ArrayList<>(Arrays.asList("G", "H")));

        HashMap<String, ArrayList<String>> graph2 = new HashMap<>();
        graph2.put("A", new ArrayList<>(Arrays.asList("B")));
        graph2.put("B", new ArrayList<>(Arrays.asList("A", "G")));
        graph2.put("C", new ArrayList<>(Arrays.asList("F")));
        graph2.put("D", new ArrayList<>(Arrays.asList("H")));
        graph2.put("E", new ArrayList<>(Arrays.asList("F")));
        graph2.put("F", new ArrayList<>(Arrays.asList("C", "E", "G")));
        graph2.put("G", new ArrayList<>(Arrays.asList("B", "F", "I")));
        graph2.put("H", new ArrayList<>(Arrays.asList("D", "K")));
        graph2.put("I", new ArrayList<>(Arrays.asList("G", "J")));
        graph2.put("J", new ArrayList<>(Arrays.asList("I")));
        graph2.put("K", new ArrayList<>(Arrays.asList("H")));

        HashMap<String, ArrayList<String>> graph3 = new HashMap<>();
        graph3.put("A", new ArrayList<>(Arrays.asList("B")));
        graph3.put("B", new ArrayList<>(Arrays.asList("A", "G")));
        graph3.put("C", new ArrayList<>(Arrays.asList("F", "E")));
        graph3.put("D", new ArrayList<>(Arrays.asList("H")));
        graph3.put("E", new ArrayList<>(Arrays.asList("F", "C")));
        graph3.put("F", new ArrayList<>(Arrays.asList("C", "E", "G")));
        graph3.put("G", new ArrayList<>(Arrays.asList("B", "F", "I", "K")));
        graph3.put("H", new ArrayList<>(Arrays.asList("D", "K")));
        graph3.put("I", new ArrayList<>(Arrays.asList("G", "J")));
        graph3.put("J", new ArrayList<>(Arrays.asList("I")));
        graph3.put("K", new ArrayList<>(Arrays.asList("G", "H")));

        System.out.println("Graph 1 is tree: " + isTree(graph1));
        System.out.println("Graph 2 is tree: " + isTree(graph2));
        System.out.println("Graph 3 is tree: " + isTree(graph3));

        // Output:
        // Graph 1 is tree: true
        // Graph 2 is tree: false
        // Graph 3 is tree: false
    }
}
