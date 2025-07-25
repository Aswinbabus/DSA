package algorithms.graph.topologicalSort;

import java.util.ArrayList;
import java.util.List;

public class CourseSchedulingII
{
	public int[] findOrder(int numCourses, int[][] prerequisites) {


		/**
		 *  0 - not visited yet
		 *  1 - waiting for prerequities to get completed
		 *  2 - completed the course
		 */
		int[] visited = new int[numCourses];
		ArrayList<Integer> courseCompletionOrder = new ArrayList<>();
		int courseCompletionIndex = 0;

		// let us create an adjacency List to represent the graph
		List<Integer>[] adjencyList= new List[numCourses];

		for(int[] edge : prerequisites) {

			if(adjencyList[edge[0]] == null) {
				adjencyList[edge[0]] = new ArrayList<>();
			}
			adjencyList[edge[0]].add(edge[1]);

		}

		// let perform DFS from vertice 0
		for(int i = 0;i<numCourses;i++)
		{
			if(visited[i] == 0)
			{

				if(!dfs(i, visited, adjencyList,courseCompletionOrder)){
					return new int[]{};
				}


			}
		}

		return  courseCompletionOrder.stream().mapToInt(Integer::intValue).toArray();

	}

	boolean dfs(int currVertice,int[] visited,List<Integer>[] adjacencyList,List<Integer> courseCompletionOrder) {


		// changing state to trying to solve the course
		visited[currVertice] = 1;

		List<Integer> toBeCompleted = adjacencyList[currVertice];

		// base case - Marking the course which has no pre-requesites as visited
		if(toBeCompleted == null || toBeCompleted.isEmpty()) {
			visited[currVertice] = 2;
		}
		// recursive Case
		else
		{

			// recursive case - trying to complete
			for (Integer course : toBeCompleted)
			{

				// already course completed
				if (visited[course] == 2)
				{
					continue;
				}
				// this course cycle
				if (visited[course] == 1)
				{
					return false;
				}
				if (!dfs(course, visited, adjacencyList,courseCompletionOrder))
				{
					return false;
				}

			}
			visited[currVertice] = 2;
		}
		// if all pre-requistes can be completed
		courseCompletionOrder.add(currVertice);
		return true;

	}

}
