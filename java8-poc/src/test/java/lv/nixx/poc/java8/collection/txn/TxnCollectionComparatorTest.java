package lv.nixx.poc.java8.collection.txn;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import lv.nixx.poc.java8.collection.txn.Transaction;
import lv.nixx.poc.java8.collection.txn.TxnCollectionComparator;
import lv.nixx.poc.java8.collection.txn.TxnHolder;

public class TxnCollectionComparatorTest {
	
	@Test
	@Ignore
	public void performanceTest() throws ParseException {
		
		final int updatedCount = 2000000;
		final int newCount = 100000;
		final int deletedCount = 30000;

		TxnHolder existingTxn = new TxnHolder();
		TxnHolder newTxn = new TxnHolder();
		
		long stime = System.currentTimeMillis(); 

		for (int i = 0; i < updatedCount; i++) {
			existingTxn.add(new Transaction("id" + i, BigDecimal.valueOf(10.10), "ACC1", "USD", getDate("01.09.2016")));
			newTxn.add(new Transaction("id" + i, BigDecimal.valueOf(10.10), "ACC2", "USD", getDate("01.09.2016")));
		}

		for (int i = 0; i < newCount; i++) {
			newTxn.add(new Transaction("nid" + i, BigDecimal.valueOf(10.10), "ACC2", "USD", getDate("01.09.2016")));
		}
		
		for (int i = 0; i < deletedCount; i++) {
			existingTxn.add(new Transaction("did" + i, BigDecimal.valueOf(10.10), "ACC2", "USD", getDate("01.09.2016")));
		}
		
		System.out.println("Existing txn element count: " + existingTxn.size());
		System.out.println("New txn element count: " + newTxn.size());
		System.out.println("Test data creation time: " + (System.currentTimeMillis() - stime));
		
		stime = System.currentTimeMillis(); 
		
		TxnCollectionComparator comparator = new TxnCollectionComparator();
		comparator.compareCollections(existingTxn, newTxn);
		
		System.out.println("Comparator processing time: " + (System.currentTimeMillis() - stime));
		
		assertEquals(updatedCount, comparator.getUpdatedRecords().size());
		assertEquals(newCount, comparator.getNewRecords().size());
		assertEquals(deletedCount, comparator.getDeletedRecordsKeys().size());
	}
	

	@Test
	public void getChangedRecords() throws ParseException {
		TxnHolder existingTxn = new TxnHolder();
		existingTxn.add(new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD", getDate("01.09.2016")));
		existingTxn.add(new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD", getDate("01.09.2016")));
		existingTxn.add(new Transaction("id4", BigDecimal.valueOf(20.12), "ACC2.DELETED", "USD", getDate("01.09.2016")));

		TxnHolder newTxn = new TxnHolder();
		newTxn.add(new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1.CHANGED", "USD", getDate("01.09.2016")));
		newTxn.add(new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD", getDate("01.08.2016")));
		newTxn.add(new Transaction("id3", BigDecimal.valueOf(20.12), "ACC3", "USD", getDate("03.09.2016")));
		newTxn.add(new Transaction("id6", BigDecimal.valueOf(20.12), "ACC3.NEW", "USD", getDate("03.09.2016")));

		TxnCollectionComparator comparator = new TxnCollectionComparator();
		comparator.compareCollections(existingTxn, newTxn);
		
		
		System.out.println(" -- New --");
		final Collection<Transaction> newRecords = comparator.getNewRecords();
		assertEquals(2, newRecords.size());
		for (Transaction t : newRecords) {
			System.out.println(t);
		}
		
		System.out.println(" -- Updated --");
		final Collection<Transaction> updatedRecords = comparator.getUpdatedRecords();
		for (Transaction t : updatedRecords) {
			System.out.println(t);
		}
		assertEquals(1, updatedRecords.size());
		
		System.out.println(" -- Deleted --");
		final Collection<String> deletedRecordsKeys = comparator.getDeletedRecordsKeys();
		for (String t : deletedRecordsKeys) {
			System.out.println(t);
		}
		
		assertEquals(1, deletedRecordsKeys.size() );
		
	}

	private Date getDate(String date) throws ParseException {
		return new SimpleDateFormat("dd.MM.yyyy").parse(date);
	}

}
