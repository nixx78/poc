package lv.nixx.poc.gleif.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProcessGleifXMLFileRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// @formatter:off
		from("file:/tmp/gleif/unzip?move=../done/").id("ProcessGLEIFFile")
		.transacted()
		.bean("gleifModelCreator")
		.log("LouCount [${body.louCount}] LeiCount [${body.leiCount}]")
		.end();
		// @formatter:on
	}

}
