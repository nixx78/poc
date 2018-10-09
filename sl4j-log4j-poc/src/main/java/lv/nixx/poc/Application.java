package lv.nixx.poc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
	
	
	private static final Logger log = LoggerFactory.getLogger("AppLogger");
	
	public static void main(String[] args) {
		log.debug("Simple message DEBUG");
		log.debug("Simple message SYSTEM_X DEBUG");
		log.info("INFO message");
		log.error("ERROR message");
		log.error("Exclude string EX1000");
		
		log.error("SYSTEM_X", new Exception("Simple exception"));
	}

}
