package algorithms.graph.topologicalSort;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LoudAndRich
{
	public int[] loudAndRich(int[][] richer, int[] quiet) {

		Map<Integer, Set<Integer>> riches = new HashMap<>();
		int n = quiet.length;

		boolean[] flattened = new boolean[n];

		for(int[] arr : richer) {
			riches.computeIfAbsent(arr[1],(k) -> new HashSet<>()).add(arr[0]);
		}

		// flatten
		for(Integer people : riches.keySet()) {

			if(!flattened[people]) {
				flatten(people,riches,flattened);
			}

		}

		int[] result = new int[n];

		for(int i = 0;i<n;i++) {

			Set<Integer> richerPeople = riches.get(i);

			// initializing with its own values
			result[i] = quiet[i];

			if(richerPeople != null && !richerPeople.isEmpty()) {

				result[i] = richerPeople.stream().mapToInt(people -> quiet[people]).min().getAsInt();
				result[i] = richerPeople.stream().reduce((a,b) -> {
					if(quiet[a] < quiet[b]) return a;
					else return b;
				}).get();

			}

		}

		return result;




	}


	void flatten(Integer people,Map<Integer,Set<Integer>> riches,boolean[] flattened) {

		if(!flattened[people]) {

			if(riches.containsKey(people)) {

				Set<Integer> allRicherPeoples = new HashSet<>();
				for(Integer richerPeople : riches.get(people)) {

					flatten(richerPeople,riches,flattened);
					if(riches.containsKey(richerPeople))
					{
						allRicherPeoples.addAll(riches.get(richerPeople));
					}

				}
				riches.get(people).addAll(allRicherPeoples);

			}

		}

		flattened[people] = true;

	}
}
