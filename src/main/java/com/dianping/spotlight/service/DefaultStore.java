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

import java.util.Set;

/**
 * 
 * @author Leo Liang
 * 
 */
public class DefaultStore implements Store {

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#init()
     */
    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#listHotkeys(java.lang.String)
     */
    @Override
    public Set<Hotkey> listHotkeys(String app) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#record(java.lang.String,
     * java.util.Set)
     */
    @Override
    public void record(String appName, Set<Hotkey> hotkeys) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#higherThan(java.lang.String,
     * int)
     */
    @Override
    public double higherThan(String appName, int score) {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dianping.spotlight.service.Store#saveScore(java.lang.String,
     * int)
     */
    @Override
    public void saveScore(String appName, int score) {
        // TODO Auto-generated method stub

    }

}
