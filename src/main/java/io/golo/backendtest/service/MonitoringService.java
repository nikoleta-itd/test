package io.golo.backendtest.service;

import java.time.Instant;
import java.util.LinkedList;

import org.springframework.web.client.RestTemplate;

import io.golo.backendtest.model.MonitoringData;
import io.golo.backendtest.model.MonitoringStatistic;

public class MonitoringService implements Runnable{

    private LinkedList<MonitoringStatistic> statistics;
    private String url;

	public MonitoringService(String url) {
		super();
		this.statistics = new LinkedList<MonitoringStatistic>();
		this.url = url;
	}

	public LinkedList<MonitoringStatistic> getStatistics() {
		return statistics;
	}

	public void setStatistics(LinkedList<MonitoringStatistic> statistics) {
		this.statistics = statistics;
	}



	@Override
	public void run() {
		RestTemplate restTemplate = new RestTemplate();
			  MonitoringData monitoringData =
		  restTemplate.getForObject(this.url, MonitoringData.class);
			  
		  MonitoringStatistic singleStatistic = 
				  new MonitoringStatistic(Instant.now(), monitoringData.getStatus());
		  System.out.println("single stat: " + singleStatistic.toString()); 
		  
		  this.statistics.add(singleStatistic); 
	}
}
