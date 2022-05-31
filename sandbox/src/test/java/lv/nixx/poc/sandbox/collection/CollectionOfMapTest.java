package lv.nixx.poc.sandbox.collection;

import org.junit.Test;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

public class CollectionOfMapTest {

    @Test
    public void convertTest() {

        Collection<Map<String, String>> m = List.of(
                Map.of(
                        "key", "key1",
                        "value", "1653996203000"
                ),
                Map.of(
                        "key", "key2",
                        "value", "1653996205000"
                ),
                Map.of(
                        "key", "key3",
                        "value", "1653996201000"
                )
        );

        Map<String, Date> collect = m.stream()
                .collect(Collectors.toMap(t -> t.get("key"), t -> new Date(Long.parseLong(t.get("value")))));

        assertEquals(3, collect.size());
    }
}
