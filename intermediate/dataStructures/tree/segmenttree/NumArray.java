package dataStructures.tree.segmenttree;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
public class NumArray
{
	Node[] tree;
	BinaryOperator<Integer> operator;
	int identity = 0;

	public NumArray(int[] nums) {
		tree = new Node[nums.length*4];
		operator = Integer::sum;
		buildTree(1,nums.length,1,nums);
	}

	public void update(int index, int val) {
		onPointUpdate(1,index+1,val);
	}

	public int sumRange(int left, int right) {
		return onQuery(1,left+1,right+1);
	}

	private void onPointUpdate(int curr,int index,int value) {

		Node node = tree[curr];

		if(node.start == index && node.end == index) {
			node.value = value;
		}

		else {

			int mid = node.start + (node.end-node.start) / 2;

			if(index > mid) {
				onPointUpdate(2*curr+1,index,value);
				node.value = operator.apply(tree[2*curr].value,tree[2*curr+1].value);
			}
			else {
				onPointUpdate(2*curr,index,value);
				node.value = operator.apply(tree[2*curr].value,tree[2*curr+1].value);
			}

		}


	}


	private int buildTree(int start, int end,int curr, int[] elements)
	{

		if(start == end) {
			tree[curr] = new Node(start,end,elements[start-1]);
		}

		else {
			int mid = start + (end - start) / 2;
			int val = operator.apply( buildTree(start,mid,2*curr,elements), buildTree(mid+1,end,2*curr+1,elements) );
			tree[curr] = new Node(start,end,val);
		}

		return tree[curr].value;
	}

	private int onQuery(int curr,int from,int to) {

		Node node = tree[curr];

		// No overlap
		if(node.start > to || node.end < from) {
			return this.identity;
		}

		// complete overlap
		if(from <=node.start && to >= node.end) {
			return node.value;
		}

		// partial overlap
		return operator.apply(onQuery(2*curr,from,to),onQuery(2*curr+1,from,to));


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

	public static void main(String[] args) {
		int[] arr = {1, 3, 5};
		NumArray numArray = new NumArray(arr);

		System.out.println(Arrays.toString(numArray.tree));
		// sumRange(0, 2)
		System.out.println("sumRange(0, 2): " + numArray.sumRange(0, 2)); // Expected: 9

		// update(1, 2)
		numArray.update(1, 2);

		// sumRange(0, 2)
		System.out.println("sumRange(0, 2): " + numArray.sumRange(0, 2)); // Expected: 8
	}
}





/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(index,val);
 * int param_2 = obj.sumRange(left,right);
 */
