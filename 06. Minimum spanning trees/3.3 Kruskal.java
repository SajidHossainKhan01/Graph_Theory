import java.util.*;

public class Kruskal {
    
    public static<T> HashMap<T, ArrayList<Tuple<T>>> kruskal(HashMap<T, ArrayList<Tuple<T>>> graph) {
        ArrayList<T> vertices = new ArrayList<>(graph.keySet());
        ArrayList<Edge<T>> edges = new ArrayList<>();
        HashSet<T> visited = new HashSet<>();
        for (T u : vertices) {
            visited.add(u);
            for (Tuple<T> tuple : graph.get(u)) {
                T v = tuple.vertex;
                double w = tuple.weight;
                if (!visited.contains(v))
                    edges.add(new Edge<>(u, v, w));
            }
        }
        Collections.sort(edges, new Comparator<Edge<T>>() {
            public int compare(Edge<T> a, Edge<T> b) {
                return (int) (a.weight - b.weight);
            }
        });
        HashMap<T, ArrayList<Tuple<T>>> mst = new HashMap<>();
        DisjointSet<T> ds = new DisjointSet<>(vertices);
        int nbEdges = 0;
        for (Edge<T> edge : edges) {
            T u = edge.src;
            T v = edge.dest;
            if (ds.find(u) != ds.find(v)) {
                mst.putIfAbsent(u, new ArrayList<>());
                mst.putIfAbsent(v, new ArrayList<>());
                mst.get(u).add(new Tuple<>(v, edge.weight));
                mst.get(v).add(new Tuple<>(u, edge.weight));
                nbEdges++;
                ds.union(u, v);
                if (nbEdges == vertices.size()-1)
                    break;
            }
        }
        return mst;
    }

    public static void main(String[] args) {
        HashMap<String, ArrayList<Tuple<String>>> graph = new HashMap<>();
        graph.put("H", new ArrayList<>(Arrays.asList(new Tuple<>("I", 1), new Tuple<>("J", 4), new Tuple<>("G", 4))));
        graph.put("I", new ArrayList<>(Arrays.asList(new Tuple<>("H", 1), new Tuple<>("J", 3), new Tuple<>("G", 5), new Tuple<>("E", 9))));
        graph.put("C", new ArrayList<>(Arrays.asList(new Tuple<>("D", 2), new Tuple<>("A", 3), new Tuple<>("B", 4), new Tuple<>("E", 9), new Tuple<>("F", 9))));
        graph.put("D", new ArrayList<>(Arrays.asList(new Tuple<>("C", 2), new Tuple<>("B", 2), new Tuple<>("E", 8), new Tuple<>("G", 9))));
        graph.put("B", new ArrayList<>(Arrays.asList(new Tuple<>("D", 2), new Tuple<>("C", 4), new Tuple<>("A", 6), new Tuple<>("G", 9))));
        graph.put("A", new ArrayList<>(Arrays.asList(new Tuple<>("C", 3), new Tuple<>("B", 6), new Tuple<>("F", 9))));
        graph.put("J", new ArrayList<>(Arrays.asList(new Tuple<>("I", 3), new Tuple<>("H", 4), new Tuple<>("E", 10), new Tuple<>("F", 18))));
        graph.put("G", new ArrayList<>(Arrays.asList(new Tuple<>("H", 4), new Tuple<>("I", 5), new Tuple<>("E", 7), new Tuple<>("B", 9), new Tuple<>("D", 9))));
        graph.put("E", new ArrayList<>(Arrays.asList(new Tuple<>("G", 7), new Tuple<>("F", 8), new Tuple<>("D", 8), new Tuple<>("C", 9), new Tuple<>("I", 9), new Tuple<>("J", 10))));
        graph.put("F", new ArrayList<>(Arrays.asList(new Tuple<>("E", 8), new Tuple<>("C", 9), new Tuple<>("A", 9), new Tuple<>("J", 18))));

        HashMap<String, ArrayList<Tuple<String>>> mst = kruskal(graph);
        System.out.println(mst);

        // Output:
        /*{
            A=[(C, 3)], 
            B=[(D, 2)], 
            C=[(A, 3), (D, 2)], 
            D=[(C, 2), (B, 2), (E, 8)], 
            E=[(D, 8), (G, 7), (F, 8)], 
            F=[(E, 8)], 
            G=[(E, 7), (H, 4)], 
            H=[(G, 4), (I, 1)], 
            I=[(H, 1), (J, 3)], 
            J=[(I, 3)]
        }*/
    }
}
