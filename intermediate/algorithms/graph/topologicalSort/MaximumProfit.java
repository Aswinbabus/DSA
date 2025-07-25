package algorithms.graph.topologicalSort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MaximumProfit
{

	public int maxProfit(int n, int[][] edges, int[] score) {

		List<Integer>[]  adjacencyList = new List[n];

		int[] indegree = new int[n];

		for(int[] edge : edges) {

			if(adjacencyList[edge[0]] == null) {
				adjacencyList[edge[0]] = new ArrayList<>();
			}
			adjacencyList[edge[0]].add(edge[1]);

			indegree[edge[1]]++;

		}

		for(int i = 0;i<n;i++) {
			System.out.println(indegree[i]);
		}

		PriorityQueue<Integer> verticeScore = new PriorityQueue<>(Comparator.comparingInt(a -> score[a]));

		// adding indegree 0 vertices to the priority queue
		for(int i = 0;i<n;i++) {

			if(indegree[i]==0) {
				verticeScore.add(i);
			}

		}

		int pos = 1;
		int maxProfit = 0;

		while(!verticeScore.isEmpty()) {

			System.out.println(verticeScore);

		   // get the vertex with maximum score
			int currVertice = verticeScore.poll();
		   maxProfit = maxProfit + (score[currVertice] * pos);

		   if(adjacencyList[currVertice] != null)
		   {

			   for(Integer v : adjacencyList[currVertice]) {

				 indegree[v]--;
				 if(indegree[v] == 0) {
					 verticeScore.add(v);
				 }

			  }

		   }
		   pos++;



		}

		return maxProfit;


	}

	public static void main(String[] args)
	{
		MaximumProfit maximumProfit = new MaximumProfit();
		maximumProfit.maxProfit(2,new int[][]{{0,1}},new int[]{37884,22011});
	}

}
