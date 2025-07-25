package algorithms.graph.graphTraversal;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class LongestCycle
{

	public int longestCycle(int[] edges) {


		int n = edges.length,longestCycle = -1;

		int[] indegrees = new int[n];

		for(int i = 0;i< edges.length;i++) {

			// making it not reachable
			if(edges[i] == -1) {
				indegrees[i] = -1;
			}
			// checking for self loop
			else if(i == edges[i]) {
				longestCycle = 1;
				indegrees[i] = -1;
			}
			else if(indegrees[edges[i]] == 0)
			{
				if(indegrees[edges[i]] == 0)
				{
					indegrees[edges[i]] = 1;
				}
				// edge to non-cyclic vertice , so the current vertice also non-cyclic
				else {
					indegrees[i] = -1;
				}
			}


		}

		for(int i = 0;i<edges.length;i++) {

			if(indegrees[i] > 0) {

				int curr = i;
				LinkedHashSet<Integer> set = new LinkedHashSet<>();
				// iterate until node is non-cyclic
				do {
					set.add(curr);
					indegrees[curr] = -1;
					curr = edges[curr];
				}while(curr != -1 && indegrees[curr] > 0 && !set.contains(curr));

				// cycle Detected
				if(set.contains(curr)) {
					int cycleLength = set.size();
					for (Integer integer : set)
					{
						if (integer == curr)
						{
							break;
						}
						cycleLength--;
					}
					longestCycle = Math.max(longestCycle,cycleLength);
				}

			}

		}
		System.out.println(Arrays.toString(indegrees));

		return longestCycle;


	}

	public static void main(String[] args)
	{
		System.out.println(new LongestCycle().longestCycle(new int[]{3,3,4,2,3}));
	}
}
