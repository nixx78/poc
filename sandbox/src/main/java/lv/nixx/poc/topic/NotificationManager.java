package lv.nixx.poc.topic;

import java.util.HashMap;
import java.util.Map;

import lv.nixx.poc.topic.dto.*;
import lv.nixx.poc.topic.service.*;

public class NotificationManager {
	
	private Map<Class<?>, Subscriber<?>> notifications = new HashMap<>();
	
	public NotificationManager() {
		notifications.put(UIMessageOne.class, new ServiceOne());
		notifications.put(UIMessageTwo.class, new ServiceTwo());
		notifications.put(UIMessageThree.class, new ServiceThree());
	}


	public void notifyAbout(UIMessageOne message) {
		MessageWrapper mw = new MessageWrapper<UIMessageOne>(message);
		notifications.get(message.getClass()).send(mw);
	}

	public void notifyAbout(UIMessageTwo message) {
		MessageWrapper mw = new MessageWrapper<UIMessageTwo>(message);
		notifications.get(message.getClass()).send(mw);	
	}

	public void notifyAbout(UIMessageThree message) {
		MessageWrapper mw = new MessageWrapper<UIMessageThree>(message);
		notifications.get(message.getClass()).send(mw);	
	}

}
