package lv.nixx.poc.hazelcast;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hazelcast.core.*;
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

}
