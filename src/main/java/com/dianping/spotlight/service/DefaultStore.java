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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Leo Liang
 * 
 */
public class DefaultStore implements Store {
    private Map<String, Leaderboard> appLeaderboards;
    private Map<String, Set<Hotkey>> appHotkeys;
    private Map<String, Set<Hotkey>> appHotkeyStats;

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#init()
     */
    @Override
    public synchronized void init() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#listHotkeys(java.lang.String)
     */
    @Override
    public synchronized Set<Hotkey> listHotkeys(String app) {
        return appHotkeyStats.get(app);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#record(java.lang.String,
     * java.util.Set)
     */
    @Override
    public synchronized void record(String appName, Set<Hotkey> hotkeys) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#higherThan(java.lang.String,
     * int)
     */
    @Override
    public synchronized double higherThan(String appName, int score) {
        Leaderboard appLeaderboard = appLeaderboards.get(appName);
        if (appLeaderboard == null) {
            appLeaderboard = new Leaderboard();
            appLeaderboard.scores = new ArrayList<ScoreStat>();
            appLeaderboard.size = 0;
            appLeaderboards.put(appName, appLeaderboard);
        }
        if (appLeaderboard.size == 0) {
            return 100d;
        } else {
            int higherThanCount = 0;
            for (ScoreStat scoreStat : appLeaderboard.scores) {
                if (scoreStat.score > score) {
                    higherThanCount += scoreStat.count;
                } else {
                    break;
                }
            }
            return 100d * (higherThanCount / appLeaderboard.size);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#saveScore(java.lang.String,
     * int)
     */
    @Override
    public synchronized void saveScore(String appName, int score) {
        Leaderboard appLeaderboard = appLeaderboards.get(appName);
        if (appLeaderboard == null) {
            appLeaderboard = new Leaderboard();
            appLeaderboard.scores = new ArrayList<ScoreStat>();
            appLeaderboard.size = 0;
            appLeaderboards.put(appName, appLeaderboard);
        }
        ScoreStat existScore = null;
        for (ScoreStat scoreStat : appLeaderboard.scores) {
            if (scoreStat.score == score) {
                existScore = scoreStat;
                break;
            }
        }
        if (existScore != null) {
            existScore.count++;
        } else {
            appLeaderboard.scores.add(new ScoreStat(score, 1));
            Collections.sort(appLeaderboard.scores);
        }
        appLeaderboard.size++;
    }

    private static class ScoreStat implements Comparable<ScoreStat> {
        private int score;
        private int count;

        public ScoreStat(int score, int count) {
            this.score = score;
            this.count = count;
        }

        @Override
        public int compareTo(ScoreStat o) {
            return o.score - this.score;
        }

    }

    private static class Leaderboard {
        private List<ScoreStat> scores;
        private int             size;
    }

}
