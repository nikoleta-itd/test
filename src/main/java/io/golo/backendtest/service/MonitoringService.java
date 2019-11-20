package io.golo.backendtest.service;

import java.time.Instant;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import io.golo.backendtest.model.MonitoringData;
import io.golo.backendtest.model.MonitoringStatistic;

public class MonitoringService implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringService.class);

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

	public String getUrl() {
		return url;
	}

	@Override
	public void run() {

		MonitoringStatistic singleStatistic;
		try {
			RestTemplate restTemplate = new RestTemplate();
			MonitoringData monitoringData = restTemplate.getForObject(this.url, MonitoringData.class);
			singleStatistic = new MonitoringStatistic(Instant.now(), monitoringData.getStatus());
			LOGGER.trace("single stat: " + singleStatistic.toString());
		} catch (Exception e) {
			singleStatistic = new MonitoringStatistic(Instant.now(), "DOWN");
		}

		this.statistics.add(singleStatistic);
	}
}
