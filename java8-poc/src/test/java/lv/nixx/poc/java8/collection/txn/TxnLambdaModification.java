package lv.nixx.poc.java8.collection.txn;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

public class TxnLambdaModification {

	@Test
	public void functionCall() {

		List<Transaction> txns = Arrays.asList(
				new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
				new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
				new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
				new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR")
				);
		
		TxnProcessor myProcessor = new TxnProcessor();
		// сссылка на метод, у конкретного экземпляра класса
		txns.stream().forEach(myProcessor::simpleMethod);
		// вызов статического метода с параметрами
		txns.stream().forEach(t -> TxnProcessor.staticMethod(t.getAmount(), t.getCurrency()));

		// Moжно вызывать и вот так, поскольу, MyProcessor implements Consumer
		MyProcessor mp = new MyProcessor();
		txns.forEach(mp);
	}

	@Test(expected = IllegalStateException.class)
	public void transformCollectionToMapDuplicateKey() {
		
		Collection<Transaction> txnSet = new HashSet<>();
		txnSet.add(new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"));
		txnSet.add(new Transaction("id1", BigDecimal.valueOf(99.10), "ACC1", "USD"));
		txnSet.add(new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"));
		txnSet.add(new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"));
		txnSet.add(new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR"));
		
		txnSet.stream().collect(Collectors.toMap(t-> t.getId(), Function.identity()));
	}
	
	
	@Test
	public void createAccountHolder() {
		Collection<Transaction> txnSet = new HashSet<>();
		txnSet.add(new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"));
		txnSet.add(new Transaction("id1", BigDecimal.valueOf(99.10), "ACC1", "USD"));
		txnSet.add(new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"));
		txnSet.add(new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"));
		txnSet.add(new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR"));
		
		AccountHolder ah = new AccountHolder();
		txnSet.forEach(ah::process);
		
		ah.accounts.entrySet().forEach(e-> {System.out.println(e.getKey() + ":" + e.getValue());});
	}
	
	@Test
	public void getUniqueAccounts() {
		Collection<Transaction> txnSet = new HashSet<>();
		txnSet.add(new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"));
		txnSet.add(new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"));
		txnSet.add(new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"));
		txnSet.add(new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR"));
		txnSet.add(new Transaction("id5", BigDecimal.valueOf(40.14), "ACC5", "EUR"));
		
		final Set<String> accounts = txnSet.stream()
				.map(t->t.getAccount())
				.collect(Collectors.toSet());
		
		assertEquals(4, accounts.size());
		accounts.forEach(System.out::println);
	}
	
	@Test
	public void transformCollectionToMapIgnoreDuplicates() throws ParseException {

		final String id1 = "id1";
		final Transaction id1LastVersion = new Transaction(id1, BigDecimal.valueOf(99.10), "ACC1.Last", "GBP", getDate("10.09.2016"));

		Set<Transaction> txnSet = new HashSet<>();
		txnSet.add(new Transaction(id1, BigDecimal.valueOf(10.10), "ACC1", "GBP", getDate("01.09.2016")));
		txnSet.add(new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD", getDate("01.09.2016")));
		txnSet.add(new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR", getDate("01.09.2016")));
		txnSet.add(new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR", getDate("01.09.2016")));
		txnSet.add(id1LastVersion);
		
		
		@SuppressWarnings("unused")
		BinaryOperator<Transaction> mergeFunction = new BinaryOperator<Transaction>() {
			@Override
			public Transaction apply(Transaction oldValue, Transaction newValue) {
				return oldValue.getLastUpdateDate().compareTo(newValue.getLastUpdateDate()) <=0 ? newValue : oldValue; 
			}
		};
		// mergeFunction и mergeFunction1 делают одно и тоже, просто разная форма записи
		BinaryOperator<Transaction> mergeFunction1 = (oldValue, newValue) ->{ return oldValue.getLastUpdateDate().compareTo(newValue.getLastUpdateDate()) <=0 ? newValue : oldValue; };
		
		// Если не указать mergeFunction, то данный запрос будет "падать", так как коллекция содержит повторяющиеся элементы
		final Map<String, Transaction> collect = txnSet.stream()
				.collect(Collectors.toMap(Transaction::getId, Function.identity(), mergeFunction1));

		collect.values().stream().forEach(System.out::println);
		assertEquals(4, collect.size());
		
		assertEquals(id1LastVersion, collect.get(id1));
	}	

	
	@Test
	public void transformCollectionToIdList() {
		List<Transaction> txns = Arrays.asList(
				new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
				new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
				new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
				new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR")
				);
		
		List<String> array = txns.stream()
							.filter(t-> t.getCurrency().equalsIgnoreCase("USD"))
							.map(t-> t.getId())
							.collect(Collectors.toList());
		
		assertEquals(2, array.size());
	}

	@Test
	public void groupByAccountCurrencies() {
		List<Transaction> txns = Arrays.asList(
				new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
				new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
				new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
				new Transaction("id4", BigDecimal.valueOf(20.00), "ACC2", "EUR"),
				new Transaction("id5", BigDecimal.valueOf(40.14), "ACC3", "EUR")
				);
	
		// Счет, список валют
		Map<String, List<String>> c = 
				txns.stream()
			           .collect(Collectors.groupingBy(
			        		   Transaction::getAccount,
			               Collectors.mapping(Transaction::getCurrency, Collectors.toList()))
			           );
		
		c.entrySet().forEach(System.out::println);
		
		assertEquals(3, c.size());
	}
	
	@Test
	public void groupByTotalAmountByCurrency() {
		List<Transaction> txns = Arrays.asList(
				new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
				new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
				new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
				new Transaction("id4", BigDecimal.valueOf(20.00), "ACC2", "EUR"),
				new Transaction("id5", BigDecimal.valueOf(40.14), "ACC3", "EUR")
				);
	
		
		Map<String, BigDecimal> c = txns.stream()
				.collect(
				Collectors.groupingBy(Transaction::getCurrency,
						Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add))
				);
		
		c.entrySet().forEach(System.out::println);
		
		assertEquals(2, c.size());
	}
	
	
	
	
	
	@Test
	public void groupByAccountAmountList() {
		List<Transaction> txns = Arrays.asList(
				new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
				new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
				new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
				new Transaction("id4", BigDecimal.valueOf(20.00), "ACC2", "EUR"),
				new Transaction("id5", BigDecimal.valueOf(40.14), "ACC3", "EUR")
				);
	
		Map<String, List<Amount>> c = 
				txns.stream()
			           .collect(Collectors.groupingBy(Transaction::getAccount,
			               Collectors.mapping(Amount::new, Collectors.toList())
			               )
			           );
			
		c.entrySet().forEach(System.out::println);

		assertEquals(3, c.size());
	}
	
	@Test
	public void groupByCurrencyAccountStatistic() {
		List<Transaction> txns = Arrays.asList(
				new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
				new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
				new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
				new Transaction("id4", BigDecimal.valueOf(20.00), "ACC2", "EUR"),
				new Transaction("id5", BigDecimal.valueOf(40.14), "ACC3", "EUR"),
				new Transaction("id6", BigDecimal.valueOf(40.14), "ACC4", "RUR")

				);
	
				Map<String, Map<String, Long>> c = txns.stream()
			           .collect(Collectors.groupingBy(Transaction::getCurrency,
			               Collectors.groupingBy(Transaction::getAccount, Collectors.counting())
			               )
			           );
			
		c.entrySet().forEach(System.out::println);

		assertEquals(3, c.size());
	}
	
	@Test
	public void mapAndGroup() {
		List<Transaction> txns = Arrays.asList(
				new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
				new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
				new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
				new Transaction("id4", BigDecimal.valueOf(20.00), "ACC2", "EUR"),
				new Transaction("id5", BigDecimal.valueOf(40.14), "ACC3", "EUR")
				);
	
		Collection<Amount> c = 
				txns.stream()
				.map(Amount::new).collect(Collectors.toSet());
			      
		c.forEach(System.out::println);

		assertEquals(5, c.size());
	}
	
	@Test
	public void totalAmountByCurrency() {
		Set<Amount> set = new HashSet<>();
		set.add(new Amount("EUR", BigDecimal.valueOf(30.13)));
		set.add(new Amount("EUR", BigDecimal.valueOf(20.0)));
		set.add(new Amount("USD", BigDecimal.valueOf(20.12)));
		set.add(new Amount("USD", BigDecimal.valueOf(50)));
		set.add(new Amount("GBP", BigDecimal.valueOf(150)));
		
		Map<String, Optional<Amount>> r = set.stream()
				.collect(
						Collectors.groupingBy(Amount::getCurrency,
						Collectors.reducing(Amount::increase))
						);
						
		r.entrySet().forEach(System.out::println);

		assertEquals(3,r.size());
	}
	
	@Test
	public void txnCountByCurrency() {
		Set<Amount> set = new HashSet<>();
		set.add(new Amount("EUR", BigDecimal.valueOf(30.13)));
		set.add(new Amount("EUR", BigDecimal.valueOf(20.0)));
		set.add(new Amount("USD", BigDecimal.valueOf(20.12)));
		set.add(new Amount("USD", BigDecimal.valueOf(50)));
		set.add(new Amount("GBP", BigDecimal.valueOf(150)));
		
		Map<String, Long> r = set.stream()
				.collect(
						Collectors.groupingBy(Amount::getCurrency,
								Collectors.counting())
						);
						
		r.entrySet().forEach(System.out::println);

		assertEquals(3,r.size());
	}
	
	
	
	@Test
	public void partitioningBy() {
		List<Transaction> txns = Arrays.asList(
				new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
				new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
				new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
				new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR")
				);
		
		final Map<Boolean, List<Transaction>> c = txns.stream()
				.collect(Collectors.partitioningBy(t -> t.getCurrency().equals("USD")));
		
		c.forEach((b, t) -> System.out.println(b + ":" + t));
	}
	
	@Test
	public void txnCreateFromString() throws Exception {
		String txnString = 
				"id1, 10.30, ACC1, USD\n" +
				"id2, 20.40, ACC2, USD\n" +
				"id3, 5.00,  ACC3, EUR\n" + 
				"id4, 20.00, ACC3, EUR\n" +
				"id5, 15.00, ACC4, EUR\n";
		
		try (BufferedReader br = new BufferedReader(new StringReader(txnString))) {
			final List<Transaction> txnList = br.lines()
			.map( t-> t.split("\\,"))
			.filter( t-> t.length == 4)
			.map(this::removeSpaces)
			.map(t-> new Transaction( t[0], parse(t[1]), t[2], t[3]))
			.collect(Collectors.toList());
			
			txnList.forEach(System.out::println);
		}
	}
	
	@Test
	public void sortTxnBySeveralParams() {
		List<Transaction> txns = Arrays.asList(
				new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
				new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
				new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
				new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR"),
				new Transaction("id4", BigDecimal.valueOf(1.00), "ACC3", "EUR")

				);
		
		final Comparator<Transaction> c = 
				Comparator.comparing(Transaction::getAccount).reversed()
						  .thenComparing(Transaction::getCurrency)
						  .thenComparing(Transaction::getAmount);
				
		List<Transaction> sorted = txns.stream().sorted(c).collect(Collectors.toList());
		sorted.forEach(System.out::println);
	}
	
	
	private String[] removeSpaces(String[] source){
		for (int i = 0; i < source.length; i++) {
			source[i] = source[i].trim();
		}
		return source;
	}
	
	private BigDecimal parse(String amount) {
		DecimalFormat df = new DecimalFormat();
		df.setParseBigDecimal(true);
		try {
			return (BigDecimal)df.parse(amount.trim());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return BigDecimal.ZERO;
	}
	
	class AccountHolder {

		Map<String, Account> accounts = new HashMap<>();

		public void process(Transaction txn) {
			String accId = txn.getAccount();
			Account account = null;
			if ( accounts.containsKey(accId)) {
				account  = accounts.get(accId);
			} else {
				account  = new Account(accId);
			}
			account.increase(txn.getCurrency(), txn.getAmount());
			accounts.put(accId, account);
		}
		
	}
	
	class Account {
		private String id;
		Map<String, Amount> amounts = new HashMap<>();
		
		public Account(String accId) {
			this.id = accId;
		}
		
		public Account increase(Transaction txn) {
			final String currency = txn.getCurrency();
			final BigDecimal amount = txn.getAmount();
			if (amounts.containsKey(currency)) {
				amounts.get(currency).increase(amount);
			} else {
				amounts.put(currency, new Amount(currency, amount));
			}
			return this;
		}


		public void increase(String currency, BigDecimal amount) {
			if (amounts.containsKey(currency)) {
				amounts.get(currency).increase(amount);
			} else {
				amounts.put(currency, new Amount(currency, amount));
			}
		}

		@Override
		public String toString() {
			return "Account [id=" + id + ", amounts=" + amounts + "]";
		}
	}
	
	class Amount {
		private String currency;
		private BigDecimal totalAmount = BigDecimal.ZERO;
		
		Amount(Transaction txn) {
			this.currency = txn.getCurrency();
			this.totalAmount = txn.getAmount();
		}
		
		Amount(String currency, BigDecimal amount) {
			this.currency = currency;
			this.totalAmount = amount;
		}
		
		public void increase(BigDecimal amount) {
			this.totalAmount = totalAmount.add(amount);
		}
		
		public Amount increase(Amount totalAmount) {
			increase(totalAmount.getTotalAmount());
			return this;
		}
		
		public String getCurrency() {
			return currency;
		}

		public BigDecimal getTotalAmount() {
			return totalAmount;
		}

		@Override
		public String toString() {
			return "TotalAmount [currency=" + currency + ", totalAmount=" + totalAmount + "]";
		}
	}
	
	static class TxnProcessor {
		public void simpleMethod(Transaction t) {
			System.out.println(t);
		}
		public static void staticMethod(BigDecimal amount, String currency) {
			System.out.println(amount + ":" + currency);
		}
	}
	
	static class MyProcessor implements Consumer<Transaction> {

		@Override
		public void accept(Transaction t) {
			System.out.println("#" + t);
		}

	}
	
	private Date getDate(String date) throws ParseException {
		return new SimpleDateFormat("dd.MM.yyyy").parse(date);
	}

	
}
