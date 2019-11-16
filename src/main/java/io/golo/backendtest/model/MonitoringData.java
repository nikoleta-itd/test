package io.golo.backendtest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MonitoringData {
	//private String hour;
	private String status;
	public MonitoringData() {
		super();
	}
	/*public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}*/
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "MonitoringData [status=" + status + "]";
	}

	
	
}
