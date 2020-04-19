package lv.nixx.poc.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicates;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class HazelcastJsonValueSample {

    private HazelcastInstance hz = HazelcastTestInstance.get();

    //TODO https://docs.hazelcast.org/docs/latest-dev/manual/html-single/#querying-json-strings

    @Test
    public void storeRetrieveSample() {
        String t1 = "{ \"curr\": \"USD\", \"amount\": 1.05 }";
        String t2 = "{ \"curr\": \"EUR\", \"amount\": 10.34 }";
        String t3 = "{ \"curr\": \"EURs\", \"amount\": 15 }";

        IMap<Integer, HazelcastJsonValue> idPersonMap = hz.getMap("jsonValues");
        idPersonMap.put(1, new HazelcastJsonValue(t1));
        idPersonMap.put(2, new HazelcastJsonValue(t2));
        idPersonMap.put(3, new HazelcastJsonValue(t3));

        Collection<HazelcastJsonValue> filteredTxns = idPersonMap.values(Predicates.lessThan("amount", 10));
        System.out.println(filteredTxns);
        assertEquals(1, filteredTxns.size());
    }
}
