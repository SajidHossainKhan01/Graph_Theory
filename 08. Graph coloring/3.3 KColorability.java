import java.util.*;

public class KColorability {
    
    public static<T> boolean rec(Map<T, List<T>> graph, int k, List<T> vertices, int i, Map<T, Integer> colorMap) {
        if (i == vertices.size()) {
            return true;
        }
        Set<Integer> used = new HashSet<>();
        for (T neighbor : graph.get(vertices.get(i)))
            used.add(colorMap.get(neighbor));
        for (int color = 0; color < k; color++) {
            if (!used.contains(color)) {
                colorMap.put(vertices.get(i), color);
                if (rec(graph, k, vertices, i + 1, colorMap))
                    return true;
            }
        }
        colorMap.put(vertices.get(i), -1);
        return false;
    }

    public static<T> boolean isKColorable(Map<T, List<T>> graph, int k) {
        ArrayList<T> vertices = new ArrayList<>(graph.keySet());
        Map<T, Integer> colorMap = new HashMap<>();
        for (T vertex : vertices)
            colorMap.put(vertex, -1);
        return rec(graph, k, vertices, 0, colorMap);
    }

    public static Map<String, Integer> kColoring(Map<String, List<String>> graph, int k) {
        List<String> vertices = List.of(graph.keySet().toArray(new String[0]));
        Map<String, Integer> colorMap = new HashMap<>();
        for (String vertex : vertices)
            colorMap.put(vertex, -1);
        rec(graph, k, vertices, 0, colorMap);
        return colorMap;
    }

    public static int chromaticNumber(Map<String, List<String>> graph) {
        int k = 1;
        while (k <= graph.size()) {
            if (isKColorable(graph, k))
                return k;
            k++;
        }
        return -1;
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", List.of("B", "C", "E", "H", "F"));
        graph.put("B", List.of("A", "C", "E"));
        graph.put("C", List.of("A", "B"));
        graph.put("D", List.of("G", "H", "E"));
        graph.put("E", List.of("A", "F", "G", "B", "I", "D"));
        graph.put("F", List.of("E", "I", "A"));
        graph.put("G", List.of("D", "E", "H"));
        graph.put("H", List.of("D", "G", "A", "I"));
        graph.put("I", List.of("E", "F", "H"));
        System.out.println("Is 2-colorable: " + isKColorable(graph, 2));
        System.out.println("Is 3-colorable: " + isKColorable(graph, 3));
        Map<String, Integer> colorMap = kColoring(graph, 3);
        System.out.println("Coloring: " + colorMap);
        System.out.println("Chromatic number: " + chromaticNumber(graph));
    }
}