package com.dianping.spotlight.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class RecommendServiceImpl implements RecommendService {

	private StatisticsService statisticService;
	
	@Override
	public RecommendResult recommend(File inputSeqFile) {
		try {
			List<String> lines = FileUtils.readLines(inputSeqFile);
			
			for(String line:lines){
				
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void init() {
		statisticService.init();
		
	}

}
