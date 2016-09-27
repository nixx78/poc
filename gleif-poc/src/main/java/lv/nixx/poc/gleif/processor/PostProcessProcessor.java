package lv.nixx.poc.gleif.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component( "postProcess")
public class PostProcessProcessor implements Processor {
	
	private int tryCount = 1;
	private boolean isProcessed = false;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		tryCount++;
		isProcessed = true;
	}

	public boolean isProcessed() {
		return isProcessed;
	}

	public int getTryCount() {
		return tryCount;
	}
	

}
