package algorithms.graph.topologicalSort;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CookingReceipe
{
	public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {

		Map<String,Node> receipeBook = new HashMap<>();

		// creating a graphical representation
		for(int i = 0;i<recipes.length;i++) {

			String receipe = recipes[i];

			Node node;

			if(receipeBook.containsKey(receipe)) {
				receipeBook.get(receipe).isReceipe = true;
				node = receipeBook.get(receipe);
			}
			else
			{
				node = new Node(receipe,true);
			}

			node.addIngredients(ingredients.get(i).stream().map(ingredientName ->
			{
				receipeBook.putIfAbsent(ingredientName, new Node(ingredientName, false));
				return receipeBook.get(ingredientName);
			}).collect(Collectors.toList()));

			receipeBook.put(receipe, node);

		}

		Set<String> result = new HashSet<>();

		for(String receipe : recipes) {

			Set<String> visited = new HashSet<>();
			if(canMake(receipe,receipeBook,new HashSet<String>(Arrays.asList(supplies)),visited)) {
	            result.add(receipe);
			}

		}

		return new ArrayList<>(result);

	}

	boolean canMake(String recipe, Map<String,Node> recipeBook,Set<String> supplies,Set<String> visited) {


		Node currNode = recipeBook.get(recipe);

		if(currNode.canMake != null) {
			return currNode.canMake;
		}

		if(visited.contains(recipe)) {

			return false;
		}
		visited.add(recipe);

		for (Node ingredient : currNode.ingredients)
		{

			if (ingredient.isReceipe)
			{
				if (!canMake(ingredient.receipe, recipeBook, supplies,visited))
				{
					currNode.canMake = false;
					return false;
				}
			}
			else
			{
				if (!supplies.contains(ingredient.receipe))
				{
					currNode.canMake = false;
					return false;
				}
			}

		}

		currNode.canMake = true;
		return true;


	}

	class Node {

		private String receipe;
		private Set<Node> ingredients;
		private boolean isReceipe;
		/**
		 * null - not check
		 * false - cannot make
		 * true - can make
		 */
		private Boolean canMake;

		Node(String receipe,boolean isReceipe) {
			this.receipe = receipe;
			this.isReceipe = isReceipe;
			this.ingredients = new HashSet<>();
		}

		void addIngredients(List<Node> ingredients) {
			this.ingredients.addAll(ingredients);
		}

		boolean canMake(List<Node> supplies) {
			return ingredients.containsAll(supplies);
		}

		@Override
		public String toString()
		{
			return receipe + "-" + ingredients.toString();
		}
	}

	public static void main(String[] args) {
		CookingReceipe cookingReceipe = new CookingReceipe();

		String[] recipes = { "bread", "milk", "sandwich" };
		List<List<String>> ingredients = List.of(
				List.of("yeast", "flour"),
				List.of("bread", "sandwich", "ghee"),
				List.of("bread", "meat")
		);
		String[] supplies = { "yeast", "flour", "meat" };

		List<String> result = cookingReceipe.findAllRecipes(recipes, ingredients, supplies);
		System.out.println(result); // Expected output: ["bread", "sandwich"]
	}
}
