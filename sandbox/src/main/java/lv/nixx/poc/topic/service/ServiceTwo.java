package lv.nixx.poc.topic.service;

import lv.nixx.poc.topic.MessageWrapper;
import lv.nixx.poc.topic.Subscriber;
import lv.nixx.poc.topic.dto.UIMessageTwo;

public class ServiceTwo implements Subscriber<UIMessageTwo> {

	@Override
	public void send(MessageWrapper<UIMessageTwo> message) {
		System.out.println("ServiceTwo:" + message.getMessage());
	}

}
