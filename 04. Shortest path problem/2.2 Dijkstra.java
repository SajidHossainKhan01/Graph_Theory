import java.util.*;


public class Dijkstra {

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

    public static<T> ArrayList<Object> dijkstra(HashMap<T, ArrayList<Tuple<T>>> graph, T src) {
        HashMap<T, Double> dist = new HashMap<>();
        HashMap<T, T> prev = new HashMap<>();
        HashMap<T, FibonacciHeap.Entry<T>> nodes = new HashMap<>();
        FibonacciHeap<T> queue = new FibonacciHeap<>();
        for (T u : graph.keySet()) {
            dist.put(u, Double.MAX_VALUE);
            prev.put(u, null);
            nodes.put(u, queue.enqueue(u, Integer.MAX_VALUE));
        }
        dist.put(src, (double) 0);
        queue.decreaseKey(nodes.get(src), 0);
        while (!queue.isEmpty()) {
            FibonacciHeap.Entry<T> entry = queue.dequeueMin();
            T u = entry.getValue();
            for (Tuple<T> tuple : graph.get(u)) {
                T v = tuple.vertex;
                double w = tuple.weight;
                if (dist.get(u) != Double.MAX_VALUE && dist.get(u)+w < dist.get(v)) {
                    dist.put(v, dist.get(u)+w);
                    prev.put(v, u);
                    queue.decreaseKey(nodes.get(v), dist.get(u)+w);
                }
            }
        }
        return new ArrayList<>(Arrays.asList(dist, prev));
    }

    public static void main(String[] args) {
        HashMap<String, ArrayList<Tuple<String>>> graph = new HashMap<>();
        graph.put("A", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("B", 7)); add(new Tuple<>("C", 9)); add(new Tuple<>("E", 13)); add(new Tuple<>("F", 14)); }});
        graph.put("B", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("D", 4)); add(new Tuple<>("E", 8)); }});
        graph.put("C", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("F", 12)); add(new Tuple<>("I", 22));}});
        graph.put("D", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("E", 6)); add(new Tuple<>("G", 15)); }});
        graph.put("E", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("G", 10)); add(new Tuple<>("F", 7));}});
        graph.put("F", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("G", 11)); add(new Tuple<>("H", 13)); add(new Tuple<>("I", 14));}});
        graph.put("G", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("J", 12)); add(new Tuple<>("H", 12));}});
        graph.put("H", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("J", 6)); add(new Tuple<>("K", 8)); add(new Tuple<>("L", 9));}});
        graph.put("I", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("H", 6)); add(new Tuple<>("L", 8));}});
        graph.put("J", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("K", 2));}});
        graph.put("K", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("L", 10));}});
        graph.put("L", new ArrayList<Tuple<String>>() {{ }});

        String src = "A";
        ArrayList<Object> result = dijkstra(graph, src);
        HashMap<String, Integer> dist = (HashMap<String, Integer>) result.get(0);
        HashMap<String, String> prev = (HashMap<String, String>) result.get(1);
        System.out.println("Shortest distances from " + src + " to vertices:");
        System.out.println(dist);
        String dest = "L";
        System.out.println("Shortest path from " + src + " to " + dest + ":");
        System.out.println(reconstruct(prev, dest));
        // Output:
        // Shortest distances from A to vertices:
        // Shortest distances from Ato vertices:
        // {A=0, B=7, C=9, D=11, E=13, F=14, G=23, H=27, I=28, J=33, K=35, L=36}
        // Shortest path from A to L:
        // [A, F, H, L]
    }
}