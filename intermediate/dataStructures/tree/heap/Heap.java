package dataStructures.tree.heap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class Heap
{
	List<Integer> heap;
	private static final Function<Integer,Integer> parentFunction = (index) -> (index - 1) / 2;
	private static final Function<Integer,Integer> leftChildFunction = (index) ->  2 * index + 1;
	private static final Function<Integer,Integer> rightChildFunction = (index) -> 2 * index + 2;


	private final BiPredicate<Integer,Integer> minHeap =  (index, parent) -> heap.get(parent) < heap.get(index);
	private final BiPredicate<Integer,Integer> maxHeap = (index, parent) -> heap.get(parent) > heap.get(index);
	private final BiPredicate<Integer,Integer> currHeap;


	public Heap(int[] elements,boolean isMaxHeap) {

		if(isMaxHeap) {
			this.currHeap = maxHeap;
		}
		else {
			this.currHeap = minHeap;
		}
		heap = new ArrayList<>();

		if(elements != null && elements.length > 0)
		{
			for (int i : elements)
			{
				heap.add(i);
			}
			for (int i = elements.length / 2 - 1; i >= 0; i--)
			{
				shiftDown(i);
			}
		}


	}

	public void insert(int element) {

		heap.add(element);
       shiftUp(heap.size()-1);



	}

	public void shiftUp(int index) {

		int parent = parentFunction.apply(index);

		while(index > parent && !currHeap.test(index,parent)) {

			swap(index,parent);
			index = parent;
			parent = parentFunction.apply(index);
		}

	}

	public void shiftDown(int parent)
	{

		int n = heap.size();

		while (parent < n/2)
		{

			int leftChild = leftChildFunction.apply(parent), rightChild = rightChildFunction.apply(parent);

			// no child exists
			if (leftChild >= n && rightChild >= n)
			{
				return;
			}
			// only one child exists
			else if (rightChild >= n)
			{

				if (!currHeap.test(leftChild, parent))
				{
					swap(leftChild, parent);
					parent = leftChild;
				}
				else
				{
					return;
				}

			}
			// we have both childs
			else
			{

				if (!currHeap.test(rightChild, parent) && !currHeap.test(leftChild, parent))
				{

					if (currHeap == minHeap)
					{
						if (heap.get(rightChild) < heap.get(leftChild))
						{
							swap(rightChild, parent);
							parent = rightChild;
						}
						else
						{
							swap(leftChild, parent);
							parent = leftChild;
						}
					}
					else
					{
						if (heap.get(rightChild) > heap.get(leftChild))
						{
							swap(rightChild, parent);
							parent = rightChild;
						}
						else
						{
							swap(leftChild, parent);
							parent = leftChild;
						}
					}

				}
				else if (!currHeap.test(rightChild, parent))
				{
					swap(rightChild, parent);
					parent = rightChild;
				}
				else if(!currHeap.test(leftChild,parent))
				{
					swap(leftChild, parent);
					parent = leftChild;
				}
				else {
					break;
				}

			}

		}
	}

	public int size() {
		return this.heap.size();
	}

	private void swap(int leftIndex, int rightIndex)
	{
		int temp = heap.get(leftIndex);
		heap.set(leftIndex,heap.get(rightIndex));
		heap.set(rightIndex,temp);
	}

	public Integer peekTop() {

		if(!heap.isEmpty())
		{
			return heap.get(0);
		}

		return null;

	}

	public Integer pop() {



		if(!heap.isEmpty()) {

			int element = heap.get(0);

			int lastElement = heap.get(heap.size()-1);

			heap.remove(heap.size()-1);

			if(!heap.isEmpty()) {
			heap.set(0,lastElement);

			shiftDown(0);
			}

			return element;

		}

		return null;

	}

    public static void main(String[] args) {
        // MinHeap test cases
        int[] arr = {5, 3, 8, 1, 2};
        Heap minHeap = new Heap(arr, false);

        System.out.println("Test 1 - Peek Top: " + minHeap.peekTop()); // Expected: 1

        System.out.print("Test 2 - Pop Order: ");
        while (minHeap.peekTop() != null) {
            System.out.print(minHeap.pop() + " ");
        }
        // Expected: 1 2 3 5 8

        // Test 3: Insert after emptying
        minHeap.insert(10);
        minHeap.insert(4);
        System.out.println("\nTest 3 - Peek Top after insert: " + minHeap.peekTop()); // Expected: 4

        // Test 4: Pop single element
        System.out.println("Test 4 - Pop single element: " + minHeap.pop()); // Expected: 4
        System.out.println("Test 4 - Pop next element: " + minHeap.pop()); // Expected: 10

        // Test 5: Pop from empty heap
        System.out.println("Test 5 - Pop from empty: " + minHeap.pop()); // Expected: null
    }

}
