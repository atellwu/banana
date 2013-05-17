package com.dianping.spotlight.service;

import java.util.Set;

public interface StatisticsService {
	public Set<Hotkey> listHotkeys(String appName);

	public double record(Set<Hotkey> hotkeys, int score);
}
