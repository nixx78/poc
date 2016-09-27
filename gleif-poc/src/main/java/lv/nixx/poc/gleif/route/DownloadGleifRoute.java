package lv.nixx.poc.gleif.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lv.nixx.poc.gleif.processor.PostProcessProcessor;

//@Component
public class DownloadGleifRoute extends RouteBuilder {
	
	@Autowired
	PostProcessProcessor postProcessProcessor; 

	@Override
	public void configure() throws Exception {
		//  @formatter:off
		from("timer://gleifDownloadTimer?period=2000&repeatCount=2").id("DownloadGLEIFFile")
		.setHeader("isAlreadyProcessed", simple(String.valueOf(postProcessProcessor.isProcessed())))
		.setHeader("tryCount", simple(String.valueOf(postProcessProcessor.getTryCount())))
		.choice()
			.when( header("isAlreadyProcessed").isEqualTo(false)).to("direct:not_processed").endChoice()
			.when( header("isAlreadyProcessed").isEqualTo(true)).to("direct:already_processed").endChoice()
		.end();

		from("direct:not_processed")
		.setHeader("date", simple(getGleifFileDate()))
		.setHeader(Exchange.FILE_NAME).simple("${header.date}-GLEIF-concatenated-file.zip")
		.log("Try to download file [${header." + Exchange.FILE_NAME + "}] try [${header.tryCount}]")
		 .setHeader(Exchange.HTTP_URI, simple("https://www.gleif.org/lei-files/${header.date}/GLEIF/${header."+ Exchange.FILE_NAME + "}"))
		.to("https://dummyhost") // This host will be changed to HTTP_URI host
		.to("file:/tmp/gleif/zip")
		.bean("unzipProcessor")
		.log("File downloaded and unzipped")
		.process(postProcessProcessor)
		.end();
		
		from("direct:already_processed")
		.log("File already downloaded and unzipped")
		.end();
		
		// @formatter:on
	}
	
	public static String getGleifFileDate() {
		return "20160919";
	}


}
