import java.util.*;

public class TspDp {

    public static double tsp(double[][] mat, int s, int A, HashMap<String, Double> memo, HashMap<String, Integer> nxt) {
        String key = s + " " + A;
        if (memo.containsKey(key))
            return memo.get(key);
        if (A == 0)
            return mat[s][0];
        else {
            double min = Double.MAX_VALUE;
            int minIdx = -1;
            for (int i = 0; i < mat.length; i++) {
                if ((A & (1 << i)) != 0) {
                    double val = tsp(mat, i, A ^ (1 << i), memo, nxt) + mat[s][i];
                    if (val < min) {
                        min = val;
                        minIdx = i;
                    }
                }
            }
            memo.put(key, min);
            nxt.put(key, minIdx);
            return min;
        }
    }

    public static ArrayList<Object> tspDp(double[][] mat) {
        int n = mat.length;
        int s = 0;
        int A = (1<<n) - 1;
        A ^= 1;
        HashMap<String, Double> memo = new HashMap<>();
        HashMap<String, Integer> nxt = new HashMap<>();
        double minCost = tsp(mat, s, A, memo, nxt);
        ArrayList<Integer> minTour = new ArrayList<>();
        minTour.add(s);
        while (A != 0) {
            s = nxt.get(s + " " + A);
            minTour.add(s);
            A ^= 1 << s;
        }
        minTour.add(0);
        return new ArrayList<>(Arrays.asList(minCost, minTour));
    }

    public static void main(String[] args) {

        double[][] points = {{1, 1}, {2, 5}, {2, 9}, {4, 0}, {5, 8}, {6, 3}, {7, 7}, {8, 2}, {9, 6}, {10, 10}, {11, 4}, {12, 9}};
        double[][] graph = new double[points.length][points.length];
        for (int i = 0; i < points.length; i++) {
            double[] point1 = points[i];
            for (int j = 0; j < points.length; j++) {
                double[] point2 = points[j];
                graph[i][j] = (double) Math.round(Math.sqrt(Math.pow(point1[0] - point2[0], 2) + Math.pow(point1[1] - point2[1], 2))*100)/100.0;
            }
        }
        ArrayList<Object> res = tspDp(graph);
        double minCost = (double) res.get(0);
        ArrayList<Object> minTour = (ArrayList<Object>) res.get(1);
        for(int i = 0; i < minTour.size(); i++)
            minTour.set(i, points[(int) minTour.get(i)]);
        System.out.print("Shortest tour: ");
        for (int i = 0; i < minTour.size(); i++)
            System.out.print(Arrays.toString((double[]) minTour.get(i)) + " ");
        System.out.println("(cost: " + minCost + ")");

        // Output: Shortest tour: [1.0, 1.0] [4.0, 0.0] [6.0, 3.0] [8.0, 2.0] [11.0, 4.0] [9.0, 6.0] [12.0, 9.0] [10.0, 10.0] [7.0, 7.0] [5.0, 8.0] [2.0, 9.0] [2.0, 5.0] [1.0, 1.0] (cost: 39.69)
    }
}
