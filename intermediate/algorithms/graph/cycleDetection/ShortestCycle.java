package algorithms.graph.cycleDetection;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShortestCycle
{
	public int findShortestCycle(int n, int[][] edges) {

		AdjacencyList adjacencyList = new AdjacencyList(n,edges);

		int[] level = new int[n];
		int[] parent = new int[n];


		Integer minCycleLength = Integer.MAX_VALUE;

		for(int i = 0;i<n;i++) {

			if(level[i] == 0) {

				Deque<Integer> bfs = new ArrayDeque<>();

				bfs.add(i);
				level[i] = 1;
				parent[i] = i;
				int levelCounter = 2;

				while(!bfs.isEmpty()) {

					int curr = bfs.poll();

					for(Integer neighbour : adjacencyList.getNeighbours(curr)) {

						if(parent[curr] == neighbour) {
							continue;
						}
						// cycle
						if(level[neighbour] != 0) {

							// neighbour backedge
							if(level[curr] == level[neighbour]) {
								minCycleLength = Math.min(minCycleLength,computeCycleLength(parent,curr,neighbour));
							}
							// child backedge
							else {
								minCycleLength = Math.min(minCycleLength,computeCycleLength(parent,parent[curr],neighbour) + 1);
							}

						}
						else
						{
							bfs.add(neighbour);
							level[neighbour] = level[curr] + 1;
							parent[neighbour] = curr;
						}

					}



				}

			}

		}

		return minCycleLength == Integer.MAX_VALUE ? -1 : minCycleLength;


	}

	int computeCycleLength(int[] parent,int vertexA,int vertexB) {

		int cycleLength = 1;
		while(vertexA != vertexB){
			vertexA = parent[vertexA];
			vertexB = parent[vertexB];
			cycleLength+=2;
		}
		return cycleLength;

	}


	class AdjacencyList {

		// undirected
		Set<Integer>[] values;
		int n;

		private AdjacencyList() {

		}

		AdjacencyList(int n, int[][] edges) {
			this.n = n;
			values = new HashSet[n];
			for(int i = 0;i<n;i++) {
				values[i] = new HashSet<>();
			}
			for(int[] edge : edges) {
				values[edge[0]].add(edge[1]);
				values[edge[1]].add(edge[0]);
			}
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

		public void remove(int a,int b) {
			values[a].remove(b);
			values[b].remove(a);
		}

		public Collection<Integer> getNeighbours(int vertex) {
			if(vertex >= 0 && vertex < n) {
				return values[vertex];
			}

			return new ArrayList<>();
		}



	}


	public static void main(String[] args) {
		ShortestCycle shortestCycle = new ShortestCycle();

		int n = 7;
		int[][] edges = {
				{0, 1}, {1, 2}, {2, 0},
				{3, 4}, {4, 5}, {5, 6}, {6, 3}
		};

		int result = shortestCycle.findShortestCycle(n, edges);
		System.out.println("Shortest Cycle Length: " + result); // Expected: 3
	}
}
