package lv.nixx.poc.topic;

import lv.nixx.poc.topic.dto.UIMessage;

public interface Subscriber<T extends UIMessage> {
	
	void send(MessageWrapper<?> message);

}
