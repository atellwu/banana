package com.dianping.spotlight.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.directory.SearchResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AppController.class);

    @RequestMapping(value = { "/", "/index" })
    public ModelAndView index() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("homeActive", true);
        return new ModelAndView("index", map);
    }

    @RequestMapping(value = "/list")
    public ModelAndView list(String source, String keyword, String pageNum) throws IOException {
        //验证参数
        if (StringUtils.isBlank(keyword)) {
            return index();
        }
        if (StringUtils.isBlank(source)) {
            source = "all";
        }
        Integer pageNum0 = null;
        try {
            pageNum0 = Integer.parseInt(pageNum);
        } catch (NumberFormatException e) {
        }
        if (pageNum0 == null || pageNum0 <= 0) {
            pageNum0 = 1;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("source", source);
        map.put("listActive", true);
        return new ModelAndView("list", map);
    }

}
