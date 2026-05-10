import java.util.*;

public class ShortestPathUnweighted {
    
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

    public static<T> ArrayList<Object> ssspUndirected(HashMap<T, ArrayList<T>> graph, T src) {
        HashMap<T, Integer> dist = new HashMap<>();
        HashMap<T, T> prev = new HashMap<>();
        for(T vertex : graph.keySet()) {
            dist.put(vertex, Integer.MAX_VALUE);
            prev.put(vertex, null);
        }
        dist.put(src, 0);
        Queue<Tuple<T>> queue = new LinkedList<>();
        queue.add(new Tuple<>(src, 0d));
        HashSet<T> visited = new HashSet<>();
        visited.add(src);
        while(!queue.isEmpty()) {
            Tuple<T> tuple = queue.poll();
            T vertex = tuple.vertex;
            int level = (int) tuple.weight;
            for(T neighbor : graph.get(vertex)) {
                if (!visited.contains(neighbor)) {
                    prev.put(neighbor, vertex);
                    dist.put(neighbor, level+1);
                    visited.add(neighbor);
                    queue.add(new Tuple<>(neighbor, level+1));
                }
            }
        }
        return new ArrayList<>() {{ add(dist); add(prev); }};
    }

    public static void main(String[] args) {
        /*graph = {
        'A': ['B', 'C', 'D'],
        'B': ['A', 'C', 'I'],
        'C': ['A', 'B', 'F'],
        'D': ['A', 'G'],
        'E': ['C', 'H'],
        'F': ['C', 'H', 'J'],
        'G': ['C', 'D', 'I'],
        'H': ['C', 'E', 'F', 'I'],
        'I': ['B', 'G', 'K'],
        'J': ['F', 'K'],
        'K': ['I', 'J']
    } */
        HashMap<String, ArrayList<String>> graph = new HashMap<>();
        graph.put("A", new ArrayList<>() {{ add("B"); add("C"); add("D"); }});
        graph.put("B", new ArrayList<>() {{ add("A"); add("C"); add("I"); }});
        graph.put("C", new ArrayList<>() {{ add("A"); add("B"); add("F"); }});
        graph.put("D", new ArrayList<>() {{ add("A"); add("G"); }});
        graph.put("E", new ArrayList<>() {{ add("C"); add("H"); }});
        graph.put("F", new ArrayList<>() {{ add("C"); add("H"); add("J"); }});
        graph.put("G", new ArrayList<>() {{ add("C"); add("D"); add("I"); }});
        graph.put("H", new ArrayList<>() {{ add("C"); add("E"); add("F"); add("I"); }});
        graph.put("I", new ArrayList<>() {{ add("B"); add("G"); add("K"); }});
        graph.put("J", new ArrayList<>() {{ add("F"); add("K"); }});
        graph.put("K", new ArrayList<>() {{ add("I"); add("J"); }});

        String src = "A";
        ArrayList<Object> result = ssspUndirected(graph, src);
        HashMap<String, Integer> dist = (HashMap<String, Integer>) result.get(0);
        HashMap<String, String> prev = (HashMap<String, String>) result.get(1);
        System.out.println("dist: " + dist);
        System.out.println("prev: " + prev);
        String dest = "K";
        System.out.println("sp from " + src + " to " + dest + " is " + reconstruct(prev, dest) + " (length: " + dist.get(dest) + ")");

        /*Output:
        dist: {A=0, B=1, C=1, D=1, E=4, F=2, G=2, H=3, I=2, J=3, K=3}
        prev: {A=null, B=A, C=A, D=A, E=H, F=C, G=D, H=F, I=B, J=F, K=I}
        sp from A to K is [A, B, I, K] (length: 3)*/
    }
}
