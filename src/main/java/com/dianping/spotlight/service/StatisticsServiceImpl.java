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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 * @author Leo Liang
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
	private static final double DEFAULT_HIGER_LOWERST = 0d;
	private static final double DEFAULT_HIGER_HIGHEST = 100d;
	private Store store;

	public void setStore(Store store) {
		this.store = store;
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
		return store.listHotkeys(appName);
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
		store.record(appName, hotkeys);
		double scorehigherThan = scorehigherThan(appName, score);
		store.saveScore(appName, score);
		return scorehigherThan;
	}

	private double scorehigherThan(String appName, int score) {
		Leaderboard appLeaderboard = store.getLeaderboard(appName);

		if (appLeaderboard == null) {
			return DEFAULT_HIGER_LOWERST;
		}

		if (appLeaderboard.getSize() == 0) {
			return DEFAULT_HIGER_HIGHEST;
		} else {
			int higherThanCount = 0;
			for (ScoreStat scoreStat : appLeaderboard.getScores()) {
				if (scoreStat.getScore() < score) {
					higherThanCount += scoreStat.getCount();
				} else {
					break;
				}
			}
			return 100d * higherThanCount / appLeaderboard.getSize();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dianping.spotlight.service.StatisticsService#usageHigherThan(double)
	 */
	@Override
	public double usageHigherThan(String appName, double usage) {
		Map<Double, Integer> usages = store.getUsages(appName);
		if (usages == null) {
			return DEFAULT_HIGER_LOWERST;
		} else {
			int higher = 0;
			int total = 0;
			for (Entry<Double, Integer> entry : usages.entrySet()) {
				total += entry.getValue();
				if (entry.getKey().compareTo(usage) < 0) {
					higher += entry.getValue();
				}
			}

			store.saveUsage(appName, usage);
			return total == 0 ? DEFAULT_HIGER_HIGHEST : 100d * higher / total;
		}

	}

	// public static void main(String[] args) throws IOException {
	// StatisticsService statisticsService = new StatisticsServiceImpl();
	// Store store = new DefaultStore();
	// store.init();
	// statisticsService.setStore(store);
	//
	// XStream xstream = new XStream();
	// xstream.alias("set", HashSet.class);
	// xstream.alias("map", HashMap.class);
	// xstream.alias("leaderboard", Leaderboard.class);
	// xstream.alias("string", String.class);
	// xstream.alias("int", Integer.class);
	// xstream.alias("double", Double.class);
	// xstream.alias("hotkey", Hotkey.class);
	// xstream.alias("list", ArrayList.class);
	// xstream.alias("scorestat", ScoreStat.class);
	// System.out.println(xstream.toXML(statisticsService.listHotkeys("eclipse")));
	//
	// statisticsService.record("eclipse",
	// statisticsService.listHotkeys("eclipse"), 90);
	// System.out.println(statisticsService.record("eclipse",
	// statisticsService.listHotkeys("eclipse"), 10));
	// System.out.println(statisticsService.usageHigherThan("eclipse", 0.1));
	// System.out.println(statisticsService.usageHigherThan("eclipse", 0.4));
	// System.out.println(statisticsService.usageHigherThan("eclipse", 0.3));
	// System.out.println(statisticsService.usageHigherThan("eclipse", 0.2));
	//
	// System.in.read();
	// System.exit(0);
	// }
}
