package lv.nixx.poc.sandbox;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AtomicReferenceSandbox {

    @Test
    void sandbox() {

        AtomicReference<BigDecimal> ar = new AtomicReference<>(new BigDecimal(0));

        ar.set(BigDecimal.valueOf(100));
        assertAll(
                () -> assertThat(ar.get()).isEqualTo(new BigDecimal(100)),
                () -> assertThat(ar.accumulateAndGet(new BigDecimal(20), BigDecimal::add)).isEqualTo(new BigDecimal(120)),
                () -> assertThat(ar.getAndAccumulate(new BigDecimal(30), BigDecimal::add)).isEqualTo(new BigDecimal(120)),
                () -> assertThat(ar.get()).isEqualTo(new BigDecimal(150))
        );
    }

}
