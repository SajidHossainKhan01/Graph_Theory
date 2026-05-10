import java.util.*;

public class DSatur {

    public static<T> HashMap<T, Integer> dsatur(HashMap<T, ArrayList<T>> graph) {
        HashMap<T, Integer> colorMap = new HashMap<>();
        HashMap<T, HashSet<Integer>> neighborhoodColors = new HashMap<>();
        FibonacciHeap<T> queue = new FibonacciHeap<>();
        HashMap<T, FibonacciHeap.Entry<T>> nodes = new HashMap<>();
        for (T vertex : graph.keySet()) {
            nodes.put(vertex, queue.enqueue(vertex, -graph.get(vertex).size()));
            neighborhoodColors.put(vertex, new HashSet<>());
        }
        while (!queue.isEmpty()) {
            FibonacciHeap.Entry<T> entry = queue.dequeueMin();
            T u = entry.getValue();
            int color = 0;
            while (neighborhoodColors.get(u).contains(color))
                color++;
            colorMap.put(u, color);
            for (T v : graph.get(u)) {
                neighborhoodColors.get(v).add(color);
                if (!colorMap.containsKey(v)) {
                    // the library does not support tuples as priority of nodes, so we combine the two values into one
                    double newPriority = -neighborhoodColors.get(v).size()*graph.keySet().size() - graph.get(v).size();
                    queue.decreaseKey(nodes.get(v), newPriority);
                }
            }
        }
        return colorMap;
    }

    public static void main(String[] args) {
        HashMap<String, ArrayList<String>> graph = new HashMap<>();
        graph.put("A", new ArrayList<String>() {{ add("B"); add("C"); add("E"); add("H"); add("F"); }});
        graph.put("B", new ArrayList<String>() {{ add("A"); add("C"); add("E"); }});
        graph.put("C", new ArrayList<String>() {{ add("A"); add("B"); }});
        graph.put("D", new ArrayList<String>() {{ add("G"); add("H"); add("E"); }});
        graph.put("E", new ArrayList<String>() {{ add("A"); add("F"); add("G"); add("B"); add("I"); add("D"); }});
        graph.put("F", new ArrayList<String>() {{ add("E"); add("I"); add("A"); }});
        graph.put("G", new ArrayList<String>() {{ add("D"); add("E"); add("H"); }});
        graph.put("H", new ArrayList<String>() {{ add("D"); add("G"); add("A"); add("I"); }});
        graph.put("I", new ArrayList<String>() {{ add("E"); add("F"); add("H"); }});

        System.out.println(dsatur(graph));
        // Output: {A=0, B=1, C=2, D=0, E=2, F=1, G=1, H=2, I=0}
    }
}
