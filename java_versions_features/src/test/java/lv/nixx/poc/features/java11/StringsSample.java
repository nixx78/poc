package lv.nixx.poc.features.java11;

import org.junit.Test;

import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringsSample {

    @Test
    public void isBlankSample() {
        assertTrue(" ".isBlank());
        assertTrue("".isBlank());
        assertFalse("XYZ".isBlank());
    }

    @Test
    public void linesSample() {
        String str = "line1\nline2\nline3";
        System.out.println(str);
        System.out.println(str.lines().collect(Collectors.toList()));
    }

    @Test
    public void repeatSample() {
        System.out.println("X.repeat(10):" + "X".repeat(10));
    }

}
