package algorithms.graph.shortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FloydWarshell
{

	int[] shortestPaths(int n,int[][] graph,int startNode) {

		int[][] dist = new int[n][n];

		for(int i = 0;i<n;i++) {
			Arrays.fill(dist[i],Integer.MAX_VALUE);
		}

		for(int[] edge : graph) {
			dist[edge[0]][edge[1]] = edge[2];
		}


		for(int k = 0;k<n;k++) {
			for(int i = 0;i<n;i++) {
				for(int j = 0;j<n;j++) {

					if(dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE && dist[i][j] > dist[i][k] + dist[k][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
					}

				}
			}
		}

		// Negative cycle detection
		for (int i = 0; i < n; i++) {
			if (dist[i][i] < 0) {
				System.out.println("Negative cycle detected");
				return null;
			}
		}

		return dist[startNode];

	}


	class AdjacencyList
	{

		// undirected
		List<BellManFord.Pair>[] values;
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
				values[arr[0]].add(new BellManFord.Pair(arr[1],arr[2]));
				if(!isDirected)
				{
					values[arr[1]].add(new BellManFord.Pair(arr[0], arr[2]));
				}
			}

		}


		public List<BellManFord.Pair> get(int n)
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

		@Override
		public boolean equals(Object o)
		{
			if (!(o instanceof BellManFord.Pair))
				return false;
			BellManFord.Pair pair = (BellManFord.Pair) o;
			return node == pair.node;
		}

		@Override
		public int hashCode()
		{
			return Objects.hashCode(node);
		}
	}

	public static void main(String[] args) {
		FloydWarshell floydWarshell = new FloydWarshell();

		// Test Case 1: Simple graph with no negative weights
		int[][] graph1 = {
				{0, 1, 4},
				{0, 2, 5},
				{1, 2, -2},
				{2, 3, 3}
		};
		int[] expected1 = {0, 4, 2, 5};
		assert Arrays.equals(floydWarshell.shortestPaths(4, graph1, 0), expected1) : "Test Case 1 Failed";

		// Test Case 2: Graph with negative weights but no negative cycles
		int[][] graph2 = {
				{0, 1, 1},
				{1, 2, -1},
				{2, 3, 2},
				{3, 0, 3}
		};
		int[] expected2 = {0, 1, 0, 2};
		assert Arrays.equals(floydWarshell.shortestPaths(4, graph2, 0), expected2) : "Test Case 2 Failed";

		// Test Case 3: Graph with a negative cycle
		int[][] graph3 = {
				{0, 1, 1},
				{1, 2, -1},
				{2, 3, 2},
				{3, 1, -2}
		};
		int[] expected3 = {0, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE}; // Negative cycle detection
		assert Arrays.equals(floydWarshell.shortestPaths(4, graph3, 0), expected3) : "Test Case 3 Failed";

		// Test Case 4: Disconnected graph
		int[][] graph4 = {
				{0, 1, 4},
				{2, 3, 5}
		};
		int[] expected4 = {0, 4, Integer.MAX_VALUE, Integer.MAX_VALUE};
		assert Arrays.equals(floydWarshell.shortestPaths(4, graph4, 0), expected4) : "Test Case 4 Failed";

		// Test Case 5: Single node graph
		int[][] graph5 = {};
		int[] expected5 = {0};
		assert Arrays.equals(floydWarshell.shortestPaths(1, graph5, 0), expected5) : "Test Case 5 Failed";

		// Test Case 6: Graph with multiple paths to the destination
		int[][] graph6 = {
				{0, 1, 2},
				{0, 2, 4},
				{1, 2, 1},
				{1, 3, 7},
				{2, 3, 3}
		};
		int[] expected6 = {0, 2, 3, 6};
		assert Arrays.equals(floydWarshell.shortestPaths(4, graph6, 0), expected6) : "Test Case 6 Failed";

		// Test Case 7: Graph with unreachable destination
		int[][] graph7 = {
				{0, 1, 2},
				{1, 2, 3}
		};
		int[] expected7 = {0, 2, 5, Integer.MAX_VALUE};
		assert Arrays.equals(floydWarshell.shortestPaths(4, graph7, 0), expected7) : "Test Case 7 Failed";

		System.out.println("All test cases passed!");
	}

}
