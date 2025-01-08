package com.menna.LetsDoIt;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.function.Function;

@SpringBootApplication
public class LetsDoItApplication {

	public static void main(String[] args) {
		SpringApplication.run(LetsDoItApplication.class, args);
	}


}
