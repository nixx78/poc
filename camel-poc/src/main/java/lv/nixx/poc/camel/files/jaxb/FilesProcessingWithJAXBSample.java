package lv.nixx.poc.camel.files.jaxb;

import lv.nixx.poc.camel.common.CamelRunner;
import lv.nixx.poc.camel.model.Response;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.XPathBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

public class FilesProcessingWithJAXBSample {

	public static void main(String args[]) throws Exception {
		
		CamelRunner runner = new CamelRunner();
		
		runner.main().bind("model", new JaxbDataFormat("lv.nixx.poc.camel.model"));
		runner.main().bind("personProcessor", new PersonProcessor());
		runner.main().bind("responseCreator", new ResponseCreator());
		
		final RouteBuilder mainRouteBuilder = new RouteBuilder() {
			public void configure() {
				from("file:data/inbox?move=../done/")
				.unmarshal("model")
				.split(new XPathBuilder("persons/person"))
				.process("personProcessor")
				.choice()
					.when( bodyAs(Response.class).method("isSuccess").isEqualTo(true)).to("direct:success").endChoice()
					.when( bodyAs(Response.class).method("isSuccess").isEqualTo(false)).to("direct:fail").endChoice();
			}
		};

		final RouteBuilder successRouteBuilder = new RouteBuilder() {
			public void configure() {
					from("direct:success")
					.aggregate(header("id"), new ResponseAggregatorStrategy()).completionTimeout(1000)
					.process("responseCreator")
					.to("file:data/ok_outbox")
					.end();
			}
		};
		
		final RouteBuilder failRouteBuilder = new RouteBuilder() {
			public void configure() {
				from("direct:fail")
				.aggregate(header("id"), new ResponseAggregatorStrategy()).completionTimeout(1000)
				.process("responseCreator")
				.to("file:data/fail_outbox")
				.end();
			}
		};
		
		runner.main().addRouteBuilder(mainRouteBuilder);
		runner.main().addRouteBuilder(successRouteBuilder);
		runner.main().addRouteBuilder(failRouteBuilder);
		runner.run();
	}

}
