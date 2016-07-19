package lv.nixx.poc.camel.integration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.spring.Main;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lv.nixx.poc.camel.domain.BigTransactionReport;

@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class Application extends SingleRouteCamelConfiguration {

	public static void main(String[] args) throws Exception {
		new Main().run(args);
	}
	
	@Override
	@Bean
    public List<RouteBuilder> routes() {
		return Arrays.asList(new FileUploadRoute(), new RestConfiguration());
	}	
	
	@Bean
	public JaxbDataFormat model() {
		return new JaxbDataFormat("lv.nixx.poc.camel.domain");
	}
		
	class FileUploadRoute extends RouteBuilder {
		
		private static final String ERROR_TYPE_HEADER = "error_type";

		public void configure() {
			
			onException(IOException.class).setHeader(ERROR_TYPE_HEADER, simple("ParseException")).id("ParseExceptionRoute").to("direct:error-route");
			onException(BussinessError.class).setHeader(ERROR_TYPE_HEADER, simple("BussinessError")).id("BussinessExceptionRoute").to("direct:error-route");
			
			from("file:data/inbox?move=../done/&moveFailed=../error/").id("MainRoute")
			.unmarshal("model")
			.log("Start processing file: ${file:name}")
			.wireTap("direct:statistic").copy()
			.split(simple("${body.transactionList}"))
			.process("transactionProcessor")
			.choice()
				.when( simple("${header.isBigTransaction}")).wireTap("direct:bigTransactions").copy().id("BigTransaction")
				.endChoice()
	        .setBody(simple("insert into trans_table(id, source_account, target_account, amount, is_big_transaction) "	        		
	        				+ "values(${body.id}, '${body.sourceAccount}', '${body.targetAccount}', ${body.amount}, ${header.isBigTransaction})"))
	        .to("jdbc:dataSource")
	        .end();
			
			from("direct:statistic")
			.process("statisticsProcessor")
			.setBody(simple("insert into stat_table(file_name, count) values('${body.fileName}', ${body.count})"))
	        .to("jdbc:dataSource")
			.end();
			
			DataFormat df = new BindyCsvDataFormat(BigTransactionReport.class);
			
			from("direct:bigTransactions")
			.process("reportCreator")
			.marshal(df)
			.to("file:data/big_transactions?fileName=${file:name.noext}.big_txn&fileExist=Append")
			.end();
			
			from("direct:error-route")
			.choice()
				.when(header(ERROR_TYPE_HEADER).isEqualTo("ParseException")).log("####### ParseException")
			.otherwise()	
				.when(header(ERROR_TYPE_HEADER).isEqualTo("BussinessError")).log("####### BussinessError")
			.end()	
			.process("errorProcessor")
			.end();

		}
	}	
	
	class RestConfiguration extends RouteBuilder {
		public void configure() {
			
			restConfiguration()
			.component("restlet")
			.host("localhost")
			.port(8080)
			.bindingMode(RestBindingMode.auto);
			
			rest("/transaction/statistic").get().to("direct:getStatistics");
			rest("/transaction/all").get().to("direct:allTransactions");
			
	        from("direct:getStatistics")
            .setBody(simple("select * from stat_table"))
            .to("jdbc:dataSource");
	        
	        from("direct:allTransactions")
            .setBody(simple("select * from trans_table"))
            .to("jdbc:dataSource");

		}
	}	

	@Override
	@Bean
	public RouteBuilder route() {
		return null;
	}

}
