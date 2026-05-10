import java.util.*;

public class Prim {

    public static<T> HashMap<T, ArrayList<Tuple<T>>> prim(HashMap<T, ArrayList<Tuple<T>>> graph) {
        HashMap<T, ArrayList<Tuple<T>>> mst = new HashMap<>();
        T start = graph.keySet().iterator().next();
        mst.put(start, new ArrayList<>());
        HashMap<T, Double> cost = new HashMap<>(); 
        HashMap<T, T> parent = new HashMap<>();
        HashMap<T, FibonacciHeap.Entry<T>> nodes = new HashMap<>();
        FibonacciHeap<T> queue = new FibonacciHeap<>();
        for (T u : graph.keySet()) {
            cost.put(u, u == start ? 0 : Double.MAX_VALUE);
            parent.put(u, null);
            nodes.put(u, queue.enqueue(u, cost.get(u)));
        }
        while (mst.size() < graph.size()) {
            FibonacciHeap.Entry<T> entry = queue.dequeueMin();
            T u = entry.getValue();
            if (u != start) {
                mst.putIfAbsent(u, new ArrayList<>());
                mst.get(parent.get(u)).add(new Tuple<>(u, cost.get(u)));
                mst.get(u).add(new Tuple<>(parent.get(u), cost.get(u)));
            }
            for (Tuple<T> tuple : graph.get(u)) {
                T v = tuple.vertex;
                double w = tuple.weight;
                if (!mst.containsKey(v) && w < cost.get(v)) {
                    cost.put(v, w);
                    parent.put(v, u);
                    queue.decreaseKey(nodes.get(v), cost.get(v));
                }
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

        HashMap<String, ArrayList<Tuple<String>>> mst = prim(graph);
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
