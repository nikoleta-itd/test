package io.golo.backendtest.model;

import java.time.Instant;

public class MonitoringStatistic {
	private Instant time;
	private String status;

	public MonitoringStatistic() {
		super();
	}

	public MonitoringStatistic(Instant time, String status) {
		super();
		this.time = time;
		this.status = status;
	}

	public Instant getTime() {
		return time;
	}

	public void setTime(Instant time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MonitoringStatistic [time=" + time + ", status=" + status + "]";
	}
}
