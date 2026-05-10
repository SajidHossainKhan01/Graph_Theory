import java.util.*;

public class GreedyColoring {
    
    public static<T> HashMap<T, Integer> greedyColoring(HashMap<T, ArrayList<T>> graph) {
        ArrayList<T> order = new ArrayList<>(graph.keySet());
        HashMap<T, Integer> colorMap = new HashMap<>();
        for (T vertex : order) {
            ArrayList<T> neighbors = graph.get(vertex);
            HashSet<Integer> used = new HashSet<>();
            for (T neighbor : neighbors)
                if (colorMap.containsKey(neighbor))
                    used.add(colorMap.get(neighbor));
            int color = 0;
            while (used.contains(color))
                color++;
            colorMap.put(vertex, color);
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

        System.out.println(greedyColoring(graph));
        // Output: {A=0, B=1, C=2, D=0, E=2, F=1, G=1, H=2, I=0}
    }
}