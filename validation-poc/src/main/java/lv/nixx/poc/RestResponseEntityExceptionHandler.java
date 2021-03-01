package lv.nixx.poc;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, List<String>> validationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();

        return result.getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(FieldError::getField,
                              Collectors.mapping(f -> f.getDefaultMessage() + ":" + f.getRejectedValue(), Collectors.toList()))
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Collection<String> constraintViolationExceptionHandler(ConstraintViolationException ex) {

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        return constraintViolations.stream()
                .map(t-> t.getPropertyPath().toString() + ":" + t.getMessage())
                .collect(Collectors.toList());
    }

}
