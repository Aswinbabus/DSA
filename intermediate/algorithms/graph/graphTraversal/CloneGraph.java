package algorithms.graph.graphTraversal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// using BFS
public class CloneGraph
{
	public Node cloneGraph(Node node) {

		if(node ==null)
			return null;

		Node root = new Node();
		Map<Node,Node> visited = new HashMap<>();
		Queue<Node[]> bfs = new ArrayDeque<>();

		bfs.add(new Node[]{root,node});

		while(!bfs.isEmpty()) {

			Node[] arr = bfs.poll();

			Node currNewNode = arr[0];
			Node copyNode = arr[1];

			currNewNode.val = copyNode.val;
			for (Node neighbor : copyNode.neighbors)
			{
				if(!visited.containsKey(neighbor))
				{
					Node newNeighbourNode = new Node();
					visited.put(neighbor,newNeighbourNode);
					currNewNode.neighbors.add(newNeighbourNode);
					bfs.add(new Node[] { newNeighbourNode, neighbor });
				}
				else {
					currNewNode.neighbors.add(visited.get(neighbor));
				}
			}

			visited.put(copyNode,currNewNode);


		}

		return root;

	}
}
class Node {
	public int val;
	public List<Node> neighbors;
	public Node() {
		val = 0;
		neighbors = new ArrayList<Node>();
	}
	public Node(int _val) {
		val = _val;
		neighbors = new ArrayList<Node>();
	}
	public Node(int _val, ArrayList<Node> _neighbors) {
		val = _val;
		neighbors = _neighbors;
	}
}