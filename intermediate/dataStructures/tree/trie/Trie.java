package dataStructures.tree.trie;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class Trie
{

	TrieNode root;
	Function<Character,Short> characterToInt = (ch) -> (short) (ch - 97);

	public Trie() {
		root = new TrieNode();
	}

	public void insert(String str) {

		TrieNode currNode = root;

		for(char c : str.toCharArray()) {

			int index = characterToInt.apply(c);

			// creating path if no exists
			if(!currNode.nodes.containsKey(index)) {
				TrieNode node = new TrieNode();
				node.charIndex = index;
				currNode.nodes.put((int) index,node);
				currNode.totalPaths++;
			}

			currNode = currNode.nodes.get(index);
			// we are adding new add in this path
			currNode.prefixCount++;

		}

		// marking the end of word
		currNode.wordCount++;


	}

	public boolean delete(String str) {

		 Deque<TrieNode> stack = new ArrayDeque<>();

         TrieNode currNode = root;

		 stack.push(currNode);

		 for(char c : str.toCharArray()) {

			 int index = characterToInt.apply(c);

			 if(!currNode.nodes.containsKey(index)) {
				 return false;
			 }

			 currNode = currNode.nodes.get(index);

			 stack.push(currNode);
		 }

		 if(!stack.isEmpty()) {

			 TrieNode leafNode = stack.pop();
			 int deleteIndex = -1;

			 // word is not found
			 if(leafNode.wordCount == 0) {
				 return false;
			 }
			 // if word has more frequency
			 else if(leafNode.wordCount >= 1) {

				 leafNode.wordCount--;
				 leafNode.prefixCount--;
				 return true;
			 }
			 // we need to delete the word
			 else {
				 deleteIndex = leafNode.charIndex;
			 }

			 while(!stack.isEmpty() && deleteIndex != -1) {

				 TrieNode node = stack.pop();

				 node.nodes.put(deleteIndex, null);
				 node.totalPaths--;
				 node.prefixCount--;

				 if(node.totalPaths == 0 && node.wordCount == 0) {
					 deleteIndex = node.charIndex;
				 }
				 else {
					 break;
				 }

			 }

			 return true;


		 }

		 return false;


	}

	public int getWordCount(String str) {

		TrieNode node = traverse(str);

		if(node != null && node.wordCount > 0) {
			return node.wordCount;
		}

		return 0;

	}

	public boolean searchWithDots(String str) {
		return searchWithDots(root,str);
	}

	public boolean searchWithDots(TrieNode node,String str) {


		TrieNode currNode = node;

		for(int index = 0;index < str.length();index++) {

			char c = str.charAt(index);
			if(c == '.') {

				for(TrieNode trieNode : currNode.nodes.values()) {

					if(trieNode != null)
					{
						// dot at last
						if (index == str.length() - 1)
						{
							if(trieNode.wordCount > 0)
							{
								return true;
							}
						}
						else
						{
							// search remaining word
							if (searchWithDots(trieNode, str.substring(index + 1)))
							{
								return true;
							}
						}
					}

				}

				return false;

			}
			else
			{

				int charIndex = characterToInt.apply(c);

				if (!currNode.nodes.containsKey(charIndex))
				{
					return false;
				}

				currNode = currNode.nodes.get(charIndex);
			}

		}

		if(currNode != null && currNode.wordCount > 0) {
			return true;
		}

		return false;

	}

	private TrieNode traverse(String str) {

		TrieNode currNode = root;

		for(char c : str.toCharArray()) {

			int index = characterToInt.apply(c);

			if(currNode == null || !currNode.nodes.containsKey(index)) {
				return null;
			}

			currNode = currNode.nodes.get(index);

		}

		return currNode;

	}

	public int startsWith(String prefix) {

		TrieNode currNode = traverse(prefix);

		if(currNode != null && currNode.prefixCount > 0) {
			return currNode.prefixCount;
		}

		return 0;

	}

	public List<String> findWords(char[][] board) {


		Set<String> result = new HashSet<>();
		Map<Character,List<Cell>> cells = new HashMap<>();

		for(int row = 0;row<board.length;row++) {
			for(int col = 0;col<board[0].length;col++) {

				if(!cells.containsKey(board[row][col])) {
					cells.put(board[row][col],new ArrayList<>());
				}

				cells.get(board[row][col]).add(new Cell(row,col));

			}
		}

		for(TrieNode trieNode : root.nodes.values()) {

			Set<String> temp = new HashSet<>();

			char ch = (char) (trieNode.charIndex + 'a');
			if(cells.containsKey(ch))
			{
				StringBuilder builder;
				for(Cell cell : cells.get(ch))
				{

					builder = new StringBuilder();
					Set<Integer> visited = new HashSet<>();
					dfs(trieNode,cell,temp,visited,board,builder);
					result.addAll(temp);
					temp.forEach(this::delete);
					temp.clear();

				}
			}

		}

		return new ArrayList<>(result);

	}

	private void dfs(TrieNode trieNode, Cell cell, Set<String> temp, Set<Integer> visited,char[][] board, StringBuilder builder)
	{

		// check bounds
		if(cell.row < 0 || cell.row >= board.length || cell.col < 0 || cell.col >= board[0].length) {
			return;
		}

		int cellNumber = cellNumber(cell.row,board[0].length,cell.col);

		if(trieNode == null || visited.contains(cellNumber) || trieNode.charIndex != board[cell.row][cell.col] - 'a') {
			return;
		}

		char ch = (char) (trieNode.charIndex + 'a');

		builder.append(ch);

		if(trieNode.wordCount > 0) {
			temp.add(builder.toString());
		}

		visited.add(cellNumber);

		for(TrieNode node : trieNode.nodes.values()) {

			// left
			dfs(node,new Cell(cell.row,cell.col-1),temp,visited,board,builder);
			// right
			dfs(node,new Cell(cell.row,cell.col+1),temp,visited,board,builder);
			// up
			dfs(node,new Cell(cell.row-1,cell.col),temp,visited,board,builder);
			// down
			dfs(node,new Cell(cell.row+1,cell.col),temp,visited,board,builder);

		}

		visited.remove(cellNumber);
		builder.deleteCharAt(builder.length()-1);

	}

	private int cellNumber(int currRow,int totalCol,int currCol) {
		return currRow * totalCol + currCol;
	}


	public static void main(String[] args) {
		Trie trie = new Trie();

		// Test 1: Insert and search
		trie.insert("apple");
		System.out.println("search('apple'): " + trie.getWordCount("apple")); // Expected: 1
		System.out.println("search('app'): " + trie.getWordCount("app")); // Expected: 0

		// Test 2: startsWith
		System.out.println("startsWith('app'): " + trie.startsWith("app")); // Expected: 1

		// Test 3: Insert prefix and search
		trie.insert("app");
		System.out.println("search('app'): " + trie.getWordCount("app")); // Expected: 1
		System.out.println("startsWith('app'): " + trie.startsWith("app")); // Expected: 2

		// Test 4: Insert duplicate and search
		trie.insert("apple");
		System.out.println("search('apple'): " + trie.getWordCount("apple")); // Expected: 2
		System.out.println("dot" + trie.searchWithDots("a..lle"));
		System.out.println("dot" + trie.searchWithDots("a..xe"));
		System.out.println("dot" + trie.searchWithDots("a.p.e"));
		System.out.println("dot" + trie.searchWithDots("..p.e"));
		System.out.println("dot" + trie.searchWithDots("ap..."));



		// Test 5: Delete one occurrence
		System.out.println("delete('apple'): " + trie.delete("apple")); // Expected: true
		System.out.println("search('apple'): " + trie.getWordCount("apple")); // Expected: 1

		// Test 6: Delete all occurrences
		System.out.println("delete('apple'): " + trie.delete("apple")); // Expected: true
		System.out.println("search('apple'): " + trie.getWordCount("apple")); // Expected: 0

		// Test 7: Delete non-existing word
		System.out.println("delete('banana'): " + trie.delete("banana")); // Expected: false

		// Test 8: startsWith after deletions
		System.out.println("startsWith('app'): " + trie.startsWith("app")); // Expected: 1

		// Test 9: Delete prefix word
		System.out.println("delete('app'): " + trie.delete("app")); // Expected: true
		System.out.println("search('app'): " + trie.getWordCount("app")); // Expected: 0
		System.out.println("startsWith('app'): " + trie.startsWith("app")); // Expected: 0
	}

	class Cell {

		int row;
		int col;

		public Cell(int row, int col)
		{
			this.row = row;
			this.col = col;
		}

	}

}

class TrieNode {

	Map<Integer,TrieNode> nodes;
	int charIndex;
	int wordCount;
	int prefixCount;
	int totalPaths;

	TrieNode() {
		nodes = new HashMap<>();
		wordCount = 0;
		prefixCount = 0;
		totalPaths = 0;
	}
}


