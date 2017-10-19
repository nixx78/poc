package lv.nixx.poc.spring5poc.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

	private final String id;
	private final BigDecimal amount;
	private final Status status;
	
	public Transaction(@JsonProperty("id") String id, @JsonProperty("amount") BigDecimal amount, @JsonProperty("status") String status) {
		this.id = id;
		this.amount = amount;
		this.status = Status.valueOf(status);
	}

	public String getId() {
		return id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Status getStatus() {
		return status;
	}
	
}
