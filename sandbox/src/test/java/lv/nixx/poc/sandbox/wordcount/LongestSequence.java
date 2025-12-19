package lv.nixx.poc.sandbox.wordcount;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestSequence {
	
	@Test
	public void test1() {
		assertEquals("01", getLongestSequence("01") );
	}

	@Test
	public void test2() {
		assertEquals("1234", getLongestSequence("00123411133") );
	}

	@Test
	public void test3() {
		assertEquals("01", getLongestSequence("101") );
	}

	@Test
	public void test4() {
		assertEquals("X", getLongestSequence("X") );
	}
	
	private String getLongestSequence(String in) {
		String maxSequence = "";
		Set<Character> seq = new HashSet<>();
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			if (seq.contains(c)) {
				if ( maxSequence.length() < seq.size() ) {
					maxSequence = stringValue(seq);
				}
				seq.clear();
			} else {
				seq.add(c);
			}
		}

		return maxSequence.length() == 0 ? stringValue(seq) : maxSequence;
	}
	
	private String stringValue(Set<Character> seq) {
		StringBuilder b = new StringBuilder();
		seq.forEach(b::append);
		return b.toString();
	}
	
	

}
