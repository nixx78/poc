package lv.nixx.poc;


import lv.nixx.poc.model.ClassToValidate;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JavaValidationTest {

    private static final Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Test
    public void testValidators() {
        final ClassToValidate request = new ClassToValidate()
                .setId(null)
                .setName(null);

        Set<ConstraintViolation<ClassToValidate>> validates = validator.validate(request);
        assertTrue(validates.size() > 0);
        validates.stream().map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
    }

    @Test
    public void testValidators1() {
        final ClassToValidate request = new ClassToValidate()
                .setId(null)
                .setName(null)
                .setAccounts(List.of("ACC1"))
                .setTypes(List.of("ERROR_TYPE"));

        Set<ConstraintViolation<ClassToValidate>> validates = validator.validate(request);
        assertTrue(validates.size() > 0);
        validates.stream().map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
    }

    @Test
    public void successValidationTest() {
        final ClassToValidate request = new ClassToValidate()
                .setId("1")
                .setName("Name")
                .setAccounts(List.of("ACC1"))
                .setTypes(List.of("TYPE1"));

        Set<ConstraintViolation<ClassToValidate>> validates = validator.validate(request);
        assertEquals(0, validates.size());
    }
}
