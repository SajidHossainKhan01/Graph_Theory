import java.util.*;

public class ReconstructItinerary {
    
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

    public static List<String> findItinerary(List<List<String>> tickets) {
        HashMap<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
        for (List<String> ticket : tickets) {
            String start = ticket.get(0);
            String end = ticket.get(1);
            graph.putIfAbsent(start, new ArrayList<String>());
            graph.get(start).add(end);
        }
        for (String u : graph.keySet())
            Collections.sort(graph.get(u), Collections.reverseOrder());
        List<String> order = hierholzer(graph);
        return order;
    }

    public static void main(String[] args) {
        List<List<String>> tickets = new ArrayList<List<String>>();
        tickets.add(Arrays.asList("JFK", "ATL"));
        tickets.add(Arrays.asList("JFK", "SFO"));
        tickets.add(Arrays.asList("ATL", "JFK"));
        tickets.add(Arrays.asList("ATL", "MUC"));
        tickets.add(Arrays.asList("ATL", "LHR"));
        tickets.add(Arrays.asList("MUC", "LHR"));
        tickets.add(Arrays.asList("LHR", "ATL"));
        tickets.add(Arrays.asList("LHR", "SFO"));
        tickets.add(Arrays.asList("SFO", "ATL"));

        System.out.println(findItinerary(tickets));

        // Output:
        // [JFK, ATL, JFK, SFO, ATL, LHR, ATL, MUC, LHR, SFO]
    }
}
