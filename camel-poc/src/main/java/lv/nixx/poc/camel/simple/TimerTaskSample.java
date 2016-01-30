package lv.nixx.poc.camel.simple;

import java.util.Date;

import lv.nixx.poc.camel.common.CamelRunner;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class TimerTaskSample {
	
	public static void main(String args[]) throws Exception {
		
		System.setProperty("period.interval", "5000");
		
		CamelRunner runner = new CamelRunner();
		runner.main().addRouteBuilder(new RouteBuilder() {
			public void configure() {
				// Process each period, period is taken from properties
	            from("timer:simpleTimer?period={{period.interval}}").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Invoked timer at " + new Date());
                    }
                })
                .bean("first").bean("second");
	            // Beans chain call
			}
		});
		
		runner.main().bind("first", new SimpleBean());
		runner.main().bind("second", new SimpleBeanTwo());
		runner.run();
	}
	
    public static class SimpleBean {
        public void callMe() {
            System.out.println("--- SimpleBeanOne is called");
        }
    }

    public static class SimpleBeanTwo {
        public void callMe() {
            System.out.println("+++ SimpleBeanTwo is called");
        }
    }


}
