package lv.nixx.poc.camel.simple;

import lv.nixx.poc.camel.common.CamelRunner;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Apache Camel Sample
 * 
 * 1. Apache Camel standalone application (Main class) 2. File: read -> move to
 * processed -> write to output
 * 
 * 
 * @author Nikolajs
 * 
 */

public class FileCopierWithCamel {

	public static void main(String args[]) throws Exception {
		CamelRunner runner = new CamelRunner();
		
		runner.main().addRouteBuilder(new RouteBuilder() {
			public void configure() {
				from("file:data/inbox?move=../done/").convertBodyTo(String.class).process("contentProcessor").to("file:data/outbox");
			}
		});
		runner.main().bind("contentProcessor", new FileContentProcessor());

		runner.run();
	}

	static class FileContentProcessor implements Processor {
		@Override
		public void process(Exchange exchange) throws Exception {
			Message in = exchange.getIn();

			String fileName = String.valueOf(in.getHeader("CamelFileName"));
			String body = in.getBody(String.class);
			System.out.println("Process file [" + fileName + "] body [" + body + "]");
			
			in.setBody(body + ".processed", String.class);
			in.setHeader("CamelFileName", "proc." + fileName );
		}
	}

}
