import java.util.*;

public class FloydWarshall {

    public static<T> ArrayList<T> reconstruct(int[][] nxt, HashMap<T, Integer> idx, T src, T dest) {
        if (nxt[idx.get(src)][idx.get(dest)] == -1)
            return null;
        HashMap<Integer, T> label = new HashMap<>();
        for (T u : idx.keySet())
            label.put(idx.get(u), u);
        ArrayList<T> path = new ArrayList<>();
        path.add(src);
        int temp = idx.get(src);
        while (temp != idx.get(dest)) {
            temp = nxt[temp][idx.get(dest)];
            path.add(label.get(temp));
        }
        return path;
    }

    public static<T> ArrayList<Object> floydWarshall(HashMap<T, ArrayList<Tuple<T>>> graph) {
        ArrayList<T> vertices = new ArrayList<>(graph.keySet());
        HashMap<T, Integer> idx = new HashMap<>();
        for (int i = 0; i < vertices.size(); i++)
            idx.put(vertices.get(i), i);
        int n = vertices.size();
        double[][] dist = new double[n][n];
        int[][] nxt = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Double.MAX_VALUE);
            Arrays.fill(nxt[i], -1);
        }
        for (T u : graph.keySet()) {
            for(Tuple<T> tuple : graph.get(u)) {
                T v = tuple.vertex;
                double w = tuple.weight;
                dist[idx.get(u)][idx.get(v)] = w;
                nxt[idx.get(u)][idx.get(v)] = idx.get(v);
            }
        }
        for(int i = 0; i < n; i++) {
            dist[i][i] = 0;
            nxt[i][i] = i;
        }
        for(int k = 0; k < n; k++) {
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    if (dist[i][k] != Double.MAX_VALUE && dist[k][j] != Double.MAX_VALUE) { // check for overflow
                        if (dist[i][k]+dist[k][j] < dist[i][j]) {
                            dist[i][j] = dist[i][k]+dist[k][j];
                            nxt[i][j] = nxt[i][k];
                        }
                    }
                }
            }
        }
        for(int i = 0; i < n; i++)
            if (dist[i][i] < 0)
                return null;
        return new ArrayList<>(Arrays.asList(dist, nxt, idx));
    }

    public static void main(String[] args) {
        HashMap<String, ArrayList<Tuple<String>>> graph = new HashMap<>();
        graph.put("A", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("B", 18)); add(new Tuple<>("C", -4)); add(new Tuple<>("E", 3));}});
        graph.put("B", new ArrayList<Tuple<String>>() {{ }});
        graph.put("C", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("B", 16)); add(new Tuple<>("E", -2)); add(new Tuple<>("D", 14));}});
        graph.put("D", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("B", 6)); }});
        graph.put("E", new ArrayList<Tuple<String>>() {{ add(new Tuple<>("D", 5)); }});
        ArrayList<Object> result = floydWarshall(graph);
        double[][] dist = (double[][]) result.get(0);
        int[][] nxt = (int[][]) result.get(1);
        HashMap<String, Integer> idx = (HashMap<String, Integer>) result.get(2);

        System.out.println("Distances:");
        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist.length; j++) {
                System.out.print(dist[i][j] + " ");
            }
            System.out.println();
        }

        // Output:
        // 0,   5,  -4,  -1,  -6
        // inf, 0,  inf, inf, inf
        // inf, 9,  0,   3,   -2
        // inf, 6,  inf, 0,   inf
        // inf, 11, inf, 5,   0

        String src = "A", dest = "E";
        System.out.println("\nShortest path from " + src + " to " + dest + ":");
        System.out.println(reconstruct(nxt, idx, src, dest));

        // Output:
        // Shortest path from A to E:
        // [A, C, E]
    }
}
