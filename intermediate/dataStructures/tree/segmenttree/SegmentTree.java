package dataStructures.tree.segmenttree;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

public class SegmentTree<T>
{

	Node<T>[] tree;
	int n;
	BinaryOperator<T> operator;
	T identity;


	public SegmentTree(int n, List<T> elements,BinaryOperator<T> operator,T identity) {

		this.n = n;
		tree = new Node[4*n];
		this.operator = operator;
		buildTree(1,n,1,elements);
		this.identity = identity;


	}

	private T buildTree(int start, int end,int curr, List<T> elements)
	{

		if(start == end) {
			tree[curr] = new Node<T>(start,end,elements.get(start-1));
		}

		else {
			int mid = start + (end - start) / 2;
			T val = operator.apply( buildTree(start,mid,2*curr,elements), buildTree(mid+1,end,2*curr+1,elements) );
			tree[curr] = new Node(start,end,val);
		}

        return tree[curr].value;
	}

	public void pointUpdate(int index,T value) {
           onPointUpdate(1,index,value);
	}

	public void rangeUpdate(int from,int to,T value) {
		onRangeUpdate(1,from,to,value);
	}

	private void onRangeUpdate(int curr,int from,int to,T value) {


		Node<T> node = tree[curr];

		// base case - leaf and the current index is completely inclusive on given range
		if(node.start == node.end && node.start <= from && node.end >= to) {
			node.value = operator.apply(node.value,value);
		}

		else{

			int mid = node.start + (node.end - node.start) / 2;

			// range is inclusive of first half
			if(node.start <= from && to <= mid) {
				onRangeUpdate(2*curr,from,to,value);
			}
			// range is inclusive of second half
			else if(mid+1 <= from && to <= node.end) {
				onRangeUpdate(2*curr+1,from,to,value);
			}
			// range is in both halves
			else {
				onRangeUpdate(2*curr,from,mid,value);
				onRangeUpdate(2*curr+1,mid+1,to,value);
			}

			tree[curr].value = operator.apply(tree[2*curr].value,tree[2*curr+1].value);
		}


	}

	private void onPointUpdate(int curr,int index,T value) {

		Node<T> node = tree[curr];

		if(node.start == index && node.end == index) {
			node.value = operator.apply(node.value,value);
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

	public T query(int start,int end) {
		return onQuery(1,start,end);
	}

	private T onQuery(int curr,int from,int to) {

		Node<T> node = tree[curr];

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




}

class Node<T> {

	int start;
	int end;
	T value;

	public Node(int start, int end, T value)
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
