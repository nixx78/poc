package lv.nixx.poc.camel.domain;

import java.math.BigDecimal;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ",")
public class BigTransactionReport {

	@DataField(pos = 1)
	private int id;
	
	@DataField(pos = 2)
	private String fileName;
	
	@DataField(pos = 3)
	private BigDecimal amount;
	
	public BigTransactionReport(int id, String fileName, BigDecimal amount) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public String getFileName() {
		return fileName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

}
