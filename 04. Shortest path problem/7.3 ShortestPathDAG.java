import java.util.*;

public class ShortestPathDAG {
    
    public static<T> ArrayList<T> topologicalSort(HashMap<T, ArrayList<Tuple<T>>> graph) {
        HashMap<T, Integer> indegree = new HashMap<>();
        for(T vertex : graph.keySet()) indegree.put(vertex, 0);
        for(T vertex : graph.keySet()) {
            for(Tuple<T> tuple : graph.get(vertex)) {
                T neighbor = tuple.vertex;
                indegree.put(neighbor, indegree.get(neighbor)+1);
            }
        }
        Queue<T> queue = new LinkedList<>();
        for(T vertex : indegree.keySet())
            if(indegree.get(vertex) == 0)
                queue.add(vertex);
        ArrayList<T> ordering = new ArrayList<>();
        while(!queue.isEmpty()) {
            T vertex = queue.poll();
            ordering.add(vertex);
            for(Tuple<T> tuple : graph.get(vertex)) {
                T neighbor = tuple.vertex;
                indegree.put(neighbor, indegree.get(neighbor)-1);
                if(indegree.get(neighbor) == 0)
                    queue.add(neighbor);
            }
        }
        if(ordering.size() < graph.size())
            return null;
        return ordering;
    }

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

    // Approach 1: min of in-neighbors
    public static<T> ArrayList<Object> shortestPath1(HashMap<T, ArrayList<Tuple<T>>> graph, T src) {
        ArrayList<T> ordering = topologicalSort(graph);
        if(ordering == null)
            return null;
        HashMap<T, ArrayList<Tuple<T>>> tgraph = new HashMap<>();
        for(T vertex : graph.keySet())
            tgraph.put(vertex, new ArrayList<>());
        for(T vertex : graph.keySet()) {
            for(Tuple<T> tuple : graph.get(vertex)) {
                T neighbor = tuple.vertex;
                double weight = tuple.weight;
                tgraph.get(neighbor).add(new Tuple<T>(vertex, weight));
            }
        }
        HashMap<T, Double> dist = new HashMap<>();
        HashMap<T, T> prev = new HashMap<>();
        for (T vertex : graph.keySet()) {
            dist.put(vertex, Double.MAX_VALUE);
            prev.put(vertex, null);
        }
        dist.put(src, 0d);
        for(T v : ordering) {
            if (v != src && tgraph.get(v).stream().anyMatch(t -> dist.get(t.vertex) != Double.MAX_VALUE)) {
                Tuple<T> min = tgraph.get(v).stream().min(Comparator.comparing(t -> dist.get(t.vertex)+t.weight)).get();
                T u = min.vertex;
                double w = min.weight;
                dist.put(v, dist.get(u)+w);
                prev.put(v, u);
            }
        }
        return new ArrayList<>() {{ add(dist); add(prev); }};
    }

    // Approach 2: relaxing edges
    public static<T> ArrayList<Object> shortestPath2(HashMap<T, ArrayList<Tuple<T>>> graph, T src) {
        ArrayList<T> ordering = topologicalSort(graph);
        if(ordering == null)
            return null;
        HashMap<T, Double> dist = new HashMap<>();
        HashMap<T, T> prev = new HashMap<>();
        for (T vertex : graph.keySet()) {
            dist.put(vertex, Double.MAX_VALUE);
            prev.put(vertex, null);
        }
        dist.put(src, 0d);
        for(T u : ordering) {
            for(Tuple<T> tuple : graph.get(u)) {
                T v = tuple.vertex;
                double w = tuple.weight;
                if(dist.get(u) != Double.MAX_VALUE && dist.get(u)+w < dist.get(v)) {
                    dist.put(v, dist.get(u)+w);
                    prev.put(v, u);
                }
            }
        }
        return new ArrayList<>() {{ add(dist); add(prev); }};
    }

    public static void main(String[] args) {
        HashMap<String, ArrayList<Tuple<String>>> graph = new HashMap<>();
        graph.put("A", new ArrayList<>() {{ add(new Tuple<>("B", 2)); add(new Tuple<>("D", 4)); add(new Tuple<>("E", 5)); }});
        graph.put("B", new ArrayList<>() {{ add(new Tuple<>("E", 6)); }});
        graph.put("C", new ArrayList<>() {{ add(new Tuple<>("E", 1)); add(new Tuple<>("F", 4)); }});
        graph.put("D", new ArrayList<>() {{ add(new Tuple<>("E", 3)); add(new Tuple<>("H", 7)); }});
        graph.put("E", new ArrayList<>() {{ add(new Tuple<>("H", 2)); }});
        graph.put("F", new ArrayList<>() {{ add(new Tuple<>("I", 3)); }});
        graph.put("G", new ArrayList<>() {{ add(new Tuple<>("H", 3)); add(new Tuple<>("J", 8)); }});
        graph.put("H", new ArrayList<>() {{ add(new Tuple<>("I", 4)); add(new Tuple<>("K", 4)); add(new Tuple<>("L", 9)); }});
        graph.put("I", new ArrayList<>() {{ add(new Tuple<>("L", 1)); }});
        graph.put("J", new ArrayList<>() {{ add(new Tuple<>("K", 2)); }});
        graph.put("K", new ArrayList<>());
        graph.put("L", new ArrayList<>());

        System.out.println("Approach 1");
        String src = "D";
        ArrayList<Object> result = shortestPath1(graph, src);
        HashMap<String, Double> dist = (HashMap<String, Double>) result.get(0);
        HashMap<String, String> prev = (HashMap<String, String>) result.get(1);
        System.out.println("dist: " + dist);
        System.out.println("prev: " + prev);
        String dest = "L";
        System.out.println("sp from " + src + " to " + dest + ": " + reconstruct(prev, dest));

        System.out.println("\nApproach 2:");
        src = "D";
        result = shortestPath2(graph, src);
        dist = (HashMap<String, Double>) result.get(0);
        prev = (HashMap<String, String>) result.get(1);
        System.out.println("dist: " + dist);
        System.out.println("prev: " + prev);
        dest = "L";
        System.out.println("sp from " + src + " to " + dest + ": " + reconstruct(prev, dest));

        /*Output:
        dist: {A=inf, B=inf, C=inf, D=0.0, E=3.0, F=inf, G=inf, H=5.0, I=9.0, J=inf, K=9.0, L=10.0}
        prev: {A=null, B=null, C=null, D=null, E=D, F=null, G=null, H=E, I=H, J=null, K=H, L=I}
        sp from D to L: [D, E, H, I, L]

        Approach 2:
        dist: {A=inf, B=inf, C=inf, D=0.0, E=3.0, F=inf, G=inf, H=5.0, I=9.0, J=inf, K=9.0, L=10.0}
        prev: {A=null, B=null, C=null, D=null, E=D, F=null, G=null, H=E, I=H, J=null, K=H, L=I}
        sp from D to L: [D, E, H, I, L]
        */
    }
}
