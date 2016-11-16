package lv.nixx.poc.aspect;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//Основные артефакты:
//Aspect
//Poincut
//Advice

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages="lv.nixx.poc.aspect")
public class AppConfiguration {
	
}
