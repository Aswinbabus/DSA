package algorithms.graph.shortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BellManFord
{

	int getShortestPath(int n,int[][] graph,int startNode,int endNode) {

		int[] dist = new int[n];
		int[] parent = new int[n];

		Arrays.fill(dist,Integer.MAX_VALUE);

		dist[startNode] = 0;

		AdjacencyList adjacencyList = new AdjacencyList(n,graph,true);

		for(int currVertice = 0;currVertice<n-1;currVertice++) {

			for(Pair neighbourVertice : adjacencyList.get(currVertice)) {

				double currDist = dist[currVertice] + neighbourVertice.cost;

				if(dist[neighbourVertice.node] > currDist) {

					dist[neighbourVertice.node] = (int) currDist;
					parent[neighbourVertice.node] = currVertice;

				}


			}

		}

		for(int[] edge : graph) {

			int u = edge[0],v = edge[1],cost = edge[2];

			if(dist[u] + cost < dist[v]) {
				System.out.println("Negative cycle exists");
				return -1;
			}

		}

		return dist[endNode] == Integer.MAX_VALUE ? -1 : dist[endNode];


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

		@Override
		public boolean equals(Object o)
		{
			if (!(o instanceof Pair))
				return false;
			Pair pair = (Pair) o;
			return node == pair.node;
		}

		@Override
		public int hashCode()
		{
			return Objects.hashCode(node);
		}
	}


	public static void main(String[] args) {
		BellManFord bellManFord = new BellManFord();

		// Test Case 1: Simple graph with no negative weights
		int[][] graph1 = {
				{0, 1, 4},
				{0, 2, 5},
				{1, 2, -2},
				{2, 3, 3}
		};
		assert bellManFord.getShortestPath(4, graph1, 0, 3) == 7 : "Test Case 1 Failed";

		// Test Case 2: Graph with negative weights but no negative cycles
		int[][] graph2 = {
				{0, 1, 1},
				{1, 2, -1},
				{2, 3, 2},
				{3, 0, 3}
		};
		assert bellManFord.getShortestPath(4, graph2, 0, 3) == 2 : "Test Case 2 Failed";

		// Test Case 3: Graph with a negative cycle
		int[][] graph3 = {
				{0, 1, 1},
				{1, 2, -1},
				{2, 3, 2},
				{3, 1, -2}
		};
		assert bellManFord.getShortestPath(4, graph3, 0, 3) == -1 : "Test Case 3 Failed";

		// Test Case 4: Disconnected graph
		int[][] graph4 = {
				{0, 1, 4},
				{2, 3, 5}
		};
		assert bellManFord.getShortestPath(4, graph4, 0, 3) == -1 : "Test Case 4 Failed";

		// Test Case 5: Single node graph
		int[][] graph5 = {};
		assert bellManFord.getShortestPath(1, graph5, 0, 0) == 0 : "Test Case 5 Failed";

		// Test Case 6: Graph with multiple paths to the destination
		int[][] graph6 = {
				{0, 1, 2},
				{0, 2, 4},
				{1, 2, 1},
				{1, 3, 7},
				{2, 3, 3}
		};
		assert bellManFord.getShortestPath(4, graph6, 0, 3) == 6 : "Test Case 6 Failed";

		// Test Case 7: Graph with unreachable destination
		int[][] graph7 = {
				{0, 1, 2},
				{1, 2, 3}
		};
		assert bellManFord.getShortestPath(4, graph7, 0, 3) == -1 : "Test Case 7 Failed";

		System.out.println("All test cases passed!");
	}


}
