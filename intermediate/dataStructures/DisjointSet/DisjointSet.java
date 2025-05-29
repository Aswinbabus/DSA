package dataStructures.DisjointSet;

import java.util.Comparator;

public interface DisjointSet<T>
{

	void associate(T a,T b);
	boolean checkAssociate(T a, T b);

}
