import java.util.*;

public class TspChristofides {

    public static HashMap<Integer, ArrayList<Tuple<Integer>>> prim(double[][] mat) {
        int n = mat.length;
        HashMap<Integer, ArrayList<Tuple<Integer>>> mst = new HashMap<>();
        Integer start = 0;
        mst.put(start, new ArrayList<>());
        HashMap<Integer, Double> cost = new HashMap<>(); 
        HashMap<Integer, Integer> parent = new HashMap<>();
        HashMap<Integer, FibonacciHeap.Entry<Integer>> nodes = new HashMap<>();
        FibonacciHeap<Integer> queue = new FibonacciHeap<>();
        for (int u = 0; u < n; u++) {
            cost.put(u, u == start ? 0 : Double.MAX_VALUE);
            parent.put(u, null);
            nodes.put(u, queue.enqueue(u, cost.get(u)));
        }
        while (mst.size() < n) {
            FibonacciHeap.Entry<Integer> entry = queue.dequeueMin();
            Integer u = entry.getValue();
            if (u != start) {
                mst.putIfAbsent(u, new ArrayList<>());
                mst.get(parent.get(u)).add(new Tuple<>(u, cost.get(u)));
                mst.get(u).add(new Tuple<>(parent.get(u), cost.get(u)));
            }
            for (int v = 0; v < n; v++) {
                double w = mat[u][v];
                if (!mst.containsKey(v) && w < cost.get(v)) {
                    cost.put(v, w);
                    parent.put(v, u);
                    queue.decreaseKey(nodes.get(v), cost.get(v));
                }
            }
        }
        return mst;
    }

    public static ArrayList<Object[]> minWeightPerfectMatching(double[][] graph, ArrayList<Integer> odds) {
        Collections.shuffle(odds);
        ArrayList<Object[]> matching = new ArrayList<>();
        while (!odds.isEmpty()) {
            Integer u = odds.remove(odds.size() - 1);
            Integer closest = null;
            double minDist = Double.MAX_VALUE;
            for (Integer v : odds) {
                if (u != v && graph[u][v] < minDist) {
                    minDist = graph[u][v];
                    closest = v;
                }
            }
            matching.add(new Object[]{u, closest, minDist});
            odds.remove(closest);
        }
        return matching;
    }

    public static void dfs(HashMap<Integer, ArrayList<Tuple<Integer>>> graph, Integer vertex, HashMap<Integer, Integer> deg, ArrayList<Integer> output, HashMap<String, Integer> edges) {
        while (deg.get(vertex) > 0) {
            deg.put(vertex, deg.get(vertex)-1);
            Integer neighbor = graph.get(vertex).get(deg.get(vertex)).vertex;
            String key = vertex < neighbor ? vertex + "," + neighbor : neighbor + "," + vertex;
            if (edges.get(key) > 0) {
                if (vertex == neighbor)
                    edges.put(key, edges.get(key)-1);
                else
                    edges.put(key, edges.get(key)-2);
                dfs(graph, neighbor, deg, output, edges);
            }
        }
        output.add(vertex);
    }

    public static ArrayList<Integer> hierholzer(HashMap<Integer, ArrayList<Tuple<Integer>>> graph) {
        HashMap<Integer, Integer> deg = new HashMap<>();
        for (Integer u : graph.keySet())
            deg.put(u, graph.get(u).size());
        HashMap<String, Integer> edges = new HashMap<>();
        for (Integer u : graph.keySet()) {
            for (Tuple<Integer> tuple : graph.get(u)) {
                Integer v = tuple.vertex;
                String key = u < v ? u + "," + v : v + "," + u;
                edges.putIfAbsent(key, 0);
                edges.put(key, edges.get(key)+1);
            }
        }
        Integer start = 0;
        ArrayList<Integer> output = new ArrayList<>();
        dfs(graph, start, deg, output, edges);
        Collections.reverse(output);
        return output;
    }

