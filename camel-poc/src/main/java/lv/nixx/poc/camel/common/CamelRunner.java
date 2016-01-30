package lv.nixx.poc.camel.common;

import org.apache.camel.main.Main;

public class CamelRunner {
	
	private Main main;
	
	public CamelRunner(){
		main = new Main();
	}
	
	public Main main() {
		return main;
	}
	
	public void run() throws Exception {
		System.out.println("Starting Camel. Use ctrl + c to terminate the JVM.\n");
		main.addMainListener(new Events());
		main.run();
	}

}
