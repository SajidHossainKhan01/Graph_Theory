import java.util.*;

public class MinCostConnect {

    public static double manhattanDistance(int[] p0, int[] p1){
        return Math.abs(p0[0]-p1[0]) + Math.abs(p0[1]-p1[1]);
    }

    public static double minCostConnect(int[][] points){
        double totalCost = 0;
        HashMap<int[], ArrayList<Tuple<int[]>>> mst = new HashMap<>();
        int[] start = points[0];
        mst.put(start, new ArrayList<>());
        HashMap<int[], Double> cost = new HashMap<>(); 
        HashMap<int[], int[]> parent = new HashMap<>();
        HashMap<int[], FibonacciHeap.Entry<int[]>> nodes = new HashMap<>();
        FibonacciHeap<int[]> queue = new FibonacciHeap<>();
        for (int[] u : points) {
            cost.put(u, u == start ? (double) 0 : Double.MAX_VALUE);
            parent.put(u, null);
            nodes.put(u, queue.enqueue(u, cost.get(u)));
        }
        while (mst.size() < points.length) {
            FibonacciHeap.Entry<int[]> entry = queue.dequeueMin();
            int[] u = entry.getValue();
            if (u != start) {
                mst.putIfAbsent(u, new ArrayList<>());
                mst.get(parent.get(u)).add(new Tuple<>(u, cost.get(u)));
                mst.get(u).add(new Tuple<>(parent.get(u), cost.get(u)));
                totalCost += cost.get(u);
            }
            for (int[] v : points) {
                double w = manhattanDistance(u, v);
                if (!mst.containsKey(v) && w < cost.get(v)) {
                    cost.put(v, w);
                    parent.put(v, u);
                    queue.decreaseKey(nodes.get(v), cost.get(v));
                }
            }
        }
        return totalCost;
    }
    public static void main(String[] args) {
        int[][] points = {{0, 0}, {2, 2}, {3, 10}, {5, 2}, {7, 0}};
        System.out.println(minCostConnect(points));

        // Output: 20.0
    }
}
