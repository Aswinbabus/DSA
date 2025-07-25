package algorithms.graph.graphTraversal;

import java.util.Arrays;

public class ChepeastTravel
{

	public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {

		int[] dist = new int[n];
		Arrays.fill(dist,Integer.MAX_VALUE);

		dist[src] = 0;

		for(int i = 0;i<=k;i++)
		{

			int[] localDist = Arrays.copyOf(dist,n);

			for (int[] flight : flights)
			{

				// relaxing the edge
				if(dist[flight[0]] != Integer.MAX_VALUE)
				{
					int currDist = dist[flight[0]] + flight[2];

					if (localDist[flight[1]] > currDist)
					{
						localDist[flight[1]] = currDist;
					}
				}

			}

			dist = localDist;

		}

		return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];



	}



	public static void main(String[] args) {
		ChepeastTravel chepeastTravel = new ChepeastTravel();

		// Test Case 1
		int n1 = 4;
		int[][] flights1 = {{0, 1, 100}, {1, 2, 100}, {2, 0, 100}, {1, 3, 600}, {2, 3, 200}};
		int src1 = 0, dst1 = 3, k1 = 1;
		System.out.println("Test Case 1: " + chepeastTravel.findCheapestPrice(n1, flights1, src1, dst1, k1));

		// Test Case 2
		int n2 = 3;
		int[][] flights2 = {{0, 1, 100}, {1, 2, 100}, {0, 2, 500}};
		int src2 = 0, dst2 = 2, k2 = 1;
		System.out.println("Test Case 2: " + chepeastTravel.findCheapestPrice(n2, flights2, src2, dst2, k2));

		// Test Case 3
		int n3 = 3;
		int[][] flights3 = {{0, 1, 100}, {1, 2, 100}, {0, 2, 500}};
		int src3 = 0, dst3 = 2, k3 = 0;
		System.out.println("Test Case 3: " + chepeastTravel.findCheapestPrice(n3, flights3, src3, dst3, k3));

		// Test Case 4: Complex graph with multiple paths and stops
		int n4 = 15;
		int[][] flights4 = {
				{10, 14, 43}, {1, 12, 62}, {4, 2, 62}, {14, 10, 49}, {9, 5, 29}, {13, 7, 53}, {4, 12, 90},
				{14, 9, 38}, {11, 2, 64}, {2, 13, 92}, {11, 5, 42}, {10, 1, 89}, {14, 0, 32}, {9, 4, 81},
				{3, 6, 97}, {7, 13, 35}, {11, 9, 63}, {5, 7, 82}, {13, 6, 57}, {4, 5, 100}, {2, 9, 34},
				{11, 13, 1}, {14, 8, 1}, {12, 10, 42}, {2, 4, 41}, {0, 6, 55}, {5, 12, 1}, {13, 3, 67},
				{3, 13, 36}, {3, 12, 73}, {7, 5, 72}, {5, 6, 100}, {7, 6, 52}, {4, 7, 43}, {6, 3, 67},
				{3, 1, 66}, {8, 12, 30}, {8, 3, 42}, {9, 3, 57}, {12, 6, 31}, {2, 7, 10}, {14, 4, 91},
				{2, 3, 29}, {8, 9, 29}, {2, 11, 65}, {3, 8, 49}, {6, 14, 22}, {4, 6, 38}, {13, 0, 78},
				{1, 10, 97}, {8, 14, 40}, {7, 9, 3}, {14, 6, 4}, {4, 8, 75}, {1, 6, 56}
		};
		int src4 = 1, dst4 = 4, k4 = 10;
		System.out.println("Test Case 4: " + chepeastTravel.findCheapestPrice(n4, flights4, src4, dst4, k4));
	}

}
