package lv.nixx.poc.java8.collection.txn;

import java.util.*;

public class Account {
	
	private String id;
	private List<Transaction> txn = new ArrayList<>();
	
	public Account(String id, List<Transaction> txn) {
		this.id = id;
		this.txn = txn;
	}
	
	public Collection<Transaction> getTxns() {
		return txn;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + "]";
	}
	
}
