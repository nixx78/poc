package lv.nixx.poc.cucumber.stepdef.transaction;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lv.nixx.poc.cucumber.transaction.CountBy;
import lv.nixx.poc.cucumber.transaction.Transaction;
import lv.nixx.poc.cucumber.transaction.TransactionDao;
import lv.nixx.poc.cucumber.transaction.TransactionReport;
import lv.nixx.poc.cucumber.transaction.TransactionReportService;

public class TransactionReportSteps {
	
	private TransactionReport actualReport;
	
	@Mock
	private TransactionDao dao;
	
	@Mock
	private TransactionReportService service;

	@Given("^Transactions exists:$")
	public void transactionsExists(DataTable table) {
		Collection<Transaction> c = new ArrayList<>();
		for (Map<String, String> row : table.asMaps(String.class, String.class)) {
	        Transaction t = new Transaction();
	        t.setId(Long.valueOf(row.get("id")));
	        t.setAmount(BigDecimal.valueOf(Double.valueOf(row.get("amount"))));
	        t.setCurrency(row.get("currency"));
	        try {
				t.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(row.get("date")));
			} catch (ParseException e) {
			}
	        c.add(t);
	    }
		doReturn(c).when(dao).getTransactions(null, null);
	}

	@Given("^Transaction report service is available$")
	public void transactionServiceCreated() throws Throwable {
		MockitoAnnotations.initMocks(this);
		service = new TransactionReportService();
		service.setDao(dao);
	}
	
	@When("^create report with date range: from \\\"([^\\\"]*)\\\" to \\\"([^\\\"]*)\\\" and count by field \\\"([^\\\"]*)\\\"$")
	public void createReport(String dateFrom, String dateTo, String countBy) {
		this.actualReport = service.createReport(null, null, CountBy.valueOf(countBy));
		assertNotNull(this.actualReport);
	}
	
	@Then("^expect report with following data:$")
	public void checkReportRows(DataTable table) {
		
		Map<String, BigDecimal> expectedCounts = new HashMap<>();
		
		for (Map<String, String> row : table.asMaps(String.class, String.class)) {
			final String curr = row.get("currency");
			final String expectedCount = row.get("count");

			BigDecimal bd = null;
			if (actualReport.getCountField() == CountBy.Count) {
				Long v = Long.valueOf(expectedCount);
				bd = BigDecimal.valueOf(v);
			} else if (actualReport.getCountField() == CountBy.Amount) {
				Double v = Double.valueOf(expectedCount);
				bd = BigDecimal.valueOf(v);
			}
			
			expectedCounts.put(curr, bd);
		}
		
		Map<String, BigDecimal> actualCounts = this.actualReport.getCurrency();
		
		assertThat(actualCounts.entrySet(), equalTo(expectedCounts.entrySet()));
	}
	
	@Then("^expect total transaction count (\\\\d+)$\"")
	public void checkTotal(int count) {
		
	}
	

}
