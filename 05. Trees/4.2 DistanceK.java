import java.util.*;

public class DistanceK {
    
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

    public static<T> HashMap<Node<T>, Node<T>> getParentMap (Node<T> root) {
        HashMap<Node<T>, Node<T>> parentMap = new HashMap<>();
        parentMap.put(root, null);
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<T> node = queue.poll();
            for (Node<T> child : node.children) {
                parentMap.put(child, node);
                queue.add(child);
            }
        }
        return parentMap;
    }

    public static<T> ArrayList<T> distanceK (Node<T> root, Node<T> start, int k) {
        HashMap<Node<T>, Node<T>> parentMap = getParentMap(root);
        ArrayList<T> output = new ArrayList<>();
        Queue<Tuple<Node<T>>> queue = new LinkedList<>();
        queue.add(new Tuple<Node<T>>(start, 0));
        HashSet<Node<T>> visited = new HashSet<>();
        visited.add(start);
        while (!queue.isEmpty()) {
            Tuple<Node<T>> tuple = queue.poll();
            Node<T> node = tuple.vertex;
            int level = (int) tuple.weight;
            if (level == k)
                output.add(node.val);
            ArrayList<Node<T>> neighbors = (ArrayList<DistanceK.Node<T>>) node.children.clone();
            neighbors.add(parentMap.get(node));
            for (Node<T> neighbor : neighbors) {
                if (level < k && neighbor != null && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(new Tuple<Node<T>>(neighbor, level+1));
                    
                }
            }
        }
        return output;
    }

    public static void main(String[] args) {

        HashMap<String, Node<String>> nodes = new HashMap<>();
        for (String u : new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"})
            nodes.put(u, new Node<String>(u));
        nodes.get("A").children.add(nodes.get("B"));
        nodes.get("A").children.add(nodes.get("C"));
        nodes.get("A").children.add(nodes.get("D"));
        nodes.get("B").children.add(nodes.get("E"));
        nodes.get("B").children.add(nodes.get("F"));
        nodes.get("C").children.add(nodes.get("G"));
        nodes.get("D").children.add(nodes.get("H"));
        nodes.get("D").children.add(nodes.get("I"));
        nodes.get("D").children.add(nodes.get("J"));
        nodes.get("E").children.add(nodes.get("K"));
        nodes.get("F").children.add(nodes.get("L"));
        nodes.get("F").children.add(nodes.get("M"));
        nodes.get("H").children.add(nodes.get("N"));
        nodes.get("J").children.add(nodes.get("O"));
        nodes.get("L").children.add(nodes.get("P"));
        nodes.get("L").children.add(nodes.get("Q"));
        nodes.get("M").children.add(nodes.get("R"));
        nodes.get("O").children.add(nodes.get("S"));
        nodes.get("O").children.add(nodes.get("T"));

        Node<String> root = nodes.get("A"), start = nodes.get("B");
        int k = 3;

        System.out.println("Input:");
        root.display();

        System.out.println("\nOutput:");
        System.out.println(distanceK(root, start, k));

        /*Input:
        A
            B
                E        
                    K    
                F        
                    L    
                        P
                        Q
                    M
                        R
            C
                G
            D
                H
                    N
                I
                J
                    O
                        S
                        T

        Output:
        [P, Q, R, G, H, I, J]*/
    }


}
