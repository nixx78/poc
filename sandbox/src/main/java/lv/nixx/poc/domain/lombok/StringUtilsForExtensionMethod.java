package lv.nixx.poc.domain.lombok;

public class StringUtilsForExtensionMethod {

    public static String reverse(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    public static String updateWithValue(String initialValue, String newValue) {
        return initialValue + ":" + newValue;
    }

}
