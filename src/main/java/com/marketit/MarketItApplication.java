package com.marketit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MarketItApplication {

	public static void main(String[] args) {
		log.info("Welcome ~~~~~~~~~~~~~ ");
		SpringApplication.run(MarketItApplication.class, args);
	}

}
