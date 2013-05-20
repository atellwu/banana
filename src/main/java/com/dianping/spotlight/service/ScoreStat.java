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

/**
 * @author Leo Liang
 * 
 */
public class ScoreStat implements Comparable<ScoreStat> {
    private int score;
    private int count;

    public ScoreStat(int score, int count) {
        this.score = score;
        this.count = count;
    }
    
    public ScoreStat(){
    	
    }

    @Override
    public int compareTo(ScoreStat o) {
        return this.score - o.score;
    }

    public int getScore() {
        return score;
    }

    public int getCount() {
        return count;
    }

    public void increaseCount() {
        this.count++;
    }
}
