package lv.nixx.poc.camel.simple.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomMessagePrinter implements MessagePrinter {
	
	private Logger LOG = LoggerFactory.getLogger(getClass());

	@Override
	public void print(String message) {
		LOG.info(message);
	}
	
}
