package dataStructures.tree.fenwicktree;

import java.util.Arrays;

public class MaxHappiness
{
	public long maximumHappinessSum(int[] happiness, int k) {

		Arrays.sort(happiness);

		int delta = 1,result = 0,currIndex = happiness.length - 1;

		while(k > 0) {

			if(happiness[currIndex] - delta >= 0)
			{
				result += happiness[currIndex] - delta;
			}

			k--;
			delta++;

		}

		return result;


	}
}
