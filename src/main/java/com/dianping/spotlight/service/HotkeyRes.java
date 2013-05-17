package com.dianping.spotlight.service;

import java.io.Serializable;

public class HotkeyRes implements Serializable {

	private static final long serialVersionUID = 1L;
	private Hotkey hotkey;
	private boolean used;

	public Hotkey getHotkey() {
		return hotkey;
	}

	public void setHotkey(Hotkey hotkey) {
		this.hotkey = hotkey;
	}


	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

}
