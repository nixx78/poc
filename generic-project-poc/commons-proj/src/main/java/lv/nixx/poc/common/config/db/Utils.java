package lv.nixx.poc.common.config.db;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

public class Utils {

    public static boolean loadJPABeans(ListableBeanFactory beanFactory, Class<? extends Annotation> annotationType) {

        if (beanFactory != null) {
            Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(annotationType);

            if (beansWithAnnotation.size() > 1) {
                throw new IllegalStateException("Application run fail, only single annotation [" + annotationType.getSimpleName() + "] allowed in Application");
            }

            Object bean = beansWithAnnotation.values().stream().findFirst().orElse(null);
            if (bean != null) {
                Annotation annotation = AnnotationUtils.findAnnotation(bean.getClass(), annotationType);
                if (annotation == null) {
                    return false;
                } else {
                    try {
                        Method jpaSupport = annotation.annotationType().getMethod("jpaSupport", null);

                        return (Boolean) jpaSupport.invoke(annotation, null);
                    } catch (Exception e) {
                        throw new IllegalStateException("Application run fail, problem with annotation: " + annotationType.getSimpleName(), e);
                    }
                }
            }
            return false;
        }
        return false;
    }
}
