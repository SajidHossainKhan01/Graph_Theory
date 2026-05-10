import java.util.*;

public class TspNearestNeighbor {

    public static ArrayList<Object> tspNearestNeighbor(double[][] graph, int start) {
        int n = graph.length;
        int vertex = start;
        ArrayList<Integer> tour = new ArrayList<>();
        tour.add(start);
        HashSet<Integer> visited = new HashSet<>();
        visited.add(start);
        double totalCost = 0;
        for (int i = 0; i < n - 1; i++) {
            double minCost = Double.MAX_VALUE;
            int minNeighbor = -1;
            for (int neighbor = 0; neighbor < n; neighbor++) {
                if (!visited.contains(neighbor) && graph[vertex][neighbor] < minCost) {
                    minCost = graph[vertex][neighbor];
                    minNeighbor = neighbor;
                }
            }
            totalCost += minCost;
            tour.add(minNeighbor);
            visited.add(minNeighbor);
            vertex = minNeighbor;
        }
        totalCost += graph[tour.get(tour.size() - 1)][tour.get(0)];
        tour.add(tour.get(0));
        return new ArrayList<>(Arrays.asList(totalCost, tour));
    }

    public static ArrayList<Object> tspNearestNeighbor(double[][] graph) {
        return tspNearestNeighbor(graph, 0);
    }
    
    public static ArrayList<Object> tspRepetitiveNearestNeighbor(double[][] graph) {
        int n = graph.length;
        double minCost = Double.MAX_VALUE;
        ArrayList<Integer> minTour = new ArrayList<>();
        for (int start = 0; start < n; start++) {
            ArrayList<Object> res = tspNearestNeighbor(graph, start);
            double totalCost = (double) res.get(0);
            ArrayList<Integer> tour = (ArrayList<Integer>) res.get(1);
            if (totalCost < minCost) {
                minCost = totalCost;
                minTour = tour;
            }
        }
        return new ArrayList<>(Arrays.asList(minCost, minTour));
    }

    public static void main(String[] args) {
        double[][] points = {{1, 1}, {2, 5}, {2, 9}, {4, 0}, {5, 8}, {5, 11}, {6, 4}, {9, 2}, {10, 11}, {12, 8}};
        double[][] graph = new double[points.length][points.length];
        for (int i = 0; i < points.length; i++) {
            double[] point1 = points[i];
            for (int j = 0; j < points.length; j++) {
                double[] point2 = points[j];
                graph[i][j] = (double) Math.round(Math.sqrt(Math.pow(point1[0] - point2[0], 2) + Math.pow(point1[1] - point2[1], 2))*100)/100.0;
            }
        }
        ArrayList<Object> res = tspRepetitiveNearestNeighbor(graph);
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
