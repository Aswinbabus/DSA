package algorithms.graph.graphconnectivity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class CriticalConnection
{

	public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {

		AdjacencyList adjacencyList = new AdjacencyList(n,connections);

		int[] discoveryTime = new int[n];
		Arrays.fill(discoveryTime,-1);

		int[] lowLink = new int[n];

		boolean[] stack = new boolean[n];
		List<List<Integer>> result = new ArrayList<>();
		Deque<Integer> dfsStack = new ArrayDeque<>();

		int[] time = new int[1];

		// to traverse all connected components
		for(int i = 0;i<n;i++) {


			if(discoveryTime[i] == -1) {
				dfs(i,-1,adjacencyList,discoveryTime,lowLink,stack,dfsStack,time,result);
			}

		}

		return result;



	}

	void dfs(int vertice,int parent,AdjacencyList adjacencyList,int[] discoveryTime,int[] lowLink,boolean[] OnStack,Deque<Integer> stack,int[] discoveryTimeCounter,List<List<Integer>> result) {


		discoveryTime[vertice] = lowLink[vertice] = discoveryTimeCounter[0]++;

		OnStack[vertice] = true;
		stack.push(vertice);

		adjacencyList.get(vertice).forEach(neighbour -> {

			if(neighbour == parent) {
				return;
			}

			// finding the back edge - already discovered and currently in the stack
			if(OnStack[neighbour]) {

				lowLink[vertice] = Math.min(lowLink[vertice],discoveryTime[neighbour]);

			}
			// tree edge
			else if(discoveryTime[neighbour] == -1){
				dfs(neighbour,vertice,adjacencyList,discoveryTime,lowLink,OnStack,stack,discoveryTimeCounter,result);
				lowLink[vertice] = Math.min(lowLink[vertice],lowLink[neighbour]);

				if(lowLink[neighbour] > discoveryTime[vertice]) {
					result.add(Arrays.asList(vertice,neighbour));
				}

			}






		});


		if(lowLink[vertice] == discoveryTime[vertice]) {

			int node;
			do {
				node = stack.pop();
				OnStack[node] = false;
			}while(node != vertice);

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

		AdjacencyList(int n, List<List<Integer>> edges)
		{

			this.n = n;
			values = new List[n];

			// one indexed
			for (int i = 0; i < n; i++)
			{
				values[i] = new ArrayList<>();
			}

			for (List<Integer> arr : edges)
			{
				values[arr.get(0)].add(arr.get(1));
				values[arr.get(1)].add(arr.get(0));
			}

		}


		public List<Integer> get(int n)
		{
			return values[n];
		}


	}


	public static void main(String[] args) {
		CriticalConnection criticalConnection = new CriticalConnection();

		// Test Case 1
		int n1 = 4;
		List<List<Integer>> connections1 = new ArrayList<>();
		connections1.add(Arrays.asList(0, 1));
		connections1.add(Arrays.asList(1, 2));
		connections1.add(Arrays.asList(2, 0));
		connections1.add(Arrays.asList(1, 3));
		List<List<Integer>> result1 = criticalConnection.criticalConnections(n1, connections1);
		System.out.println("Critical Connections (Test Case 1): " + result1);
		// Expected Output: [[1, 3]]

		// Test Case 2
		int n2 = 2;
		List<List<Integer>> connections2 = new ArrayList<>();
		connections2.add(Arrays.asList(0, 1));
		List<List<Integer>> result2 = criticalConnection.criticalConnections(n2, connections2);
		System.out.println("Critical Connections (Test Case 2): " + result2);
		// Expected Output: [[0, 1]]
	}


}
