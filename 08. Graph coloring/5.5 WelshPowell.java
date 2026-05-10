import java.util.*;

public class WelshPowell {
    
    public static<T> HashMap<T, Integer> welshPowell(HashMap<T, ArrayList<T>> graph) {
        ArrayList<T> order = new ArrayList<>(graph.keySet());
        order.sort((a, b) -> graph.get(b).size() - graph.get(a).size());
        HashMap<T, Integer> colorMap = new HashMap<>();
        int color = 0;
        while (colorMap.size() < graph.size()) {
            final int currColor = color; // final variable required for lambda expression
            for (T vertex : order) {
                if (!colorMap.containsKey(vertex) && graph.get(vertex).stream().allMatch(neighbor -> !colorMap.containsKey(neighbor) || colorMap.get(neighbor) != currColor))
                    colorMap.put(vertex, color);
            }
            color++;
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

        System.out.println(welshPowell(graph));
        // Output: {A=1, B=2, C=0, D=1, E=0, F=2, G=2, H=0, I=1}
    }
}