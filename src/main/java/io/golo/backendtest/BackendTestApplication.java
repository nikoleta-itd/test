package io.golo.backendtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application
 */
@SpringBootApplication
public class BackendTestApplication {
	
	private static final Logger log = LoggerFactory.getLogger(BackendTestApplication.class);

    /**
     * Spring Boot application runner
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendTestApplication.class, args);
    }
}
