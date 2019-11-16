package io.golo.backendtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.golo.backendtest.model.MonitoringData;

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
/*
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			MonitoringData monitoringData = restTemplate.getForObject(
					"https://api.test.paysafe.com/accountmanagement/monitor", MonitoringData.class);
			log.info(monitoringData.toString());
		};
	}*/
}
