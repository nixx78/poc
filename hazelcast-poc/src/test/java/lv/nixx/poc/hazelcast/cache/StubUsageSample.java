package lv.nixx.poc.hazelcast.cache;

import lv.nixx.poc.hazelcast.model.Person;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StubUsageSample {

    @Test
    public void stubSample() {

        PersonStringKeyCacheOperations stub = new PersonStringKeyCacheOperations.Stub();
        stub.add("key", new Person());

        assertEquals(1, stub.size());

    }


}
