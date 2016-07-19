package lv.nixx.poc.camel.integration;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lv.nixx.poc.camel.domain.Statistic;
import lv.nixx.poc.camel.domain.TransactionList;

@Component
public class StatisticsProcessor implements Processor{
	
	private Logger LOG = LoggerFactory.getLogger(getClass());

	@Override
	public void process(Exchange exchange) throws Exception {
		Message msg = exchange.getIn();
		TransactionList body = msg.getBody(TransactionList.class);
		
		final String fileName = msg.getHeader("CamelFileName", String.class);
		final int size = body.getTransactionList().size();
		LOG.info("File [{}] element count [{}]", fileName,  size);
		msg.setBody(new Statistic(fileName, size));
	}

}
