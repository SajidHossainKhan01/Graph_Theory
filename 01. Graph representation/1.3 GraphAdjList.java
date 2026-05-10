import java.util.*;

public class GraphAdjList<T> {
    
    public HashMap<T, ArrayList<T>> adjList;

    public GraphAdjList() {
        this.adjList = new HashMap<>();
    }

    public void addVertex(T u) {
        if (!this.adjList.containsKey(u))
            this.adjList.put(u, new ArrayList<>());
    }

    public void deleteVertex(T u) {
        if (!this.adjList.containsKey(u))
            return;
        this.adjList.remove(u);
        for (T v : this.adjList.keySet())
            if (this.adjList.get(v).contains(u))
                this.adjList.get(v).remove(u);
    }

    public void addEdge(T u, T v) {
        if (!this.adjList.containsKey(u))
            this.adjList.put(u, new ArrayList<>());
        if (!this.adjList.containsKey(v))
            this.adjList.put(v, new ArrayList<>());
        this.adjList.get(u).add(v);
        this.adjList.get(v).add(u);
    }

    public void removeEdge(T u, T v) {
        if (this.adjList.containsKey(u) && this.adjList.get(u).contains(v) && this.adjList.containsKey(v) && this.adjList.get(v).contains(u)) {
            this.adjList.get(u).remove(v);
            this.adjList.get(v).remove(u);
        }
    }

    public boolean edgeExists(T u, T v) {
        return this.adjList.containsKey(u) && this.adjList.get(u).contains(v);
    }

    public ArrayList<T> getVertices() {
        return new ArrayList<>(this.adjList.keySet());
    }

    public ArrayList<T> getNeighbors(T u) {
        if (!this.adjList.containsKey(u))
            return new ArrayList<>();
        return this.adjList.get(u);
    }

    public void initialize(ArrayList<T> vertices, ArrayList<T[]> edges) {
        for (T vertex : vertices)
            addVertex(vertex);
        for (T[] edge : edges) {
            T src = edge[0], dest = edge[1];
            this.addEdge(src, dest);
        }
    }

    public static void main(String[] args) {
        GraphAdjList<String> graph = new GraphAdjList<>();
        ArrayList<String> vertices = new ArrayList<>(List.of("A", "B", "C", "D", "E", "F", "G", "H", "I"));
        ArrayList<String[]> edges = new ArrayList<>();
        edges.add(new String[] {"A", "B"});
        edges.add(new String[] {"A", "C"});
        edges.add(new String[] {"A", "D"});
        edges.add(new String[] {"B", "E"});
        edges.add(new String[] {"B", "F"});
        edges.add(new String[] {"C", "G"});
        edges.add(new String[] {"D", "H"});
        edges.add(new String[] {"D", "I"});
        graph.initialize(vertices, edges);

        System.out.println(graph.adjList);
        System.out.println(graph.getNeighbors("A"));
        System.out.println(graph.edgeExists("A", "B"));
        System.out.println(graph.edgeExists("A", "E"));
        graph.removeEdge("A", "B");
        System.out.println(graph.edgeExists("A", "B"));
        graph.addEdge("A", "B");
        System.out.println(graph.edgeExists("A", "B"));
        graph.deleteVertex("A");
        System.out.println(graph.adjList);

        /*Output:
        {
            A=[B, C, D], 
            B=[A, E, F], 
            C=[A, G], 
            D=[A, H, I], 
            E=[B], 
            F=[B], 
            G=[C], 
            H=[D], 
            I=[D]
        }

        [B, C, D]

        true

        false

        false

        true

        {
            B=[E, F], 
            C=[G], 
            D=[H, I], 
            E=[B], 
            F=[B], 
            G=[C], 
            H=[D], 
            I=[D]
        }*/
    }
}
