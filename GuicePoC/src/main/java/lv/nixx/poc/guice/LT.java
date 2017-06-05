package lv.nixx.poc.guice;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

@BindingAnnotation
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER, METHOD })
public @interface LT {
}
