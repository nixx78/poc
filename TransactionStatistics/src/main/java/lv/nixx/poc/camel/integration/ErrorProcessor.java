package lv.nixx.poc.camel.integration;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ErrorProcessor implements Processor {
	
	Logger LOG = LoggerFactory.getLogger(getClass());

	@Override
	public void process(Exchange exchange) throws Exception {
		final Message message = exchange.getIn();
		
		LOG.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		LOG.error("Error processing message [{}]", message);
		LOG.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		String fileName = message.getHeader("CamelFileName", String.class);
		message.setHeader("CamelFileName", "person." + fileName );

	}

}
