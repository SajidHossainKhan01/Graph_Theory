import java.util.*;

public class Dinic {

    public static<T> ArrayList<T[]> dfs(HashMap<T, HashMap<T, Double>> residual, T u, T sink, ArrayList<T[]> path, HashSet<T> visited, HashMap<T, Integer> level) {
        if (u.equals(sink))
            return path;
        visited.add(u);
        for (T v : residual.get(u).keySet()) {
            if (!visited.contains(v) && residual.get(u).get(v) > 0 && level.get(u) < level.get(v)) {
                path.add((T[]) new Object[]{u, v});
                if (dfs(residual, v, sink, path, visited, level) != null)
                    return path;
                path.remove(path.size()-1);
            }
        }
        return null;
    }

    public static<T> HashMap<T, Integer> getLevelMap(HashMap<T, HashMap<T, Double>> residual, T source) {
        Queue<T> queue = new LinkedList<>();
        queue.add(source);
        HashMap<T, Integer> level = new HashMap<>();
        level.put(source, 0);
        while (!queue.isEmpty()) {
            T u = queue.poll();
            for (T v : residual.get(u).keySet()) {
                if (!level.containsKey(v) && residual.get(u).get(v) > 0) {
                    queue.add(v);
                    level.put(v, level.get(u)+1);
                }
            }
        }
        return level;
    }

    public static<T> double dinic(HashMap<T, ArrayList<Tuple<T>>> graph) {
        HashMap<T, HashMap<T, Double>> residual = new HashMap<>();
        for (T u : graph.keySet()) {
            for (Tuple<T> tuple : graph.get(u)) {
                T v = tuple.vertex;
                double w = tuple.weight;
                if (!residual.containsKey(u))
                    residual.put(u, new HashMap<>());
                if (!residual.containsKey(v))
                    residual.put(v, new HashMap<>());
                residual.get(u).put(v, w);
                residual.get(v).put(u, 0.0);
            }
        }
        double totalFlow = 0;
        while (true) {
            HashMap<T, Integer> level = getLevelMap(residual, (T) "s");
            if (!level.containsKey((T) "t"))
                break;
            while (true) {
                ArrayList<T[]> path = dfs(residual, (T) "s", (T) "t", new ArrayList<>(), new HashSet<>(), level);
                if (path == null)
                    break;
                double bottleneck = Double.MAX_VALUE;
                for (T[] tuple : path)
                    bottleneck = Math.min(bottleneck, residual.get(tuple[0]).get(tuple[1]));
                totalFlow += bottleneck;
                for (T[] tuple : path) {
                    residual.get(tuple[0]).put(tuple[1], residual.get(tuple[0]).get(tuple[1])-bottleneck);
                    residual.get(tuple[1]).put(tuple[0], residual.get(tuple[1]).get(tuple[0])+bottleneck);
                }
            }
        }
        return totalFlow;
    }

    public static void main(String[] args) {

        HashMap<String, ArrayList<Tuple<String>>> graph1 = new HashMap<>();
        graph1.put("s", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("A", 10)); add(new Tuple<>("B", 10)); }});
        graph1.put("A", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("C", 4)); add(new Tuple<>("D", 8)); }});
        graph1.put("B", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("D", 8)); }});
        graph1.put("C", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("E", 6)); }});
        graph1.put("D", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("F", 6)); add(new Tuple<>("E", 8)); }});
        graph1.put("E", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("F", 6)); add(new Tuple<>("t", 8)); }});
        graph1.put("F", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("t", 10)); }});

        HashMap<String, ArrayList<Tuple<String>>> graph2 = new HashMap<>();
        graph2.put("s", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("A", 10)); add(new Tuple<>("B", 10)); add(new Tuple<>("C", 10)); }});
        graph2.put("A", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("B", 6)); add(new Tuple<>("D", 8)); }});
        graph2.put("B", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("D", 4)); add(new Tuple<>("E", 7)); }});
        graph2.put("C", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("F", 8)); }});
        graph2.put("D", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("G", 6)); add(new Tuple<>("H", 6)); }});
        graph2.put("E", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("C", 7)); add(new Tuple<>("D", 6)); add(new Tuple<>("I", 4)); }});
        graph2.put("F", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("E", 5)); add(new Tuple<>("I", 6)); }});
        graph2.put("G", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("H", 8)); add(new Tuple<>("t", 10)); }});
        graph2.put("H", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("E", 5)); add(new Tuple<>("t", 10)); }});
        graph2.put("I", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("H", 6)); add(new Tuple<>("t", 8)); }});

        System.out.println("Graph 1: Maximum flow = " + dinic(graph1));
        System.out.println("Graph 2: Maximum flow = " + dinic(graph2));

        // Output:
        // Graph 1: Maximum flow = 18.0
        // Graph 2: Maximum flow = 22.0
    }
}
