import java.util.*;

public class Johnson {

    public static<T> ArrayList<T> reconstruct(HashMap<T, HashMap<T, T>> prev, T src, T dest) {
        if (src == dest) {
            ArrayList<T> path = new ArrayList<>();
            path.add(src);
            return path;
        } else if (prev.get(src).get(dest) == null)
            return null;
        ArrayList<T> path = new ArrayList<>();
        path.add(dest);
        T vertex = dest;
        while (prev.get(src).get(vertex) != null) {
            vertex = prev.get(src).get(vertex);
            path.add(vertex);
        }
        Collections.reverse(path);
        return path;
    }

    public static<T> ArrayList<Object> johnson(HashMap<T, ArrayList<Tuple<T>>> graph) {
        graph.put((T) "q", new ArrayList<>());
        for (T u : graph.keySet())
            graph.get("q").add(new Tuple<>(u, 0));
        ArrayList<Object> result = BellmanFord.bellmanFord(graph, (T) "q");
        if (result == null)
            return null;
        HashMap<T, Double> h = (HashMap<T, Double>) result.get(0);
        for (T u : graph.keySet()) {
            for(int i = 0; i < graph.get(u).size(); i++) {
                Tuple<T> tuple = graph.get(u).get(i);
                T v = tuple.vertex;
                double w = tuple.weight;
                graph.get(u).set(i, new Tuple<>(v, w+h.get(u)-h.get(v)));
            }
        }
        graph.remove("q");
        HashMap<T, HashMap<T, Double>> dist = new HashMap<>();
        HashMap<T, HashMap<T, T>> prev = new HashMap<>();
        for (T u : graph.keySet()) {
            result = Dijkstra.dijkstra(graph, u);
            dist.put(u, (HashMap<T, Double>) result.get(0));
            prev.put(u, (HashMap<T, T>) result.get(1));
        }
        for (T u : graph.keySet()) {
            for (T v : graph.keySet()) {
                if (dist.get(u).get(v) != Double.MAX_VALUE)
                    dist.get(u).put(v, dist.get(u).get(v)+h.get(v)-h.get(u));
            }
        }
        return new ArrayList<>(Arrays.asList(dist, prev));
    }

    public static void main(String[] args) {
        HashMap<String, ArrayList<Tuple<String>>> graph = new HashMap<>();
        graph.put("A", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("B", 18)); add(new Tuple<>("C", -4)); add(new Tuple<>("E", 3));}});
        graph.put("B", new ArrayList<Tuple<String>>() {{ }});
        graph.put("C", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("B", 16)); add(new Tuple<>("E", -2)); add(new Tuple<>("D", 14));}});
        graph.put("D", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("B", 6)); }});
        graph.put("E", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("D", 5)); }});
        ArrayList<Object> result = johnson(graph);
        if (result == null) {
            System.out.println("Negative cycle detected.");
        } else {
            HashMap<String, HashMap<String, Integer>> dist = (HashMap<String, HashMap<String, Integer>>) result.get(0);
            HashMap<String, HashMap<String, String>> prev = (HashMap<String, HashMap<String, String>>) result.get(1);
            System.out.println("Distances:");
            for (String u : dist.keySet()) {
                for (String v : dist.get(u).keySet()) {
                    System.out.print(dist.get(u).get(v) + " ");
                }
                System.out.println();
            }
            String src = "A", dest = "E";
            System.out.println("\nShortest path from " + src + " to " + dest + ":");
            System.out.println(reconstruct(prev, src, dest));
        }

        // Output:
        // 0,   5,  -4,  -1,  -6
        // inf, 0,  inf, inf, inf
        // inf, 9,  0,   3,   -2
        // inf, 6,  inf, 0,   inf
        // inf, 11, inf, 5,   0


        // Output:
        // Shortest path from A to E:
        // [A, C, E]
    }




}
