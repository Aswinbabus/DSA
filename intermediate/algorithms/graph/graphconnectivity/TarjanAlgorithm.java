package algorithms.graph.graphconnectivity;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.*;
import java.util.Map;

public class TarjanAlgorithm
{

	List<List<Integer>> getSCC(int n,int[][] edges) {

		AdjacencyList adjacencyList = new AdjacencyList(n,edges);

		int[] discoveryTime = new int[n];
		Arrays.fill(discoveryTime,-1);

		int[] lowLink = new int[n];

		boolean[] stack = new boolean[n];
		Deque<Integer> dfsStack = new ArrayDeque<>();
		List<List<Integer>> result = new ArrayList<>();

		int[] time = new int[1];

		// to traverse all connected components
		for(int i = 0;i<n;i++) {


                if(discoveryTime[i] == -1) {
					dfs(i,adjacencyList,discoveryTime,lowLink,stack,dfsStack,time,result);
				}

		}

		return result;



	}

	void dfs(int vertice,AdjacencyList adjacencyList,int[] discoveryTime,int[] lowLink,boolean[] OnStack,Deque<Integer> stack,int[] discoveryTimeCounter,List<List<Integer>> result) {


			discoveryTime[vertice] = lowLink[vertice] = discoveryTimeCounter[0]++;

			OnStack[vertice] = true;
			stack.push(vertice);

			adjacencyList.get(vertice).forEach(neighbour -> {


				// finding the back edge - already discovered and currently in the stack
				if(OnStack[neighbour]) {

					lowLink[vertice] = Math.min(lowLink[vertice],discoveryTime[neighbour]);

				}
				// tree edge
				else {
					dfs(neighbour,adjacencyList,discoveryTime,lowLink,OnStack,stack,discoveryTimeCounter,result);
					lowLink[vertice] = Math.min(lowLink[vertice],lowLink[neighbour]);
				}

			});

			if(lowLink[vertice] == discoveryTime[vertice]) {

				List<Integer> scc = new ArrayList<>();

				int node;
				do {
					node = stack.pop();
					OnStack[node] = false;
					scc.add(node);
				} while (node != vertice);

				result.add(scc);

			}


	}

	class AdjacencyList
	{

		// undirected
		List<Integer>[] values;
		int n;

		private AdjacencyList()
		{

		}

		AdjacencyList(int n, int[][] edges)
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
				values[arr[0]].add(arr[1]);
			}

		}


		public List<Integer> get(int n)
		{
			return values[n];
		}


	}


	public static void main(String[] args) {
		TarjanAlgorithm tarjan = new TarjanAlgorithm();

		// Test Case: Graph with three SCCs
		int n = 8;
		int[][] edges = {
				{0, 1},
				{1, 2},
				{2, 0}, // SCC 1: 0 → 1 → 2 → 0
				{3, 4}, // SCC 2: 4 (only node 4)
				{5, 6},
				{6, 7},
				{7, 5}  // SCC 3: 5 → 6 → 7 → 5
		};

		List<List<Integer>> scc = tarjan.getSCC(n, edges);
		System.out.println("SCCs: " + scc);
		// Expected Output: [[0, 2, 1], [4], [5, 7, 6]]
	}

}
