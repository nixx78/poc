package lv.nixx.poc.sandbox.error;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorCodeDecoderTest {

    private final List<String> errorCodeDescription = List.of(
            "ERROR0",
            "ERROR1",
            "ERROR2",
            "ERROR3"
    );

    @Test
    void decodeAndMapError() {

        int errorCode = 13;
        char[] chars = Integer.toBinaryString(errorCode).toCharArray();

        System.out.println("Original error code [" + errorCode + "] binary representation [" + new String(chars) + "]");

        List<String> mappedErrors = IntStream.range(0, chars.length)
                .filter(i -> chars[i] == '1')
                .mapToObj(errorCodeDescription::get)
                .toList();

        System.out.println("\tMapped error codes " + mappedErrors);
        assertThat(mappedErrors).containsExactly("ERROR0", "ERROR1", "ERROR3");

        // Error processing in reverse order
        List<String> mappedErrorsReversed = IntStream.range(0, chars.length)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .filter(i -> chars[i] == '1')
                .map(errorCodeDescription::get)
                .toList();

        System.out.println("\tMapped error codes reverse order " + mappedErrorsReversed);
        assertThat(mappedErrorsReversed).containsExactly("ERROR3", "ERROR1", "ERROR0");

    }


}
