package lv.nixx.poc.hazelcast;

import org.junit.Test;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.map.MapInterceptor;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryRemovedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;
import com.hazelcast.test.TestHazelcastInstanceFactory;


public class HazelcastListenerSanbox {

	private HazelcastInstance hazelcastInstance = new TestHazelcastInstanceFactory().newHazelcastInstance();
	
	@Test
	public void entryListenerTest() {
		// https://docs.hazelcast.org/docs/latest-development/manual/html/Distributed_Events/Event_Listener_for_Members/Listening_for_Map_Events.html
		
		IMap<String, String> map = hazelcastInstance.getMap("abc");
		map.addEntryListener(new MyEntryListener(), true);
//		map.addInterceptor(new MapInterceptorImpl());		
		
		map.put("One", "One.value");
		map.replace("One", "One.Replaced");
		map.remove("One");
		
		map.put("Two", "Initial.Value");
		map.merge("Two", "UpdatedValue", (t1, t2) -> t1 + ":" + t2);
		
		map.computeIfAbsent("Three", t1 -> "Three.Value");
		
		map.delete("Two"); // oldValue will be null
		
	}
	
	class MyEntryListener implements EntryAddedListener<String, String>, 
									 EntryUpdatedListener<String, String>,
									 EntryRemovedListener<String, String>{
		
		@Override
		public void entryAdded(EntryEvent<String, String> entryEvent) {
			System.out.println(entryEvent);
		}

		@Override
		public void entryUpdated(EntryEvent<String, String> event) {
			System.out.println(event);
		}

		@Override
		public void entryRemoved(EntryEvent<String, String> event) {
			System.out.println(event);
		}
	}
	
	class MapInterceptorImpl implements MapInterceptor {

		@Override
		public Object interceptGet(Object value) {
			System.out.println("interceptGet: " + value);
			return value;
		}

		@Override
		public void afterGet(Object value) {
			System.out.println("afterGet: " + value);
		}

		@Override
		public Object interceptPut(Object oldValue, Object newValue) {
			System.out.println("interceptPut, oldValue: " + oldValue + " newValue: " + newValue);
			return newValue;
		}

		@Override
		public void afterPut(Object value) {
			System.out.println("afterPut: " + value);
		}

		@Override
		public Object interceptRemove(Object removedValue) {
			System.out.println("interceptRemove: " + removedValue);
			return removedValue;
		}

		@Override
		public void afterRemove(Object oldValue) {
			System.out.println("afterRemove: " + oldValue);
		}
		
	}


}
