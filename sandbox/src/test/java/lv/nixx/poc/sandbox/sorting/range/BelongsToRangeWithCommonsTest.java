package lv.nixx.poc.sandbox.sorting.range;

import org.apache.commons.lang3.Range;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BelongsToRangeWithCommonsTest {

    @Test
    void checkIfBelongsToRange() {

        assertAll(
                () -> assertEquals("First10", getLabelForRange(10)),
                () -> assertEquals("First20", getLabelForRange(20)),
                () -> assertEquals("Last100", getLabelForRange(99)),
                () -> assertEquals("NotInRange", getLabelForRange(21)),
                () -> assertEquals("NotInRange", getLabelForRange(777))
        );

    }

    private String getLabelForRange(int v) {
        return Stream.of(
                        new RangeHolder("First10", 1, 10),
                        new RangeHolder("First20", 11, 20),
                        new RangeHolder("Last100", 91, 100)
                ).filter(t -> t.isBelongsToRange(v) != null)
                .findFirst()
                .orElse(RangeHolder.notFound())
                .label;
    }

    static class RangeHolder {
        String label;
        Range<Integer> range;

        static RangeHolder notFound() {
            return new RangeHolder("NotInRange", -1, -1);
        }

        RangeHolder(String label, int from, int to) {
            this.label = label;
            range = Range.between(from, to);
        }

        RangeHolder isBelongsToRange(int v) {
            return range.contains(v) ? this : null;
        }

    }

}
