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

import java.util.List;

/**
 * @author Leo Liang
 * 
 */
public class Leaderboard {
    private List<ScoreStat> scores;
    private int             size;

    public List<ScoreStat> getScores() {
        return scores;
    }

    public void setScores(List<ScoreStat> scores) {
        this.scores = scores;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void increaseSize() {
        this.size++;
    }
}
