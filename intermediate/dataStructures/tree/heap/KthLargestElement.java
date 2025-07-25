package dataStructures.tree.heap;

public class KthLargestElement
{

	public int findKthLargest(int[] nums, int k) {
           Heap maxHeap = new Heap(nums,true);

		   for(int i = 0;i<k-1;i++) {
			   System.out.println(maxHeap.pop());
		   }

		   return maxHeap.peekTop();
	}

	public static void main(String[] args)
	{
		KthLargestElement k = new KthLargestElement();
		System.out.println(k.findKthLargest(new int[]{3,2,3,1,2,4,5,5,6,7,7,8,2,3,1,1,1,10,11,5,6,2,4,7,8,5,6},2));
	}

}
