package algorithms.graph.shortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class MaxProfit
{

	public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {

		return getShortestPath(n,edges,succProb,start_node,end_node);

	}

	double getShortestPath(int n, int[][] graph,double[] cost, int startNode,int endNode)
	{

		double[] prob = new double[n];
		boolean[] visited = new boolean[n];

		AdjacencyList list = new AdjacencyList(n, graph,cost);

		Arrays.fill(prob, Double.MIN_VALUE);

		PriorityQueue<Pair> vertices = new PriorityQueue<Pair>(Comparator.comparingDouble(pair -> -pair.cost));

		prob[startNode] = 1.0;
		vertices.add(new Pair(startNode, 1.0));

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

				double currDist = prob[currVertice] * neighbour.cost;
				if (prob[neighbour.node] < currDist)
				{
					prob[neighbour.node] = currDist;
					if(!visited[neighbour.node])
					{
						vertices.add(new Pair(neighbour.node,currDist));
					}
				}


			}

			visited[currVertice] = true;

		}

		return prob[endNode];
	}

	class AdjacencyList
	{

		// undirected
		List<Pair>[] values;
		int n;

		private AdjacencyList()
		{

		}

		AdjacencyList(int n, int[][] edges,double[] cost)
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
				values[arr[0]].add(new Pair(arr[1],cost[counter]));
				values[arr[1]].add(new Pair(arr[0],cost[counter++]));
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
			if (!(o instanceof DjiskthiraAlgo.Pair))
				return false;
			DjiskthiraAlgo.Pair pair = (DjiskthiraAlgo.Pair) o;
			return node == pair.node;
		}

		@Override
		public int hashCode()
		{
			return Objects.hashCode(node);
		}
	}



	public static void main(String[] args) {
		MaxProfit maxProfit = new MaxProfit();

		// Input data
		int n = 3;
		int[][] edges = {{0, 1}, {1, 2}, {0, 2}};
		double[] succProb = {0.5, 0.5, 0.2};
		int start = 0;
		int end = 2;

		// Call the method and print the result
		double result = maxProfit.maxProbability(n, edges, succProb, start, end);
		System.out.println("Maximum Probability: " + result);

	}



}
