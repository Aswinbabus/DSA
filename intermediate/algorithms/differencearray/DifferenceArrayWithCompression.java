package algorithms.differencearray;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DifferenceArrayWithCompression
{
	Map<Integer,Integer> compressedDiffArr;
	int n;

	public DifferenceArrayWithCompression()
	{
			compressedDiffArr = new TreeMap<>();
	}

	public void update(int start,int end,int value) {

		compressedDiffArr.merge(start,value,Integer::sum);

		compressedDiffArr.merge(end,-value,Integer::sum);

		this.n = Math.max(this.n,end);

	}

	public boolean validateBounds(int capacity) {

		int delta = 0;

		for(Map.Entry<Integer,Integer> entry : compressedDiffArr.entrySet()) {

			delta += entry.getValue();

			if(delta > capacity) {
				return false;
			}

		}

		return true;

	}
}
