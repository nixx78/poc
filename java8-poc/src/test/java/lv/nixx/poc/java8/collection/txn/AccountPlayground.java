package lv.nixx.poc.java8.collection.txn;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class AccountPlayground {
	
	@Test
	public void getAccountsWithMoreThan3Txn() {
		
		Account acc1 = new Account("AccountID1", Arrays.asList(
				new Transaction("_1txn1", BigDecimal.valueOf(10.00), "AccountID1", "USD"),
				new Transaction("_1txn2", BigDecimal.valueOf(20.00), "AccountID1", "USD"),
				new Transaction("_1txn3", BigDecimal.valueOf(30.00), "AccountID1", "USD"),
				new Transaction("_1txn4", BigDecimal.valueOf(40.00), "AccountID1", "USD")
			));
		
		
		Account acc2 = new Account("AccountID2", Arrays.asList(
				new Transaction("_2txn1", BigDecimal.valueOf(10.00), "AccountID2", "USD"),
				new Transaction("_2txn2", BigDecimal.valueOf(20.00), "AccountID2", "USD")
			));
		
		Account acc3 = new Account("AccountID3", Arrays.asList(
				new Transaction("_3txn1", BigDecimal.valueOf(10.00), "AccountID3", "USD")
			));

		Account acc4 = new Account("AccountID4", Arrays.asList(
				new Transaction("_4txn1", BigDecimal.valueOf(10.00), "AccountID4", "USD"),
				new Transaction("_4txn1", BigDecimal.valueOf(10.00), "AccountID4", "USD"),
				new Transaction("_4txn1", BigDecimal.valueOf(10.00), "AccountID4", "USD"),
				new Transaction("_4txn1", BigDecimal.valueOf(10.00), "AccountID4", "USD"),
				new Transaction("_4txn1", BigDecimal.valueOf(10.00), "AccountID4", "USD")
			));

		Stream<Account> accs = Stream.of(acc1, acc2, acc3,acc4);

		final Set<String> collect = accs
				.filter( a-> a.getTxnsStream().count()>2)
				.map(a->a.getId())
				.collect(Collectors.toSet());

		collect.forEach(System.out::println);
	}
	
	@Test
	public void doubleStatisticsAllTransactions() {
		Account acc1 = new Account("AccountID1", Arrays.asList(
				new Transaction("_1txn1", BigDecimal.valueOf(10.00), "AccountID1", "USD"),
				new Transaction("_1txn2", BigDecimal.valueOf(20.00), "AccountID1", "USD"),
				new Transaction("_1txn3", BigDecimal.valueOf(30.00), "AccountID1", "USD"),
				new Transaction("_1txn4", BigDecimal.valueOf(40.00), "AccountID1", "USD")
			));
		
		
		Account acc2 = new Account("AccountID2", Arrays.asList(
				new Transaction("_2txn1", BigDecimal.valueOf(10.00), "AccountID2", "USD"),
				new Transaction("_2txn2", BigDecimal.valueOf(20.00), "AccountID2", "USD")
			));
		
		Account acc3 = new Account("AccountID3", Arrays.asList(
				new Transaction("_3txn1", BigDecimal.valueOf(10.00), "AccountID3", "USD")
			));

		Account acc4 = new Account("AccountID4", Arrays.asList(
				new Transaction("_4txn1", BigDecimal.valueOf(10.00), "AccountID4", "USD"),
				new Transaction("_4txn1", BigDecimal.valueOf(10.00), "AccountID4", "USD"),
				new Transaction("_4txn1", BigDecimal.valueOf(10.00), "AccountID4", "USD"),
				new Transaction("_4txn1", BigDecimal.valueOf(10.00), "AccountID4", "USD"),
				new Transaction("_4txn1", BigDecimal.valueOf(777.00), "AccountID4", "USD")
			));

		
		final DoubleSummaryStatistics stat = Stream.of(acc1, acc2, acc3,acc4)
			.flatMap(a->a.getTxnsStream())
			.collect(Collectors.summarizingDouble(t->t.getAmount().doubleValue()));
		
		System.out.println(stat);
	}

}
