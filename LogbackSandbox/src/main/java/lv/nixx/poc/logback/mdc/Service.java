package lv.nixx.poc.logback.mdc;

import org.slf4j.*;

public class Service {
	
	static Logger log = LoggerFactory.getLogger("Service");
	
	Component1 c1 = new Component1();
	Component2 c2 = new Component2();
	
	public void process(String userId, String request) {
		MDC.put("userId", userId);
		log.debug("service process, user [{}] request [{}]", userId,  request);
		
		ThreadLocalHolder.storeValue(userId + ":" + request);
		
		c1.process(request);
		c2.process(request);
	}

}
