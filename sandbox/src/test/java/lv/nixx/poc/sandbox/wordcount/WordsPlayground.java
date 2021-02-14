package lv.nixx.poc.sandbox.wordcount;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WordsPlayground {
	
	@Test
	public void findTop3WordsByLength() {
		 List<String> words = Arrays.asList("22", "3333", "55555", "1", "777", "88989898");
		
		 final List<String> topWords =
				 words.stream()
					.sorted((s1, s2) -> Integer.compare(s1.length(), s2.length()))
					.limit(3)
					.collect(Collectors.toList());
		
		assertNotNull(topWords);
		assertEquals(Arrays.asList("1","22", "777"), topWords);
	}
	
	@Test
	public void groupWordsBySize() {
		List<String> words = Arrays.asList(
				"22", "3333", "55555", "0", "1", "33", "777", "88989898");

		final Map<Integer, List<String>> collect = words.stream()
				.collect(Collectors.groupingBy(t -> t.length(), Collectors.toList()));
		
		collect.entrySet().stream().forEach(System.out::println);
	}
	

}
