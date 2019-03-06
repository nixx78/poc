package lv.nixx.poc.java8.collection;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

public class NumberStatisticSample {
	
	@Test
	public void numberStatisticSample() {
		
		Collection<String> c = Arrays.asList(
				"1", "aa1", "2", "2x", "123", "3", "3x", "x3x", "z3z2"
				);

		
		int oneCount = 0;
		int twoCount = 0;
		int threeCount = 0;
		
		for (String t : c) {
			if (t.contains("1")) {
				oneCount++;
			}

			if (t.contains("2")) {
				twoCount++;
			}

			if (t.contains("3")) {
				threeCount++;
			}
		}
		
		System.out.println("OneCount:" + oneCount);
		System.out.println("TwoCount:" + twoCount);
		System.out.println("ThreeCount:" + threeCount);
		
	}

}

