package lv.nixx.poc.camel.files.jaxb;

import lv.nixx.poc.camel.model.Person;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorHandler {
	
	private Logger LOG = LoggerFactory.getLogger(getClass());
	
	public void handleError(Exchange exchange, Exception ex) {
		LOG.error(ex.getMessage());
		LOG.error("Errorneous message [{}]", exchange.getIn().getBody(Person.class));
	}

}
