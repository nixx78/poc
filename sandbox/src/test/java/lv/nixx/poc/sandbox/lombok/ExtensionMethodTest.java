package lv.nixx.poc.sandbox.lombok;


import lombok.experimental.ExtensionMethod;
import lv.nixx.poc.domain.lombok.StringUtilsForExtensionMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtensionMethod(StringUtilsForExtensionMethod.class)
public class ExtensionMethodTest {

    @Test
    void test() {
        assertAll(
                () -> assertEquals("CBA", "ABC".reverse()),
                () -> assertEquals("InitialValue:XYZ",  "InitialValue".updateWithValue("XYZ"))
        );
    }

}
