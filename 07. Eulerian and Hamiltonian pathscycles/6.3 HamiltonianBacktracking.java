import java.util.*;

public class HamiltonianBacktracking {

    public static<T> ArrayList<T> dfs(HashMap<T, ArrayList<T>> graph, T vertex, ArrayList<T> path, HashSet<T> inPath) {
        path.add(vertex);
        inPath.add(vertex);
        for (T neighbor : graph.get(vertex)) {
            if (path.size() == graph.size() && neighbor.equals(path.get(0))) {
                path.add(path.get(0));
                return path;
            } else if (!inPath.contains(neighbor)) {
                ArrayList<T> result = dfs(graph, neighbor, path, inPath);
                if (result != null)
                    return result;
            }
        }
        path.remove(path.size()-1);
        inPath.remove(vertex);
        return null;
    }

    public static<T> ArrayList<T> hamiltonianCycleBacktracking(HashMap<T, ArrayList<T>> graph) {
        T start = graph.keySet().iterator().next();
        ArrayList<T> path = new ArrayList<T>();
        HashSet<T> inPath = new HashSet<T>();
        return dfs(graph, start, path, inPath);
    }

    public static void main(String[] args) {
        HashMap<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
        graph.put("A", new ArrayList<String>(Arrays.asList("B", "C", "D")));
        graph.put("B", new ArrayList<String>(Arrays.asList("A", "C", "E")));
        graph.put("C", new ArrayList<String>(Arrays.asList("A", "B", "D", "E", "F")));
        graph.put("D", new ArrayList<String>(Arrays.asList("A", "C", "E", "F")));
        graph.put("E", new ArrayList<String>(Arrays.asList("B", "C", "D", "F")));
        graph.put("F", new ArrayList<String>(Arrays.asList("C", "D", "E")));

        System.out.println(hamiltonianCycleBacktracking(graph));

        // Output:
        // [A, B, C, E, F, D, A]
    }
}
