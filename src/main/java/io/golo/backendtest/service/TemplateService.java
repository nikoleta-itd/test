package io.golo.backendtest.service;

import java.time.Instant;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private boolean isActive;
    private Set<MonitoringStatistic> statistics = new HashSet<MonitoringStatistic>();
	
	public MonitoringData getData()
	{
	    final String uri = "https://api.test.paysafe.com/accountmanagement/monitor";

	    RestTemplate restTemplate = new RestTemplate();
	    MonitoringData monitoringData = restTemplate.getForObject(uri, MonitoringData.class);

	    return monitoringData;
	}
	
	public LinkedList<MonitoringStatistic> getStats()
	{
	    final String uri = "https://api.test.paysafe.com/accountmanagement/monitor";

	    RestTemplate restTemplate = new RestTemplate();
	    LinkedList<MonitoringStatistic> stats = new LinkedList<MonitoringStatistic>();
	    for (int i = 0; i <= 10;i++) {
	    MonitoringData monitoringData = restTemplate.getForObject(uri, MonitoringData.class);
	    MonitoringStatistic monitoringStatistic = 
	    		new MonitoringStatistic(Instant.now(), monitoringData.getStatus());
	    stats.add(monitoringStatistic);
	    }

	    return stats;
	}
	
	public LinkedList<MonitoringStatistic> getStatsFor(String url, int interval)
	{
	    RestTemplate restTemplate = new RestTemplate();
	    LinkedList<MonitoringStatistic> stats = new LinkedList<MonitoringStatistic>();
	    int counter = 0;
	    while (counter < interval) {
	    MonitoringData monitoringData = restTemplate.getForObject(url, MonitoringData.class);
	    MonitoringStatistic monitoringStatistic = 
	    		new MonitoringStatistic(Instant.now(), monitoringData.getStatus());
	    stats.add(monitoringStatistic);
	    counter++;
	    }

	    return stats;
	}
	
	public void startMonitoring(String url, int interval)
	{
		this.isActive = true;
	    RestTemplate restTemplate = new RestTemplate();
	    while (isActive) {
	    MonitoringData monitoringData = restTemplate.getForObject(url, MonitoringData.class);
	    MonitoringStatistic monitoringStatistic = 
	    		new MonitoringStatistic(Instant.now(), monitoringData.getStatus());
	    
	    this.statistics.add(monitoringStatistic);

	    }
	}
}
