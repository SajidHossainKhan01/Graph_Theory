import java.util.*;

public class TspBruteForce {

    public static ArrayList<ArrayList<Integer>> generatePermutations(ArrayList<Integer> arr) {
        if (arr.size() == 1) {
            ArrayList<ArrayList<Integer>> res = new ArrayList<>();
            res.add(arr);
            return res;
        } else {
            ArrayList<ArrayList<Integer>> permutations = new ArrayList<>();
            for (int i = 0; i < arr.size(); i++) {
                int extractedElement = arr.get(i);
                ArrayList<Integer> remainingElements = new ArrayList<>(arr.subList(0, i));
                remainingElements.addAll(arr.subList(i+1, arr.size()));
                ArrayList<ArrayList<Integer>> smallerPermutations = generatePermutations(remainingElements);
                for (ArrayList<Integer> permutation : smallerPermutations) {
                    permutation.add(0, extractedElement);
                    permutations.add(permutation);
                }
            }
            return permutations;
        }
    }


    public static ArrayList<Object> tspBruteForce(double[][] graph) {
        int n = graph.length;
        ArrayList<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < n; i++)
            vertices.add(i);
        ArrayList<ArrayList<Integer>> permutations = generatePermutations(vertices);
        double minCost = Double.POSITIVE_INFINITY;
        ArrayList<Integer> minTour = new ArrayList<>();
        for (ArrayList<Integer> permutation : permutations) {
            permutation.add(permutation.get(0));
            double cost = 0;
            for (int i = 0; i < n; i++)
                cost += graph[permutation.get(i)][permutation.get(i+1)];
            if (cost < minCost) {
                minCost = cost;
                minTour = permutation;
            }
        }
        ArrayList<Object> res = new ArrayList<>();
        res.add(minCost);
        res.add(minTour);
        return res;
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
        ArrayList<Object> res = tspBruteForce(graph);
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
