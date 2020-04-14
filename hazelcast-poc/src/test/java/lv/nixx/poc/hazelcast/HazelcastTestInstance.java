package lv.nixx.poc.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.test.TestHazelcastInstanceFactory;

public class HazelcastTestInstance {

    public static HazelcastInstance get() {
        return new TestHazelcastInstanceFactory().newHazelcastInstance();
    }

    public static HazelcastInstance get(Config config) {
        return new TestHazelcastInstanceFactory().newHazelcastInstance(config);
    }

}
