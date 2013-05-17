package com.dianping.spotlight.service;

import java.io.Serializable;

public class HotKeyRes implements Serializable {

	private static final long serialVersionUID = 1L;
	private Hotkey hotKey;
	private double usage;
	private boolean used;

	public Hotkey getHotKey() {
		return hotKey;
	}

	public void setHotKey(Hotkey hotKey) {
		this.hotKey = hotKey;
	}

	public double getUsage() {
		return usage;
	}

	public void setUsage(double usage) {
		this.usage = usage;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

}
