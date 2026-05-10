import java.util.*;

public class MaxStudentsExam {

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

    public static<T> int bipartiteMatching(HashSet<T> set1, HashSet<T> set2, ArrayList<T[]> edges) {
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
        return (int) dinic(graph);
    }

    public static int maxStudents(char[][] seats) {
        int m = seats.length, n = seats[0].length;
        HashSet<String> seatsSet = new HashSet<>();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (seats[i][j] == '.')
                    seatsSet.add(i+","+j);
        HashSet<String> set1 = new HashSet<>();
        HashSet<String> set2 = new HashSet<>();
        for (String tuple : seatsSet) {
            int i = Integer.parseInt(tuple.split(",")[0]);
            int j = Integer.parseInt(tuple.split(",")[1]);
            if (j%2 == 0)
                set1.add(tuple);
            else
                set2.add(tuple);
        }
        ArrayList<String[]> edges = new ArrayList<>();
        for (String tuple : seatsSet) {
            int i = Integer.parseInt(tuple.split(",")[0]);
            int j = Integer.parseInt(tuple.split(",")[1]);
            ArrayList<String> neighbors = new ArrayList<>(Arrays.asList((i-1)+","+(j-1), (i-1)+","+(j+1), i+","+(j-1), i+","+(j+1)));
            for (String neighbor : neighbors) {
                int ni = Integer.parseInt(neighbor.split(",")[0]);
                int nj = Integer.parseInt(neighbor.split(",")[1]);
                if (seatsSet.contains(neighbor))
                    if (set1.contains(tuple))
                        edges.add(new String[]{tuple, neighbor});
                    else
                        edges.add(new String[]{neighbor, tuple});
            }
        }
        return seatsSet.size()-bipartiteMatching(set1, set2, edges);
    }

    public static void main(String[] args) {

        char[][] seats = new char[][]{
            {'.','.','.','#','.','.','#'},
            {'.','#','#','.','#','.','#'},
            {'#','#','#','.','.','#','.'},
            {'#','.','.','#','#','.','#'}
        };

        System.out.println("We can place at most "+maxStudents(seats)+" students");

        // Output: We can place at most 8 students
    }
}
