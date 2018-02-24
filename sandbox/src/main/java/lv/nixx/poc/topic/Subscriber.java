package lv.nixx.poc.topic;

public interface Subscriber<T> {
	
	void send(MessageWrapper<T> message);

}
