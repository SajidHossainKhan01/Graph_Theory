import java.util.*;

public class ToOutTree {

    public static class Node<T> {
        public T val;
        public ArrayList<Node<T>> children;

        public Node(T val) {
            this.val = val;
            this.children = new ArrayList<>();
        }

        public void display(String indent) {
            System.out.println(indent + this.val);
            for (Node<T> child : this.children)
                child.display(indent + "    ");
        }

        public void display() {
            this.display("");
        }
    }

    public static<T> void dfs(HashMap<T, ArrayList<T>> graph, Node<T> node, HashSet<T> visited) {
        visited.add(node.val);
        for (T neighbor : graph.get(node.val)) {
            if (!visited.contains(neighbor)) {
                Node<T> neighborNode = new Node<>(neighbor);
                node.children.add(neighborNode);
                dfs(graph, neighborNode, visited);
            }
        }
    }

    public static<T> Node<T> toOutTree(HashMap<T, ArrayList<T>> graph, T rootVal) {  // We assume that graph is a valid tree
        Node<T> root = new Node<>(rootVal);
        HashSet<T> visited = new HashSet<>();
        dfs(graph, root, visited);
        return root;
    }

    public static void main(String[] args) {

        HashMap<String, ArrayList<String>> graph = new HashMap<>();
        graph.put("A", new ArrayList<>(Arrays.asList("B")));
        graph.put("B", new ArrayList<>(Arrays.asList("A", "G")));
        graph.put("C", new ArrayList<>(Arrays.asList("F")));
        graph.put("D", new ArrayList<>(Arrays.asList("H")));
        graph.put("E", new ArrayList<>(Arrays.asList("F")));
        graph.put("F", new ArrayList<>(Arrays.asList("C", "E", "G")));
        graph.put("G", new ArrayList<>(Arrays.asList("B", "F", "I", "K")));
        graph.put("H", new ArrayList<>(Arrays.asList("D", "K")));
        graph.put("I", new ArrayList<>(Arrays.asList("G", "J")));
        graph.put("J", new ArrayList<>(Arrays.asList("I")));
        graph.put("K", new ArrayList<>(Arrays.asList("G", "H")));

        toOutTree(graph, "G").display();

        // Output:
        // G
        //     B
        //         A
        //     F
        //         C
        //         E
        //     I
        //         J
        //     K
        //         H
        //             D
    }
}
