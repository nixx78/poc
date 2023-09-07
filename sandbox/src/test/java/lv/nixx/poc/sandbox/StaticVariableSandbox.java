package lv.nixx.poc.sandbox;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

public class StaticVariableSandbox {

    static Holder PROTOTYPE = new Holder("KEY_1", null);

    @Test
    void test() {

        System.out.println("HOLDER_KEY_1 Original: " + PROTOTYPE);

        System.out.println("=======");
        Holder hChanged = PROTOTYPE.withValueUsingClone("Key.Value");

        System.out.println("HOLDER_KEY_1 Original:" + PROTOTYPE);
        System.out.println("HOLDER_KEY_1 Changed:" + hChanged);

        System.out.println("=======");
        Holder hChanged2 = PROTOTYPE.withValue("Key.Value1");

        System.out.println("HOLDER_KEY_1 Original:" + PROTOTYPE);
        System.out.println("HOLDER_KEY_1 Changed:" + hChanged2);

    }

    @AllArgsConstructor
    @Getter
    @ToString
    static class Holder implements Cloneable {
        String key;

        @Setter
        String value;

        public Holder withValueUsingClone(String value) {
            Holder c = this.clone();
            c.setValue(value);
            return c;
        }

        public Holder withValue(String value) {
            return new Holder(this.key, value);
        }

        @Override
        public Holder clone() {
            try {
                return (Holder) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }


}
