package lv.nixx.poc.sandbox.core;

import lombok.AllArgsConstructor;
import org.junit.Test;

import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FindFirstNotEmptyOrNullTest {

    @Test
    public void test() {
        assertEquals("F", new Parameters("", null, "F", "X").getFirstNotEmpty());
        assertNull(new Parameters("", null, "", null).getFirstNotEmpty());
        assertEquals("A", new Parameters("A", "B", "F", "X").getFirstNotEmpty());
        assertNull(new Parameters(null, null, null, null).getFirstNotEmpty());
    }

    @AllArgsConstructor
    static class Parameters {
        String p1;
        String p2;
        String p3;
        String p4;

        String getFirstNotEmpty() {
            return Stream.of(p1, p2, p3, p4)
                    .filter(Objects::nonNull)
                    .filter(t -> !t.isEmpty())
                    .findFirst()
                    .orElseGet(() -> null);
        }
    }

}
