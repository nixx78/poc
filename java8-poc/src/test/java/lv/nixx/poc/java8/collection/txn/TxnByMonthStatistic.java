package lv.nixx.poc.java8.collection.txn;

import static java.util.stream.Collectors.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.junit.Test;

import lombok.NoArgsConstructor;
import lombok.ToString;

public class TxnByMonthStatistic {

	@Test
	public void createTxnByMonthStatistic() throws ParseException {
		
		Collection<Transaction> txns = new ArrayList<>();
		txns.add(new Transaction("id", BigDecimal.valueOf(10.10), "ACC1", "GBP", getDate("01.03.2016")));
		txns.add(new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD", getDate("01.08.2016")));
		txns.add(new Transaction("id3", BigDecimal.valueOf(1.25), "ACC2", "EUR", getDate("01.10.2016")));
		txns.add(new Transaction("id3", BigDecimal.valueOf(3.75), "ACC2", "EUR", getDate("03.10.2016")));
		txns.add(new Transaction("id31", BigDecimal.valueOf(5.8), "ACC2", "USD", getDate("02.10.2016")));
		txns.add(new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR", getDate("01.12.2016")));

		Comparator<String> monthComparator = (d1, d2) -> { 
			Integer x1 = Month.valueOf(d1).getValue();
			Integer x2 = Month.valueOf(d2).getValue();
			return x1.compareTo(x2);
		};
		
		Map<String, Map<String, StatisticAcamullator>> res = txns.stream()
				.collect(
						groupingBy( t-> getMonthFromDate(t.getLastUpdateDate()), () -> new TreeMap<>(monthComparator), 
						groupingBy( Transaction::getCurrency, new StaticsticCollector()))
				);
		
		for(Map.Entry<String,  Map<String, StatisticAcamullator>> e : res.entrySet()) {
			String month = e.getKey();
			System.out.println(month);
			for(Map.Entry<String, StatisticAcamullator> monthStats: e.getValue().entrySet()) {
				String currency = monthStats.getKey();
				System.out.println("\t" + currency + "\n \t\t" + monthStats.getValue());
			}
		}
		
 
	}

	private String getMonthFromDate(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate.getMonth().toString();
	}

	private Date getDate(String date) throws ParseException {
		return new SimpleDateFormat("dd.MM.yyyy").parse(date);
	}

	class StaticsticCollector implements Collector<Transaction, StatisticAcamullator, StatisticAcamullator> {

		@Override
		public Supplier<StatisticAcamullator> supplier() {
			return StatisticAcamullator::new;
		}

		@Override
		public BiConsumer<StatisticAcamullator, Transaction> accumulator() {
			return (s, t) -> s.increaseByTransaction(t);
		}

		@Override
		public BinaryOperator<StatisticAcamullator> combiner() {
			return (s1,s2) -> s1;
		}

		@Override
		public Function<StatisticAcamullator, StatisticAcamullator> finisher() {
			return Function.identity();
		}

		@Override
		public Set<Characteristics> characteristics() {
			return new HashSet<>(Arrays.asList(Characteristics.IDENTITY_FINISH));
		}
		
	}; 

	@NoArgsConstructor
	@ToString
	class StatisticAcamullator {
		private String currency;
		private int txnCount = 0;
		private BigDecimal amount = BigDecimal.ZERO;
		
		void increaseByTransaction(Transaction txn) {
			this.currency = txn.getCurrency();
			this.txnCount++;
			this.amount = this.amount.add( Optional.ofNullable(txn.getAmount()).orElse(BigDecimal.ZERO));
		}
	}
	
	

}
