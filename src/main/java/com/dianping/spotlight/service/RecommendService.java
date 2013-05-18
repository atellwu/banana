package com.dianping.spotlight.service;

import java.util.List;

public interface RecommendService {
    public void init();

    public RecommendResult recommend(List<String> lines);
}
