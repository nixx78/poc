package lv.nixx.poc.guice;

import javax.inject.Inject;

import lv.nixx.poc.guice.annotations.LT;
import lv.nixx.poc.guice.annotations.LV;
import lv.nixx.poc.guice.service.ServiceInterface;

public class RequestFacade {

	@Inject
	@LT
	ServiceInterface ltService;
	
	@Inject
	@LV
	ServiceInterface lvService;
	
	public String processRequest(String country, String request) {
		return ( country.equalsIgnoreCase("lv") ? lvService : ltService).processRequest(request);
	}
}
