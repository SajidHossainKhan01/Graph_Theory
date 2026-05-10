public class Edge<T> {
    public T src;
    public T dest;
    public double weight;

    public Edge(T src, T dest, double weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    public Edge(T src, T dest) {
        this.src = src;
        this.dest = dest;
        this.weight = 0d;
    }

    public double compareTo(Edge<T> other) {
        return this.weight - other.weight;
    }

    public String toString() {
        return "(" + src + ", " + dest + ", " + weight + ")";
    }
}