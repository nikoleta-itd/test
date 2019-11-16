package io.golo.backendtest.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.golo.backendtest.model.MonitoringData;
import io.golo.backendtest.model.MonitoringStatistic;

/**
 * Service for operations
 */
@Service
public class TemplateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateService.class);

	/*@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			MonitoringData monitoringData = restTemplate.getForObject(
					"https://api.test.paysafe.com/accountmanagement/monitor", MonitoringData.class);
			LOGGER.info(monitoringData.toString());
		};
	}*/
	
	/*public MonitoringData getDataOld() {
		MonitoringData monitoringData = restTemplate.getForObject(
				"https://api.test.paysafe.com/accountmanagement/monitor", MonitoringData.class);
		return monitoringData;
	}*/
	
	public MonitoringData getData()
	{
	    final String uri = "https://api.test.paysafe.com/accountmanagement/monitor";

	    RestTemplate restTemplate = new RestTemplate();
	    MonitoringData monitoringData = restTemplate.getForObject(uri, MonitoringData.class);

	    return monitoringData;
	}
	
	public Set<MonitoringStatistic> getStats()
	{
	    final String uri = "https://api.test.paysafe.com/accountmanagement/monitor";

	    RestTemplate restTemplate = new RestTemplate();
	    Set<MonitoringStatistic> stats = new HashSet<MonitoringStatistic>();
	    for (int i = 0; i <= 10;i++) {
	    MonitoringData monitoringData = restTemplate.getForObject(uri, MonitoringData.class);
	    MonitoringStatistic monitoringStatistic = 
	    		new MonitoringStatistic(LocalDateTime.now(), monitoringData.getStatus());
	    stats.add(monitoringStatistic);
	    }

	    return stats;
	}
	
	public Set<MonitoringStatistic> getStatsFor(String url, int interval)
	{
	    RestTemplate restTemplate = new RestTemplate();
	    Set<MonitoringStatistic> stats = new HashSet<MonitoringStatistic>();
	    int counter = 0;
	    while (counter < interval) {
	    MonitoringData monitoringData = restTemplate.getForObject(url, MonitoringData.class);
	    MonitoringStatistic monitoringStatistic = 
	    		new MonitoringStatistic(LocalDateTime.now(), monitoringData.getStatus());
	    stats.add(monitoringStatistic);
	    counter++;
	    }

	    return stats;
	}
}
