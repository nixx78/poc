package lv.nixx.poc.guice;

import javax.inject.Inject;

public class RequestFacade {

	@Inject
	@LT
	Service ltService;
	
	@Inject
	@LV
	Service lvService;
	
	public String processRequest(String country, String request) {
		return ( country.equalsIgnoreCase("lv") ? lvService : ltService).processRequest(request);
	}
}
