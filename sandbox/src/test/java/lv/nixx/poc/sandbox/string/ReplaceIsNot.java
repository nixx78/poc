package lv.nixx.poc.sandbox.string;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReplaceIsNot {
	
	
	@Test
	public void replace1() {
		assertEquals("is not bad", replaceIs("is bad") );
	}

	@Test
	public void replace2() {
		assertEquals("this is not bad", replaceIs("this is bad") );
	}

	@Test
	public void replace3() {
		assertEquals("apple is not green", replaceIs("apple is green"));
	}

	@Test
	public void replace4() {
		assertEquals("test test", replaceIs("test test") );
	}

	@Test
	public void replace5() {
		assertEquals("my name is not", replaceIs("my name is") );
	}

	@Test
	public void replace6() {
		assertEquals("isover is not bad", replaceIs("isover is bad") );
	}

	@Test
	public void replace7() {
		assertEquals(" is not game over", replaceIs(" is game over") );
	}

	
	private String replaceIs(String s) {
		
		int i = 0;
		StringBuilder sb = new StringBuilder();
		int length = s.length();
		
		while (i <= length ) {
			
			if ( i!=length ) {
				sb.append(s.charAt(i));
			}

			if ( i-2 >= 0 ) {
				final String last2Chars = s.substring(i-2, i);

				if (last2Chars.equals("is")) {
					if ( i-2 == 0 && s.charAt(i) == ' ' ) {
						sb.append("not ");
					} else {
						final int prevElemIndex = i-3;
						if (i==length && s.charAt(prevElemIndex) == ' ' ) {
							sb.append(" not");	
						} else if (prevElemIndex >= 0 && s.charAt(prevElemIndex) == ' ' && s.charAt(i) == ' '){
							sb.append("not ");
						}
					}
				}
			}	
			i++;
		}
		return sb.toString();
	}

}
