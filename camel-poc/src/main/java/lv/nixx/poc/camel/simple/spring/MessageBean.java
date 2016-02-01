package lv.nixx.poc.camel.simple.spring;

import org.springframework.stereotype.Component;


@Component
public class MessageBean {

	private String message;

	public MessageBean(){
	}
	
	public MessageBean(String message) {
		this.message = message;
	}
	
	public void sayMessage() {
		System.out.println("Say: " + message);
	}

}
