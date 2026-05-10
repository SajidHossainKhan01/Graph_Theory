import java.util.*;

public class KeysAndRooms {

    // Approach 1: Depth-first search
    public static void dfs(int[][] graph, int vertex, HashSet<Integer> visited) {
        visited.add(vertex);
        for (int neighbor : graph[vertex])
            if (!visited.contains(neighbor))
                dfs(graph, neighbor, visited);
    }

    public static boolean canVisitAllRoomsDfs(int[][] rooms) {
        HashSet<Integer> visited = new HashSet<>();
        dfs(rooms, 0, visited);
        return visited.size() == rooms.length;
    }

    // Approach 2: Breadth-first search
    public static boolean canVisitAllRoomsBfs(int[][] rooms) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        HashSet<Integer> visited = new HashSet<>();
        visited.add(0);
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (int neighbor : rooms[vertex])
                if (!visited.contains(neighbor))
                    queue.add(neighbor);
                    visited.add(vertex);
        }
        return visited.size() == rooms.length;
    }

    public static void main(String[] args) {
        System.out.println("Example 1:");
        int[][] rooms = new int[][]{{1, 2}, {2, 3}, {3}, {4}, {2}};
        System.out.println("Can visit all rooms (dfs): " + canVisitAllRoomsDfs(rooms));
        System.out.println("Can visit all rooms (bfs): " + canVisitAllRoomsBfs(rooms));

        System.out.println("\nExample 2:");
        rooms = new int[][]{{1, 2}, {2, 3}, {3}, {}, {2, 4}};
        System.out.println("Can visit all rooms (dfs): " + canVisitAllRoomsDfs(rooms));
        System.out.println("Can visit all rooms (bfs): " + canVisitAllRoomsBfs(rooms));

        // Output:
        // Example 1:
        // Can visit all rooms (dfs): true
        // Can visit all rooms (bfs): true

        // Example 2:
        // Can visit all rooms (dfs): false
        // Can visit all rooms (bfs): false
    }
}
