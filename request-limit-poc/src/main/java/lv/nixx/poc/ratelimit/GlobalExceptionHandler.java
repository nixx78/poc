package lv.nixx.poc.ratelimit;


import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RequestNotPermitted.class)
    protected ResponseEntity<Object> requestNotPermittedHandler(RuntimeException e, WebRequest request) {

        final String url = ((ServletWebRequest) request).getRequest().getRequestURL().toString();

        return new ResponseEntity<>("Too many request to URL:" + url, HttpStatus.TOO_MANY_REQUESTS);
    }

}
