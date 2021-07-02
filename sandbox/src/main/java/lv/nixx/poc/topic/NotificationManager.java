package lv.nixx.poc.topic;

import java.util.HashMap;
import java.util.Map;

import lv.nixx.poc.topic.dto.*;
import lv.nixx.poc.topic.service.*;

public class NotificationManager {
	
	private final Map<Class<? extends UIMessage>, Subscriber<?>> notifications = new HashMap<>();
	
	public NotificationManager() {
		notifications.put(UIMessageOne.class, new ServiceOne());
		notifications.put(UIMessageTwo.class, new ServiceTwo());
		notifications.put(UIMessageThree.class, new ServiceThree());
	}

	public void notifyAbout(UIMessage message) {
		MessageWrapper<UIMessage> mw = new MessageWrapper<>(message);
		notifications.get(message.getClass()).send(mw);
	}

}

