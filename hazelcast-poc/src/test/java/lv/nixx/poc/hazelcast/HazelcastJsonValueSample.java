package lv.nixx.poc.hazelcast;

import com.hazelcast.aggregation.Aggregators;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicates;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HazelcastJsonValueSample {

    private final HazelcastInstance hz = HazelcastTestInstance.get();

    @Test
    public void storeAndRetrieveSample() {
        String t1 = "{ \"curr\": \"USD\", \"amount\": 1.05 }";
        String t2 = "{ \"curr\": \"EUR\", \"amount\": 10.34 }";
        String t3 = "{ \"curr\": \"EURs\", \"amount\": 15 }";

        IMap<Integer, HazelcastJsonValue> idPersonMap = hz.getMap("currencyJsonValues");
        idPersonMap.put(1, new HazelcastJsonValue(t1));
        idPersonMap.put(2, new HazelcastJsonValue(t2));
        idPersonMap.put(3, new HazelcastJsonValue(t3));

        Collection<HazelcastJsonValue> filteredTxns = idPersonMap.values(Predicates.lessThan("amount", 10));
        System.out.println(filteredTxns);
        assertEquals(1, filteredTxns.size());
    }

    @Test
    public void searchInJsonValue() {

        IMap<String, HazelcastJsonValue> m = hz.getMap("valuesWithPos");

        m.putAll(Map.of(
                "v1.1", new HazelcastJsonValue("{ \"value\": \"v1\", \"pos\": 1 }"),
                "v1.2", new HazelcastJsonValue("{ \"value\": \"v1\", \"pos\": 2 }"),
                "v1.5", new HazelcastJsonValue("{ \"value\": \"v1\", \"pos\": 5 }"),
                "v2.1", new HazelcastJsonValue("{ \"value\": \"v2\", \"pos\": 1 }"),
                "v2.2", new HazelcastJsonValue("{ \"value\": \"v2\", \"pos\": 2 }")
        ));

        String vKey = "v1";
        Long aggregate = m.aggregate(Aggregators.longMax("pos"),
                Predicates.and(Predicates.equal("value", vKey), Predicates.lessThan("pos", 5)));

        assertEquals(Long.valueOf(2), aggregate);

        Collection<HazelcastJsonValue> value =
                m.values(
                        Predicates.and(Predicates.equal("value", vKey), Predicates.equal("pos", 1))
                );

        assertEquals("{ \"value\": \"v1\", \"pos\": 1 }", value.iterator().next().toString());
    }

}
