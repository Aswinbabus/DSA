package algorithms.graph.topologicalSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParallelCoursesIII
{
	public int minimumTime(int n, int[][] relations, int[] time) {

		List<Integer>[] adjacencyList = new List[n];
        int[] courseCompletionTime = new int[n];


		for(int[] relation : relations) {

			// reversing graph
			if(adjacencyList[relation[1] - 1] == null) {
				adjacencyList[relation[1] - 1] = new ArrayList<>();
			}
			adjacencyList[relation[1]].add(relation[0] - 1);
		}


		for(int i = 0;i<n;i++) {

			// not visited
			if(courseCompletionTime[i] == 0) {
				completeCourse(i,adjacencyList,time,courseCompletionTime);
			}

		}

		return Arrays.stream(courseCompletionTime).max().getAsInt();


	}

	private void completeCourse(int currCourse, List<Integer>[] adjacencyList, int[] time, int[] courseCompletionTime)
	{


		// Base case : not pre-requiste courses
		if(adjacencyList[currCourse] == null || adjacencyList[currCourse].isEmpty()) {
			courseCompletionTime[currCourse] = time[currCourse];
			return;
		}
		// recursive Case : has pre-requiste courses
		int maxTime = 0;
		for(Integer preRequisteCourse : adjacencyList[currCourse]) {

			if(courseCompletionTime[preRequisteCourse] == 0) {
				completeCourse(preRequisteCourse,adjacencyList,time,courseCompletionTime);
			}
			maxTime = Math.max(maxTime,courseCompletionTime[preRequisteCourse]);
		}

		courseCompletionTime[currCourse] = time[currCourse] + maxTime;

	}
}
