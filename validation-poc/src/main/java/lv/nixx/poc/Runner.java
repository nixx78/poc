package lv.nixx.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

//TODO https://habr.com/ru/post/536612/
//TODO https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/boot-features-external-config.html

@SpringBootApplication
@ConfigurationPropertiesScan("lv.nixx.poc.configuration")
public class Runner {

	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}

}