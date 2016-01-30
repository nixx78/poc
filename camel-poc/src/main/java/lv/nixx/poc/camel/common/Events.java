package lv.nixx.poc.camel.common;

import org.apache.camel.main.MainListenerSupport;
import org.apache.camel.main.MainSupport;

public class Events extends MainListenerSupport {
	
	@Override
	public void afterStart(MainSupport main) {
		System.out.println("MainExample with Camel is now started!");
	}

	@Override
	public void beforeStop(MainSupport main) {
		System.out.println("MainExample with Camel is now being stopped!");
	}
}