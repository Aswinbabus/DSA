package algorithms.graph.minimumspanningtree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import org.junit.Test;

public class PrimsAlgorithm
{

	public int getMSTCost(int n,int[][] graph){

		AdjacencyList adjacencyList = new AdjacencyList(n,graph,false);

		PriorityQueue<Pair> queue = new PriorityQueue<>(Comparator.comparingInt(pair -> (int) pair.cost));

		queue.add(new Pair(0,0));

		int verticeCount = 0;
		int totalCost = 0;

		boolean[] included = new boolean[n];


		while(!queue.isEmpty()) {

			Pair leastCostPair = queue.poll();

			if(!included[leastCostPair.node]) {

				totalCost += (int) leastCostPair.cost;

				verticeCount++;

				queue.addAll(adjacencyList.get(leastCostPair.node).stream().filter(node-> !included[node.node]).collect(Collectors.toList()));

				included[leastCostPair.node] = true;

				if(verticeCount == n) {
					break;
				}
			}

		}

		return totalCost;


	}

	class AdjacencyList
	{

		// undirected
		List<Pair>[] values;
		int n;

		private AdjacencyList()
		{

		}

		AdjacencyList(int n, int[][] edges,boolean isDirected)
		{

			this.n = n;
			values = new List[n];

			// one indexed
			for (int i = 0; i < n; i++)
			{
				values[i] = new ArrayList<>();
			}

			int counter = 0;

			for (int[] arr : edges)
			{
				values[arr[0]].add(new Pair(arr[1],arr[2]));
				if(!isDirected)
				{
					values[arr[1]].add(new Pair(arr[0], arr[2]));
				}
			}

		}


		public List<Pair> get(int n)
		{
			return values[n];
		}


	}

	static class Pair
	{

		int node;
		double cost;

		Pair(int node, double cost)
		{
			this.node = node;
			this.cost = cost;
		}

	}

	@Test
	public void testPrimsAlgorithmEdgeCases() {
		PrimsAlgorithm prims = new PrimsAlgorithm();

		// Test Case 1: No edges
		int n1 = 4;
		int[][] edges1 = {};
		int result1 = prims.getMSTCost(n1, edges1);
		assert result1 == 0 : "Failed Test Case 1: No edges";

		// Test Case 2: Single node
		int n2 = 1;
		int[][] edges2 = {};
		int result2 = prims.getMSTCost(n2, edges2);
		assert result2 == 0 : "Failed Test Case 2: Single node";

		// Test Case 3: Disconnected graph
		int n3 = 4;
		int[][] edges3 = {{0, 1, 10}, {2, 3, 5}};
		int result3 = prims.getMSTCost(n3, edges3);
		assert result3 == 0 : "Failed Test Case 3: Disconnected graph";

		// Test Case 4: All edges with the same weight
		int n4 = 4;
		int[][] edges4 = {{0, 1, 5}, {1, 2, 5}, {2, 3, 5}, {3, 0, 5}};
		int result4 = prims.getMSTCost(n4, edges4);
		assert result4 == 15 : "Failed Test Case 4: All edges with the same weight";

		// Test Case 5: Negative edge weights
		int n5 = 4;
		int[][] edges5 = {{0, 1, -10}, {1, 2, -20}, {2, 3, -30}, {3, 0, -40}};
		int result5 = prims.getMSTCost(n5, edges5);
		assert result5 == -100 : "Failed Test Case 5: Negative edge weights";

		// Test Case 6: Graph with a cycle
		int n6 = 4;
		int[][] edges6 = {{0, 1, 1}, {1, 2, 2}, {2, 3, 3}, {3, 0, 4}};
		int result6 = prims.getMSTCost(n6, edges6);
		assert result6 == 6 : "Failed Test Case 6: Graph with a cycle";

		// Test Case 7: Large graph
		int n7 = 6;
		int[][] edges7 = {
				{0, 1, 1}, {1, 2, 2}, {2, 3, 3}, {3, 4, 4}, {4, 5, 5}, {5, 0, 6},
				{0, 2, 7}, {1, 3, 8}, {2, 4, 9}, {3, 5, 10}
		};
		int result7 = prims.getMSTCost(n7, edges7);
		assert result7 == 15 : "Failed Test Case 7: Large graph";

		System.out.println("All test cases passed!");
	}



}
