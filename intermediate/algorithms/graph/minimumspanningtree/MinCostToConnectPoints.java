package algorithms.graph.minimumspanningtree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.stream.Collectors;

public class MinCostToConnectPoints
{

	public int minCostConnectPoints(int[][] points) {

		int n = points.length;

		List<Pair>[] edgeList = new List[n];

		for(int i = 0;i<n;i++) {
			edgeList[i] = new ArrayList<>();
		}

        for(int i = 0;i<n;i++) {

			for(int j = 1;j<n;j++) {

				int manhattanDistance = getManhattanDistance(points[i][0],points[j][0],points[i][1],points[j][1]);

				edgeList[i].add(new Pair(j,manhattanDistance));
                edgeList[j].add(new Pair(i,manhattanDistance));

			}

		}

		return getMSTCost(n,edgeList);

	}

	public int getMSTCost(int n,List<Pair>[] graph){

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

		AdjacencyList(int n, List<Pair>[] edges,boolean isDirected)
		{

			this.n = n;
			values = edges;

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

	int getManhattanDistance(int x1,int x2,int y1,int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1-y2);
	}

	public static void main(String[] args) {
		MinCostToConnectPoints minCostToConnectPoints = new MinCostToConnectPoints();

		// Generate 1000 random points
		int[][] points = generateRandomPoints(1000, -1000, 1000);

		// Run the test case
		int result = minCostToConnectPoints.minCostConnectPoints(points);
		System.out.println("Output for Test Case with 1000 points: " + result);
	}

	private static int[][] generateRandomPoints(int numPoints, int min, int max) {
		Random random = new Random();
		int[][] points = new int[numPoints][2];
		for (int i = 0; i < numPoints; i++) {
			points[i][0] = random.nextInt(max - min + 1) + min; // Random x-coordinate
			points[i][1] = random.nextInt(max - min + 1) + min; // Random y-coordinate
		}
		return points;
	}


}
