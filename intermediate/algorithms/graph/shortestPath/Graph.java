package algorithms.graph.shortestPath;

import java.util.Arrays;

class Graph
{

	int[][] dist = null;
	int n;

	public Graph(int n, int[][] edges) {

		dist = new int[n][n];

		for(int i = 0;i<n;i++) {
			dist[i][i] = 0;
			Arrays.fill(dist[i],Integer.MAX_VALUE);
		}

		for(int[] edge : edges) {
			dist[edge[0]][edge[1]] = edge[2];
		}

		for(int k = 0;k<n;k++) {

			for(int u = 0;u<n;u++) {

				for(int v = 0;v<n;v++) {

					if(dist[u][k] != Integer.MAX_VALUE && dist[k][v] != Integer.MAX_VALUE)
					{
						int currDist = dist[u][k] + dist[k][v];

						if (currDist < dist[u][v])
						{
							dist[u][v] = currDist;
						}
					}

				}

			}

		}

		this.n = n;

	}

	public void addEdge(int[] edge) {

		int from = edge[0],to = edge[1],cost = edge[2];

		// cascading the changes
		if(dist[from][to] > cost)
		{
			cascadeChange(from, to, cost);
		}


	}

	public void cascadeChange(int from,int to,int cost) {

			dist[from][to] = cost;

			// considering from as intermediate node
			int u,k = from,v = to;

			for(u = 0;u<n;u++) {

				if(dist[u][k] != Integer.MAX_VALUE)
				{
					if (dist[u][k] + dist[k][v] < dist[u][v])
					{
						cascadeChange(u,v,dist[u][k] + dist[k][v]);
					}
				}

			}

			// consider to as intermediate node
			u = from;
			k = to;

			for(v = 0;v<n;v++) {
				if(dist[k][v] != Integer.MAX_VALUE)
				{
					if (dist[u][k] + dist[k][v] < dist[u][v])
					{
						cascadeChange(u,v,dist[u][k] + dist[k][v]);
					}
				}
			}


	}

	public int shortestPath(int node1, int node2) {

		if(node1 == node2) {
			return 0;
		}
		return dist[node1][node2] == Integer.MAX_VALUE ? -1 : dist[node1][node2];

	}


	public static void main(String[] args) {
		// Initialize the Graph
		int n = 13;
		int[][] edges = {
				{11, 6, 84715}, {7, 9, 764823}, {6, 0, 315591}, {1, 4, 909432}, {6, 5, 514907},
				{9, 6, 105610}, {3, 10, 471042}, {7, 10, 348752}, {5, 11, 715628}, {6, 1, 973999},
				{8, 7, 593929}, {7, 6, 64688}, {6, 4, 741734}, {10, 1, 894247}, {9, 7, 81181},
				{2, 11, 75418}, {12, 2, 85431}, {7, 2, 260306}, {11, 9, 640614}, {2, 3, 648804},
				{4, 12, 568023}, {0, 8, 730096}, {9, 11, 633474}, {3, 6, 390214}, {1, 10, 117955},
				{9, 8, 222602}, {10, 7, 689294}
		};
		Graph graph = new Graph(n, edges);

		// Perform operations
		graph.addEdge(new int[]{1, 2, 36450});
		graph.addEdge(new int[]{8, 0, 709628});
		graph.addEdge(new int[]{2, 4, 30185});
		graph.addEdge(new int[]{12, 1, 21696});
		graph.addEdge(new int[]{1, 8, 2553});
		System.out.println("shortestPath(8, 9) = " + graph.shortestPath(8, 9));
		System.out.println("shortestPath(1, 11) = " + graph.shortestPath(1, 11));
		System.out.println("shortestPath(3, 4) = " + graph.shortestPath(3, 4));
		graph.addEdge(new int[]{4, 6, 2182});
		graph.addEdge(new int[]{7, 5, 206});
		graph.addEdge(new int[]{5, 7, 140});
		System.out.println("shortestPath(12, 5) = " + graph.shortestPath(12, 5));
		graph.addEdge(new int[]{12, 6, 365184});
		graph.addEdge(new int[]{3, 2, 5});
		System.out.println("shortestPath(4, 8) = " + graph.shortestPath(4, 8));
		System.out.println("shortestPath(7, 10) = " + graph.shortestPath(7, 10));
		System.out.println("shortestPath(0, 5) = " + graph.shortestPath(0, 5));
		graph.addEdge(new int[]{0, 11, 5});
		graph.addEdge(new int[]{1, 7, 5});
		System.out.println("shortestPath(0, 8) = " + graph.shortestPath(0, 8));
		System.out.println("shortestPath(11, 11) = " + graph.shortestPath(11, 11));
		System.out.println("shortestPath(7, 4) = " + graph.shortestPath(7, 4));
		System.out.println("shortestPath(0, 12) = " + graph.shortestPath(0, 12));
		graph.addEdge(new int[]{3, 9, 858944});
		System.out.println("shortestPath(0, 9) = " + graph.shortestPath(0, 9));
		System.out.println("shortestPath(3, 5) = " + graph.shortestPath(3, 5));
		System.out.println("shortestPath(4, 5) = " + graph.shortestPath(4, 5));
		System.out.println("shortestPath(12, 9) = " + graph.shortestPath(12, 9));
		System.out.println("shortestPath(9, 8) = " + graph.shortestPath(9, 8));
		System.out.println("shortestPath(3, 5) = " + graph.shortestPath(3, 5));
		graph.addEdge(new int[]{12, 9, 629052});
		System.out.println("shortestPath(3, 8) = " + graph.shortestPath(3, 8));
		graph.addEdge(new int[]{4, 0, 545201});
		System.out.println("shortestPath(11, 8) = " + graph.shortestPath(11, 8));
		System.out.println("shortestPath(4, 11) = " + graph.shortestPath(4, 11));
		System.out.println("shortestPath(9, 6) = " + graph.shortestPath(9, 6));
		graph.addEdge(new int[]{12, 7, 4});
		System.out.println("shortestPath(7, 10) = " + graph.shortestPath(7, 10));
		System.out.println("shortestPath(2, 5) = " + graph.shortestPath(2, 5));
		System.out.println("shortestPath(6, 11) = " + graph.shortestPath(6, 11));
		System.out.println("shortestPath(12, 2) = " + graph.shortestPath(12, 2));
		System.out.println("shortestPath(9, 7) = " + graph.shortestPath(9, 7));
		graph.addEdge(new int[]{4, 3, 879736});
		System.out.println("shortestPath(1, 3) = " + graph.shortestPath(1, 3));
		System.out.println("shortestPath(1, 0) = " + graph.shortestPath(1, 0));
		System.out.println("shortestPath(4, 6) = " + graph.shortestPath(4, 6));
	}
}