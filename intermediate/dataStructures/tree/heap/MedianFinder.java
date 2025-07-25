package dataStructures.tree.heap;

public class MedianFinder
{

	    Heap minHeap,maxHeap;
		int size = 0;

		public MedianFinder() {

			minHeap = new Heap(null,false);
			maxHeap = new Heap(null,true);

		}

		public void addNum(int num) {


             double median = findMedian();

			 if(median == -1) {
				 maxHeap.insert(num);
			 }
			 else if(median > num) {
				 maxHeap.insert(num);
			 }
			 else {
				 minHeap.insert(num);
			 }

			size++;
			 balanceHeaps();

		}

		private void balanceHeaps()
		{

			if(maxHeap.size() - minHeap.size() == 2) {
				minHeap.insert(maxHeap.pop());
			}
			else if(minHeap.size() - maxHeap.size() == 2) {
				maxHeap.insert(minHeap.pop());
			}

		}

		public double findMedian() {

				if(size == 0) return -1;
				else if(size == 1) return maxHeap.peekTop();


				if(size % 2 == 0) {
					return (double) (minHeap.peekTop() + maxHeap.peekTop()) / 2;
				}
				else {

					if(maxHeap.size() > minHeap.size()) {
						return maxHeap.peekTop();
					}
					else {
						return minHeap.peekTop();
					}

				}

			}

		public static void main(String[] args) {
			MedianFinder mf = new MedianFinder();

			// ["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
			// [[], [1], [2], [], [3], []]

			mf.addNum(1);
			mf.addNum(2);
			System.out.println("findMedian: " + mf.findMedian()); // Expected: 1.5
			mf.addNum(3);
			System.out.println("findMedian: " + mf.findMedian()); // Expected: 2.0
		}

}
