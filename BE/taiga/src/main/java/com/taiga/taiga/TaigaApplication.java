package com.taiga.taiga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class TaigaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaigaApplication.class, args);
	}

}
