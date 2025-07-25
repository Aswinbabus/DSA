package dataStructures.DisjointSet.optmialSolution;

import java.util.HashMap;
import java.util.Map;

class ReduntentConnection {

	public int[] findRedundantConnection(int[][] edges) {

		DisjointSetLocal<Integer> set = new DisjointSetLocal<>();

         for(int[] edge : edges) {

			 try
			 {
				 if (set.checkAssociate(edge[0], edge[1]))
				 {
					 return edge;
				 }
				 else {
					 set.associate(edge[0],edge[1]);
				 }
			 }
			 catch (IllegalArgumentException e) {
				 // If the edge is not present in the disjoint set, we can associate it
				 set.associate(edge[0], edge[1]);
			 }

		 }

		 return new int[]{-1,-1};

	}

}

class DisjointSetLocal<T> implements dataStructures.DisjointSet.DisjointSet<T>
{

	Map<T,Node> nodes;

	public DisjointSetLocal()
	{
		this.nodes = new HashMap<>();
	}

	// Path Compression Strategy
	private Node findSet(Node node) {

		if(node != node.parent) {
			node.parent = findSet(node.parent);
		}

		return node.parent;

	}

	@Override
	public void associate(T a, T b)
	{

		nodes.computeIfAbsent(a, (k) ->
			new Node(a)
		);
		nodes.computeIfAbsent(b, (k) ->
			new Node(b)
	     );
		union(a, b);

	}

	private void union(T a,T b) {

		Node parent1 = findSet(nodes.get(a)),parent2 = findSet(nodes.get(b));

		// if already they are associated
		if(parent1 == parent2) {
			return;
		}

		if(parent1.rank > parent2.rank) {
			parent2.parent = parent1;
		}
		else if(parent1.rank < parent2.rank) {
			parent1.parent = parent2;
		}
		else {
			parent2.parent = parent1;
			parent1.rank++;
		}

	}

	@Override
	public boolean checkAssociate(T a, T b) throws IllegalArgumentException
	{
		if(!nodes.containsKey(a) || !nodes.containsKey(b))
			throw new IllegalArgumentException("One or both elements are not present in the disjoint set.");

		return findSet(nodes.get(a)) == findSet(nodes.get(b));
	}

	private class Node {
		T data;
		Node parent;
		int rank;

		Node(T data) {
			this.data = data;
			this.parent = this; // Initially, a node is its own parent
			this.rank = 0; // Rank is initially 0
		}
	}
}

