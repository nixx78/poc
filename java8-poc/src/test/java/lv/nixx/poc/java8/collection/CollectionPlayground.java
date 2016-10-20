package lv.nixx.poc.java8.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.Test;

public class CollectionPlayground {
	
	@Test(expected = NullPointerException.class)
	public void nullToTreeSet() {
		Set<String> set = new TreeSet<>();
		set.add("s1");
		set.add(null);
	}
	
	@Test
	public void nullToSet() {
		Set<String> set = new LinkedHashSet<>();
		set.add("s1");
		set.add("s2");
		set.add(null);
		set.add("s4");
		// Все хорошо, null можно добавлять в LinkedHashSet
		set.forEach(System.out::println);
	}
	
	@Test
	public void linkedHashMap() {
		Map<String, String> map = new HashMap<>();
		map.put("key1", "value1");
		map.put(null, null);
		map.put(null, "nullValue");
		map.put("key3", "value3");
		map.put("key4", "value4");
		// тут тоже все хорошо
		assertEquals(4, map.size());
		
		map.entrySet().forEach(System.out::println);
	}
	
	@Test(expected = NullPointerException.class)
	public void addNullToTreeMap() {
		Map<String, String> map = new TreeMap<>();
		map.put("key1", "value1");
		map.put(null, "nullValue");
	}
	
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

