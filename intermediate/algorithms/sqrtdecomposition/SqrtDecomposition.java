package algorithms.sqrtdecomposition;

import java.util.Arrays;

public class SqrtDecomposition
{

	int[] blocks;
	int[] elements;
	int blockSize;
	int sqrt;

	public SqrtDecomposition(int[] arr) {


		this.blockSize = (int) Math.ceil(Math.sqrt(arr.length));
		int size = (int) Math.ceil((double) arr.length / this.blockSize);
		blocks = new int[size+1];
		elements = new int[arr.length+1];

		int blockIndex = 1,blockCounter = 0,blockSum = 0;

		for(int i = 0;i<arr.length;i++) {

			elements[i+1] = arr[i];
			blockCounter++;
			blockSum += arr[i];

			if(blockCounter == blockSize) {
				blocks[blockIndex++] = blockSum;
				blockCounter = 0;
				blockSum = 0;
			}
		}

		if(blockCounter > 0) {
			blocks[blockIndex] = blockSum;
		}


	}

	void pointUpdate(int index,int value) {

		if(elements[index+1] < value)
		{
			blocks[getBlock(index + 1)] += (value - elements[index+1]);
		}
		else {
			blocks[getBlock(index + 1)] -= (elements[index + 1] - value);
		}
		elements[index+1] = value;
	}

	int rangeQuery(int start,int end){

		int resultSum = 0;

		start++;
		end++;

		int startBlock = getBlock(start), endBlock = getBlock(end);

		if(startBlock == endBlock) {
			for(int i = start;i<=end;i++) {
				resultSum += elements[i];
			}
			return resultSum;
		}


		for(int i = startBlock+1;i<endBlock;i++) {
			resultSum += blocks[i];
		}

		for(int i = start;i<= Math.min(this.elements.length-1,startBlock * blockSize);i++) {
			resultSum += elements[i];
		}


		for (int i = (endBlock - 1) * blockSize + 1; i <= end; i++)
		{
			resultSum += elements[i];
		}



		return resultSum;


	}

	int getBlock(int index) {
		int rem = index%blockSize;
		return index / blockSize + (rem == 0 ? 0 : 1);
	}

	class NumArray {

		SqrtDecomposition decomposition;
		public NumArray(int[] nums) {
			SqrtDecomposition sqrtDecomposition = new SqrtDecomposition(nums);
		}

		public void update(int index, int val) {
              decomposition.pointUpdate(index,val);
		}

		public int sumRange(int left, int right) {
              return decomposition.rangeQuery(left,right);
		}
	}

}
