import java.util.*;

public class Hierholzer {
    
    public static<T> T findStart(HashMap<T, ArrayList<T>> graph) {
        HashMap<T, Integer> outDegree = new HashMap<T, Integer>();
        HashMap<T, Integer> inDegree = new HashMap<T, Integer>();
        for (T u : graph.keySet()) {
            for (T v : graph.get(u)) {
                outDegree.putIfAbsent(u, 0);
                outDegree.put(u, outDegree.get(u)+1);
                inDegree.putIfAbsent(v, 0);
                inDegree.put(v, inDegree.get(v)+1);
            }
        }
        T start = graph.keySet().iterator().next();
        for (T u : graph.keySet()) {
            if (outDegree.get(u) - inDegree.get(u) == 1) {
                start = u;
                break;
            }
        }
        return start;
    }

    public static<T> ArrayList<T> dfs(HashMap<T, ArrayList<T>> graph, T vertex, HashMap<T, Integer> outDegree, ArrayList<T> output) {
        while (outDegree.get(vertex) > 0) {
            outDegree.put(vertex, outDegree.get(vertex)-1);
            T neighbor = graph.get(vertex).get(outDegree.get(vertex));
            dfs(graph, neighbor, outDegree, output);
        }
        output.add(vertex);
        return output;
    }

    public static<T> ArrayList<T> hierholzer(HashMap<T, ArrayList<T>> graph) {  // assumes that the graph is semi-Eulerian
        HashMap<T, Integer> outDegree = new HashMap<T, Integer>();
        for (T u : graph.keySet())
            outDegree.put(u, graph.get(u).size());
        T start = findStart(graph);
        ArrayList<T> output = new ArrayList<T>();
        dfs(graph, start, outDegree, output);
        Collections.reverse(output);
        return output;
    }

    public static void main(String[] args) {
        HashMap<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
        graph.put("A", new ArrayList<String>(Arrays.asList("A", "D")));
        graph.put("B", new ArrayList<String>(Arrays.asList("C", "E")));
        graph.put("C", new ArrayList<String>(Arrays.asList("G", "B")));
        graph.put("D", new ArrayList<String>(Arrays.asList("H", "H", "B")));
        graph.put("E", new ArrayList<String>(Arrays.asList("A", "J")));
        graph.put("F", new ArrayList<String>(Arrays.asList("L", "K", "G")));
        graph.put("G", new ArrayList<String>(Arrays.asList("K")));
        graph.put("H", new ArrayList<String>(Arrays.asList("I", "E")));
        graph.put("I", new ArrayList<String>(Arrays.asList("D")));
        graph.put("J", new ArrayList<String>(Arrays.asList("J", "F")));
        graph.put("K", new ArrayList<String>(Arrays.asList("F", "C")));
        graph.put("L", new ArrayList<String>(Arrays.asList("F")));

        System.out.println(hierholzer(graph));

        // Output:
        // [D, B, E, A, A, D, H, I, D, H, E, J, J, F, G, K, F, L, F, K, C, B, C, G]
    }
}
