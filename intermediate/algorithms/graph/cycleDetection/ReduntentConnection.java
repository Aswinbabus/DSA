package algorithms.graph.cycleDetection;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReduntentConnection
{
	public int[] findRedundantDirectedConnection(int[][] edges) {

		AdjacencyList adjacencyList = new AdjacencyList(edges.length,edges);
		int n = edges.length,root = -1;

		int[] incomingEdges = new int[n+1];
		int[] outGoingEdges = new int[n+1];

		for(int[] edge : edges) {
			incomingEdges[edge[1]]++;
			outGoingEdges[edge[0]]++;
		}

		for(int i = 1;i<=n;i++) {
			if(incomingEdges[i] == 0) {
				root = i;
			}
		}

		for(int i = 1;i<=n;i++) {
			if(outGoingEdges[i] > 1) {
				root = i;
			}
		}

		int[] result = null;

		DisjointSet<Integer> disjointSet = new DisjointSet();

		for(int[] edge : edges) {

			if(edge[0] == root) {
				disjointSet.union(edge[0],edge[1]);
			}

		}

		for(int[] edge : edges) {

			if(edge[0] != root) {

				if(disjointSet.find(edge[0],edge[1])) {
					result = new int[]{edge[0],edge[1]};
				}
				else {
					disjointSet.union(edge[0],edge[1]);
				}

			}

		}

		return result;

	}


	class AdjacencyList {

		// undirected
		Set<Integer>[] values;
		int n;

		private AdjacencyList() {

		}

		AdjacencyList(int n, int[][] edges) {

			this.n = n;
			values = new HashSet[n+1];

			for(int i = 1;i<=n;i++) {
				values[i] = new HashSet<>();
			}

			for(int[] edge : edges) {
				values[edge[0]].add(edge[1]);
			}

		}

	}

	static class DisjointSet<T> {

		Map<T,Node<T>> nodes = new HashMap<>();

		public void makeSet(T a) {
			if(!nodes.containsKey(a)) {
				nodes.put(a, new Node<>(a));
			}
		}

		public void union(T a,T b) {

			if(!nodes.containsKey(a)) {
				makeSet(a);
			}

			if(!nodes.containsKey(b)) {
				makeSet(b);
			}

			Node parentA = getParent(nodes.get(a));
			Node parentB = getParent(nodes.get(b));

			if(parentA == parentB) {
				return;
			}

			else if(parentA.rank > parentB.rank) {
				parentB.parent = parentA;
			}
			else if(parentB.rank > parentA.rank) {
				parentA.parent = parentB;
			}
			else {
				parentB.parent = parentA;
				parentA.rank++;
			}

		}

		public boolean find(T a,T b) {

			if(!nodes.containsKey(a)) {
				makeSet(a);
			}

			if(!nodes.containsKey(b)) {
				makeSet(b);
			}
			Node<T> nodeA = nodes.get(a),nodeB = nodes.get(b);

			Node<T> parentA = getParent(nodeA),parentB = getParent(nodeB);

			return parentA == parentB;

		}


		public Node<T> getParent(Node<T> node) {

			if(node.parent != node) {
				node.parent = getParent(node.parent);
			}

			return node.parent;

		}


	}

	static class Node<T> {
		T data;
		Node<T> parent;
		int rank;

		public Node(T data) {
			this.data = data;
			this.parent = this;
			this.rank = 0;
		}

	}


	public static void main(String[] args) {
		ReduntentConnection redundantConnection = new ReduntentConnection();

		// Test Case 1
		int[][] edges1 = {
				{1, 2},
				{1, 3},
				{2, 3}
		};
		int[] result1 = redundantConnection.findRedundantDirectedConnection(edges1);
		System.out.println("Redundant Connection (Test Case 1): " + Arrays.toString(result1));
		// Expected Output: [2, 3]

		// Test Case 2
		int[][] edges2 = {
				{1, 2},
				{2, 3},
				{3, 4},
				{4, 1},
				{1, 5}
		};
		int[] result2 = redundantConnection.findRedundantDirectedConnection(edges2);
		System.out.println("Redundant Connection (Test Case 2): " + Arrays.toString(result2));
		// Expected Output: [4, 1]
	}

}
