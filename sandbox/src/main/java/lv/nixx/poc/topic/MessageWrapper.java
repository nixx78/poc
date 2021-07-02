package lv.nixx.poc.topic;

import lv.nixx.poc.topic.dto.UIMessage;

public class MessageWrapper<T extends UIMessage> {
	
	private final T message;
	
	public MessageWrapper(T message) {
		this.message = message;
	}
	
	public T getMessage() {
		return message;
	}
	

}
