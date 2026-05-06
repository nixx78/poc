package lv.nixx.poc.sandbox;


import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MethodCallSandbox {

    @Test
    void methodCallUsingReflectionsTest() {

        assertAll(
                () -> assertThat(invokeHelper(new ClassWithBigDecimal())).isEqualByComparingTo(BigDecimal.valueOf(100.00)),
                () -> assertThat(invokeHelper(new ClassWithString())).isEqualByComparingTo(BigDecimal.valueOf(777.00)),
                () -> assertThrows(IllegalStateException.class, () -> invokeHelper(new ClassWithDouble())),
                () -> assertThrows(NullPointerException.class, () -> invokeHelper(null), "Target object cannot be null")
        );

    }

    private static BigDecimal invokeHelper(Object target) {

        Objects.requireNonNull(target, "Target object cannot be null");

        Class<?> returnType;
        try {
            Method m = target.getClass().getMethod("getAmount");
            returnType = m.getReturnType();

            if (returnType.equals(BigDecimal.class)) {
                return (BigDecimal) m.invoke(target);
            }

            if (returnType.equals(String.class)) {
                return new BigDecimal((String) m.invoke(target));
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        throw new IllegalStateException("Return type %s not supported".formatted(returnType));
    }

}

class ClassWithBigDecimal {
    public BigDecimal getAmount() {
        return BigDecimal.valueOf(100.00);
    }
}

class ClassWithString {

    public String getAmount() {
        return "777.00";
    }
}

class ClassWithDouble {

    public Double getAmount() {
        return 10.999;
    }

}
