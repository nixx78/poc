package lv.nixx.poc.logback.mdc;

import org.slf4j.*;

public class Component2 {
	
	static Logger log = LoggerFactory.getLogger("Component2");
	
	public void process(String request){
		String v = ThreadLocalHolder.getValue();
		log.debug("Component2 [{}] value [{}]", request, v);
	}
}
