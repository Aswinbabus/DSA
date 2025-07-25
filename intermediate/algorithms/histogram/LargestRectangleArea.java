package algorithms.histogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;

public class LargestRectangleArea
{
	public int largestRectangleArea(int[] heights) {

		List<Integer> arr = new ArrayList<>();
		for(int height : heights) {
			arr.add(height);
		}
		SegmentTree segmentTree = new SegmentTree(heights.length,arr,Math::min,-1);

		return getMaxArea(0,heights.length-1,heights,segmentTree);


	}

	private int getMaxArea(int start, int end, int[] heights, SegmentTree segmentTree)
	{

		if(start < 0 || end >= heights.length || start > end) {
			return Integer.MIN_VALUE;
		}

		if(start == end) {
			return heights[start];
		}

		int minIndex = segmentTree.query(start,end);

		int area = heights[minIndex] * (end - start + 1);

		return Math.max(area,Math.max(getMaxArea(start,minIndex-1,heights,segmentTree),getMaxArea(minIndex+1,end,heights,segmentTree)));

	}

	public static void main(String[] args) {
		LargestRectangleArea solver = new LargestRectangleArea();

		// Test case 1
		int[] heights1 = {2,1,5,6,2,3};
		int result1 = solver.largestRectangleArea(heights1);
		System.out.println("Test 1 Output: " + result1); // Expected: 10

		// Test case 2
		int[] heights2 = {175, 55, 169, 47, 97, 27, 118, 162, 244, 126, 160, 154, 18, 89, 135, 190, 39, 187, 219, 172, 59, 53, 242, 125, 155, 70, 107, 105, 176, 171, 113, 119, 248, 131, 89, 147, 196, 63, 168, 108, 48, 196, 80, 68, 111, 152, 231, 8, 198, 144, 12, 111, 231, 145, 100, 175, 27, 231, 161, 218, 11, 107, 69, 229, 36, 0, 64, 16, 89, 70, 117, 152, 23, 104, 107, 60, 228, 85, 250, 54, 38, 93, 114, 51, 27, 189, 139, 46, 125, 218, 106, 57, 158, 21, 61, 197, 196, 103, 199, 86, 28, 181, 90, 109, 38, 75, 230, 20, 5, 7, 90, 118, 150, 171, 130, 173, 145, 135, 201, 73, 108, 164, 229, 159, 117, 96, 187, 164, 72, 116, 176, 76, 241, 45, 163, 235, 66, 82, 184, 93, 234, 217, 116, 190, 89, 61, 130, 3, 60, 186, 174, 223, 185, 211, 48, 131, 181, 160, 157, 87, 133, 234, 102, 92, 132, 85, 51, 168, 122, 123, 51, 180, 156, 63, 0, 67, 89, 197, 222, 216, 134, 135, 75, 122, 48, 159, 198, 6, 81, 129, 107, 158, 102, 83, 192, 175, 205, 240, 183, 110, 159, 175, 188, 182, 155, 101, 97, 129, 219, 209, 234, 210, 16, 250, 63, 19, 101, 33, 42, 40, 177, 214, 73, 17, 24, 141, 92, 10, 158, 111, 28, 170, 141, 14, 132, 12, 225, 16, 239, 71, 44, 152, 120, 38, 58, 66, 55, 68, 178, 4, 33, 186, 31, 8, 240, 240, 219, 21, 61, 145, 84, 198, 114, 132, 166, 89, 153, 3, 99, 54, 219, 80, 87, 58, 123, 162, 144, 90, 4, 58, 184, 94, 215, 173, 147, 149, 10, 27, 218, 11, 84, 123, 246, 198, 138, 240, 145, 147, 140, 104, 161, 184, 132, 115, 46, 32, 63, 157, 72, 92, 140, 213, 81, 24, 61, 54, 137, 99, 185, 202, 12, 218, 74, 159, 119, 79, 239, 153, 214, 100, 74, 221, 187, 123, 14, 50, 8, 69, 55, 18, 234, 181, 106, 38, 162, 123, 157, 210, 149, 115, 178, 83, 103, 33, 57, 111, 71, 152, 195, 215, 156, 61, 246, 27, 183, 117, 29, 201, 102, 147, 127, 166, 84, 47, 72, 149, 218, 80, 23, 198, 201, 244, 247, 57, 131, 191, 99, 193, 150, 209, 145, 242, 226, 136, 164, 156, 124, 223, 48, 238, 250, 86, 42, 165, 244, 76, 142, 206, 218, 160, 227, 243, 66, 168, 137, 170, 184, 21, 31, 170, 208, 77, 7, 73, 1, 83, 199, 198, 183, 87, 13, 142, 175, 99, 229, 117, 204, 240, 195, 129, 216, 250, 192, 112, 247, 94, 248, 143, 217, 113, 29, 79, 194, 67, 112, 7, 79, 44, 223, 71, 240, 100, 108, 203, 79, 112, 170, 178, 67, 124, 238, 39, 154, 198, 229, 158, 58, 203, 159, 2, 152, 48, 138, 248, 168, 184, 168, 143, 25, 11, 165, 47, 136, 129, 231, 60, 62, 4, 190, 166};
		int result2 = solver.largestRectangleArea(heights2);
		System.out.println("Test 2 Output: " + result2); // Expected: 1944
	}



}
class SegmentTree
{

	Node[] tree;
	int n;
	IntBinaryOperator operator;
	int identity;
	List<Integer> elements;

	public SegmentTree(int n, List<Integer> elements,IntBinaryOperator operator,int identity) {

		this.elements = elements;
		this.n = n;
		tree = new Node[4*n];
		this.operator = operator;
		buildTree(1,n,1,elements);
		this.identity = identity;


	}

	private int buildTree(int start, int end,int curr, List<Integer> elements)
	{

		if(start == end) {
			tree[curr] = new Node(start,end,elements.get(start-1),start-1);
		}

		else {
			int mid = start + (end - start) / 2;
			int leftIndex = buildTree(start,mid,2*curr,elements);
			int rightIndex = buildTree(mid+1,end,2*curr+1,elements);

			if(operator.applyAsInt(elements.get(leftIndex),elements.get(rightIndex)) == elements.get(leftIndex)) {
				tree[curr] = new Node(start,end,elements.get(leftIndex),leftIndex);
			}
			else {
				tree[curr] = new Node(start,end,elements.get(rightIndex),rightIndex);
			}

		}

		return tree[curr].index;


	}

	public int query(int start,int end) {
		return onQuery(1,start+1,end+1);
	}

	private int onQuery(int curr,int from,int to) {

		Node node = tree[curr];

		// No overlap
		if(node.start > to || node.end < from) {
			return this.identity;
		}

		// complete overlap
		if(from <=node.start && to >= node.end) {
			return node.index;
		}

		int left = onQuery(2*curr,from,to);
		int right = onQuery(2*curr+1,from,to);

		if(left == -1) {
			return right;
		}
		if(right == -1) {
			return  left;
		}

		if(operator.applyAsInt(elements.get(left),elements.get(right)) == elements.get(left)) {
			return left;
		}
		else{
			return right;
		}



	}

}

class Node {

	int start;
	int end;
	int value;
    int index;

	public Node(int start, int end, int value,int index)
	{
		this.start = start;
		this.end = end;
		this.value = value;
		this.index = index;
	}


	@Override
	public String toString()
	{
		return "Node{" + "start=" + start + ", end=" + end + ", value=" + value + '}';
	}
}
