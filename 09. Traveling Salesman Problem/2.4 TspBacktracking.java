import java.util.*;

public class TspBacktracking {

    public static ArrayList<Object> rec(double[][] graph, ArrayList<Integer> tour, double cost, double minCost, ArrayList<Integer> minTour) {
        int n = graph.length;
        if (tour.size() == n) {
            tour.add(tour.get(0));
            cost += graph[tour.get(tour.size()-2)][tour.get(tour.size()-1)];
            if (cost < minCost) {
                minCost = cost;
                minTour = (ArrayList<Integer>) tour.clone();
            }
            cost -= graph[tour.get(tour.size()-2)][tour.get(tour.size()-1)];
            tour.remove(tour.size()-1);
            ArrayList<Object> res = new ArrayList<>();
            res.add(minCost);
            res.add(minTour);
            return res;
        }
        for (int i = 0; i < n; i++) {
            if (!tour.contains(i)) {
                tour.add(i);
                cost += graph[tour.get(tour.size()-2)][tour.get(tour.size()-1)];
                if (cost < minCost) {
                    ArrayList<Object> temp = rec(graph, tour, cost, minCost, minTour);
                    minCost = (double) temp.get(0);
                    minTour = (ArrayList<Integer>) temp.get(1);
                }
                cost -= graph[tour.get(tour.size()-2)][tour.get(tour.size()-1)];
                tour.remove(tour.size()-1);
            }
        }
        ArrayList<Object> res = new ArrayList<>();
        res.add(minCost);
        res.add(minTour);
        return res;
    }

    /*
     * def tsp_backtracking(graph: list[list[float]]) -> tuple[float,list[int]]:
    start: int = 0
    tour: list[int] = [start]
    cost: float = 0
    min_tour: list[int] = None
    min_cost: float = float('inf')
    min_cost, min_tour = rec(graph, tour, cost, min_tour, min_cost)
    return min_cost, min_tour
     */
    public static ArrayList<Object> tspBacktracking(double[][] graph) {
        int start = 0;
        ArrayList<Integer> tour = new ArrayList<>();
        tour.add(start);
        double cost = 0;
        ArrayList<Integer> minTour = null;
        double minCost = Double.MAX_VALUE;
        ArrayList<Object> res = rec(graph, tour, cost, minCost, minTour);
        minCost = (double) res.get(0);
        minTour = (ArrayList<Integer>) res.get(1);
        return new ArrayList<>(Arrays.asList(minCost, minTour));
    }

    public static void main(String[] args) {

        double[][] points = new double[][]{{1, 1}, {2, 5}, {2, 9}, {4, 0}, {5, 8}, {5, 11}, {6, 4}, {9, 2}, {10, 11}, {12, 8}};
        double[][] graph = new double[points.length][points.length];
        for (int i = 0; i < points.length; i++) {
            double[] point1 = points[i];
            for (int j = 0; j < points.length; j++) {
                double[] point2 = points[j];
                graph[i][j] = (double) Math.round(Math.sqrt(Math.pow(point1[0] - point2[0], 2) + Math.pow(point1[1] - point2[1], 2))*100)/100.0;
            }
        }
        ArrayList<Object> res = tspBacktracking(graph);
        double minCost = (double) res.get(0);
        ArrayList<Object> minTour = (ArrayList<Object>) res.get(1);
        for(int i = 0; i < minTour.size(); i++)
            minTour.set(i, points[(int) minTour.get(i)]);
        System.out.print("Shortest tour: ");
        for (int i = 0; i < minTour.size(); i++)
            System.out.print(Arrays.toString((double[]) minTour.get(i)) + " ");
        System.out.println("(cost: " + minCost + ")");

        // Output: Shortest tour: [1.0, 1.0] [4.0, 0.0] [6.0, 4.0] [9.0, 2.0] [12.0, 8.0] [10.0, 11.0] [5.0, 11.0] [5.0, 8.0] [2.0, 9.0] [2.0, 5.0] [1.0, 1.0] (cost: 40.839999999999996)
    }
}
