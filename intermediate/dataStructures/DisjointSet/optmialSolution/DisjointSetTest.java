package dataStructures.DisjointSet.optmialSolution;

import dataStructures.DisjointSet.optmialSolution.DisjointSet;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DisjointSetTest {

	@Test
	void testDisjointSet() {
		DisjointSet<Integer> disjointSet = new DisjointSet<>();

		// Test associating two nodes
		disjointSet.associate(1, 2);
		assertTrue(disjointSet.checkAssociate(1, 2), "1 and 2 should be in the same set");

		// Test associating another pair of nodes
		disjointSet.associate(3, 4);
		assertTrue(disjointSet.checkAssociate(3, 4), "3 and 4 should be in the same set");

		// Test nodes in different sets
		assertFalse(disjointSet.checkAssociate(1, 3), "1 and 3 should not be in the same set");

		// Merge two sets
		disjointSet.associate(2, 3);
		assertTrue(disjointSet.checkAssociate(1, 4), "1 and 4 should now be in the same set");

		// Edge Case: Associating nodes already in the same set
		disjointSet.associate(1, 4);
		assertTrue(disjointSet.checkAssociate(1, 4), "1 and 4 should still be in the same set");

		// Edge Case: Associating a node with itself
		disjointSet.associate(5, 5);
		assertTrue(disjointSet.checkAssociate(5, 5), "A node should always be associated with itself");

		// Edge Case: Associating a node not initialized
		disjointSet.associate(6, 7);
		assertTrue(disjointSet.checkAssociate(6, 7), "6 and 7 should now be in the same set");
		assertFalse(disjointSet.checkAssociate(1, 6), "1 and 6 should not be in the same set");

		// Edge Case: Checking association for uninitialized nodes
		assertFalse(disjointSet.checkAssociate(8, 9), "Uninitialized nodes should not be associated");

		// Edge Case: Circular association
		disjointSet.associate(4, 5);
		assertTrue(disjointSet.checkAssociate(1, 5), "1 and 5 should now be in the same set due to circular association");

		// Edge Case: Large number of nodes
		disjointSet.associate(10, 11);
		disjointSet.associate(12, 13);
		disjointSet.associate(11, 12);
		assertTrue(disjointSet.checkAssociate(10, 13), "10 and 13 should be in the same set");
		assertFalse(disjointSet.checkAssociate(10, 14), "10 and 14 should not be in the same set");

		// Edge Case: Deep hierarchy
		disjointSet.associate(14, 15);
		disjointSet.associate(15, 16);
		disjointSet.associate(16, 17);
		assertTrue(disjointSet.checkAssociate(14, 17), "14 and 17 should be in the same set");
	}
}
