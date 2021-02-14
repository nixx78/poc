package lv.nixx.poc.sandbox.collection.txn;

import lv.nixx.poc.domain.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TxnHolderTest {
	
	@Test
	public void onlyLatesTxnShouldBeStored() throws ParseException {
		
		TxnHolder th = new TxnHolder();
		th.add(new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1.CHANGED", "USD", getDate("01.09.2016")));
		th.add(new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD", getDate("01.08.2016")));
		th.add(new Transaction("id3", BigDecimal.valueOf(20.12), "ACC3", "USD", getDate("03.09.2016")));
		th.add(new Transaction("id6", BigDecimal.valueOf(20.12), "ACC3.NEW", "USD", getDate("03.09.2016")));
		th.add(new Transaction("id3", BigDecimal.valueOf(20.12), "ACC3.CHANGED", "USD", getDate("03.09.2016")));
		th.add(new Transaction("id3", BigDecimal.valueOf(20.12), "ACC3.CHANGED1", "USD", getDate("03.08.2016")));

		System.out.println("--- Unique values ---");
		final Collection<Transaction> values = th.getValues();
		assertEquals(4, values.size());
		
		for (Transaction t : values) {
			System.out.println(t);
		}
		
		System.out.println("--- Duplicated values ---");
		final Collection<Transaction> duplicatedValues = th.getDuplicatedValues();
		assertEquals(2, duplicatedValues.size());
		for (Transaction t : duplicatedValues) {
			System.out.println(t);
		}
		
	}
	
	private Date getDate(String date) throws ParseException {
		return new SimpleDateFormat("dd.MM.yyyy").parse(date);
	}

}
