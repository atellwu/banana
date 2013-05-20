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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

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
    private static final String               DEFAULT_STATS_FOLDER          = "/data/appdatas/banana";

    private String                            appHotkeyConfigPath           = DEFAULT_APPHOTKEYSCONFIG_PATH;
    private String                            statsOutputFolder             = DEFAULT_STATS_FOLDER;
    private Map<String, Leaderboard>          appLeaderboards               = new HashMap<String, Leaderboard>();
    private Map<String, Set<Hotkey>>          appHotkeys                    = new HashMap<String, Set<Hotkey>>();
    private Map<String, Map<Hotkey, Integer>> appHotkeyStats                = new HashMap<String, Map<Hotkey, Integer>>();
    private Map<String, Integer>              appStats                      = new HashMap<String, Integer>();
    private Map<String, Map<Double, Integer>> appHotkeyUsageStats           = new HashMap<String, Map<Double, Integer>>();
    private final XStream                     xstream                       = new XStream();
    private ScheduledExecutorService          scheduledExecutorService;

    public void setAppHotkeyConfigPath(String appHotkeyConfigPath) {
        this.appHotkeyConfigPath = appHotkeyConfigPath;
    }

    public void setStatsOutputFolder(String statsOutputFolder) {
        this.statsOutputFolder = statsOutputFolder;
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
        loadAndSchedulePersistStatsFiles();
    }

    @SuppressWarnings("unchecked")
    private void loadAndSchedulePersistStatsFiles() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });
        try {
            final File appLeaderboardsFile = new File(statsOutputFolder, "appLeaderboards.xml");
            if (appLeaderboardsFile.exists()) {
                Map<String, Leaderboard> newAppLeaderboards = (Map<String, Leaderboard>) xstream
                        .fromXML(new FileInputStream(appLeaderboardsFile));
                appLeaderboards = newAppLeaderboards;
            }

            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (appLeaderboards != null && appLeaderboards.size() > 0) {
                            FileUtils.writeStringToFile(appLeaderboardsFile, xstream.toXML(appLeaderboards));
                        }
                    } catch (Throwable e) {
                        // ignore
                    }
                }
            }, 5, 5, TimeUnit.SECONDS);

            final File appHotkeyStatsFile = new File(statsOutputFolder, "appHotkeyStats.xml");
            if (appHotkeyStatsFile.exists()) {
                Map<String, Map<Hotkey, Integer>> newAppHotkeyStats = (Map<String, Map<Hotkey, Integer>>) xstream
                        .fromXML(new FileInputStream(appHotkeyStatsFile));
                appHotkeyStats = newAppHotkeyStats;
            }

            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (appHotkeyStats != null && appHotkeyStats.size() > 0) {
                            FileUtils.writeStringToFile(appHotkeyStatsFile, xstream.toXML(appHotkeyStats));
                        }
                    } catch (Throwable e) {
                        // ignore
                    }
                }
            }, 5, 5, TimeUnit.SECONDS);

            final File appStatsFile = new File(statsOutputFolder, "appStats.xml");
            if (appStatsFile.exists()) {
                Map<String, Integer> newAppStats = (Map<String, Integer>) xstream.fromXML(new FileInputStream(
                        appStatsFile));
                appStats = newAppStats;
            }

            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (appStats != null && appStats.size() > 0) {
                            FileUtils.writeStringToFile(appStatsFile, xstream.toXML(appStats));
                        }
                    } catch (Throwable e) {
                        // ignore
                    }
                }
            }, 5, 5, TimeUnit.SECONDS);

            final File appHotkeyUsageStatsFile = new File(statsOutputFolder, "appHotkeyUsageStats.xml");
            if (appHotkeyUsageStatsFile.exists()) {
                Map<String, Map<Double, Integer>> newAppHotkeyUsageStats = (Map<String, Map<Double, Integer>>) xstream
                        .fromXML(new FileInputStream(appHotkeyUsageStatsFile));
                appHotkeyUsageStats = newAppHotkeyUsageStats;
            }

            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (appHotkeyUsageStats != null && appHotkeyUsageStats.size() > 0) {
                            FileUtils.writeStringToFile(appHotkeyUsageStatsFile, xstream.toXML(appHotkeyUsageStats));
                        }
                    } catch (Throwable e) {
                        // ignore
                    }
                }
            }, 5, 5, TimeUnit.SECONDS);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
                appHotkeys = newAppHotkeys;
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

        Integer appStatCount = appStats.get(app);
        if (appStatCount == null) {
            appStatCount = 0;
        }

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
        if (!appHotkeys.containsKey(appName) || hotkeys == null) {
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

        // ////////////////////////////////////////////
        // add hotkey to generate file
        // copy
        Set<String> t1 = new HashSet<String>();
        t1.add("control");
        t1.add("c");
        Hotkey h1 = new Hotkey(t1, "Copy", "", 0d);
        hotkeys.add(h1);

        // pase
        Set<String> t2 = new HashSet<String>();
        t2.add("control");
        t2.add("v");
        Hotkey h2 = new Hotkey(t2, "Paste", "", 0d);
        hotkeys.add(h2);
        
        Set<String> t3 = new HashSet<String>();
        t3.add("control");
        t3.add("/");
        Hotkey h3 = new Hotkey(t3, "Comment", "", 0d);
        hotkeys.add(h3);
        
        Set<String> t4 = new HashSet<String>();
        t4.add("alt");
        t4.add("/");
        Hotkey h4 = new Hotkey(t4, "Assit", "", 0d);
        hotkeys.add(h4);
        
        Set<String> t5 = new HashSet<String>();
        t5.add("alt");
        t5.add("left");
        Hotkey h5 = new Hotkey(t5, "Previous", "", 0d);
        hotkeys.add(h5);
        
        Set<String> t6 = new HashSet<String>();
        t6.add("alt");
        t6.add("up");
        Hotkey h6 = new Hotkey(t6, "Move Line up", "", 0d);
        hotkeys.add(h6);
        
        Set<String> t7 = new HashSet<String>();
        t7.add("alt");
        t7.add("right");
        Hotkey h7 = new Hotkey(t7, "forward", "", 0d);
        hotkeys.add(h7);
        
        Set<String> t8 = new HashSet<String>();
        t8.add("alt");
        t8.add("down");
        Hotkey h8 = new Hotkey(t8, "Move Line down", "", 0d);
        hotkeys.add(h8);
        // end add hotkey to generate file
        // ////////////////////////////////////////////

        FileUtils.writeStringToFile(new File(DEFAULT_APPHOTKEYSCONFIG_PATH), xstream.toXML(appHotkeys));

        // /////////////////////////////////////////////////////////////////////////////////////////////////////
         DefaultStore store = new DefaultStore();
         store.init();
         Set<Hotkey> uses = new HashSet<Hotkey>();
         Set<String> utoken = new HashSet<String>();
         utoken.add("l_control");
         utoken.add("c");
         Hotkey uh1 = new Hotkey(utoken, "", "", 0);
         uses.add(uh1);
         store.record("eclipse", uses);
         store.saveUsage("eclipse", 0.1);
         store.saveScore("eclipse", 100);
        
         System.out.println(xstream.toXML(store.listHotkeys("eclipse")));
         System.out.println(xstream.toXML(store.getLeaderboard("eclipse")));
         System.out.println(xstream.toXML(store.getUsages("eclipse")));
        
         System.in.read();
         System.exit(0);
    }
}
