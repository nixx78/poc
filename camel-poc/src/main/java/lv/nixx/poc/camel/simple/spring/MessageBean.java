package lv.nixx.poc.camel.simple.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MessageBean {

	private String message;
	
	@Autowired
	private MessagePrinter printer;

	public MessageBean(){
	}
	
	public MessageBean(String message) {
		this.message = message;
	}
	
	public void sayMessage() {
		printer.print("Say: " + message);
	}

}
