package lv.nixx.poc.topic.service;

import lv.nixx.poc.topic.MessageWrapper;
import lv.nixx.poc.topic.Subscriber;
import lv.nixx.poc.topic.dto.UIMessageOne;

public class ServiceOne implements Subscriber<UIMessageOne> {

	@Override
	public void send(MessageWrapper<UIMessageOne> message) {
		System.out.println("ServiceOne:" + message.getMessage());
	}

}
