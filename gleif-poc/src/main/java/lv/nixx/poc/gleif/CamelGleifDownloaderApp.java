package lv.nixx.poc.gleif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamelGleifDownloaderApp {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AppConfig.class, args);
	}

}
