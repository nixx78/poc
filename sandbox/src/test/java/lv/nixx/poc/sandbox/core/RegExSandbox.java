package lv.nixx.poc.sandbox.core;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegExSandbox {

    @Test
    void sandbox() {

        String oneToken = createRegexForTokens(1);

        assertAll(
                () -> assertTrue("1".matches(oneToken)),
                () -> assertTrue("1.1".matches(oneToken)),
                () -> assertFalse("A".matches(oneToken))
        );

        String twoTokens = createRegexForTokens(2);

        assertAll(
                () -> assertTrue("1/1".matches(twoTokens)),
                () -> assertTrue("1.1/1.2".matches(twoTokens)),
                () -> assertFalse("A/1/2".matches(twoTokens)),
                () -> assertFalse("1/1/4".matches(twoTokens)),
                () -> assertFalse("1/2/3/4".matches(twoTokens))
        );

        String threeTokens = createRegexForTokens(3);

        assertAll(
                () -> assertTrue("1/1/1".matches(threeTokens)),
                () -> assertTrue("1.1/1.2/1.3".matches(threeTokens)),
                () -> assertFalse("A/1/2".matches(threeTokens)),
                () -> assertFalse("1/4".matches(threeTokens)),
                () -> assertFalse("1".matches(threeTokens))
        );

    }

    private String createRegexForTokens(int tokenCount) {
        return StringUtils.repeat("\\+?[0-9.]*", "/", tokenCount);
    }

}