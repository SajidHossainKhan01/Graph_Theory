import java.util.*;

public class FindSpanningTree {
    
    public static<T> void dfs(HashMap<T,ArrayList<T>> graph, T vertex, Set<T> visited, HashMap<T,ArrayList<T>> spanningTree) {
        visited.add(vertex);
        ArrayList<T> neighbors = graph.get(vertex);
        for (T neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                spanningTree.get(vertex).add(neighbor);
                spanningTree.get(neighbor).add(vertex);
                dfs(graph, neighbor, visited, spanningTree);
            }
        }
    }

    public static<T> HashMap<T,ArrayList<T>> findSpanningTree(HashMap<T,ArrayList<T>> graph) {
        HashMap<T,ArrayList<T>> spanningTree = new HashMap<T,ArrayList<T>>();
        for (T vertex : graph.keySet())
            spanningTree.put(vertex, new ArrayList<T>());
        HashSet<T> visited = new HashSet<T>();
        T arbitraryVertex = graph.keySet().iterator().next();
        dfs(graph, arbitraryVertex, visited, spanningTree);
        return spanningTree;
    }

    public static void main(String[] args) {

        HashMap<String,ArrayList<String>> graph = new HashMap<String,ArrayList<String>>();
        graph.put("A", new ArrayList<String>(Arrays.asList("B", "C", "F")));
        graph.put("B", new ArrayList<String>(Arrays.asList("A", "C", "D", "G")));
        graph.put("C", new ArrayList<String>(Arrays.asList("A", "B", "D", "E", "F")));
        graph.put("D", new ArrayList<String>(Arrays.asList("B", "C", "E", "G")));
        graph.put("E", new ArrayList<String>(Arrays.asList("C", "D", "F", "G", "I", "J")));
        graph.put("F", new ArrayList<String>(Arrays.asList("A", "C", "E", "J")));
        graph.put("G", new ArrayList<String>(Arrays.asList("B", "D", "E", "H", "I")));
        graph.put("H", new ArrayList<String>(Arrays.asList("G", "I", "J")));
        graph.put("I", new ArrayList<String>(Arrays.asList("E", "G", "H")));
        graph.put("J", new ArrayList<String>(Arrays.asList("E", "F", "H")));

        HashMap<String,ArrayList<String>> spanningTree = findSpanningTree(graph);
        System.out.println(spanningTree);

        /*Output:
        {
            A=[B], 
            B=[A, C], 
            C=[B, D], 
            D=[C, E], 
            E=[D, F], 
            F=[E, J], 
            G=[H, I], 
            H=[J, G], 
            I=[G], 
            J=[F, H]}
        */
    }
}
