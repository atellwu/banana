/**
 * Project: banana
 * 
 * File Created at 2013-5-18
 * $Id$
 * 
 * Copyright 2010 dianping.com.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Dianping Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with dianping.com.
 */
package com.dianping.spotlight.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author Leo Liang
 * 
 */
public class DefaultStore implements Store {
    private static final String               DEFAULT_APPHOTKEYSCONFIG_PATH = "/data/appdatas/banana/apphotkeys.xml";

    private String                            appHotkeyConfigPath           = DEFAULT_APPHOTKEYSCONFIG_PATH;
    private Map<String, Leaderboard>          appLeaderboards               = new HashMap<String, Leaderboard>();
    private Map<String, Set<Hotkey>>          appHotkeys                    = new HashMap<String, Set<Hotkey>>();
    private Map<String, Map<Hotkey, Integer>> appHotkeyStats                = new HashMap<String, Map<Hotkey, Integer>>();
    private Map<String, Integer>              appStats                      = new HashMap<String, Integer>();
    private Map<String, Map<Double, Integer>> appHotkeyUsageStats           = new HashMap<String, Map<Double, Integer>>();
    private XStream                           xstream                       = new XStream();

    public void setAppHotkeyConfigPath(String appHotkeyConfigPath) {
        this.appHotkeyConfigPath = appHotkeyConfigPath;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#init()
     */
    @Override
    public synchronized void init() {
        xstream.alias("set", Set.class);
        xstream.alias("map", Map.class);
        xstream.alias("leaderboard", Leaderboard.class);
        xstream.alias("string", String.class);
        xstream.alias("int", Integer.class);
        xstream.alias("double", Double.class);
        xstream.alias("hotkey", Hotkey.class);
        xstream.alias("list", List.class);
        xstream.alias("scorestat", ScoreStat.class);
        loadAppHotkeys();
    }

    @SuppressWarnings("unchecked")
    private void loadAppHotkeys() {
        File configFile = new File(appHotkeyConfigPath);
        if (!configFile.exists()) {
            throw new RuntimeException(String.format("AppHotkeyCofing %s not found", appHotkeyConfigPath));
        } else {
            try {
                Map<String, Set<Hotkey>> newAppHotkeys = (Map<String, Set<Hotkey>>) xstream
                        .fromXML(new FileInputStream(appHotkeyConfigPath));
                appHotkeys.putAll(newAppHotkeys);
            } catch (Exception e) {
                throw new RuntimeException(String.format("Load %s fail", appHotkeyConfigPath), e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#listHotkeys(java.lang.String)
     */
    @Override
    public synchronized Set<Hotkey> listHotkeys(String app) {
        if (!appHotkeys.containsKey(app)) {
            return null;
        }
        Set<Hotkey> res = new HashSet<Hotkey>();

        int appStatCount = appStats.get(app);

        for (Hotkey hotkey : appHotkeys.get(app)) {
            Integer hotkeyUsage = getAppHotkeyStats(app).get(hotkey);
            if (hotkeyUsage == null) {
                hotkeyUsage = 0;
            }
            res.add(new Hotkey(hotkey.getTokens(), hotkey.getName(), hotkey.getVideoUrl(), appStatCount == 0 ? 0d
                    : 100d * (hotkeyUsage / appStatCount)));
        }
        return res;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#record(java.lang.String,
     * java.util.Set)
     */
    @Override
    public synchronized void record(String appName, Set<Hotkey> hotkeys) {
        if (!appHotkeys.containsKey(appName)) {
            return;
        }
        Map<Hotkey, Integer> hotkeyStats = getAppHotkeyStats(appName);

        Integer appUsageCount = appStats.get(appName);
        if (appUsageCount == null) {
            appStats.put(appName, 1);
        } else {
            appStats.put(appName, appUsageCount + 1);
        }

        for (Hotkey hotkey : hotkeys) {
            if (appHotkeys.get(appName).contains(hotkey)) {
                if (hotkeyStats.containsKey(hotkey)) {
                    hotkeyStats.put(hotkey, hotkeyStats.get(hotkey) + 1);
                } else {
                    hotkeyStats.put(new Hotkey(hotkey.getTokens(), hotkey.getName(), hotkey.getVideoUrl(), 0), 1);
                }
            }
        }
    }

    private Map<Hotkey, Integer> getAppHotkeyStats(String appName) {
        Map<Hotkey, Integer> hotkeyStats = appHotkeyStats.get(appName);
        if (hotkeyStats == null) {
            hotkeyStats = new HashMap<Hotkey, Integer>();
            appHotkeyStats.put(appName, hotkeyStats);
        }
        return hotkeyStats;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#saveScore(java.lang.String,
     * int)
     */
    @Override
    public synchronized void saveScore(String appName, int score) {
        if (!appHotkeys.containsKey(appName)) {
            return;
        }
        Leaderboard appLeaderboard = getLeaderboard(appName);
        if (appLeaderboard == null) {
            return;
        }
        ScoreStat existScore = null;
        for (ScoreStat scoreStat : appLeaderboard.getScores()) {
            if (scoreStat.getScore() == score) {
                existScore = scoreStat;
                break;
            }
        }
        if (existScore != null) {
            existScore.increaseCount();
        } else {
            appLeaderboard.getScores().add(new ScoreStat(score, 1));
            Collections.sort(appLeaderboard.getScores());
        }
        appLeaderboard.increaseSize();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dianping.spotlight.service.Store#getLeaderboard(java.lang.String)
     */
    @Override
    public synchronized Leaderboard getLeaderboard(String appName) {
        if (!appHotkeys.containsKey(appName)) {
            return null;
        }
        Leaderboard appLeaderboard = appLeaderboards.get(appName);
        if (appLeaderboard == null) {
            appLeaderboard = new Leaderboard();
            appLeaderboard.setScores(new ArrayList<ScoreStat>());
            appLeaderboard.setSize(0);
            appLeaderboards.put(appName, appLeaderboard);
        }
        return appLeaderboard;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#getUsages()
     */
    @Override
    public Map<Double, Integer> getUsages(String appName) {
        if (!appHotkeys.containsKey(appName)) {
            return null;
        }
        Map<Double, Integer> appUsages = appHotkeyUsageStats.get(appName);
        if (appUsages == null) {
            appUsages = new HashMap<Double, Integer>();
            appHotkeyUsageStats.put(appName, appUsages);
        }
        return appUsages;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#saveUsage(java.lang.String,
     * double)
     */
    @Override
    public void saveUsage(String appName, double usage) {
        Map<Double, Integer> usages = getUsages(appName);
        if (usages == null) {
            return;
        } else {
            if (usages.containsKey(usage)) {
                usages.put(usage, usages.get(usage) + 1);
            } else {
                usages.put(usage, 1);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        XStream xstream = new XStream();
        xstream.alias("set", HashSet.class);
        xstream.alias("map", HashMap.class);
        xstream.alias("leaderboard", Leaderboard.class);
        xstream.alias("string", String.class);
        xstream.alias("int", Integer.class);
        xstream.alias("double", Double.class);
        xstream.alias("hotkey", Hotkey.class);
        xstream.alias("list", ArrayList.class);
        xstream.alias("scorestat", ScoreStat.class);

        Map<String, Set<Hotkey>> appHotkeys = new HashMap<String, Set<Hotkey>>();
        Set<Hotkey> hotkeys = new HashSet<Hotkey>();
        appHotkeys.put("eclipse", hotkeys);
        Set<String> tokens = new HashSet<String>();

        // copy
        tokens.clear();
        tokens.add("l_control");
        tokens.add("c");
        Hotkey h1 = new Hotkey(tokens, "Copy", "", 0d);
        hotkeys.add(h1);

        FileUtils.writeStringToFile(new File(DEFAULT_APPHOTKEYSCONFIG_PATH), xstream.toXML(appHotkeys));
    }
}
