package lv.nixx.poc.sandbox.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class MapWithCollectionTest {

    @Test
    void operations() {
        // Comment 1.Changed
        // Comment 2
        // Comment 3
        // Comment 4
        // Comment 5

        Map<String, Collection<String>> map = new HashMap<>();

        map.computeIfAbsent("k1", k -> new ArrayList<>()).add("v1");
        map.computeIfAbsent("k1", k -> new ArrayList<>()).add("v2");
        map.computeIfAbsent("k2", k -> new ArrayList<>()).add("v2");

        System.out.println(map);
}

}
