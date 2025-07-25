package algorithms.graph.topologicalSort;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AncestorNodes
{
	public List<List<Integer>> getAncestors(int n, int[][] edges) {

		List<Set<Integer>> ancestors = new ArrayList<>();

		List<Integer>[] adjacencyList = new List[n];

		int[] indegree = new int[n];

		for(int[] edge : edges) {

			if(adjacencyList[edge[0]] == null) {
				adjacencyList[edge[0]] = new ArrayList<>();
			}
			adjacencyList[edge[0]].add(edge[1]);
			indegree[edge[1]]++;
		}

		Deque<Integer> bfs = new ArrayDeque<>();
		for(int i = 0;i<n;i++) {

			ancestors.add(new HashSet<>());
			if(adjacencyList[i] == null)
			{
				adjacencyList[i] = new ArrayList<>();
			}
			if(indegree[i] == 0)
			{
				bfs.add(i);
			}

		}

		while(!bfs.isEmpty()) {

			int vertice = bfs.poll();

			for(int adjacent : adjacencyList[vertice]) {
				ancestors.get(adjacent).add(vertice);
				ancestors.get(adjacent).addAll(ancestors.get(vertice));
				indegree[adjacent]--;
				if(indegree[adjacent] == 0)
				{
					bfs.push(adjacent);
				}
			}

		}

		List<List<Integer>> result = new ArrayList<>();

		for(Set<Integer> arr : ancestors) {
			result.add(new ArrayList<>(arr));
		}

		return result;



	}

	public static void main(String[] args) {
	    AncestorNodes nodes = new AncestorNodes();

	    int n = 8;
	    int[][] edgeList = {
	        {0, 3}, {0, 4}, {1, 3}, {2, 4}, {2, 7},
	        {3, 5}, {3, 6}, {3, 7}, {4, 6}
	    };

	    List<List<Integer>> result = nodes.getAncestors(n, edgeList);
	    System.out.println(result);
	}
}
