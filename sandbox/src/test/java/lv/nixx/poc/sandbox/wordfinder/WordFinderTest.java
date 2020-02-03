package lv.nixx.poc.sandbox.wordfinder;

import static org.junit.Assert.*;

import org.junit.Test;

public class WordFinderTest {
	
	@Test
	public void testFindLongesWord() {
		WordFinder wf = new WordFinder("./src/test/resources/short_list.txt");
		String longest = wf.longestWord("ajsxuytcnhre");
		
		assertNotNull(longest);
		assertEquals("saturn", longest);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkMinWordLength() {
		WordFinder wf = new WordFinder("./src/test/resources/short_list.txt");               
		wf.longestWord("abcd");
	}
	
	@Test
	public void googleTop10000() {
		WordFinder wf = new WordFinder("./src/test/resources/google.top10000.txt");
		final String longest = wf.longestWord("ekapbccasxxxxx");
		assertNotNull(longest);
		assertEquals("space", longest);

	}

}
