import java.util.*;

public class DisjointSet<T> {

    public ArrayList<T> elems;
    public HashMap<T, T> parent;
    public HashMap<T, Integer> size;

    public DisjointSet() {
        parent = new HashMap<>();
        size = new HashMap<>();
    }

    public DisjointSet(ArrayList<T> elems) {
        this();
        for (T elem : elems) {
            this.makeSet(elem);
        }
    }

    public void makeSet(T elem) {
        this.parent.put(elem, elem);
        this.size.put(elem, 1);
    }

    public T find(T a) {
        if (this.parent.get(a) == a) {
            return a;
        } else {
            this.parent.put(a, this.find(this.parent.get(a)));
            return this.parent.get(a);
        } 
    }

    public void union(T a, T b) {
        T rootA = this.find(a);
        T rootB = this.find(b);
        if (rootA == rootB) {
            return;
        } else if (this.size.get(rootA) <= this.size.get(rootB)) {
            this.parent.put(rootA, rootB);
            this.size.put(rootB, this.size.get(rootB)+this.size.get(rootA));
        } else {
            this.parent.put(rootB, rootA);
            this.size.put(rootA, this.size.get(rootA)+this.size.get(rootB));
        }
    }
}
