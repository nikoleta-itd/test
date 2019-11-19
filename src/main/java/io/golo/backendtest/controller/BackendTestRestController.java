package io.golo.backendtest.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
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

    //private TemplateService templateService;
    private MonitoringService monitoringService;
    private ScheduledExecutorService ses;
    //private HashMap<String , MonitoringService> services = new HashMap<String, MonitoringService>();
    //private ScheduledFuture<?> scheduledFuture;

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
	
	
//
//	public HashMap<String, MonitoringService> getServices() {
//		return services;
//	}
//
//	public void setServices(HashMap<String, MonitoringService> services) {
//		this.services = services;
//	}

	/**
     * Starts monitoring service
     *
     * @param url the url to be monitored
     * @param interval the interval to monitor the url
     *
     * @return The response containing the data to retrieve.
     */
    @PostMapping(value = "/monitor-start")
    public ResponseEntity<LinkedList<MonitoringStatistic>> startMonitoring(
    		@URL @RequestParam(name = "url", required = true) String url,
    		@Min(1) @RequestParam(name = "interval", required = true) int interval) {
        LOGGER.trace("Request to START monitoring on url: {} with interval in seconds: {}", url, interval );
        
        if (monitoringService == null) {
        this.monitoringService = new MonitoringService(url);
        this.ses = Executors.newScheduledThreadPool(1);
    	//this.scheduledFuture = 
    			ses.scheduleAtFixedRate(monitoringService, 0, interval, TimeUnit.SECONDS );
        }
        return new ResponseEntity<>(HttpStatus.OK);
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
    public ResponseEntity<LinkedList<MonitoringStatistic>> stopMonitoring(
    		@URL @RequestParam(name = "url", required = true) String url) {
        LOGGER.trace("Request to STOP monitoring for url: {}", url );
		if (this.monitoringService != null && url.equals(this.monitoringService.getUrl())) {
		    	//this.scheduledFuture.cancel(true);
		    if ( ! this.ses.isShutdown()) {
		    	this.ses.shutdownNow();
		    	this.monitoringService = null;
	        }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
