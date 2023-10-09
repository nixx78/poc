package lv.nixx.poc.ratelimit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



//TODO Configure on Spring boot level https://bootcamptoprod.com/resilience4j-rate-limiter/

@SpringBootApplication
public class AppRunner {
	public static void main(String[] args) {
		SpringApplication.run(AppRunner.class, args);
	}

}
