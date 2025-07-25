package dataStructures.tree.fenwicktree;

import java.util.Arrays;

public class FenwickTree
{

	int[] bit;
	int[] arr;
	int n;

	public FenwickTree(int[] arr)
	{
		this.arr = Arrays.copyOf(arr,arr.length);
		this.n = arr.length;
		bit = new int[n + 1];

		// building the initial fenwick tree
		for (int i = 0; i < n; i++)
		{
			onUpdate(i + 1, arr[i]);
		}

	}

	public void update(int index,int value) {


		int diff = 0;
		if(arr[index] > value) {
			diff = -(arr[index] - value);
		}
		else {
			diff = value - arr[index];
		}
		arr[index] += diff;

		onUpdate(index+1,diff);


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

	public int rangeQuery(int start, int end)
	{
		return prefixSum(end+1) - prefixSum(start);
	}

}
