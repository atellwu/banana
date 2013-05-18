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
 * @author Leo Liang
 * 
 */
public interface Store {

    public void init();

    public Set<Hotkey> listHotkeys(String app);

    public void record(String appName, Set<Hotkey> hotkeys);

    public double higherThan(String appName, int score);

    public void saveScore(String appName, int score);

}
