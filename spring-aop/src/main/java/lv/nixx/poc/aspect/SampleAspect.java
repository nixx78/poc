package lv.nixx.poc.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component 
// Должен обязательно стоять @Component или быть factory метод с @Bean, 
// иначе, контейнер не видет компонент
public class SampleAspect {
	
	@Before("execution(* lv.nixx.poc.aspect.BussinessService.process1(..))")
    public void doAccessCheck() {
    	System.out.println("Before: process1");
    }
	
	@Around("execution(* lv.nixx.poc.aspect.BussinessService.process2(..))")
	public Object aroundProcess(ProceedingJoinPoint proceedingJoinPoint) {
		System.out.println("Method [" + proceedingJoinPoint.getSignature().getName() + "] parameters:");
		Arrays.stream(proceedingJoinPoint.getArgs()).forEach(System.out::println);
		try {
			return proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@After("anyPublicOperationPointcut()")
	public void anyPublicOperation() {
		System.out.println("after any BussinessService methos call");
	}
	
	// @Pointcut - мы можем использовать несколко раз, используя имя метода
	// как параметер Advice -> 	@After("anyPublicOperationPointcut()")
	@Pointcut("execution(* lv.nixx.poc.aspect.BussinessService.*(..))")
	private void anyPublicOperationPointcut() {
	}

}
