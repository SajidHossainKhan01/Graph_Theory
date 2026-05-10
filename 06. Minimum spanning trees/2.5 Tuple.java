public class Tuple<T> {
    public T vertex;
    public double weight;

    public Tuple(T vertex, double weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public String toString() {
        return "(" + vertex + ", " + weight + ")";
    }
}