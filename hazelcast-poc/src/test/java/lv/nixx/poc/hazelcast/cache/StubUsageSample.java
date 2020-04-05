package lv.nixx.poc.hazelcast.cache;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StubUsageSample {

    @Test
    public void stubSample() {

//        PersonStringKeyCrudCacheOperations stub = new PersonStringKeyCrudCacheOperations.Stub();
//        stub.add("key", new Person());
//
//        assertEquals(1, stub.size());

        PersonStringKeyCache cache = new PersonStringKeyCache.Stub();

    }


}
