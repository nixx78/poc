package lv.nixx.poc.camel.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="transaction")
@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction {

	@XmlElement(name="id")
	private Integer id;
	
	private Date date;

	@XmlElement(name="amount")
	@XmlJavaTypeAdapter(BigDecimalAdapter.class)
	private BigDecimal amount;
	
	@XmlElement(name="source")
	private String sourceAccount;
	
	@XmlElement(name="target")
	private String targetAccount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public String getTargetAccount() {
		return targetAccount;
	}

	public void setTargetAccount(String targetAccount) {
		this.targetAccount = targetAccount;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", date=" + date + ", amount=" + amount + ", sourceAccount=" + sourceAccount
				+ ", targetAccount=" + targetAccount + "]";
	}
	
}
