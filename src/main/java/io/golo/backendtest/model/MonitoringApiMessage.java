package io.golo.backendtest.model;

public class MonitoringApiMessage {

	   private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	  public MonitoringApiMessage() {
	       super();
	   }
	  
	  public MonitoringApiMessage(String message) {
	       this();
	       this.message = message;
	   }
}
