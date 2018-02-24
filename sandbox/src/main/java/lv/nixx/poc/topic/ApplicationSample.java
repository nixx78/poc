package lv.nixx.poc.topic;

import lv.nixx.poc.topic.dto.*;

public class ApplicationSample {
	
	public static void main(String[] args) {
		NotificationManager manager = new NotificationManager();

		manager.notifyAbout(new UIMessageOne("message.value1"));
		manager.notifyAbout(new UIMessageTwo("message.value2"));
		manager.notifyAbout(new UIMessageThree("message.value3"));
	}

}
