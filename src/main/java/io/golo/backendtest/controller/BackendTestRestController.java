package io.golo.backendtest.controller;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.golo.backendtest.model.MonitoringData;
import io.golo.backendtest.model.MonitoringStatistic;
import io.golo.backendtest.model.TemplateDTO;
import io.golo.backendtest.service.TemplateService;

/**
 * Rest controller exposing all API endpoints
 */
@RestController
public class BackendTestRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackendTestRestController.class);

    private TemplateService templateService;
    /**
     * Default constructor
     */
    @Autowired
    public BackendTestRestController(TemplateService templateService) {
        this.setTemplateService(templateService);
    }
    
    

    public TemplateService getTemplateService() {
		return templateService;
	}



	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}



	/**
     * Returns API resource
     *
     * @param parameter
     *
     * @return The response containing the data to retrieve.
     */
    @GetMapping(value = "/myservice")
    public ResponseEntity<TemplateDTO> getData(
    		@RequestParam(name = "parameter", required = false) String parameter) {
        //LOGGER.trace("Get resource");

        return new ResponseEntity<>(new TemplateDTO("my service"), HttpStatus.OK);

    }
    
    @GetMapping(value = "/monitor-direct")
    public ResponseEntity<MonitoringData> monitorDirect(
    		@RequestParam(name = "parameter", required = false) String parameter) {
        //LOGGER.trace("Get resource");
        MonitoringData data = templateService.getData();

        return new ResponseEntity<>(data, HttpStatus.OK);

    }

   /* @GetMapping(value = "/monitor-statistic")
    public ResponseEntity<Set<MonitoringStatistic>> monitorStatistic(
    		@RequestParam(name = "parameter", required = false) String parameter) {
        LOGGER.trace("Get resource");
        Set<MonitoringStatistic> statistic = templateService.getStats();

        return new ResponseEntity<>(statistic, HttpStatus.OK);

    }*/
    
    @GetMapping(value = "/monitor-statistic")
    public ResponseEntity<Set<MonitoringStatistic>> monitorStatisticWithParams(
    		@RequestParam(name = "url", required = true) String url,
    		@RequestParam(name = "interval", required = true) int interval) {
       // LOGGER.trace("Get resource");
        Set<MonitoringStatistic> statistic = templateService.getStatsFor(url, interval);

        return new ResponseEntity<>(statistic, HttpStatus.OK);

    }
}
