package lv.nixx.poc.hazelcast.json;

import com.hazelcast.aggregation.Aggregators;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.map.IMap;
import com.hazelcast.projection.Projections;
import com.hazelcast.query.Predicates;
import lv.nixx.poc.hazelcast.HazelcastTestInstance;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HazelcastJsonValueSample {

    private final HazelcastInstance hazelcastInstance = HazelcastTestInstance.get();

    // https://docs.hazelcast.com/hazelcast/5.0/query/querying-maps-predicates

    @Test
    public void storeAndRetrieveSample() {
        String t1 = "{ \"curr\": \"USD\", \"amount\": 1.05 }";
        String t2 = "{ \"curr\": \"EUR\", \"amount\": 10.34 }";
        String t3 = "{ \"curr\": \"EURs\", \"amount\": 15 }";

        IMap<Integer, HazelcastJsonValue> idPersonMap = hazelcastInstance.getMap("currencyJsonValues");
        idPersonMap.put(1, new HazelcastJsonValue(t1));
        idPersonMap.put(2, new HazelcastJsonValue(t2));
        idPersonMap.put(3, new HazelcastJsonValue(t3));

        Collection<HazelcastJsonValue> filteredTxns = idPersonMap.values(Predicates.lessThan("amount", 10));
        System.out.println(filteredTxns);
        assertEquals(1, filteredTxns.size());
    }

    @Test
    public void searchInJsonValue() {

        IMap<String, HazelcastJsonValue> m = hazelcastInstance.getMap("valuesWithPos");

        m.putAll(Map.of(
                "v1.1", new HazelcastJsonValue("{ \"value\": \"v1\", \"pos\": 1 }"),
                "v1.2", new HazelcastJsonValue("{ \"value\": \"v1\", \"pos\": 2 }"),
                "v1.5", new HazelcastJsonValue("{ \"value\": \"v1\", \"pos\": 5 }"),
                "v2.1", new HazelcastJsonValue("{ \"value\": \"v2\", \"pos\": 1 }"),
                "v2.3", new HazelcastJsonValue("{ \"value\": \"v2\", \"pos\": 10 }"),
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

//        com.hazelcast.sql.HazelcastSqlException: Failed to resolve value metadata: JSON objects are not supported.
//        SqlResult sqlResult = hazelcastInstance.getSql().execute("select pos from valuesWithPos");
//
//        for (SqlRow sr : sqlResult) {
//            System.out.println((char[]) sr.getObject(1));
//        }
    }

    @Test
    public void projectionSample() {

        IMap<String, HazelcastJsonValue> m = hazelcastInstance.getMap("pesons.json");

        m.putAll(Map.of(
                "k1", new HazelcastJsonValue("{ \"name\": \"Name1\", \"surname\": \"Surname1\", \"age\": 18 }"),
                "k2", new HazelcastJsonValue("{ \"name\": \"Name2\", \"surname\": \"Surname2\", \"age\": 19 }"),
                "k3", new HazelcastJsonValue("{ \"name\": \"Name3\", \"surname\": \"Surname3\", \"age\": 20 }"),
                "k4", new HazelcastJsonValue("{ \"name\": \"Name4\", \"surname\": \"Surname4\", \"age\": 21 }"),
                "k5", new HazelcastJsonValue("{ \"name\": \"Name5\", \"surname\": \"Surname5\", \"age\": 22 }")
        ));

        Collection<Object[]> project = m.project(Projections.multiAttribute("name", "surname"));
        project.forEach(t -> System.out.println(Arrays.toString(t)));
    }


    @Test
    public void betweenPredicateSample() {

        IMap<String, HazelcastJsonValue> m = hazelcastInstance.getMap("dtz.json");

        m.putAll(Map.of(
                "k1", new HazelcastJsonValue("{ \"id\": 100, \"date\": \"2021-01-13T15:00:00Z\" }"),
                "k2", new HazelcastJsonValue("{ \"id\": 101, \"date\": \"2021-01-13T15:01:00Z\" }"),
                "k3", new HazelcastJsonValue("{ \"id\": 102, \"date\": \"2021-01-13T15:02:00Z\" }")
        ));

        ZonedDateTime from = ZonedDateTime.parse("2021-01-13T15:00:00.000Z");
        ZonedDateTime to = ZonedDateTime.parse("2021-01-13T15:01:00Z");

        Collection<HazelcastJsonValue> d = m.values(Predicates.between("date", from, to));

        d.forEach(System.out::println);

    }


}
