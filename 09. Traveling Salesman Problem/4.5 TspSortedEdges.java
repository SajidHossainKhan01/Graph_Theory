import java.util.*;

public class TspSortedEdges {

    public static ArrayList<Object> tspSortedEdges(double[][] graph) {
        int n = graph.length;
        ArrayList<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < n; i++)
            vertices.add(i);
        ArrayList<ArrayList<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < n; i++)
            for (int j = i+1; j < n; j++)
                edges.add(new ArrayList<>(Arrays.asList(i, j)));
        edges.sort((edge1, edge2) -> Double.compare(graph[edge1.get(0)][edge1.get(1)], graph[edge2.get(0)][edge2.get(1)]));
        HashMap<Integer, ArrayList<ArrayList<Double>>> tourGraph = new HashMap<>();
        for (int i = 0; i < n; i++)
            tourGraph.put(i, new ArrayList<>());
        DisjointSet<Integer> ds = new DisjointSet<>(vertices);
        int nbEdges = 0;
        for (ArrayList<Integer> edge : edges) {
            int u = edge.get(0), v = edge.get(1);
            double w = graph[u][v];
            if (ds.find(u) != ds.find(v) && tourGraph.get(u).size() < 2 && tourGraph.get(v).size() < 2) {
                tourGraph.get(u).add(new ArrayList<>(Arrays.asList((double) v, w)));
                tourGraph.get(v).add(new ArrayList<>(Arrays.asList((double) u, w)));
                ds.union(u, v);
                if (++nbEdges == vertices.size()-1)
                    break;
            }
        }
        int start = -1, end = -1;
        for (int u : tourGraph.keySet())
            if (tourGraph.get(u).size() == 1) {
                if (start == -1)
                    start = u;
                else
                    end = u;
            }
        tourGraph.get(start).add(new ArrayList<>(Arrays.asList((double) end, graph[start][end])));
        tourGraph.get(end).add(new ArrayList<>(Arrays.asList((double) start, graph[start][end])));
        ArrayList<Integer> tour = new ArrayList<>(Arrays.asList(start));
        double totalCost = 0;
        int vertex = start;
        for (int i = 0; i < n; i++) {
            int neighbor = (int) Math.round(tourGraph.get(vertex).get(0).get(0));  // Math.round to cast to int
            double w = tourGraph.get(vertex).get(0).get(1);
            totalCost += w;
            tour.add(neighbor);
            tourGraph.get(vertex).remove(new ArrayList<>(Arrays.asList((double) neighbor, w)));
            tourGraph.get(neighbor).remove(new ArrayList<>(Arrays.asList((double) vertex, w)));
            vertex = neighbor;
        }
        return new ArrayList<>(Arrays.asList(totalCost, tour));
    }

    public static void main(String[] args) {
        double[][] points = {{1, 1}, {2, 5}, {2, 9}, {4, 0}, {5, 8}, {5, 11}, {6, 4}, {9, 2}, {10, 11}, {12, 8}};
        double[][] graph = new double[points.length][points.length];
        for (int i = 0; i < points.length; i++) {
            double[] point1 = points[i];
            for (int j = 0; j < points.length; j++) {
                double[] point2 = points[j];
                graph[i][j] = (double) Math.round(Math.sqrt(Math.pow(point1[0] - point2[0], 2) + Math.pow(point1[1] - point2[1], 2))*100)/100.0;
            }
        }
        ArrayList<Object> res = tspSortedEdges(graph);
        double minCost = (double) res.get(0);
        ArrayList<Object> minTour = (ArrayList<Object>) res.get(1);
        for(int i = 0; i < minTour.size(); i++)
            minTour.set(i, points[(int) minTour.get(i)]);
        System.out.print("Shortest tour: ");
        for (int i = 0; i < minTour.size(); i++)
            System.out.print(Arrays.toString((double[]) minTour.get(i)) + " ");
        System.out.println("(cost: " + minCost + ")");

        // Output: Shortest tour: [9.0, 2.0] [6.0, 4.0] [4.0, 0.0] [1.0, 1.0] [2.0, 5.0] [2.0, 9.0] [5.0, 8.0] [5.0, 11.0] [10.0, 11.0] [12.0, 8.0] [9.0, 2.0] (cost: 40.84)
    }
}
{"threads":[{"position":0,"start":0,"end":1957,"connection":"open"},{"position":3914,"start":1958,"end":3914,"connection":"closed"}],"url":"https://att-c.udemycdn.com/2023-07-30_02-48-24-e0abf5764a699254aa2e8b1a111b5ba5/original.java?response-content-disposition=attachment%3B+filename%3DTspSortedEdges.java&Expires=1693145340&Signature=XA3JN~BjbflNNpiYkjDNSt5qO60l0NrmRyOH1TYHiGeGo9CIKeeVnnoHGH6KrKepk3IkA3etiEFgnNXAKzxQo5~FhZjx3ncPuqvDm8WW7FmyW0RsAGtIPxKshSgILZX1uAWF46AHktMEJ6N2VJiVBqg8ghGAHAYlyZkNKdHME6ryyF8kQLfQPeJyA5fnZr~TDj9lmPWpZaGaGhupwx39psPwNUxtQHTK9qS4nlPx9cu3pflXvpfzI0PiUezll6JvUMwXzLsY5Yy0jKJOHkD63dPQLwCO9E0nXRKYTo3rC8bXeH4sf1iSgwW-28ldb9i64XhAWWKydrrIGzO7kxhZkA__&Key-Pair-Id=APKAITJV77WS5ZT7262A","method":"GET","port":443,"downloadSize":3914,"headers":{"content-type":"binary/octet-stream","content-length":"3914","connection":"close","date":"Sun, 27 Aug 2023 09:41:32 GMT","x-amz-replication-status":"COMPLETED","last-modified":"Sun, 30 Jul 2023 02:48:26 GMT","etag":"\"2a64703b1cff861bceeb833d81cf223a\"","x-amz-server-side-encryption":"AES256","x-amz-meta-qqfilename":"TspSortedEdges.java","x-amz-version-id":"mZigd1YV4gbzvIN7MdKC6BpeVh0I9xyS","content-disposition":"attachment; filename=TspSortedEdges.java","accept-ranges":"bytes","server":"AmazonS3","x-cache":"Miss from cloudfront","via":"1.1 4d43f2ff38c53dabf47263f1495ad9c0.cloudfront.net (CloudFront)","x-amz-cf-pop":"AMS1-C1","x-amz-cf-id":"MuTrguAcdx4KFvx1UyfEI_nlagqUIbh7VyoXOQIVI3tSDoMXHTvJvw==","x-cdn":"cf-cloudfront","vary":"Origin"}}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               