package lv.nixx.poc.cucumber.transaction;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class TransactionReportService {

	private TransactionDao dao;
	
	
	public void setDao(TransactionDao dao) {
		this.dao = dao;
	}

	public TransactionReport createReport(Date dateFrom, Date dateTo, CountBy countField) {
		Collection<Transaction> txns = dao.getTransactions(dateFrom, dateTo);

		Collector<Transaction, ?, BigDecimal> reducingFunction = null;

		if (countField == CountBy.Count) {
			reducingFunction = Collectors.reducing(BigDecimal.ZERO, t-> BigDecimal.ONE , BigDecimal::add);
		} else if (countField == CountBy.Amount) {
			reducingFunction = Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount , BigDecimal::add);
		}
	
		Map<String, BigDecimal> lst = txns.stream()
				.collect(
				Collectors.groupingBy(Transaction::getCurrency,
						reducingFunction)
				);
		
		TransactionReport transactionReport = new TransactionReport(countField);
		transactionReport.setCurrency(lst);
		transactionReport.setTotalOperationCount(txns.size());
		
		return transactionReport;
	}

}
