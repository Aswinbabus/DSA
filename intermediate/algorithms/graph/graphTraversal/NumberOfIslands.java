package algorithms.graph.graphTraversal;

import java.util.ArrayDeque;
import java.util.Queue;

// using BFS
public class NumberOfIslands
{
	public int numIslands(char[][] grid) {

		int m = grid.length,n = grid[0].length;

		boolean[][] visited = new boolean[m][n];

		int islandCount = 0;

		Queue<Integer[]> bfs = new ArrayDeque<>();

		for(int i = 0;i<m;i++) {

			for(int j = 0;j<n;j++) {


				if(grid[i][j] == '1' && !visited[i][j]) {

					islandCount++;

					visited[i][j] = true;
					bfs.add(new Integer[]{i,j});

					while(!bfs.isEmpty())
					{

						Integer[] curr = bfs.poll();

						int x = curr[0],y = curr[1];


						if(x + 1 < m && !visited[x+1][y] && grid[x + 1][y] == '1') {
							visited[x+1][y] = true;
							bfs.add(new Integer[]{x+1,y});
						}

						if(y + 1 < n && !visited[x][y+1] && grid[x][y+1] == '1') {
							visited[x][y+1] = true;
							bfs.add(new Integer[]{x,y+1});
						}

						if(x - 1 >= 0 && !visited[x-1][y] && grid[x - 1][y] == '1') {
							visited[x-1][y] = true;
							bfs.add(new Integer[]{x-1,y});
						}

						if(y - 1 >= 0 && !visited[x][y-1] && grid[x][y-1] == '1') {
							visited[x][y-1] = true;
							bfs.add(new Integer[]{x,y-1});
						}



					}

				}

			}

		}

		return islandCount;

	}

public static void main(String[] args) {
    NumberOfIslands i = new NumberOfIslands();
    System.out.println(i.numIslands(new char[][]{
        {'1', '1', '1'},
        {'0', '1', '0'},
        {'1', '1', '1'}
    }));
}
}
