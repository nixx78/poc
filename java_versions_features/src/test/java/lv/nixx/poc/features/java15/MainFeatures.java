package lv.nixx.poc.features.java15;

import org.junit.Test;

import java.util.stream.Collectors;

public class MainFeatures {

    // https://blogs.oracle.com/javamagazine/inside-java-15-fourteen-jeps-in-five-buckets

    @Test
    public void textBlockSample() {

        String textBlock = """
                  line1
                    line2
                            line3
                """;

        String str = "line1\nline2\nline3";

        System.out.println("Text Block String:\n" + textBlock);
        System.out.println("Normal String Literal:\n" + str);

        System.out.println("textBlock.lines()\n" + textBlock.lines().collect(Collectors.toList()));

        System.out.println("Text Block and String Literal equals() Comparison: " + (textBlock.equals(str)));
        System.out.println("Text Block and String Literal == Comparison: " + (textBlock == str));

        System.out.println("After stripIndent");
        System.out.println(textBlock.stripIndent().replace(" ", "*"));
    }

}
