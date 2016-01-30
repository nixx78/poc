package lv.nixx.poc.camel.files.jaxb;

import org.apache.camel.Exchange;

public class ErrorHandler {
	
	public void handleError(Exchange exchange){
		System.out.println(exchange);
	}

}
