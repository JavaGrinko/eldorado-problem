package javagrinko.eldorado.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "javagrinko.eldorado")
public class EldoradoProblemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EldoradoProblemApplication.class, args);
	}
}
