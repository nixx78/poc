package lv.nixx.poc.topic;

public class MessageWrapper<T> {
	
	private T message;
	
	public MessageWrapper(T message) {
		this.message = message;
	}
	
	public T getMessage() {
		return message;
	}
	

}
