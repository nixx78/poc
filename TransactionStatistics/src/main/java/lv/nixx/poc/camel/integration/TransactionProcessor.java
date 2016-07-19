package lv.nixx.poc.camel.integration;

import java.math.BigDecimal;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lv.nixx.poc.camel.domain.Transaction;

@Component
public class TransactionProcessor implements Processor {
	
	Logger LOG = LoggerFactory.getLogger(getClass());

	@Override
	public void process(Exchange exchange) throws Exception {
		Message msg = exchange.getIn();
		Transaction txn = msg.getBody(Transaction.class);
		
		if ( txn.getId() == 5000 ) {
			throw new BussinessError("Bussiness Error for transaction");
		}
		
		msg.setHeader("isBigTransaction", txn.getAmount().compareTo(BigDecimal.valueOf(100.00)) == 1);
	}

}
