package com.dianping.spotlight.service;

import java.io.Serializable;

public class HotKeyRes implements Serializable {

	private static final long serialVersionUID = 1L;
	private Hotkey hotKey;
	
	private boolean used;

	public Hotkey getHotKey() {
		return hotKey;
	}

	public void setHotKey(Hotkey hotKey) {
		this.hotKey = hotKey;
	}


	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

}
