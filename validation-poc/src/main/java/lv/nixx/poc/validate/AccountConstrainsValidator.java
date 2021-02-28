package lv.nixx.poc.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class AccountConstrainsValidator implements ConstraintValidator<AccountConstrains, Collection<String>> {

    @Override
    public boolean isValid(Collection<String> accounts, ConstraintValidatorContext constraintValidatorContext) {
        return accounts!=null && accounts.contains("ACC1");
    }

}
