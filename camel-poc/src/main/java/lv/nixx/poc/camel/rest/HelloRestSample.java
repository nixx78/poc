package lv.nixx.poc.camel.rest;

import javax.servlet.http.HttpServletRequest;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import lv.nixx.poc.camel.common.CamelRunner;

public class HelloRestSample {
	
	public static void main(String[] args) throws Exception {

		CamelRunner cr = new CamelRunner();
		cr.main().addRouteBuilder(new RouteBuilder() {
			public void configure() {
				
				final int port = 8080;
				
				restConfiguration()
					.component("restlet")
					.host("localhost")
					.port(port)
					.bindingMode(RestBindingMode.auto);
				rest("/say/hello")
					.get().route().transform().constant("Hello World");
				
				from("jetty:http://localhost:${port}/myapp/myservice").process(new HelloWorldService()).end();
			}
		});
		cr.run();
	}
	
	static class HelloWorldService implements Processor {

		@Override
		public void process(Exchange exchange) throws Exception {
	        String body = exchange.getIn().getBody(String.class);
	        System.out.println(body);
	        // we have access to the HttpServletRequest here and we can grab it if we need it
	        
	        HttpServletRequest req = exchange.getIn().getBody(HttpServletRequest.class);
	        exchange.getOut().setBody("Hello Apache Camel REST");			
		}
		
	}

}
