package lv.nixx.poc.features;

import org.junit.Test;

public class TextBlockSandbox {

    @Test
    public void test() {

        String textBlock = """
                  line1
                    line2
                            line3
                """;

        String str = "line1\nline2\nline3";

        System.out.println("Text Block String:\n" + textBlock);
        System.out.println("Normal String Literal:\n" + str);

        System.out.println("Text Block and String Literal equals() Comparison: " + (textBlock.equals(str)));
        System.out.println("Text Block and String Literal == Comparison: " + (textBlock == str));

        System.out.println("After stripIndent");
        System.out.println(textBlock.stripIndent().replace(" ", "*"));
    }

}
