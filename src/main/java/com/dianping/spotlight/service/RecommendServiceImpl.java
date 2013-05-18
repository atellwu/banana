package com.dianping.spotlight.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class RecommendServiceImpl implements RecommendService {

	private StatisticsService statisticsService;
	
	private static final String APP_PREFIX = "active";
	
	
	@Override
	public RecommendResult recommend(File inputSeqFile) {
		
		List<String> lines = null;
		try {
			lines = FileUtils.readLines(inputSeqFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		RecommendResult result = new RecommendResult();
		String appName = "";
		List<HotkeyRes> hotkeyRess = new ArrayList<HotkeyRes>();
		result.setAppName(appName);
		result.setRecommendKeys(hotkeyRess);

		for(String line:lines){
			line = line.trim().toLowerCase();
			if(line.startsWith(APP_PREFIX)) {
				int startIndex = line.lastIndexOf(".");
				if(startIndex == line.length()-1 || line.length() == APP_PREFIX.length()) {
					appName = "";
				} else if (startIndex == -1){
					appName = line.substring(APP_PREFIX.length() + 1).trim();
				} else {
					appName = line.substring(startIndex, line.length());
				}
				if(!appName.isEmpty()) {
					break;
				}
			}
		}
		if(!appName.isEmpty()) {
			return null;
		}
		
		Set<Hotkey> hotkeys = statisticsService.listHotkeys(appName);
		Map<Set<String>,Hotkey> tokensToHotKeyMap = new HashMap<Set<String>,Hotkey>();
		Map<Set<String>,Integer> tokensToNumMap = new HashMap<Set<String>,Integer>();
		for(Hotkey hotkey:hotkeys){
			tokensToHotKeyMap.put(hotkey.getTokens(), hotkey);
			tokensToNumMap.put(hotkey.getTokens(), 0);
		}
		for(String line:lines){
			line = line.trim().toLowerCase();
			Set<String> tokens = new HashSet<String>();
			for(String token:line.split(" ")){
				tokens.add(token);
			}
			if(tokensToHotKeyMap.containsKey(tokens)){
				HotkeyRes hotkeyRes = new HotkeyRes();
				hotkeyRes.setHotkey(tokensToHotKeyMap.get(tokens));
				hotkeyRes.setUsed(true);
				hotkeyRess.add(hotkeyRes);
				tokensToHotKeyMap.remove(tokens);
			}
		}
		if(!tokensToHotKeyMap.isEmpty()){
			for(Set<String> tokens:tokensToHotKeyMap.keySet()){
				HotkeyRes hotkeyRes = new HotkeyRes();
				hotkeyRes.setHotkey(tokensToHotKeyMap.get(tokens));
				hotkeyRes.setUsed(false);
				hotkeyRess.add(hotkeyRes);
			}
		}
		
		return result;
	}

	@Override
	public void init() {
//		statisticsService = new 
//		statisticService.init();
		
	}

}
