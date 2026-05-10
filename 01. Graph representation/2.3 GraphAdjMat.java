import java.util.*;

public class GraphAdjMat<T> {
    
    ArrayList<ArrayList<Integer>> adjMat;
    HashMap<T, Integer> idx;
    HashMap<Integer, T> label;

    public GraphAdjMat() {
        this.adjMat = new ArrayList<>();
        this.idx = new HashMap<>();
        this.label = new HashMap<>();
    }

    public void addVertex(T u) {
        if (this.idx.containsKey(u))
            return;
        for (int i = 0; i < this.adjMat.size(); i++)
            this.adjMat.get(i).add(0);
        this.idx.put(u, this.adjMat.size());
        this.label.put(this.adjMat.size(), u);
        this.adjMat.add(new ArrayList<>());
        for (int i = 0; i < this.adjMat.size(); i++)
            this.adjMat.get(this.adjMat.size()-1).add(0);
    }

    public void deleteVertex(T u) {
        if (!this.idx.containsKey(u))
            return;
        this.adjMat.set(this.idx.get(u), this.adjMat.get(this.adjMat.size()-1));
        this.adjMat.remove(this.adjMat.size()-1);
        for (int i = 0; i < this.adjMat.size(); i++) {
            this.adjMat.get(i).set(this.idx.get(u), this.adjMat.get(i).get(this.adjMat.size()-1));
            this.adjMat.get(i).remove(this.adjMat.size()-1);
        }
        this.idx.put(this.label.get(this.adjMat.size()), this.idx.get(u));
        this.label.put(this.idx.get(u), this.label.get(this.adjMat.size()));
        this.idx.remove(u);
        this.label.remove(this.adjMat.size());
    }

    public void addEdge(T u, T v) {
        if (!this.idx.containsKey(u))
            this.addVertex(u);
        if (!this.idx.containsKey(v))
            this.addVertex(v);
        this.adjMat.get(this.idx.get(u)).set(this.idx.get(v), 1);
        this.adjMat.get(this.idx.get(v)).set(this.idx.get(u), 1);
    }

    public boolean edgeExists(T u, T v) {
        if (!this.idx.containsKey(u) || !this.idx.containsKey(v))
            return false;
        return this.adjMat.get(this.idx.get(u)).get(this.idx.get(v)) == 1;
    }

    public void removeEdge(T u, T v) {
        if (!this.idx.containsKey(u) || !this.idx.containsKey(v))
            return;
        this.adjMat.get(this.idx.get(u)).set(this.idx.get(v), 0);
        this.adjMat.get(this.idx.get(v)).set(this.idx.get(u), 0);
    }

    public ArrayList<T> getVertices() {
        return new ArrayList<>(this.idx.keySet());
    }

    public ArrayList<T> getNeighbors(T u) {
        ArrayList<T> neighbors = new ArrayList<>();
        if (!this.idx.containsKey(u))
            return neighbors;
        for (int i = 0; i < this.adjMat.size(); i++)
            if (this.adjMat.get(this.idx.get(u)).get(i) == 1)
                neighbors.add(this.label.get(i));
        return neighbors;
    }
    
    public void initialize(ArrayList<T> vertices, ArrayList<T[]> edges) {
        for (T vertex : vertices)
            this.addVertex(vertex);
        for (T[] edge : edges) {
            T src = edge[0], dest = edge[1];
            this.addEdge(src, dest);
        }
    }

    public static void main(String[] args) {
        GraphAdjMat<String> graph = new GraphAdjMat<>();
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

        System.out.println(graph.adjMat);
        System.out.println(graph.idx);
        System.out.println(graph.label);
        System.out.println(graph.getNeighbors("A"));
        System.out.println(graph.edgeExists("A", "B"));
        System.out.println(graph.edgeExists("A", "E"));
        graph.removeEdge("A", "B");
        System.out.println(graph.edgeExists("A", "B"));
        graph.addEdge("A", "B");
        System.out.println(graph.edgeExists("A", "B"));
        graph.deleteVertex("A");
        System.out.println(graph.adjMat);
        System.out.println(graph.idx);
        System.out.println(graph.label);

        /*Output:
        [0, 1, 1, 1, 0, 0, 0, 0, 0]
        [1, 0, 0, 0, 1, 1, 0, 0, 0]
        [1, 0, 0, 0, 0, 0, 1, 0, 0]
        [1, 0, 0, 0, 0, 0, 0, 1, 1]
        [0, 1, 0, 0, 0, 0, 0, 0, 0]
        [0, 1, 0, 0, 0, 0, 0, 0, 0]
        [0, 0, 1, 0, 0, 0, 0, 0, 0]
        [0, 0, 0, 1, 0, 0, 0, 0, 0]
        [0, 0, 0, 1, 0, 0, 0, 0, 0]

        {A=0, B=1, C=2, D=3, E=4, F=5, G=6, H=7, I=8}

        {0=A, 1=B, 2=C, 3=D, 4=E, 5=F, 6=G, 7=H, 8=I}

        [B, C, D]

        True

        False

        False

        True

        [0, 0, 0, 1, 0, 0, 0, 0]
        [0, 0, 0, 0, 1, 1, 0, 0]
        [0, 0, 0, 0, 0, 0, 1, 0]
        [1, 0, 0, 0, 0, 0, 0, 1]
        [0, 1, 0, 0, 0, 0, 0, 0]
        [0, 1, 0, 0, 0, 0, 0, 0]
        [0, 0, 1, 0, 0, 0, 0, 0]
        [0, 0, 0, 1, 0, 0, 0, 0]

        {B=1, C=2, D=3, E=4, F=5, G=6, H=7, I=0}

        {0=I, 1=B, 2=C, 3=D, 4=E, 5=F, 6=G, 7=H}*/
    }
}
