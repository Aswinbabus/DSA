package algorithms.graph.minimumspanningtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class CriticalEdges
{
	public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {

		List<List<Integer>> result = new ArrayList<>();

		LinkedList<List<Integer>> edgeList = new LinkedList<>();

		int edgeId = 0;

		for(int[] edge : edges) {
			edgeList.add(Arrays.asList(edgeId++,edge[0],edge[1],edge[2]));
		}

		edgeList.sort(Comparator.comparingInt(edge -> edge.get(3)));

		ListIterator<List<Integer>> currentIterator = edgeList.listIterator();

		while(currentIterator.hasNext()) {

			List<Integer> current = currentIterator.next();

			currentIterator.add(Arrays.asList(current.get(0),current.get(2),current.get(1),current.get(3)));

		}

		Set<Integer> nonCritical = new HashSet<>();
		Set<Integer> critical = new HashSet<>();

		MST refMST = getMST(n,edgeList,Integer.MAX_VALUE,null);

		ListIterator<List<Integer>> iterator = edgeList.listIterator();


		while(iterator.hasNext()) {

			List<Integer> currentEdge = iterator.next();
			iterator.previous();
			iterator.remove();

			List<Integer> backEdge = iterator.next();

			iterator.previous();

			iterator.remove();

			MST currentMST = getMST(n,edgeList,refMST.totalCost,null);

			if(currentMST == null  ) {
				critical.add(currentEdge.get(0));
			}


			MST nonCritical1 = getMST(n,edgeList,refMST.totalCost,currentEdge);
			MST nonCritical2 = getMST(n,edgeList,refMST.totalCost,backEdge);

			if(nonCritical2 != null || nonCritical1 != null) {
				nonCritical.add(currentEdge.get(0));
			}

			iterator.add(currentEdge);
			iterator.add(backEdge);

		}

		result.add(new ArrayList<>(critical));

		critical.forEach(nonCritical::remove);

		result.add(new ArrayList<>(nonCritical));

		return result;


	}

	public MST getMST(int n,List<List<Integer>> edges,int upperBound,List<Integer> startEdge) {


		int totalCost = 0,edgeCount = 0;

		Set<Integer> edgeSet = new HashSet<>();

		DisjointSet<Integer> disjointSet = new DisjointSet<>();

		for(int i = 0;i<n;i++) {
			disjointSet.makeSet(i);
		}

		if(startEdge != null) {

			edgeSet.add(startEdge.get(0));

			disjointSet.union(startEdge.get(1),startEdge.get(2));

			edgeCount++;

			totalCost += startEdge.get(3);

		}

		for(List<Integer> edge : edges) {

			int edgeId = edge.get(0);

			// lies in same set
			if(!disjointSet.find(edge.get(1),edge.get(2))) {

				disjointSet.union(edge.get(1),edge.get(2));
				edgeCount++;
				totalCost += edge.get(3);
				edgeSet.add(edgeId);

				if(edgeCount == n-1) {
					break;
				}

				if(totalCost > upperBound) {
					return null;
				}

			}


		}

		if(upperBound != Integer.MAX_VALUE && totalCost != upperBound) {
			return null;
		}

		return new MST(edgeSet,totalCost);

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

	static class MST {

		Set<Integer> mstEdges;
		int totalCost;

		public MST(Set<Integer> mstEdges, int totalCost)
		{
			this.mstEdges = mstEdges;
			this.totalCost = totalCost;
		}

	}

	public static void main(String[] args) {
		CriticalEdges criticalEdges = new CriticalEdges();

		// Test Case 1
		int n1 = 6;
		int[][] edges1 = {
				{0, 1, 1},
				{1, 2, 1},
				{0, 2, 1},
				{2, 3, 4},
				{3, 4, 2},
				{3, 5, 2},
				{4, 5, 2}
		};
		List<List<Integer>> result1 = criticalEdges.findCriticalAndPseudoCriticalEdges(n1, edges1);
		System.out.println("Critical Edges (Test Case 1): " + result1.get(0)); // Expected Output: [3]
		System.out.println("Pseudo-Critical Edges (Test Case 1): " + result1.get(1)); // Expected Output: [0, 1, 2, 4, 5, 6]

		// Test Case 2
		int n2 = 5;
		int[][] edges2 = {
				{0, 1, 1},
				{1, 2, 1},
				{2, 3, 2},
				{0, 3, 2},
				{0, 4, 3},
				{3, 4, 3},
				{1, 4, 6}
		};
		List<List<Integer>> result2 = criticalEdges.findCriticalAndPseudoCriticalEdges(n2, edges2);
		System.out.println("Critical Edges (Test Case 2): " + result2.get(0)); // Expected Output: [0, 1]
		System.out.println("Pseudo-Critical Edges (Test Case 2): " + result2.get(1)); // Expected Output: [2, 3, 4, 5]

		// Test Case 3
		int n3 = 4;
		int[][] edges3 = {
				{0, 1, 1},
				{1, 2, 1},
				{2, 3, 1},
				{0, 3, 1}
		};
		List<List<Integer>> result3 = criticalEdges.findCriticalAndPseudoCriticalEdges(n3, edges3);
		System.out.println("Critical Edges (Test Case 3): " + result3.get(0)); // Expected Output: []
		System.out.println("Pseudo-Critical Edges (Test Case 3): " + result3.get(1)); // Expected Output: [0, 1, 2, 3]

		// Test Case 4
		int n4 = 4;
		int[][] edges4 = {
				{0, 1, 1},
				{0, 3, 1},
				{0, 2, 1},
				{1, 2, 1},
				{1, 3, 1},
				{2, 3, 1}
		};
		List<List<Integer>> result4 = criticalEdges.findCriticalAndPseudoCriticalEdges(n4, edges4);
		System.out.println("Critical Edges (Test Case 4): " + result4.get(0)); // Expected Output: []
		System.out.println("Pseudo-Critical Edges (Test Case 4): " + result4.get(1)); // Expected Output: [0, 1, 2, 3, 4, 5]
	}


}
