package lv.nixx.poc.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor 
@AllArgsConstructor
@EqualsAndHashCode
public class Transaction {

	private int id;
	private BigDecimal amount;
	
	@JsonProperty(required=true)
	private Currency currency;
	
	@JsonProperty("taxFee")
	public double getTax() {
		return amount.multiply(BigDecimal.valueOf(0.21)).doubleValue();
	}
	
	@JsonIgnore
	public double getInnoredField() {
		return 0.00;
	}
}
