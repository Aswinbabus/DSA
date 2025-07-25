package algorithms.graph.topologicalSort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CourseScheduleIV
{

	public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {

		Map<Integer,Set<Integer>> prerequisitsCourses = new HashMap<>();

		for(int[] arr : prerequisites) {

			prerequisitsCourses.computeIfAbsent(arr[1],(k) ->new HashSet<>()).add(arr[0]);

		}

		boolean[] flattened = new boolean[numCourses];
		// flattening the mapping
		for(int i = 0;i<numCourses;i++) {

			if(!flattened[i])
				flatten(i,prerequisitsCourses,flattened);


		}

		List<Boolean> result = new ArrayList<>();
		for(int[] query : queries) {

			result.add(prerequisitsCourses.containsKey(query[1]) && prerequisitsCourses.get(query[1]).contains(query[0]));

		}

		return result;

	}

	private void flatten(int currCourse, Map<Integer, Set<Integer>> prerequisitsCourses,boolean[] flattened)
	{
		if(!flattened[currCourse])
		{
			if (prerequisitsCourses.containsKey(currCourse))
			{
				Set<Integer> courses = new HashSet<>();
				for (Integer course : prerequisitsCourses.get(currCourse))
				{
					flatten(course, prerequisitsCourses, flattened);
					if (prerequisitsCourses.containsKey(course))
					{
						courses.addAll(prerequisitsCourses.get(course));
					}
				}

				prerequisitsCourses.get(currCourse).addAll(courses);
			}

			flattened[currCourse] = true;
		}

	}

	public static void main(String[] args)
	{
		CourseScheduleIV course = new CourseScheduleIV();
		course.checkIfPrerequisite(6,new int[][]{{0,1},{1,2},{2,4},{3,2},{5,3}},new int[][]{{0,3},{1,4},{1,3},{2,3},{3,2},{5,4}}).forEach(System.out::println);;
	}

}
