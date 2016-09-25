package com.cjlyth.sousvide.pi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Configuration {
	
	private Integer id;
	private Double temperature;
	private boolean running;
	private Integer duration;
	@JsonProperty("start_time")
	private Long startTime;
	public Configuration() {
		
	}
	public Configuration(Integer id, Double temperature, boolean running, Integer duration, Long startTime) {
		super();
		this.id = id;
		this.temperature = temperature;
		this.running = running;
		this.duration = duration;
		this.startTime = startTime;
	}
	
	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @return the startTime
	 */
	public Long getStartTime() {
		return startTime;
	}
	/**
	 * @return the temperature
	 */
	public Double getTemperature() {
		return temperature;
	}
	/**
	 * @return the running
	 */
	public boolean isRunning() {
		return running;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @param running the running to set
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Configuration [id=" + id + ", temperature=" + temperature + ", running=" + running + ", duration="
				+ duration + ", startTime=" + startTime + "]";
	}
	
}
