package lv.nixx.poc.guice;

import javax.inject.Inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class ApplicationWithGuiceInjection {

	@Inject
	public RequestFacade requestFacade;

	public static void main(String[] args) {
		
		Module bankCoreConfig = new CoreBankConfig();
		final Injector injector = Guice.createInjector(bankCoreConfig);
		
		ApplicationWithGuiceInjection app = injector.getInstance(ApplicationWithGuiceInjection.class);
		
		System.out.println(app.requestFacade.processRequest("lv", "req1"));
		System.out.println(app.requestFacade.processRequest("lt", "req2"));
		
		System.out.println(app.requestFacade.processRequest("lv", "req3"));
		System.out.println(app.requestFacade.processRequest("lv", "req4"));
		System.out.println(app.requestFacade.processRequest("lv", "req5"));
	}

}
