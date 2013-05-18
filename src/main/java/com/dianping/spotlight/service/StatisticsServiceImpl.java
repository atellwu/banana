/**
 * Project: banana
 * 
 * File Created at 2013-5-17
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

import java.util.Set;

/**
 * @author Leo Liang
 * 
 */
public class StatisticsServiceImpl implements StatisticsService {

    public void init() {
        Store.INSTANCE.init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dianping.spotlight.service.StatisticsService#listHotkeys(java.lang
     * .String)
     */
    @Override
    public Set<Hotkey> listHotkeys(String appName) {
        return Store.INSTANCE.listHotkeys(appName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dianping.spotlight.service.StatisticsService#record(java.util.Set,
     * int)
     */
    @Override
    public double record(String appName, Set<Hotkey> hotkeys, int score) {
        Store.INSTANCE.record(appName, hotkeys);
        double higherThan = Store.INSTANCE.higherThan(appName, score);
        Store.INSTANCE.saveScore(appName, score);
        return higherThan;
    }

}
