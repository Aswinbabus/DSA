package dataStructures.DisjointSet;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DisjointSetWithQuickFindTest {

	@Test
	void testDisjointSetWithQuickFind() {
		DisjointSetWithQuickFind<Integer> disjointSet = new DisjointSetWithQuickFind<>();

		// Initialize the disjoint set with nodes
		disjointSet.initialize(Arrays.asList(1, 2, 3, 4, 5));

		// Test association of two nodes
		disjointSet.associate(1, 2);
		assertTrue(disjointSet.checkAssociate(1, 2), "1 and 2 should be in the same set");

		// Test association of another node
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
		assertThrows(NullPointerException.class, () -> disjointSet.checkAssociate(8, 9), "Should throw exception for uninitialized nodes");

		// Edge Case: Circular association
		disjointSet.associate(4, 5);
		assertTrue(disjointSet.checkAssociate(1, 5), "1 and 5 should now be in the same set due to circular association");

		// Edge Case: Large number of nodes
		disjointSet.initialize(Arrays.asList(10, 11, 12, 13, 14, 15));
		disjointSet.associate(10, 11);
		disjointSet.associate(12, 13);
		disjointSet.associate(11, 12);
		assertTrue(disjointSet.checkAssociate(10, 13), "10 and 13 should be in the same set");
		assertFalse(disjointSet.checkAssociate(10, 14), "10 and 14 should not be in the same set");
	}

	public static void main(String[] args)
	{
		DisjointSetWithQuickFindTest test = new DisjointSetWithQuickFindTest();
		test.testDisjointSetWithQuickFind();
		System.out.println("All tests passed!");
	}
}