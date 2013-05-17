package com.dianping.spotlight.service;

import java.io.Serializable;
import java.util.List;

public class RecommendResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<HotKeyRes> recommendKeys;
	private int score;
	private double higherThan;
	private String appName;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public List<HotKeyRes> getRecommendKeys() {
		return recommendKeys;
	}

	public void setRecommendKeys(List<HotKeyRes> recommendKeys) {
		this.recommendKeys = recommendKeys;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public double getHigherThan() {
		return higherThan;
	}

	public void setHigherThan(double higherThan) {
		this.higherThan = higherThan;
	}

}
