package lv.nixx.poc.sandbox.wordfinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class WordFinder {

	private String dictionaryFileName;

	public WordFinder(String dictionaryFileName) {
		this.dictionaryFileName = dictionaryFileName;
	}

	public String longestWord(String word) {

		if (word.length() < 12)
			throw new IllegalArgumentException("Word length must be at least 12 chracters");

		List<Character> wordSet = new LinkedList<>();
		for (char c : word.toCharArray()) {
			wordSet.add(c);
		}

		String longestWord = "";
		
		try (Stream<String> stream = Files.lines(Paths.get(dictionaryFileName))) {
			
			longestWord = stream.filter(dWord -> isBelongToCharset(dWord, wordSet))
			.max( (t1, t2) -> Integer.compare(t1.length(), t2.length())).orElse("");
				
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return longestWord;
	}

	private boolean isBelongToCharset(String dWord, List<Character> charset) {
		Set<Character> localCharset = new HashSet<>(charset);
		
		boolean isBelongToCharset = true;
		for (char c : dWord.toCharArray()) {
			if ( !localCharset.remove(c) ) {
				isBelongToCharset = false;
				break;
			}
		}

		return isBelongToCharset;
	}

}
