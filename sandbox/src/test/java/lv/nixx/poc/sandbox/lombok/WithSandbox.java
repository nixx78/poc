package lv.nixx.poc.sandbox.lombok;

import lombok.With;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class WithSandbox {

    @Test
    void modificationUsingWithTest() {

        RecWithSeveralFields existing = new RecWithSeveralFields("f1", 100, "f3");

        RecWithSeveralFields updated = existing.withField2(101)
                .withField3("f3.updated");

        assertAll(
                () -> assertThat(existing).usingRecursiveComparison()
                        .isEqualTo(new RecWithSeveralFields("f1", 100, "f3")),
                () -> assertThat(updated).usingRecursiveComparison()
                        .isEqualTo(new RecWithSeveralFields("f1", 101, "f3.updated"))
        );

    }

    record RecWithSeveralFields(
            String field1,
            @With Integer field2,
            @With String field3) {

    }

}
