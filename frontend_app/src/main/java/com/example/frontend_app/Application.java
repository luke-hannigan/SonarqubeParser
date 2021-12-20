package com.example.frontend_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
	public static TextReader textReader = new TextReader();
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		textReader.init();
	}
}
