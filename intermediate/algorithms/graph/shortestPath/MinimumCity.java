package algorithms.graph.shortestPath;

import java.util.Arrays;

public class MinimumCity
{
	public int findTheCity(int n, int[][] edges, int distanceThreshold) {

		int[][] dist = new int[n][n];

		for(int i = 0;i<n;i++) {
			Arrays.fill(dist[i],Integer.MAX_VALUE);
		}

		for(int[] edge : edges) {
			if(edge[2] <= distanceThreshold)
			{
				dist[edge[0]][edge[1]] = edge[2];
				dist[edge[1]][edge[0]] = edge[2];
			}
		}

		for(int k = 0;k<n;k++)
		{
			for (int u = 0; u < n; u++)
			{
				for (int v = 0; v < n; v++)
				{

					if(dist[u][k] != Integer.MAX_VALUE && dist[k][v] != Integer.MAX_VALUE)
					{
						int currDist = dist[u][k] + dist[k][v];

						if (currDist <= distanceThreshold && dist[u][v] > currDist)
						{
							dist[u][v] = currDist;
						}
					}

				}
			}
		}


		int minCityCount = Integer.MAX_VALUE;
		int resultCity = n-1;

		for(int i = 0;i<n;i++) {

			int currCount = 0;

			for(int j = 0;j<n;j++) {

				if(i!=j && dist[i][j] != Integer.MAX_VALUE) {
					currCount++;
				}
			}

			if(minCityCount >= currCount) {
				minCityCount = currCount;
				resultCity = i;
			}
		}

		return resultCity;

	}

	public static void main(String[] args) {
		MinimumCity minimumCity = new MinimumCity();

		// Test Case 1
		int n1 = 4;
		int[][] edges1 = {{0,1,3},{1,2,1},{1,3,4},{2,3,1}};
		int distanceThreshold1 = 4;
		int result1 = minimumCity.findTheCity(n1, edges1, distanceThreshold1);
		System.out.println("Test Case 1:");
		System.out.println("Input: n = 4, edges = [[0,1,3],[1,2,1],[1,3,4],[2,3,1]], distanceThreshold = 4");
		System.out.println("Output: " + result1);
		System.out.println("Expected: 3");
		System.out.println(result1 == 3 ? "PASSED ✓" : "FAILED ✗");
		System.out.println();

		// Test Case 2
		int n2 = 5;
		int[][] edges2 = {{0,1,2},{0,4,8},{1,2,3},{1,4,2},{2,3,1},{3,4,1}};
		int distanceThreshold2 = 2;
		int result2 = minimumCity.findTheCity(n2, edges2, distanceThreshold2);
		System.out.println("Test Case 2:");
		System.out.println("Input: n = 5, edges = [[0,1,2],[0,4,8],[1,2,3},{1,4,2},{2,3,1},{3,4,1]], distanceThreshold = 2");
		System.out.println("Output: " + result2);
		System.out.println("Expected: 0");
		System.out.println(result2 == 0 ? "PASSED ✓" : "FAILED ✗");
	}
}
