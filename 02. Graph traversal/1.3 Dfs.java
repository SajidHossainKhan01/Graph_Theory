import java.util.*;

public class Dfs {
    
    public static<T> void dfs(GraphAdjList<T> graph, T vertex, HashSet<T> visited) {
        System.out.println(vertex);
        visited.add(vertex);
        for (T neighbor : graph.getNeighbors(vertex))
            if (!visited.contains(neighbor))
                dfs(graph, neighbor, visited);
    }

    public static<T> void dfs(GraphAdjList<T> graph) {
        HashSet<T> visited = new HashSet<>();
        for (T vertex : graph.getVertices())
            if (!visited.contains(vertex))
                dfs(graph, vertex, visited);
    }
    
    public static void main(String[] args) {
        GraphAdjList<String> graph = new GraphAdjList<>();
        ArrayList<String> vertices = new ArrayList<>(List.of("A", "B", "C", "D", "E", "F", "H", "I", "J", "K", "L", "O", "P", "Q", "R"));
        ArrayList<String[]> edges = new ArrayList<>();
        edges.add(new String[] {"A", "B"});
        edges.add(new String[] {"A", "C"});
        edges.add(new String[] {"A", "D"});
        edges.add(new String[] {"B", "E"});
        edges.add(new String[] {"B", "F"});
        edges.add(new String[] {"D", "H"});
        edges.add(new String[] {"D", "I"});
        edges.add(new String[] {"D", "J"});
        edges.add(new String[] {"E", "K"});
        edges.add(new String[] {"I", "O"});
        edges.add(new String[] {"I", "J"});
        edges.add(new String[] {"K", "P"});
        edges.add(new String[] {"K", "L"});
        edges.add(new String[] {"L", "Q"});
        edges.add(new String[] {"L", "R"});
        edges.add(new String[] {"F", "L"});
        graph.initialize(vertices, edges);

        dfs(graph);

        /*Output:
        A
        B
        E
        K
        P
        L
        Q
        R
        F
        C
        D
        H
        I
        O
        J*/
    }
}
