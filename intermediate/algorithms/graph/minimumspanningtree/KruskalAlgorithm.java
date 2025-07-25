package algorithms.graph.minimumspanningtree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KruskalAlgorithm
{


	public int getMSTCost(int n,int[][] edges) {

		Arrays.sort(edges, Comparator.comparingInt(edge -> edge[2]));

		int totalCost = 0,edgeCount = 0;

		DisjointSet<Integer> disjointSet = new DisjointSet<Integer>();

		for(int i = 0;i<n;i++) {
			disjointSet.makeSet(i);
		}

		for(int[] edge : edges) {

			// lies in same set
			if(!disjointSet.find(edge[0],edge[1])) {

				disjointSet.union(edge[0],edge[1]);
				edgeCount++;
				totalCost += edge[2];

				if(edgeCount == n-1) {
					break;
				}

			}


		}

		return edgeCount == n -1 ? totalCost : Integer.MAX_VALUE;

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


}
