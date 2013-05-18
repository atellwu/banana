package com.dianping.spotlight.service;

import java.util.Set;

public interface StatisticsService {
    public void init();

    public Set<Hotkey> listHotkeys(String appName);

    public double record(String appName, Set<Hotkey> hotkeys, int score);
}
