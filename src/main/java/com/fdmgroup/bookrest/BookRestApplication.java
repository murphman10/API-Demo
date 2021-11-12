package com.fdmgroup.bookrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:C:\\Users\\Asa\\Documents\\spring_h2.properties")
public class BookRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookRestApplication.class, args);
	}

}
