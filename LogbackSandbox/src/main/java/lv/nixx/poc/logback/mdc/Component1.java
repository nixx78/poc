package lv.nixx.poc.logback.mdc;

import org.slf4j.*;

public class Component1 {
	
	static Logger log = LoggerFactory.getLogger("Component1");
	
	public void process(String request){
		log.debug("Component1 [{}]", request );
	}

}
