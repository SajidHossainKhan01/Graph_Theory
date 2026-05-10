import java.util.*;

public class ShortestSuperstring {

    public static double tsp(double[][] mat, String[] words, int s, int A, HashMap<String, Double> memo, HashMap<String, Integer> nxt) {
        String key = s + " " + A;
        if (memo.containsKey(key))
            return memo.get(key);
        if (A == 0)
            return words[s].length();
        else {
            double min = Double.MAX_VALUE;
            int minIdx = -1;
            for (int i = 0; i < mat.length; i++) {
                if ((A & (1 << i)) != 0) {
                    double val = tsp(mat, words, i, A ^ (1 << i), memo, nxt) + mat[s][i];
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

    public static ArrayList<Object> tspDp(double[][] mat, String[] words, int s) {
        int n = mat.length;
        int A = (1<<n) - 1;
        A ^= 1<<s;
        HashMap<String, Double> memo = new HashMap<>();
        HashMap<String, Integer> nxt = new HashMap<>();
        double minCost = tsp(mat, words, s, A, memo, nxt);
        ArrayList<Integer> minTour = new ArrayList<>();
        minTour.add(s);
        while (A != 0) {
            s = nxt.get(s + " " + A);
            minTour.add(s);
            A ^= 1 << s;
        }
        return new ArrayList<>(Arrays.asList(minCost, minTour));
    }

    public static double calculateCost(String[] words, int i, int j) {
        for (int k = 0; k < words[i].length(); k++) {
            if (words[j].startsWith(words[i].substring(k))) {
                return k;
            }
        }
        return (double) words[i].length();
    }

    public static String shortestSuperstring(String[] words) {
        words = Arrays.copyOf(words, words.length+1);
        words[words.length-1] = "";
        int n = words.length;
        double[][] mat = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                mat[i][j] = calculateCost(words, i, j);
        ArrayList<Object> res = tspDp(mat, words, n-1);
        ArrayList<Integer> p = (ArrayList<Integer>) res.get(1);
        StringBuilder shortest = new StringBuilder();
        for (int i = 0; i < n-1; i++) {
            String currentWord = words[p.get(i)];
            int k = (int) mat[p.get(i)][p.get(i+1)];
            shortest.append(currentWord.substring(0, k));
        }
        shortest.append(words[p.get(n-1)]);
        return shortest.toString();
    }

    public static void main(String[] args) {

        String[] words = {"yuwkxomd", "ldbcuseojg", "dbcuseojgy", "jgyim", "owpyuwkx", "mdqsldbcus"};
        String shortest = shortestSuperstring(words);
        System.out.println("Shortest superstring: " + shortest + " (length: " + shortest.length() + ")");

        // Output: Shortest superstring: owpyuwkxomdqsldbcuseojgyim (length: 26)
    }
}
