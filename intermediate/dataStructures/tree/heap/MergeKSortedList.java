package dataStructures.tree.heap;

public class MergeKSortedList
{
	public ListNode mergeKLists(ListNode[] lists) {

		MinHeap minHeap = new MinHeap(lists);

		ListNode head = new ListNode();
		ListNode curr = head;

		ListNode val = minHeap.pop();
		while(val != null) {

			curr.next = val;
			curr = val;
			val = minHeap.pop();

		}

		return head.next;



	}

	public static void main(String[] args) {
		MergeKSortedList merger = new MergeKSortedList();

		// Test case 1
		ListNode[] lists1 = new ListNode[] {
			buildList(new int[]{1,4,5}),
			buildList(new int[]{1,3,4}),
			buildList(new int[]{2,6})
		};
		ListNode result1 = merger.mergeKLists(lists1);
		System.out.print("Test 1 Output: ");
		printList(result1); // Expected: 1 1 2 3 4 4 5 6

		// Test case 2
		ListNode[] lists2 = new ListNode[] {};
		ListNode result2 = merger.mergeKLists(lists2);
		System.out.print("Test 2 Output: ");
		printList(result2); // Expected: (empty)

		// Test case 3
		ListNode[] lists3 = new ListNode[] {null};
		ListNode result3 = merger.mergeKLists(lists3);
		System.out.print("Test 3 Output: ");
		printList(result3); // Expected: (empty)
	}

	// Helper to build a linked list from array
	private static ListNode buildList(int[] arr) {
		ListNode dummy = new ListNode();
		ListNode curr = dummy;
		for (int v : arr) {
			curr.next = new ListNode(v);
			curr = curr.next;
		}
		return dummy.next;
	}

	// Helper to print a linked list
	private static void printList(ListNode node) {
		while (node != null) {
			System.out.print(node.val);
			if (node.next != null) System.out.print(" ");
			node = node.next;
		}
		System.out.println();
	}
}
