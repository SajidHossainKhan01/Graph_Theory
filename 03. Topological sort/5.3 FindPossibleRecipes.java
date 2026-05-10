import java.util.*;

public class FindPossibleRecipes {

    public static<T> ArrayList<T> topologicalSort(HashMap<T, ArrayList<T>> graph) {
        HashMap<T, Integer> indegree = new HashMap<>();
        for(T vertex : graph.keySet()) indegree.put(vertex, 0);
        for(T vertex : graph.keySet())
            for(T neighbor : graph.get(vertex))
                indegree.put(neighbor, indegree.get(neighbor)+1);
        Queue<T> queue = new LinkedList<>();
        for(T vertex : indegree.keySet())
            if(indegree.get(vertex) == 0)
                queue.add(vertex);
        ArrayList<T> ordering = new ArrayList<>();
        while(!queue.isEmpty()) {
            T vertex = queue.poll();
            ordering.add(vertex);
            for(T neighbor : graph.get(vertex)) {
                indegree.put(neighbor, indegree.get(neighbor)-1);
                if(indegree.get(neighbor) == 0)
                    queue.add(neighbor);
            }
        }
        return ordering;
    }

    public static ArrayList<String> findPossibleRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies){
        HashMap<String, HashSet<String>> ingredientsMap = new HashMap<>();
        for(int i = 0; i < recipes.length; i++){
            HashSet<String> ingredientsSet = new HashSet<>(ingredients.get(i));
            ingredientsMap.put(recipes[i], ingredientsSet);
        }
        HashSet<String> suppliesSet = new HashSet<>(Arrays.asList(supplies));
        HashMap<String, ArrayList<String>> graph = new HashMap<>();
        for(String u : recipes){
            graph.put(u, new ArrayList<>());
            for(String v : recipes)
                if(ingredientsMap.get(v).contains(u))
                    graph.get(u).add(v);
        }
        ArrayList<String> ordering = topologicalSort(graph);
        ArrayList<String> prepared = new ArrayList<>();
        for(String recipe : ordering){
            if(suppliesSet.containsAll(ingredientsMap.get(recipe))){
                prepared.add(recipe);
                suppliesSet.add(recipe);
            }
        }
        return prepared;
    }

    public static void main(String[] args) {
        String[] recipes = {"chicken burger", "buns", "crispy chicken"};
        List<List<String>> ingredients = new ArrayList<>();
        ingredients.add(Arrays.asList("buns", "crispy chicken", "lettuce", "cheese"));
        ingredients.add(Arrays.asList("yeast", "flour"));
        ingredients.add(Arrays.asList("chicken", "breadcrumbs"));
        String[] supplies = {"yeast", "flour", "cheese", "breadcrumbs", "milk", "lettuce", "chicken"};
        ArrayList<String> prepared = findPossibleRecipes(recipes, ingredients, supplies);
        System.out.println("Possible recipes are: " + prepared);
        // Output: Possible recipes are: [buns, crispy chicken, chicken burger]
    }
}
