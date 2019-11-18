package io.golo.backendtest.controller;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.golo.backendtest.model.MonitoringStatistic;
import io.golo.backendtest.service.MonitoringService;
import io.golo.backendtest.service.TemplateService;

/**
 * Rest controller exposing all API endpoints
 */
@RestController
public class BackendTestRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackendTestRestController.class);

    //private TemplateService templateService;
    private MonitoringService monitoringService;
    private ScheduledExecutorService ses;
    private ScheduledFuture<?> scheduledFuture;

    @Autowired
    public BackendTestRestController(TemplateService templateService) {
        //this.setTemplateService(templateService);
    }

//    public TemplateService getTemplateService() {
//		return templateService;
//	}
//
//	public void setTemplateService(TemplateService templateService) {
//		this.templateService = templateService;
//	}
	
	public MonitoringService getMonitoringService() {
		return monitoringService;
	}

	public void setMonitoringService(MonitoringService monitoringService) {
		this.monitoringService = monitoringService;
	}

	/**
     * Starts monitoring service
     *
     * @param url the url to be monitored
     * @param interval the interval to monitor the url
     *
     * @return The response containing the data to retrieve.
     */
    @GetMapping(value = "/monitor-start")
    public ResponseEntity<LinkedList<MonitoringStatistic>> startMonitoring(
    		@RequestParam(name = "url", required = true) String url,
    		@RequestParam(name = "interval", required = true) int interval) {
        LOGGER.trace("START monitoring on url: {} with interval in seconds: {}", url, interval );
        
        this.monitoringService = new MonitoringService(url);
    	this.ses = Executors.newScheduledThreadPool(1);
    	this.scheduledFuture = ses.scheduleAtFixedRate(monitoringService, 0, interval, TimeUnit.SECONDS );
    	
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
	/**
     * Get monitoring data
     *
     * @return The response containing the data to retrieve.
     */
    //TODO add a multiple-monitor and adapt
    @GetMapping(value = "/monitor")
    public ResponseEntity<LinkedList<MonitoringStatistic>> getCurrentData() {
        LOGGER.trace("GET monitoring data");

        LinkedList<MonitoringStatistic> statistic = monitoringService.getStatistics();
    	
        return new ResponseEntity<>(statistic, HttpStatus.OK);
    }
    
	/**
     * Stops the monitoring service
     *
     * @param url The url of the servcie that should be excluded from monitoring
     *
     * @return The response containing the data to retrieve.
     */
    @GetMapping(value = "/monitor-stop")
    public ResponseEntity<LinkedList<MonitoringStatistic>> stopMonitoring(
    		@RequestParam(name = "url", required = true) String url) {
        LOGGER.trace("STOP monitoring for url: {}", url );
        
    	this.scheduledFuture.cancel(true);
    	this.ses.shutdownNow();
    	this.monitoringService.setStatistics(new LinkedList<MonitoringStatistic>());
    	
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
