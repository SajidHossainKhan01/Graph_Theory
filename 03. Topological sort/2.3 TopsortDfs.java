import java.util.*;

public class TopsortDfs {

    private static<T> boolean dfs(HashMap<T, ArrayList<T>> graph, T vertex, HashSet<T> visited, Stack<T> stack, HashSet<T> path) {
        visited.add(vertex);
        path.add(vertex);
        for (T neighbor : graph.get(vertex)) {
            if (path.contains(neighbor) || (!visited.contains(neighbor) && !dfs(graph, neighbor, visited, stack, path))) 
                return false;
        }
        stack.push(vertex);
        path.remove(vertex);
        return true;
    }

    public static<T> ArrayList<T> topologicalSort(HashMap<T, ArrayList<T>> graph) {
        HashSet<T> visited = new HashSet<T>();
        HashSet<T> path = new HashSet<T>();
        Stack<T> stack = new Stack<T>();
        for(T vertex : graph.keySet()) {
            if(!visited.contains(vertex) && !dfs(graph, vertex, visited, stack, path)) 
                return null;
        }
        ArrayList<T> ordering = new ArrayList<T>();
        while(!stack.isEmpty()) {
            ordering.add(stack.pop());
        }
        return ordering;
    }

    public static void main(String[] args) {
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
        graph.put(0, new ArrayList<>(Arrays.asList(3, 6)));
        graph.put(1, new ArrayList<>(Arrays.asList(3)));
        graph.put(2, new ArrayList<>(Arrays.asList(4, 5)));
        graph.put(3, new ArrayList<>(Arrays.asList(6, 7)));
        graph.put(4, new ArrayList<>(Arrays.asList(3, 7, 8)));
        graph.put(5, new ArrayList<>(Arrays.asList(4, 8)));
        graph.put(6, new ArrayList<>(Arrays.asList(7, 9)));
        graph.put(7, new ArrayList<>(Arrays.asList(10)));
        graph.put(8, new ArrayList<>(Arrays.asList(11)));
        graph.put(9, new ArrayList<>(Arrays.asList(10, 12)));
        graph.put(10, new ArrayList<>(Arrays.asList(12, 14)));
        graph.put(11, new ArrayList<>(Arrays.asList(14)));
        graph.put(12, new ArrayList<>());
        graph.put(13, new ArrayList<>(Arrays.asList(14)));
        graph.put(14, new ArrayList<>());

        ArrayList<Integer> ordering = topologicalSort(graph);
        System.out.println(ordering);
        // Output: [13, 2, 5, 4, 8, 11, 1, 0, 3, 6, 9, 7, 10, 14, 12]
    }
}