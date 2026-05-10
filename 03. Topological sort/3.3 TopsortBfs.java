import java.util.*;

public class TopsortBfs {

    public static<T> ArrayList<T> topologicalSort(HashMap<T, ArrayList<T>> graph) {
        HashMap<T, Integer> indegree = new HashMap<>();
        for(T vertex : graph.keySet()) indegree.put(vertex, 0);
        for(T vertex : graph.keySet())
            for(T neighbor : graph.get(vertex))
                indegree.put(neighbor, indegree.get(neighbor)+1);
        Queue<T> queue = new LinkedList<>();
        for(T vertex : indegree.keySet())
            if(indegree.get(vertex) == 0)
                queue.add(vertex);
        ArrayList<T> ordering = new ArrayList<>();
        while(!queue.isEmpty()) {
            T vertex = queue.poll();
            ordering.add(vertex);
            for(T neighbor : graph.get(vertex)) {
                indegree.put(neighbor, indegree.get(neighbor)-1);
                if(indegree.get(neighbor) == 0)
                    queue.add(neighbor);
            }
        }
        if(ordering.size() < graph.size())
            return null;
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
        // Output: [0, 1, 2, 13, 5, 4, 3, 8, 6, 11, 7, 9, 10, 12, 14]
    }
}