package lv.nixx.poc.topic.service;

import lv.nixx.poc.topic.MessageWrapper;
import lv.nixx.poc.topic.Subscriber;
import lv.nixx.poc.topic.dto.UIMessageThree;

public class ServiceThree implements Subscriber<UIMessageThree> {

	@Override
	public void send(MessageWrapper<?> message) {
		System.out.println("ServiceThree:" + message.getMessage());
		
	}


}
