package dataStructures.tree.segmenttree;

import java.util.Arrays;

public class CountOfRangeSum
{


	public int countRangeSum(int[] nums, int lower, int upper)
	{

		int n = nums.length;
		int resultCount = 0;
		long[] prefixSum = new long[n+1];

		prefixSum[0] = 0;

		// step 1 - computing prefix sum
		for(int i = 1;i<=n;i++) {
			prefixSum[i] = prefixSum[i-1] + nums[i-1];
		}

		return mergeSort(prefixSum,0,n,lower,upper);

	}

	private int mergeSort(long[] prefixSum,int startIndex,int endIndex,int lower,int upper)
	{


		    if(startIndex > endIndex) {
				return 0;
			}

			if(startIndex == endIndex) {

				return (prefixSum[startIndex] >= lower && prefixSum[startIndex] <= upper) ? 1 : 0;

			}

			int rangeCount = 0;

            int mid = startIndex + (endIndex-startIndex) / 2;

			rangeCount += mergeSort(prefixSum,startIndex,mid,lower,upper);
			rangeCount += mergeSort(prefixSum,mid+1,endIndex,lower,upper);

			long[] arr = new long[endIndex - startIndex + 1];

			merge(prefixSum,arr,startIndex,endIndex,mid);

			for(int i = startIndex,counter = 0;i<=endIndex;i++) {
				prefixSum[i] = arr[counter++];
			}

			return rangeCount;

	}

	void merge(long[] prefixSum,long[] arr,int startIndex,int endIndex,int mid) {

		int leftPointer = startIndex,rightPointer = mid + 1;
		int index = 0;
		while(leftPointer <= mid && rightPointer <=endIndex) {

			if(prefixSum[leftPointer] <= prefixSum[rightPointer]) {
				arr[index++] = prefixSum[leftPointer++];
			}
			else {
				arr[index++] = prefixSum[rightPointer++];
			}

		}

		while(leftPointer <= mid) {
			arr[index++] = prefixSum[leftPointer++];
		}

		while(rightPointer <= endIndex) {
			arr[index++] = prefixSum[rightPointer++];
		}

	}

	public static void main(String[] args) {
		CountOfRangeSum crs = new CountOfRangeSum();

		// Test case 1
		int[] nums1 = {-2, 5, -1};
		int lower1 = -2, upper1 = 2;
		System.out.println(crs.countRangeSum(nums1, lower1, upper1)); // Output: 3

				// Edge case: all zeros, wide range
				int[] nums3 = new int[5];
				int lower3 = -5, upper3 = 5;
				System.out.println(crs.countRangeSum(nums3, lower3, upper3)); // Output: 15

		// Test case 2
		int[] nums2 = {0};
		int lower2 = 0, upper2 = 0;
		System.out.println(crs.countRangeSum(nums2, lower2, upper2)); // Output: 1



		// Edge case: single element, lower > upper (no valid range)
		int[] nums4 = {1};
		int lower4 = 2, upper4 = 1;
		System.out.println(crs.countRangeSum(nums4, lower4, upper4)); // Output: 0

		// Edge case: all negative numbers, range excludes all
		int[] nums5 = {-100, -200, -300};
		int lower5 = 0, upper5 = 10;
		System.out.println(crs.countRangeSum(nums5, lower5, upper5)); // Output: 0

		// Edge case: large values at integer boundary
		int[] nums6 = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
		int lower6 = Integer.MIN_VALUE, upper6 = Integer.MAX_VALUE;
		System.out.println(crs.countRangeSum(nums6, lower6, upper6)); // Output: 6

		// Edge case: minimum allowed length
		int[] nums7 = {12345};
		int lower7 = 12345, upper7 = 12345;
		System.out.println(crs.countRangeSum(nums7, lower7, upper7)); // Output: 1
	}

}
