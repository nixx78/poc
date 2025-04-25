package lv.nixx.poc.sandbox.record;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RecordSandbox {


    @Test
    void sandbox() {
        Collection<Identifiable> objects = List.of(
                new Customer(1L, "Name1"),
                new Customer(2L, "Name2"),
                new Transaction(3L, "Dest1"),
                new Transaction(4L, "Dest2")
        );

        assertThat(objects.stream().map(Identifiable::id).toList()).isEqualTo(List.of(1L, 2L, 3L, 4L));
    }


    record Transaction(Long id, String destination) implements Identifiable {
    }

    record Customer(Long id, String name) implements Identifiable {
    }

    interface Identifiable {
        Long id();
    }

}
