package dataStructures.DisjointSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @param <Node> - Node represents the type of actual object that being stored in the disjoint set
 * Here we are implementing , Quick find variation , which makes union little complex ( For more find operations usecases )
 */
public class DisjointSetWithQuickFind<Node extends Comparable<Node>> implements DisjointSet<Node>
{

	/*
	  Here Set<Node> represents the Group
	  Why i used Map , is to appoint leader of this Group, which will be useful for me while finding
	 */
	private Map<Node,Set<Node>> components;
	private Map<Node,Node> nodeToComponent;

	public DisjointSetWithQuickFind()
	{
		this.components = new HashMap<>();
	}

	void initialize(List<Node> nodes) {
		nodes.forEach(node ->
		{
			components.put(node,new HashSet<>());
			nodeToComponent.put(node,node);

		});
	}

	public void associate(Node nodeA,Node nodeB) {

		/*
		Case 1 : Node a and Node b are not associated with any group
		Sol : Create a new Group , and appoint minimum node as leader, and associate both nodes with this group
		 */
		if(!components.containsKey(nodeA) && !components.containsKey(nodeB)) {

			Set<Node> newComponent = new HashSet<>();
			newComponent.add(nodeA);
			newComponent.add(nodeB);

			Node leader = nodeA,child = nodeB;

			if(leader.compareTo(nodeB) > 0) {
				leader = nodeB;
				child = nodeA;
			}

			nodeToComponent.put(leader,leader);
			nodeToComponent.put(child,leader);
			components.put(leader,newComponent);


		}
		/*
		 Case 2 : One Node present ,and another is missing
		 Sol  : Add the new to group and recompute leader
		 */
		else if(!nodeToComponent.containsKey(nodeA)) {

			components.get(nodeB).add(nodeA);

			// If nodeB is leader, then no need to recompute
			if(nodeB.compareTo(nodeA) > 0) {
                 components.put(nodeA,components.get(nodeB));
				 components.remove(nodeB);
				 components.get(nodeA).forEach(node-> nodeToComponent.put(node,nodeA));
			}
			else {
				nodeToComponent.put(nodeA,nodeB);
			}

		}

		else if(!nodeToComponent.containsKey(nodeB)) {

			components.get(nodeA).add(nodeB);

			// If nodeA is leader, then no need to recompute
			if(nodeA.compareTo(nodeB) > 0) {
				components.put(nodeB,components.get(nodeA));
				components.remove(nodeA);
				components.get(nodeB).forEach(node->nodeToComponent.put(node,nodeB));
			}
			else {
				nodeToComponent.put(nodeB,nodeA);
			}

		}

		/**
		 *  Case 3 : Both are present in different components
		 *  Sol : Join both components and appoint the
		 */
		else {

			Node leader,child = nodeB;

			if(nodeA.compareTo(nodeB) > 0) {
				leader = nodeB;
				child = nodeA;
			}
			else
			{
				leader = nodeA;
			}

			components.get(leader).addAll(components.get(child));
			components.get(child).forEach(node -> nodeToComponent.put(node,leader));
			components.remove(child);

		}

	}

	public boolean checkAssociate(Node a, Node b) {

		return nodeToComponent.get(a).equals(nodeToComponent.get(b));

	}


}
