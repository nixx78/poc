package lv.nixx.poc.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class MDCUsageSample {

	 static public void main(String[] args) throws Exception {

		    // You can put values in the MDC at any time. Before anything else
		    // we put the first name
		    MDC.put("first", "Dorothy");

		    Logger logger = LoggerFactory.getLogger("MDCUsageSample");
		    // We now put the last name
		    MDC.put("last", "Parker");

		    // The most beautiful two words in the English language according
		    // to Dorothy Parker:
		    logger.trace("Check enclosed.");
		    logger.trace("The most beautiful two words in English.");

		    MDC.put("first", "Richard");
		    MDC.put("last", "Nixon");
		    logger.trace("I am not a crook.");
		    logger.trace("Attributed to the former US president. 17 Nov 1973.");
		  }

}
