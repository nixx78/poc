package lv.nixx.poc.hazelcast;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hazelcast.core.*;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.test.*;

public class HazelcastSandbox {
	
	HazelcastInstance hazelcastInstance = new TestHazelcastInstanceFactory().newHazelcastInstance();

	@Test
	public void testInstance() {
		IMap<String, String> map = hazelcastInstance.getMap("abc");
		map.put("One", "One.value");
		
		String one = String.valueOf(hazelcastInstance.getMap("abc").get("One"));
		assertEquals("One.value", one);
	}
	
	@Test
	public void entryListenerTest() {
		// https://docs.hazelcast.org/docs/latest-development/manual/html/Distributed_Events/Event_Listener_for_Members/Listening_for_Map_Events.html
		
		IMap<String, String> map = hazelcastInstance.getMap("abc");
		map.addEntryListener(new MyEntryListener(), true);
		map.put("One", "One.value");
	}
	
	class MyEntryListener implements EntryAddedListener<String, String> {
		@Override
		public void entryAdded(EntryEvent<String, String> entryEvent) {
			System.out.println(entryEvent);
		}
	}

}
