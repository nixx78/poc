package lv.nixx.poc.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class TypeConstrainsValidator implements ConstraintValidator<TypeConstrains, Collection<String>> {

    @Override
    public boolean isValid(Collection<String> types, ConstraintValidatorContext context) {
        boolean valid = types != null && !types.contains("ERROR_TYPE");
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Type: ERROR_TYPE not valid").addConstraintViolation();
        }
        return valid;
    }

}
