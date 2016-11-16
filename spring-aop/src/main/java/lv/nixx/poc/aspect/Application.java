package lv.nixx.poc.aspect;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
	
	// Запускается просто JVM, ничего дополнительно не нужно, weawing происходит при помощи JDK Dynamic Proxy
	public static void main(String[] args) {
		
		ConfigurableApplicationContext appContext = null;
		try  {
			appContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
			final BussinessService service = appContext.getBean(BussinessService.class);
		
			System.out.println(service.process1());
			System.out.println(service.process2("str", 10));

		} finally {
			if (appContext!=null)appContext.close();
		}
	}

}
