package lv.nixx.poc.java8.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Test;

public class NumberStatisticSample {
	
	private final Collection<String> input = Arrays.asList(
			"11", "aa1", "2", "2x", "123", "3333", "3x", "x3x", "z3z2"
			);

	@After
	public void after() {
		System.out.println("===============");
	}
	
	//TODO Add performance monitoring there
	// Посчитать количество элементов с вхождением различных символов,
	// например, 1an, 222as, 213 = "1" -> 2, "2" -> 2, "3" -> 1

	
	@Test
	public void firstApproach() {

		final Collection<String> checkedChars = Arrays.asList(
				"1", "2", "3"
				);

		final Map<String, Integer> statisic = new HashMap<>();
		
		for (String t : input) {
			checkedChars.forEach( chr -> {
				if (t.contains(chr)) {
					statisic.merge(chr, 1, (t1, t2) -> t1 + t2); 
				}
			});
		}
		statisic.entrySet().forEach(System.out::println);
	}
	

	@Test
	public void secondApproach() {
		final Map<String, Long> statisic = Stream.of("1", "2", "3")
				.collect(Collectors.toMap(c-> c, c-> input.stream().filter(t -> t.contains(c)).count()));

		statisic.entrySet().forEach(System.out::println);
	}

}

