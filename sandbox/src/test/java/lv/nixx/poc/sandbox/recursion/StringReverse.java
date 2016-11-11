package lv.nixx.poc.sandbox.recursion;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringReverse {

	public static String reverse(String str) {
		if ((null == str) || (str.length() <= 1)) {
			return str;
		}
		return reverse(str.substring(1)) + str.charAt(0);

	}

	@Test
	public void reverseStringTest() {
		assertEquals("987654321", reverse("123456789"));
		assertEquals("0", reverse("0"));
	}
	

}
