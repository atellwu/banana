package com.dianping.spotlight.service;

import java.io.File;

public interface RecommendService {
	public void init();
	
	public RecommendResult recommend(File inputSeqFile);
}
