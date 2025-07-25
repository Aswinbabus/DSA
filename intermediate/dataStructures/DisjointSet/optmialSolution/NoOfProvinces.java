package dataStructures.DisjointSet.optmialSolution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NoOfProvinces
{
	public int findCircleNum(int[][] isConnected) {

		int n = isConnected.length;

		DisjointSet<Integer> set = new DisjointSet<>();

		set.createSet(IntStream.range(1,n+1).boxed().collect(Collectors.toList()));

		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(isConnected[i][j] == 1 && i != j) {
					set.associate(i+1,j+1);
				}
			}
		}

		return set.groupCount();

	}

	class DisjointSet<T>
	{

		Map<T, Node> nodes;
        int groupCount;

		public DisjointSet()
		{
			this.nodes = new HashMap<>();
		}

		private void createSet(List<T> initialValues) {
			initialValues.forEach( value ->
					{
						nodes.computeIfAbsent(value,(k) -> new Node(value));
						groupCount++;
					}
			);
		}
		// Path Compression Strategy
		private Node findSet(Node node) {

			if(node != node.parent) {
				node.parent = findSet(node.parent);
			}

			return node.parent;

		}

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

		public boolean checkAssociate(T a, T b) throws IllegalArgumentException
		{
			if(!nodes.containsKey(a) || !nodes.containsKey(b))
				throw new IllegalArgumentException("One or both elements are not present in the disjoint set.");

			return findSet(nodes.get(a)) == findSet(nodes.get(b));
		}

		public int groupCount()
		{
			return groupCount;
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
}

