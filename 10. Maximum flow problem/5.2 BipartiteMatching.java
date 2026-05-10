import java.util.*;

public class BipartiteMatching {

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

    public static<T> HashMap<T, HashMap<T, Double>> dinic(HashMap<T, ArrayList<Tuple<T>>> graph) {
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
        return residual;
    }

    public static<T> ArrayList<T[]> bipartiteMatching(HashSet<T> set1, HashSet<T> set2, ArrayList<T[]> edges) {
        HashMap<T, ArrayList<Tuple<T>>> graph = new HashMap<>();
        for (T[] tuple : edges) {
            T u = tuple[0];
            T v = tuple[1];
            if (!graph.containsKey(u))
                graph.put(u, new ArrayList<>());
            graph.get(u).add(new Tuple<>(v, 1.0));
        }
        graph.put((T) "s", new ArrayList<>());
        for (T u : set1)
            graph.get((T) "s").add(new Tuple<>(u, 1.0));
        for (T u : set2) {
            if (!graph.containsKey(u))
                graph.put(u, new ArrayList<>());
            graph.get(u).add(new Tuple<>((T) "t", 1.0));
        }
        HashMap<T, HashMap<T, Double>> residual = dinic(graph);
        ArrayList<T[]> matching = new ArrayList<>();
        for (T u : set1) {
            for (T v : residual.get(u).keySet()) {
                if (!v.equals("s") && residual.get(u).get(v) == 0)
                    matching.add((T[]) new Object[]{u, v});
            }
        }
        return matching;
    }

    public static void main(String[] args) {

        HashSet<String> set1 = new HashSet<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
        HashSet<String> set2 = new HashSet<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));
        ArrayList<String[]> edges = new ArrayList<>(Arrays.asList(new String[][]{{"a", "1"}, {"a", "4"}, {"b", "1"}, {"b", "2"}, {"c", "2"}, {"c", "5"}, {"d", "1"}, {"d", "3"}, {"d", "6"}, {"e", "3"}, {"e", "5"}, {"e", "6"}, {"f", "6"}, {"f", "7"}, {"g", "5"}, {"g", "8"}, {"h", "8"}}));
        ArrayList<String[]> matching = bipartiteMatching(set1, set2, edges);
        for (Object[] tuple : matching)
            System.out.print("(" + tuple[0] + ", " + tuple[1] + ") ");

        // Output: (a, 4) (b, 1) (c, 2) (d, 3) (e, 6) (f, 7) (g, 5) (h, 8) 
    }
}
