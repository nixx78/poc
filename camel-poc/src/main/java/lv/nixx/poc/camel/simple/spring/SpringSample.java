package lv.nixx.poc.camel.simple.spring;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.Main;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@ComponentScan(basePackages = {"lv.nixx.poc.camel.simple.spring"})
public class SpringSample extends SingleRouteCamelConfiguration {
	
	public static void main(String[] args) throws Exception {
		new Main().run();
	}
	
	@Bean
	public MessageBean messageABean(){
		return new MessageBean("A");
	}
	
	@Bean
	public MessageBean messageBBean(){
		return new MessageBean("B");
	}
	
	@Bean
	public MessageBean messageCBean(){
		return new MessageBean("C");
	}

    @Override
    protected CamelContext createCamelContext() throws Exception {
        return new SpringCamelContext(getApplicationContext());
    }
    
    @Bean
	@Override
	public RouteBuilder route() {
		final RouteBuilder routeBuilder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("timer://myTimer?period=2000")
				.routeId("MainRoute")
				.log("Message")
				.bean("messageABean")
				.bean("messageBBean")
				.bean("messageCBean")
				.end();
				
			}
		};
		
		return routeBuilder;
	}

	
}
