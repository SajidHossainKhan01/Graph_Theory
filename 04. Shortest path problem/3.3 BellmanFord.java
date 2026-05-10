import java.util.*;

public class BellmanFord {

    public static<T> ArrayList<T> reconstruct(HashMap<T, T> prev, T dest) {
        ArrayList<T> path = new ArrayList<>();
        T u = dest;
        while (u != null) {
            path.add(u);
            u = prev.get(u);
        }
        Collections.reverse(path);
        return path;
    }

    public static<T> ArrayList<Edge<T>> getEdges(HashMap<T, ArrayList<Tuple<T>>> graph) {
        ArrayList<Edge<T>> edges = new ArrayList<>();
        for (T u : graph.keySet()) {
            for (Tuple<T> tuple : graph.get(u)) {
                T v = tuple.vertex;
                double w = tuple.weight;
                edges.add(new Edge<>(u, v, w));
            }
        }
        return edges;
    }

    public static<T> ArrayList<Object> bellmanFord(HashMap<T, ArrayList<Tuple<T>>> graph, T src) {
        int n = graph.size();
        ArrayList<Edge<T>> edges = getEdges(graph);
        HashMap<T, Double> dist = new HashMap<>();
        HashMap<T, T> prev = new HashMap<>();
        for (T v : graph.keySet()) {
            dist.put(v, v == src ? 0 : Double.MAX_VALUE);
            prev.put(v, null);
        }
        for(int k = 1; k < n; k++) {
            boolean changed = false;
            for (Edge<T> edge : edges) {
                T u = edge.src;
                T v = edge.dest;
                double w = edge.weight;
                if (dist.get(u) != Double.MAX_VALUE && dist.get(u)+w < dist.get(v)) {
                    dist.put(v, dist.get(u)+w);
                    prev.put(v, u);
                    changed = true;
                }
            }
            if (!changed)
                break;
        }
        for (Edge<T> edge : edges) {
            T u = edge.src;
            T v = edge.dest;
            double w = edge.weight;
            if (dist.get(u)+w < dist.get(v))
                return null;
        }
        return new ArrayList<>(Arrays.asList(dist, prev));
    }

    public static void main(String[] args) {

        // Graph with no negative cycles :
        System.out.println("Graph 1:");
        HashMap<String, ArrayList<Tuple<String>>> graph1 = new HashMap<>();
        graph1.put("A", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("B", 3)); add(new Tuple<>("C", 12)); }});
        graph1.put("B", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("C", 6)); add(new Tuple<>("D", 2)); add(new Tuple<>("E", 11));}});
        graph1.put("C", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("E", 3)); }});
        graph1.put("D", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("E", 10)); add(new Tuple<>("F", 5)); }});
        graph1.put("E", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("A", -6)); }});
        graph1.put("F", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("E", -2)); add(new Tuple<>("G", 6)); add(new Tuple<>("H", 13)); add(new Tuple<>("B", 9));}});
        graph1.put("G", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("H", 4)); }});
        graph1.put("H", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("F", -5)); }});
        String src = "A";
        ArrayList<Object> result = bellmanFord(graph1, src);
        
        if (result != null) {
            HashMap<String, Integer> dist = (HashMap<String, Integer>) result.get(0);
            HashMap<String, String> prev = (HashMap<String, String>) result.get(1);
            for (String v : graph1.keySet())
                System.out.println("sp from " + src + " to " + v + " : " + reconstruct(prev, v) + " (weight: " + dist.get(v) + ")");
        } else {
            System.out.println("The graph contains negative cycles!");
        }

        // Graph with negative cycles :
        System.out.println("\nGraph 2:");
        HashMap<String, ArrayList<Tuple<String>>> graph2 = new HashMap<>();
        graph2.put("A", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("B", 3)); add(new Tuple<>("C", 12)); }});
        graph2.put("B", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("C", 6)); add(new Tuple<>("D", 2)); add(new Tuple<>("E", 11));}});
        graph2.put("C", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("E", 3)); }});
        graph2.put("D", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("E", 10)); add(new Tuple<>("F", 5)); }});
        graph2.put("E", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("A", -6)); add(new Tuple<>("D", -6)); }});
        graph2.put("F", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("E", -2)); add(new Tuple<>("G", 6)); add(new Tuple<>("H", 13)); add(new Tuple<>("B", 9));}});
        graph2.put("G", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("H", 4)); }});
        graph2.put("H", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("F", -5)); }});
        src = "A";
        result = bellmanFord(graph2, src);
        
        if (result != null) {
            HashMap<String, Integer> dist = (HashMap<String, Integer>) result.get(0);
            HashMap<String, String> prev = (HashMap<String, String>) result.get(1);
            for (String v : graph2.keySet())
                System.out.println("sp from " + src + " to " + v + " : " + reconstruct(prev, v) + " (weight: " + dist.get(v) + ")");
        } else {
            System.out.println("The graph contains negative cycles!");
        }

        // Output:
        // Graph 1:
        // sp from A to A : [A] (weight: 0)
        // sp from A to B : [A, B] (weight: 3)
        // sp from A to C : [A, B, C] (weight: 9)
        // sp from A to D : [A, B, D] (weight: 5)
        // sp from A to E : [A, B, D, F, E] (weight: 8)
        // sp from A to F : [A, B, D, F] (weight: 10)
        // sp from A to G : [A, B, D, F, G] (weight: 16)
        // sp from A to H : [A, B, D, F, G, H] (weight: 20)

        // Graph 2:
        // The graph contains negative cycles!
    }
}
