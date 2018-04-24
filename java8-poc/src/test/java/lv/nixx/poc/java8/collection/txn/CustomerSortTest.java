package lv.nixx.poc.java8.collection.txn;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class CustomerSortTest {

	@Test
	public void customAccountSort() {

		final List<Account> sortedAccounts = Arrays.asList(new Account("X", null), 
				new Account("D", null), 
				new Account("C", null),
				new Account("A", null),
				new Account("B", null),
				new Account("TOP", null))
		.stream()
		.sorted((t1, t2)->t1.getId().equals("TOP") ? -1: t1.getId().compareTo(t2.getId()))
		.collect(Collectors.toList());
		
		sortedAccounts.forEach(System.out::println);
		
		final List<String> ids = sortedAccounts
				.stream()
				.map(Account::getId)
				.collect(Collectors.toList());
		
		assertEquals(Arrays.asList("TOP","A","B","C","D","X"), ids);
		

	}

}
