package lv.nixx.poc.sandbox.recursion;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalculateNumberSum {
	
	@Test
	public void numberCharsCalculation() {
		assertEquals(0, calculateNumbersSum(0));
		assertEquals(1, calculateNumbersSum(10));
		assertEquals(6, calculateNumbersSum(123));
		assertEquals(7, calculateNumbersSum(1978));
	}

	private int calculateNumbersSum(int number) {
		if (number < 10) {
			return number;
		} else {
			int sum = 0;
			while (number > 0) {
				sum += number % 10;
				number = number / 10;
			}
			return calculateNumbersSum(sum);
		}
	}

}
