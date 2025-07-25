package algorithms.graph.topologicalSort;

import java.util.*;

public class CourseScheduling
{
	public boolean canFinish(int numCourses, int[][] prerequisites) {

		/**
		 *  0 - not visited yet
		 *  1 - waiting for prerequities to get completed
		 *  2 - completed the course
		 */
		int[] visited = new int[numCourses];

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
				if(!dfs(i, visited, adjencyList)){
					return false;
				}
			}
		}

		return true;



	}

	boolean dfs(int currVertice,int[] visited,List<Integer>[] adjacencyList) {


		// changing state to trying to solve the course
		visited[currVertice] = 1;

		List<Integer> toBeCompleted = adjacencyList[currVertice];

		// base case - Marking the course which has no pre-requesites as visited
		if(toBeCompleted == null || toBeCompleted.isEmpty()) {
			visited[currVertice] = 2;
			return true;
		}



		// recursive case - trying to complete
		for(Integer course : toBeCompleted) {

			// already course completed
			if(visited[course] == 2) {
				continue;
			}
			// this course cycle
			if(visited[course] == 1) {
				return false;
			}
			if(!dfs(course,visited,adjacencyList)) {
				return false;
			}

		}
		// if all pre-requistes can be completed
		visited[currVertice] = 2;
		return true;

	}

	public static void main(String[] args)
	{
		CourseScheduling scheduling = new CourseScheduling();

		System.out.println(scheduling.canFinish(2,new int[][]{{1,0},{0,1}}));
	}
}
