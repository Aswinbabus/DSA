package algorithms.graph.shortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class MinimumEffort
{

	public int minimumEffortPath(int[][] heights) {

		int n = heights.length * heights[0].length;

		AdjacencyList adjacencyList = new AdjacencyList(n,buildEdges(heights));

		int[] effort = new int[n];

		for(int i = 0;i<heights.length;i++) {
			for(int j = 0;j<heights[0].length;j++) {
				effort[(i * heights[0].length) + j] = heights[i][j];
			}
		}


		return getShortestPath(heights.length,heights[0].length,heights,adjacencyList,0,n-1,effort);

	}

	int getShortestPath(int rows,int cols, int[][] arr,AdjacencyList list, int startNode,int endNode,int[] effort)
	{

		int n = rows * cols;
		int[] dist = new int[n];
		boolean[] visited = new boolean[n];

		Arrays.fill(dist, Integer.MAX_VALUE);

		PriorityQueue<Pair> vertices = new PriorityQueue<Pair>(Comparator.comparingDouble(pair -> pair.cost));

		dist[startNode] = 0;

		vertices.add(new Pair(startNode, 0));

		while (!vertices.isEmpty())
		{

			int currVertice = vertices.poll().node;

			if(currVertice == endNode) {
				break;
			}

			if(visited[currVertice]) {
				continue;
			}

			// relaxing neighbour nodes
			for (Pair neighbour : list.get(currVertice))
			{

				int currDist = Math.max(dist[currVertice],Math.abs(effort[currVertice] - effort[neighbour.node]));
				if (currVertice != neighbour.node && dist[neighbour.node] > currDist)
				{
					dist[neighbour.node] = currDist;
					if(!visited[neighbour.node])
					{
						vertices.add(new Pair(neighbour.node,currDist));
					}
				}


			}

			visited[currVertice] = true;

		}


		return dist[endNode];
	}

	private List<Integer[]> buildEdges(int[][] heights)
	{


		int rows = heights.length,column = heights[0].length;

		List<Integer[]> edges = new ArrayList<>();

		for(int i = 0;i<rows;i++) {

			for(int j = 0;j <column;j++) {

				int uniqueId = ( i * column ) + j;

					// down
					if (i + 1 < rows)
					{
						edges.add(new Integer[] { uniqueId, uniqueId + column, Math.abs(heights[i][j] - heights[i + 1][j]) });
					}
					// up
					if (i - 1 >= 0)
					{
						edges.add(new Integer[] { uniqueId, uniqueId - column, Math.abs(heights[i][j] - heights[i - 1][j]) });
					}


					// right
					if (j + 1 < column)
					{
						edges.add(new Integer[] { uniqueId, uniqueId + 1, Math.abs(heights[i][j] - heights[i][j + 1]) });
					}
					// left
					if (j - 1 >= 0)
					{
						edges.add(new Integer[] { uniqueId, uniqueId - 1, Math.abs(heights[i][j] - heights[i][j - 1]) });
					}


			}

		}

		return edges;

	}

	class AdjacencyList
	{

		// undirected
		List<Pair>[] values;
		int n;

		private AdjacencyList()
		{

		}

		AdjacencyList(int n, List<Integer[]> edges)
		{

			this.n = n;
			values = new List[n];

			// one indexed
			for (int i = 0; i < n; i++)
			{
				values[i] = new ArrayList<>();
			}

			int counter = 0;

			for (Integer[] arr : edges)
			{
				values[arr[0]].add(new Pair(arr[1],arr[2]));
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
		MinimumEffort minimumEffort = new MinimumEffort();

		// Test Case 1
		int[][] heights1 = {{1,2,3},{3,8,4},{5,3,5}};
		int result1 = minimumEffort.minimumEffortPath(heights1);
		System.out.println("Test Case 1:");
		System.out.println("Input: heights = [[1,2,3],[3,8,4],[5,3,5]]");
		System.out.println("Output: " + result1);
		System.out.println("Expected: 1");
		System.out.println(result1 == 1 ? "PASSED ✓" : "FAILED ✗");
		System.out.println();

		// Test Case 2
		int[][] heights2 = {{1,2,1,1,1},{1,2,1,2,1},{1,2,1,2,1},{1,2,1,2,1},{1,1,1,2,1}};
		int result2 = minimumEffort.minimumEffortPath(heights2);
		System.out.println("Test Case 2:");
		System.out.println("Input: heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]");
		System.out.println("Output: " + result2);
		System.out.println("Expected: 0");
		System.out.println(result2 == 0 ? "PASSED ✓" : "FAILED ✗");

		// Additional test case from the original main method
		int[][] heights3 = {{1,2,2},{3,8,2},{5,3,5}};
		int result3 = minimumEffort.minimumEffortPath(heights3);
		System.out.println("\nTest Case 3:");
		System.out.println("Input: heights = [[1,2,2],[3,8,2],[5,3,5]]");
		System.out.println("Output: " + result3);
	}

}
