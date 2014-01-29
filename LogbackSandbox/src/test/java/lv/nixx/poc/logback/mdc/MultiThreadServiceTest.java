package lv.nixx.poc.logback.mdc;

import java.util.concurrent.*;
import org.junit.*;

/**
 * 
 * Sample, how to use Mapped Diagnostic Contexts (MDC) concept (http://logback.qos.ch/manual/mdc.html) in SLF4J/Logback
 * 
 * @author Nikolajs
 */

public class MultiThreadServiceTest {
	
	@BeforeClass
	public static void init(){
		System.setProperty("logback.configurationFile", "logback_mdc.xml");
	}

	@Test
	public void multiThreadTest() throws Exception{
		 final Service service = new Service();
				
		 ExecutorService pool = Executors.newFixedThreadPool(10);
		 pool.submit(new ServiceCaller("user1", service));
		 pool.submit(new ServiceCaller("user2", service));
		 pool.submit(new ServiceCaller("user3", service));
		 
		 pool.shutdown();
		 pool.awaitTermination(1000, TimeUnit.SECONDS);
		 
		 System.out.println("press ENTER to exit... ");
		 System.in.read();
	}
	
	class ServiceCaller implements Callable<Object> {
		
		Service service;
		String userID;
		
		ServiceCaller(String userID, Service service){
			this.service = service;
			this.userID = userID;
			
		}

		public Object call() throws Exception {
			service.process(userID, "request" + String.valueOf(System.currentTimeMillis()) + userID);
			return null;
		}
		
	}
	
}
