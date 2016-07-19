package lv.nixx.poc.camel.integration;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.model.language.SimpleExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lv.nixx.poc.camel.domain.BigTransactionReport;
import lv.nixx.poc.camel.domain.Transaction;

@Component
public class ReportCreator implements Processor {

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Override
	public void process(Exchange exchange) throws Exception {
		Message msg = exchange.getIn();
		Transaction txn = msg.getBody(Transaction.class);

		LOG.info("Transaction id: [{}] is big", txn.getId() );
		String fileName = new SimpleExpression("${file:name}").evaluate(exchange, String.class);
		
		BigTransactionReport r = new BigTransactionReport(txn.getId(), fileName, txn.getAmount());
		msg.setBody(r, BigTransactionReport.class);
	}
}
