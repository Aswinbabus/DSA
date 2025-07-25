package dataStructures.tree.segmenttree;

import java.util.Arrays;

public class SegmentTreeWithLazyPropagation
{

	Node[] tree;
	int[] lazyIncrements;


	public SegmentTreeWithLazyPropagation(int[] elements) {

		tree = new Node[elements.length * 4];
		lazyIncrements = new int[elements.length * 4];

		buildTree(1,0,elements.length-1,elements);

	}

	private void buildTree(int currIndex,int startIdx,int endIdx,int[] elements)
	{

		// single element
		if(startIdx == endIdx) {

			tree[currIndex] = new Node(startIdx,endIdx,0);

		}
		else {

			int mid = (endIdx - startIdx) / 2 + startIdx;
			//left
			buildTree(2*currIndex,startIdx,mid,elements);
			// right
			buildTree(2*currIndex + 1,mid + 1,endIdx,elements);

			int value = tree[2*currIndex].value + tree[2*currIndex+1].value;

			tree[currIndex] = new Node(startIdx,endIdx,value);

		}


	}

	void rangeUpdate(int start,int end,int value) {
		onRangeUpdate(1,start,end,value);
	}

	void onRangeUpdate(int currIndex,int start,int end,int value) {

		Node currNode = tree[currIndex];

		// current range is completely inclusive
		if(start <= currNode.start && currNode.end <= end ) {
			lazyIncrements[currIndex] += value;
		}
		// out of range
		else if(currNode.end < start || currNode.start > end) {
			return;
		}
		// partial coverage
		else {

			onRangeUpdate(2*currIndex,start,end,value);
			onRangeUpdate(2*currIndex+1,start,end,value);

		}

	}

	int queryRange(int start,int end) {
		return onQueryRange(1,start,end,0);
	}

	private int onQueryRange(int currIndex, int start, int end,int lazyIncrement)
	{

		 Node currNode = tree[currIndex];

		 // completely inclusive
		 if(start <= currNode.start && currNode.end <= end) {
			 if(lazyIncrements[currIndex] != 0) {
				 currNode.value += lazyIncrements[currIndex];
			 }

			 if(lazyIncrement != 0) {
				 currNode.value += lazyIncrement;
			 }
			 return currNode.value;

		 }
		 // out of range
		 else if(currNode.end < start || currNode.start > end) {
			 lazyIncrements[currIndex] += lazyIncrement;
			 return 0;
		 }
		 // partial coverage
		 else {

			 int lazyVal = lazyIncrements[currIndex] + lazyIncrement;

			 lazyIncrements[currIndex] = 0;

			 return onQueryRange(2*currIndex,start,end,lazyVal) + onQueryRange(2*currIndex+1,start,end,lazyVal);

		 }


	}


	class Node {

		int start;
		int end;
		int value;


		public Node(int start, int end, int value)
		{
			this.start = start;
			this.end = end;
			this.value = value;
		}

		@Override
		public String toString()
		{
			return "Node{" + "start=" + start + ", end=" + end + ", value=" + value + '}';
		}
	}

}
