package io.golo.backendtest.model;

import java.time.LocalDateTime;

public class MonitoringStatistic {
 private LocalDateTime time;
 private String status;
public MonitoringStatistic() {
	super();
}


public MonitoringStatistic(LocalDateTime time, String status) {
	super();
	this.time = time;
	this.status = status;
}


public LocalDateTime getTime() {
	return time;
}
public void setTime(LocalDateTime time) {
	this.time = time;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
 
 
}