    public static ArrayList<Integer> shortcutting(ArrayList<Integer> circuit) {
        HashSet<Integer> visited = new HashSet<>();
        ArrayList<Integer> output = new ArrayList<>();
        for (Integer vertex : circuit) {
            if (!visited.contains(vertex)) {
                output.add(vertex);
                visited.add(vertex);
            }
        }
        output.add(circuit.get(0));
        return output;
    }

    /*
     * def tsp_christofides(mat: list[list[float]]) -> tuple[float,list[int]]:
    mst: dict[int,list[tuple[int,float]]] = prim(mat)
    odds: list[int] = [u for u in mst if len(mst[u]) % 2 == 1]
    matching: list[tuple[int,int,float]] = min_weight_perfect_matching(mat, odds)
    u: int; v: int; w: float
    for u, v, w in matching:
        mst[u].append((v, w))
        mst[v].append((u, w))
    eulerian: list[int] = hierholzer(mst)
    tour: list[int] = shortcutting(eulerian)
    cost: float = 0
    for i in range(len(tour)-1):
        cost += mat[tour[i]][tour[i+1]]
    return cost, tour
     */
    public static ArrayList<Object> tspChristofides(double[][] mat) {
        HashMap<Integer, ArrayList<Tuple<Integer>>> mst = prim(mat);
        ArrayList<Integer> odds = new ArrayList<>();
        for (Integer u : mst.keySet())
            if (mst.get(u).size() % 2 == 1)
                odds.add(u);
        ArrayList<Object[]> matching = minWeightPerfectMatching(mat, odds);
        for (Object[] tuple : matching) {
            Integer u = (Integer) tuple[0];
            Integer v = (Integer) tuple[1];
            Double w = (Double) tuple[2];
            mst.get(u).add(new Tuple<>(v, w));
            mst.get(v).add(new Tuple<>(u, w));
        }
        ArrayList<Integer> eulerian = hierholzer(mst);
        ArrayList<Integer> tour = shortcutting(eulerian);
        double cost = 0;
        for (int i = 0; i < tour.size()-1; i++)
            cost += mat[tour.get(i)][tour.get(i+1)];
        return new ArrayList<Object>(Arrays.asList(cost, tour));
    }


    public static void main(String[] args) {
        double[][] points = {{10, 0}, {9, -3}, {1, 3}, {6, 1}, {4, 3.5}, {5, 4.2}, {5.8, 2.8}, {7.5, 6.5}, {9, 1}, {10, 5}, {0, 4.1}, {8.8, 4.2}, {7.6, 2.5}};
        double[][] graph = new double[points.length][points.length];
        for (int i = 0; i < points.length; i++) {
            double[] point1 = points[i];
            for (int j = 0; j < points.length; j++) {
                double[] point2 = points[j];
                graph[i][j] = (double) Math.round(Math.sqrt(Math.pow(point1[0] - point2[0], 2) + Math.pow(point1[1] - point2[1], 2))*100)/100.0;
            }
        }
        ArrayList<Object> res = tspChristofides(graph);
        double minCost = (double) res.get(0);
        ArrayList<Object> minTour = (ArrayList<Object>) res.get(1);
        for(int i = 0; i < minTour.size(); i++)
            minTour.set(i, points[(int) minTour.get(i)]);
        System.out.print("Shortest tour: ");
        for (int i = 0; i < minTour.size(); i++)
            System.out.print(Arrays.toString((double[]) minTour.get(i)) + " ");
        System.out.println("(cost: " + minCost + ")");

        // Output: Shortest tour: [10.0, 0.0] [9.0, -3.0] [0.0, 4.1] [1.0, 3.0] [4.0, 3.5] [5.0, 4.2] [5.8, 2.8] [6.0, 1.0] [7.6, 2.5] [7.5, 6.5] [8.8, 4.2] [10.0, 5.0] [9.0, 1.0] [10.0, 0.0] (cost: 39.59)
    }
}
