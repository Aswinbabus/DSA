package dataStructures.tree.segmenttree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class LessThanSelf
{
	public List<Integer> countSmaller(int[] nums) {

		int n = nums.length;
		SortedMap<Integer,Integer> map = new TreeMap<>();

		for(int i = 0;i<nums.length;i++) {
			if(!map.containsKey(nums[i])) {
				map.put(nums[i],0);
			}
		}

		int index = 1;
		for(Map.Entry<Integer,Integer> entry : map.entrySet()) {
			map.put(entry.getKey(),index);
			index++;
		}

		FenwickTree fenwickTree = new FenwickTree(map);

		fenwickTree.update(map.get(nums[n-1]),1);

		List<Integer> result = new ArrayList<>();
		for(int i = 0;i<n;i++) {
			result.add(0);
		}

		for(int i = n-2;i>=0;i--) {

			int currIndex = map.get(nums[i]);
			result.set(i, fenwickTree.prefixSum( currIndex - 1));
			fenwickTree.update(currIndex,1);

		}

		return result;

	}

	public class FenwickTree
	{

		int[] bit;
		int n;

		public FenwickTree(Map<Integer,Integer> arr)
		{
			this.n = arr.size();
			bit = new int[n + 1];
		}

		public void update(int index,int value) {


			onUpdate(index,value);


		}

		public void onUpdate(int index, int value)
		{

			bit[index] += value;

			index = index + (index & -index);

			while (index <= n)
			{
				bit[index] += value;
				index = index + (index & -index);
			}

		}

		public int prefixSum(int index)
		{

			int sum = 0;
			while (index > 0) {
				sum += bit[index];
				index -= (index & -index);
			}
			return sum;
		}


	}


	public static void main(String[] args) {
		int[][] testCases = {// Empty array
				{42},              // Single element
				{7, 7, 7, 7},      // All elements the same
				{1, 2, 3, 4, 5},   // Increasing sequence
				{5, 4, 3, 2, 1},   // Decreasing sequence
				{Integer.MAX_VALUE, Integer.MIN_VALUE, 0}, // Large values
				{5, 2, 6, 1},      // Example from earlier
				{-1},              // Example from earlier
				{-1, -1}           // Example from earlier
		};


		for (int[] nums : testCases) {
			LessThanSelf lessThanSelf = new LessThanSelf();
			System.out.println("Input: " + java.util.Arrays.toString(nums));
			System.out.println("Output: " + lessThanSelf.countSmaller(nums));
			System.out.println();
		}
	}


}
