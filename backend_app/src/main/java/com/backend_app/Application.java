package com.backend_app;

import com.backend_app.filewriter.FileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {
	// info : https://spring.io/guides/gs/consuming-rest/
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Value("${url}")
	private String url;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) {
		return args -> {
			FileWriter writer = new FileWriter();
//			Message response = restTemplate.getForObject(url, Message.class);
//
//			if(response != null)
//				log.info(response.toString());

			// Need to save it to file.
			//writer.write(response.toString());


			// Writing the test data to file.
			for(Issue issue: issues()){
				writer.write(issue.toString());
			}

		};
	}

	// Temp list to use in data.txt output, to be read and displayed by the web application.
	public List<Issue> issues(){
		List<Issue> issues = new ArrayList<>();
		issues.add(new Issue("A1", 'E'));
		issues.add(new Issue("A2", 'A'));
		issues.add(new Issue("A3", 'A'));
		issues.add(new Issue("A4", 'D'));
		issues.add(new Issue("A5", 'B'));
		issues.add(new Issue("A6", 'E'));
		issues.add(new Issue("A7", 'D'));
		issues.add(new Issue("A8", 'E'));
		issues.add(new Issue("A9", 'E'));
		issues.add(new Issue("A10", 'A'));

		return issues;
	}

}
