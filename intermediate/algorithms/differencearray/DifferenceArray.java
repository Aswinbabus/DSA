package algorithms.differencearray;

import java.util.Arrays;

public class DifferenceArray
{
	long[] diff;
	int n;

	public DifferenceArray(int[] elements) {

		this.n = elements.length;
		if(this.n > 0)
		{
			diff = new long[elements.length];
			diff[0] = elements[0];

			for(int i = 1;i<elements.length;i++) {
                 diff[i] = elements[i] - elements[i-1];
			}

		}

	}

	public void update(int start,int end,int value) {

		// to check range bounds
		if(start < n) {
			diff[start] += value;
		}

		if(end+1 < n) {
			diff[end+1] -= value;
		}

	}

	public long[] reconstruct() {


		long[] elements = new long[n];

		elements[0] = diff[0];

		for(int i = 1;i<n;i++) {
			elements[i] = elements[i-1] + diff[i];
		}

		return elements;

	}
}
