package dataStructures.tree.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public class WordSearchII {

	public List<String> findWords(char[][] board, String[] words) {

		Trie trie = new Trie();

		for(String word : words) {
			trie.insert(word);
		}

		return trie.findWords(board);

	}

	public static void main(String[] args) {
		WordSearchII wordSearch = new WordSearchII();

		// Test case 1
		char[][] board1 = {
				{'o','a','a','n'},
				{'e','t','a','e'},
				{'i','h','k','r'},
				{'i','f','l','v'}
		};
		String[] words1 = {"oath","pea","eat","rain"};
		List<String> result1 = wordSearch.findWords(board1, words1);
		System.out.println("Test 1 Output: " + result1); // Expected: ["eat","oath"]

		// Test case 2
		char[][] board2 = {
				{'a','b'},
				{'c','d'}
		};
		String[] words2 = {"abcb"};
		List<String> result2 = wordSearch.findWords(board2, words2);
		System.out.println("Test 2 Output: " + result2); // Expected: []

		// Test case 3
		char[][] board3 = {
				{'a','b','c','e'},
				{'x','x','c','d'},
				{'x','x','b','a'}
		};
		String[] words3 = {"abc","abcd"};
		List<String> result3 = wordSearch.findWords(board3, words3);
		System.out.println("Test 3 Output: " + result3); // Expected: ["abc","abcd"]
	}




}

//public class WordSearchII
//{
//
//	public List<String> findWords(char[][] board, String[] words) {
//
//		List<Integer>[] alp = new List[26];
//		int row = board.length;
//		int col = board[0].length;
//
//		Map<Integer,Node> trie = new HashMap<>();
//
//		for(int i = 0;i<row;i++) {
//			for(int j = 0;j<col;j++) {
//
//				int index = board[i][j] - 'a';
//				int cellNumber = cellNumber(i,col,j);
//				if(alp[index] == null) {
//					alp[index] = new ArrayList<>();
//				}
//				alp[index].add(cellNumber);
//				trie.put(cellNumber,new Node(cellNumber,board[i][j]));
//
//			}
//		}
//
//		boolean[] visited = new boolean[row*col];
//		traverseUpAndLeft(row-1,col-1,board,trie,visited);
//		visited = new boolean[row*col];
//		traverseDownAndRight(0,0,board,trie,visited);
//
//		List<String> result = new ArrayList<>();
//
//		for(String word : words) {
//
//			char ch = word.charAt(0);
//
//			if(alp[ch - 'a'] != null)
//			{
//				for (int cellNumber : alp[ch - 'a'])
//				{
//
//					boolean[] visitedCell = new boolean[row*col];
//
//					if (searchWord(trie.get(cellNumber), word, 0, visitedCell))
//					{
//						result.add(word);
//						break;
//					}
//
//				}
//			}
//
//		}
//
//		return result;
//
//
//	}
//
//	private boolean searchWord(Node node, String word, int index, boolean[] visited)
//	{
//
//		if(visited[node.cellNumber] || node.ch != word.charAt(index)) {
//			return false;
//		}
//
//		// node is unvisited and end character matches
//		if(index == word.length() - 1) {
//			return true;
//		}
//
//		visited[node.cellNumber] = true;
//
//		boolean wordFound = false;
//
//
//		if(node.left != null) {
//			wordFound = searchWord(node.left,word,index+1,visited);
//		}
//		if(!wordFound && node.up != null) {
//			wordFound = searchWord(node.up,word,index+1,visited);
//		}
//		if(!wordFound && node.down != null) {
//			wordFound = searchWord(node.down,word,index+1,visited);
//		}
//		if(!wordFound && node.right != null) {
//			wordFound = searchWord(node.right,word,index+1,visited);
//		}
//
//		visited[node.cellNumber] = false;
//		return wordFound;
//
//	}
//
//	private Node traverseDownAndRight(int currRow,int currCol,char[][] board, Map<Integer, Node> trie,boolean[] visited)
//	{
//
//		int cellNumber = cellNumber(currRow,board[0].length,currCol);
//
//		if(visited[cellNumber]) {
//			return trie.get(cellNumber);
//		}
//
//		visited[cellNumber] = true;
//
//		Node currNode = trie.get(cellNumber);
//
//		// base case
//		if(currRow == board.length-1 && currCol == board[0].length - 1) {
//			// do nothing
//		}
//		// only right
//		else if(currRow == board.length - 1) {
//			currNode.right = traverseDownAndRight(currRow,currCol+1,board,trie,visited);
//		}
//		else if(currCol == board[0].length - 1) {
//			currNode.down = traverseDownAndRight(currRow+1,currCol,board,trie,visited);
//		}
//		else {
//			currNode.right = traverseDownAndRight(currRow,currCol+1,board,trie,visited);
//			currNode.down = traverseDownAndRight(currRow+1,currCol,board,trie,visited);
//		}
//
//		return currNode;
//
//	}
//
//	private Node traverseUpAndLeft(int currRow,int currCol,char[][] board, Map<Integer, Node> trie,boolean[] visited)
//	{
//
//		int cellNumber = cellNumber(currRow,board[0].length,currCol);
//		if(visited[cellNumber]) {
//			return trie.get(cellNumber);
//		}
//
//		visited[cellNumber] = true;
//
//		Node currNode = trie.get(cellNumber);
//
//		// base case
//		if(currRow == 0 && currCol == 0) {
//			// do nothing
//		}
//		// left only
//		else if(currRow == 0) {
//			currNode.left = traverseUpAndLeft(currRow,currCol-1,board,trie,visited);
//		}
//		// up only
//		else if(currCol == 0) {
//			currNode.up = traverseUpAndLeft(currRow-1,currCol,board,trie,visited);
//		}
//		// both directions
//		else {
//			currNode.left = traverseUpAndLeft(currRow,currCol-1,board,trie,visited);
//			currNode.up = traverseUpAndLeft(currRow-1,currCol,board,trie,visited);
//		}
//
//		return currNode;
//
//	}
//
//	private int cellNumber(int currRow,int totalCol,int currCol) {
//		return currRow * totalCol + currCol;
//	}
//
//	class Node {
//
//		int cellNumber;
//		char ch;
//
//		Node left;
//		Node up;
//		Node right;
//		Node down;
//
//		public Node(int cellNumber, char ch)
//		{
//			this.cellNumber = cellNumber;
//			this.ch = ch;
//		}
//	}
//
//
//}

