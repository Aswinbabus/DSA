package dataStructures.tree.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class MinHeap
{
	List<ListNode> heap;
	private static final Function<Integer,Integer> leftChildFunction = (index) ->  2 * index + 1;
	private static final Function<Integer,Integer> rightChildFunction = (index) -> 2 * index + 2;



	public MinHeap(ListNode[] list) {


		heap =new ArrayList<>();

		if(list != null)
		{
			for (ListNode listNode : list)
			{
				if(listNode!=null)
				{
					heap.add(listNode);
				}
			}

			int n = heap.size();
			for (int i = n / 2 - 1; i >= 0; i--)
			{
				shiftDown(i);
			}

		}




	}

	public void shiftDown(int index) {

		int n = heap.size();
		while(index < n / 2) {

			int leftChild = leftChildFunction.apply(index);
			int rightChild = rightChildFunction.apply(index);

			// both child exists
			if(leftChild < n && rightChild < n) {

				if(heap.get(index).val > heap.get(leftChild).val && heap.get(index).val > heap.get(rightChild).val) {

					if(heap.get(leftChild).val > heap.get(rightChild).val) {
						swap(rightChild,index);
						index = rightChild;
					}
					else {
						swap(leftChild,index);
						index = leftChild;
					}

				}
				else if(heap.get(index).val > heap.get(leftChild).val) {
					swap(leftChild,index);
					index = leftChild;
				}
				else if(heap.get(index).val > heap.get(rightChild).val) {
					swap(rightChild,index);
					index = rightChild;
				}
				else {
					break;
				}


			}
			else if(rightChild >= n && heap.get(index).val > heap.get(leftChild).val) {
				swap(leftChild,index);
				index = leftChild;
			}
			else {
				break;
			}

		}

	}

	private void swap(int leftIndex, int rightIndex)
	{
		ListNode temp = heap.get(leftIndex);
		heap.set(leftIndex,heap.get(rightIndex));
		heap.set(rightIndex,temp);
	}

	public ListNode pop() {

		ListNode val = null;

		if(!heap.isEmpty()) {

			ListNode topNode = heap.get(0);
			val = topNode;

			if(topNode.next != null) {
				heap.set(0,topNode.next);
				shiftDown(0);
			}
			else {

				if(heap.size() > 1) {
					ListNode lastElement = heap.get(heap.size()-1);
					heap.remove(heap.size()-1);
					heap.set(0,lastElement);
					shiftDown(0);
				}
				else {

					heap.clear();

				}

			}


		}

		return val;

	}

}

class ListNode {
	 int val;
     ListNode next;
	 ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
