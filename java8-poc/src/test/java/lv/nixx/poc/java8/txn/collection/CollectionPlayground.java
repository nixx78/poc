package lv.nixx.poc.java8.txn.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

public class CollectionPlayground {
	
	@Test
	public void collectionDisjoint() {
		
		Collection<String> c1 = Arrays.asList("1","2","3");
		Collection<String> c2 = Arrays.asList("4","5","6");
		
		// Коллекции должны быть отсортированы
		// Возврашает true - если нет общих элементов
		assertTrue("Collections are equals", Collections.disjoint(c1, c2));
	}
	
	@Test
	public void arrayStreamProcessing() {
		int[] intArray = new int[]{5, 99, 60, 12, 7, 5, 100, 777};
		
		final int[] res = Arrays.stream(intArray)
				.filter(t-> !(t<10))
				.sorted()
				.toArray();
		
		Arrays.stream(res).forEach(System.out::println);
		assertTrue(Arrays.equals(new int[]{12, 60, 99, 100, 777}, res));
	}
	
	@Test
	public void remove() {
		Collection<String> old = Arrays.asList("1","2","3");
		Collection<String> changed = Arrays.asList("4","2","3");
		
		Collection<String> result = new ArrayList<>(changed);
		result.removeAll(old);
		// 4 - new element in collection 
		result.forEach(System.out::println);
	}
	
	@Test
	public void findCommonElementInCollection() {
		Collection<String> old = Arrays.asList("1","2","3");
		Collection<String> changed = Arrays.asList("4","2","3");

		Collection<String> result = new ArrayList<>(old);
		// common elements in two collections
		result.retainAll(changed);
		assertEquals(Arrays.asList("2","3"), result);
		
		result.forEach(System.out::println);
	}
	
	@Test
	public void createCharacterStatistic() {
		String text = "aaaBBbbCC11233546556";
		Collection<Character> collection = new ArrayList<>();
		for (Character c : text.toCharArray()) {
			collection.add(c);
		}

		final Map<Character, Long> statistic = 
				collection.stream()
				.map(t->Character.toLowerCase(t))
				.collect(
						Collectors.groupingBy(t->t, 
						Collectors.counting())
				);
		
		statistic.entrySet().forEach(System.out::println);
	}
	
	@Test
	public void createCharacterStatisticGroup() {
		String text = "aaaBBbbCC11233546556";
		Collection<Character> collection = new ArrayList<>();
		for (Character c : text.toCharArray()) {
			collection.add(c);
		}
		
		final Map<Group, Long> collect = collection.stream().map(Holder::new)
		.collect(
				Collectors.groupingBy(t->t.group, 
				Collectors.counting())
		);
		
		collect.entrySet().forEach(System.out::println);
	}
	
	class Holder {
		Group group;
		Character c;

		Holder(Character c) {
			this.c = c;
			if (Character.isLetter(c)) {
				group = Group.CHARACTER;
			} else if (Character.isDigit(c)) {
				group = Group.NUMBER;
			}
		}

		@Override
		public String toString() {
			return "Holder [group=" + group + ", c=" + c + "]";
		}
		
	}
	
	enum Group {
		CHARACTER,
		NUMBER
	}

}
