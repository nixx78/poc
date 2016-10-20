package lv.nixx.poc.java8.collection.txn;

import java.math.BigDecimal;
import java.util.Date;

class Transaction implements Comparable<Transaction>{

	private String id;
	private BigDecimal amount;
	private String account;
	private String currency;
	private Date lastUpdateDate;

	public Transaction(String id, BigDecimal amout, String account, String currency, Date lastUpdateDate) {
		this.id = id;
		this.amount = amout;
		this.account = account;
		this.currency = currency;
		this.lastUpdateDate = lastUpdateDate;
	}

	public Transaction(String id, BigDecimal amout, String account, String currency) {
		this.id = id;
		this.amount = amout;
		this.account = account;
		this.currency = currency;
	}
	
	public String getId() {
		return id;
	}

	public String getAccount() {
		return account;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", amount=" + amount + ", account=" + account + ", currency=" + currency
				+ ", lastUpdateDate=" + lastUpdateDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastUpdateDate == null) {
			if (other.lastUpdateDate != null)
				return false;
		} else if (!lastUpdateDate.equals(other.lastUpdateDate))
			return false;
		return true;
	}

	@Override
	public int compareTo(Transaction t) {
		return this.getLastUpdateDate().compareTo(t.getLastUpdateDate());
	}

}