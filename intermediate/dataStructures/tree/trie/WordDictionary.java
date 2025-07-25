package dataStructures.tree.trie;

public class WordDictionary {

	Trie trie;
	public WordDictionary() {
		trie = new Trie();
	}

	public void addWord(String word) {
		trie.insert(word);
	}

	public boolean search(String word) {
		return trie.searchWithDots(word);
	}
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
