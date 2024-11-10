package lv.nixx.poc.common.config.hazelcast.v2;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({HazelcastImporter.class})
public @interface Hazelcast5 {
    HazelcastInstanceConfig[] instances() default {};
}
