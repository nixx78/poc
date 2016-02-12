package lv.nixx.poc.camel.jdbc;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.Main;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages = {"lv.nixx.poc.camel.jdbc"})
public class File2DBSample extends SingleRouteCamelConfiguration {

	public static void main(String args[]) throws Exception {
		new Main().run();
	}

	@Override
	public RouteBuilder route() {
		return new RouteBuilder() {
			
			public void configure() {
				
				from("timer://file2DbTimer?period=2000")
				.setBody().simple("A\nB\nC\nD")
				.split().tokenize("\n")
				.log("Save [" + body() + "]")
		        .setBody(simple("insert into person(firstName, lastName) values('${body}','test')"))
		        .to("jdbc:dataSource")
		        .end();
				
				from("timer://selectTimer?period=5000")
	            .setBody(simple("select count(*) from person"))
	            .to("jdbc:dataSource")
	            .log("!!!!!!!!!!!!! RecordCount [" + body() + "] !!!!!!!!!!!!!!!")
	            .end();
				
			}
		};
	}
}
