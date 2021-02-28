package lv.nixx.poc.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=AccountConstrainsValidator.class)
public @interface AccountConstrains {
    String message() default "Accounts not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
