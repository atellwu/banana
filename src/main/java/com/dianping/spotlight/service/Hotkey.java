package com.dianping.spotlight.service;

import java.io.Serializable;
import java.util.Set;

public class Hotkey implements Serializable {

	private static final long serialVersionUID = 1L;

	private Set<String> tokens;
	private String name;
	private String videoUrl;
	private double usage;

	public double getUsage() {
		return usage;
	}

	public void setUsage(double usage) {
		this.usage = usage;
	}

	public Set<String> getTokens() {
		return tokens;
	}

	public void setTokens(Set<String> tokens) {
		this.tokens = tokens;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

}
