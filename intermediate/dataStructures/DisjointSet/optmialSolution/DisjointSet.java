package dataStructures.DisjointSet.optmialSolution;

import java.util.HashMap;
import java.util.Map;

public class DisjointSet<T> implements dataStructures.DisjointSet.DisjointSet<T>
{

	Map<T,Node> nodes;

	public DisjointSet()
	{
		this.nodes = new HashMap<>();
	}

	// Path Compression Strategy
	public Node findSet(Node node) {

		if(node != node.parent) {
			node.parent = findSet(node.parent);
		}

		return node.parent;

	}

	@Override
	public void associate(T a, T b)
	{

		// if both elements not present
		if( !nodes.containsKey(a) && !nodes.containsKey(b) ) {
			nodes.put(a,new Node(a));
			nodes.put(b,new Node(b));
		}
		// b is not found
		else if(!nodes.containsKey(b)) {
			nodes.put(b, new Node(b));
		}
		else if(!nodes.containsKey(a)) {
			nodes.put(a, new Node(a));
		}

		union(a,b);
	}

	private void union(T a,T b) {

		Node parent1 = findSet(nodes.get(a)),parent2 = findSet(nodes.get(b));

		// if already they are associated
		if(parent1 == parent2) {
			return;
		}

		if(parent1.rank < parent2.rank) {

			parent1.parent = parent2;
			parent2.rank += parent1.rank;

		}
		else {
			parent2.parent = parent1;
			parent1.rank += parent2.rank;
		}


	}

	@Override
	public boolean checkAssociate(T a, T b)
	{
		if(!nodes.containsKey(a) || !nodes.containsKey(b))
			return false;

		return findSet(nodes.get(a)) == findSet(nodes.get(b));
	}

	class Node {
		T data;
		Node parent;
		int rank;

		Node(T data) {
			this.data = data;
			this.parent = this; // Initially, a node is its own parent
			this.rank = 1; // Rank is initially 0
		}
	}
}
