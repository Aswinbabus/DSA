package dataStructures.DisjointSet;

import java.util.Comparator;

public interface DisjointSet<Node extends Comparable<Node>>
{

	void associate(Node a,Node b);
	boolean checkAssociate(Node a, Node b);

}
