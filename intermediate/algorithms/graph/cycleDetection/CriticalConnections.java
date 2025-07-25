package algorithms.graph.cycleDetection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CriticalConnections
{
	public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {

          AdjacencyList adjacencyList = new AdjacencyList(n,connections);
		  AdjacencyList result = adjacencyList.clone();

		  // since the graph is connected


		  int[] parent = new int[n];
		  Arrays.fill(parent,-1);
		  parent[1] = 1;
		  Map<Integer,Integer> currStack = new HashMap<>();
		   List<Pair> stackInfo = new ArrayList<Pair>();

		  dfs(1,adjacencyList,result,parent,currStack,stackInfo);

		  List<List<Integer>> criticalConnections = new ArrayList<>();

		  for(int i = 0;i<n;i++) {
			  for(int edge : result.values[i]) {
				  criticalConnections.add(new ArrayList<>(Arrays.asList(i,edge)));
			  }
		  }

		  return criticalConnections;

	}

	void dfs(int curr,AdjacencyList adjacencyList,AdjacencyList result,int[] parent,Map<Integer,Integer> currStack,List<Pair> stackInfo) {

		if(parent[curr] != -1) {
			return;
		}


		for(Integer neighbour : adjacencyList.values[curr]) {


			if(parent[neighbour] == -1) {
				parent[neighbour] = curr;
				stackInfo.add(new Pair(curr, neighbour));
				currStack.put(neighbour,stackInfo.size()-1);
				dfs(neighbour, adjacencyList,result, parent,currStack,stackInfo);
				stackInfo.remove(stackInfo.size()-1);
				currStack.remove(neighbour);

			}
			else if(parent[curr] != neighbour && currStack.containsKey(neighbour)) {

				result.remove(neighbour,curr);

				int currIndex = stackInfo.size()-1;

				while(currIndex > currStack.get(neighbour)) {
					Pair pair = stackInfo.get(currIndex);
					result.remove(pair.a,pair.b);
					currIndex--;
				}

			}

		}

	}

	class AdjacencyList {

		// undirected
		Set<Integer>[] values;
		int n;

		private AdjacencyList() {

		}

		AdjacencyList(int n, List<List<Integer>> edges) {

			this.n = n;
			values = new HashSet[n];

			for(int i = 0;i<n;i++) {
				values[i] = new HashSet<>();
			}

			for(List<Integer> edge : edges) {
				values[edge.get(0)].add(edge.get(1));
				values[edge.get(1)].add(edge.get(0));
			}

		}

		public AdjacencyList clone() {

			AdjacencyList list = new AdjacencyList();

			list.values = new HashSet[n];



			for(int i = 0;i<n;i++) {

				list.values[i] = new HashSet<>(values[i]);

			}

			return list;

		}

		public void remove(int a,int b) {
			values[a].remove(b);
			values[b].remove(a);
		}

	}


	class Pair {
		int a;
		int b;
		Pair(int a,int b) {
			this.a = a;
			this.b = b;
		}
	}

	public static void main(String[] args)
	{

		System.out.println(new CriticalConnections().criticalConnections(4, List.of(List.of(0,1),List.of(1,2),List.of(2,0),List.of(1,3))));

	}
}
