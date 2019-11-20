package io.golo.backendtest.controller;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.golo.backendtest.model.MonitoringStatistic;
import io.golo.backendtest.service.MonitoringService;
import io.golo.backendtest.service.TemplateService;

/**
 * Rest controller exposing all API endpoints
 */
@RestController
@Validated
public class BackendTestRestController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BackendTestRestController.class);

	private MonitoringService monitoringService;
	private ScheduledExecutorService ses;

	@Autowired
	public BackendTestRestController() {
	}

	public MonitoringService getMonitoringService() {
		return monitoringService;
	}

	public void setMonitoringService(MonitoringService monitoringService) {
		this.monitoringService = monitoringService;
	}

	/**
	 * Starts monitoring service
	 *
	 * @param url      the url to be monitored
	 * @param interval the interval to monitor the url
	 *
	 * @return The response containing the data to retrieve.
	 */
	@PostMapping(value = "/monitor-start")
	public ResponseEntity<String> startMonitoring(
			@URL @RequestParam(name = "url", required = true) String url,
			@Min(1) @RequestParam(name = "interval", required = true) int interval) {
		
		LOGGER.trace("Request to START monitoring on url: {} with interval in seconds: {}", url, interval);
		String result = "";
		
		if (monitoringService == null) {
			this.monitoringService = new MonitoringService(url);
			this.ses = Executors.newScheduledThreadPool(1);
			ses.scheduleAtFixedRate(monitoringService, 0, interval, TimeUnit.SECONDS);
			result = "Monitoring service started on " + url;
		} else {
			result = "Monitoring is already active for " + url;
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/**
	 * Get monitoring data
	 *
	 * @return The response containing the data to retrieve.
	 */

	@GetMapping(value = "/monitor")
	public ResponseEntity<LinkedList<MonitoringStatistic>> getCurrentData() {
		
		LOGGER.trace("GET monitoring data");

		LinkedList<MonitoringStatistic> statistic = new LinkedList<MonitoringStatistic>();

		if (this.monitoringService != null) {
			statistic = monitoringService.getStatistics();
		}

		return new ResponseEntity<>(statistic, HttpStatus.OK);
	}

	/**
	 * Stops the monitoring service
	 *
	 * @param url The url of the servcie that should be excluded from monitoring
	 *
	 * @return The response containing the data to retrieve.
	 */
	@PostMapping(value = "/monitor-stop")
	public ResponseEntity<String> stopMonitoring(
			@URL @RequestParam(name = "url", required = true) String url) {
		
		LOGGER.trace("Request to STOP monitoring for url: {}", url);
		String result;
		
		if (this.monitoringService != null && url.equals(this.monitoringService.getUrl())) {
			if (!this.ses.isShutdown()) {
				this.ses.shutdownNow();
				this.monitoringService = null;
				
			}
			result = "Monitoring service stopped on " + url;
		} else {
			result = "There is no acive monitoring for " + url;
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
