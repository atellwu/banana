package com.dianping.spotlight.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.dianping.spotlight.service.Hotkey;
import com.dianping.spotlight.service.HotkeyRes;
import com.dianping.spotlight.service.RecommendResult;
import com.dianping.spotlight.service.RecommendService;
import com.dianping.spotlight.service.StatisticsService;
import com.google.gson.Gson;

@Controller
public class AppController {
    private static final Logger LOG = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private RecommendService recommendService;

    @RequestMapping(value = { "/" })
    public ModelAndView home() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("homeActive", true);
        return new ModelAndView("home", map);
    }

    @RequestMapping(method = RequestMethod.POST, value = { "/upload" })
    public ModelAndView upload(HttpSession session, @RequestParam("file") MultipartFile file) throws UnsupportedEncodingException,
            IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        //保存文件
        List<String> lines = IOUtils.readLines(file.getInputStream(), "UTF8");
        System.out.println(lines);
        RecommendResult result2 = recommendService.recommend(lines);
        System.out.println("result: " + result2);
        session.setAttribute("result", result2);

        map.put("homeActive", true);
        return new ModelAndView("container", map);
    }

    @RequestMapping(value = { "/result" })
    public ModelAndView result(HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        //TODO 调用service，加载文件，得到result
        //        RecommendResult result2 = (RecommendResult) session.getAttribute("result");
        //
        //        map.put("result2", result2);
        map.put("homeActive", true);
        return new ModelAndView("container", map);
    }

    private RecommendResult mockResult() {
        RecommendResult result = new RecommendResult();
        result.setAppName("eclipse");
        result.setHigherThan(9.8);
        result.setScore(99);
        List<HotkeyRes> recommendKeys = new ArrayList<HotkeyRes>();
        HotkeyRes key = new HotkeyRes();
        Hotkey hotKey = new Hotkey();
        hotKey.setName("Move");
        hotKey.setUsage(9.0);
        hotKey.setVideoUrl("http://baidu.com");
        Set<String> tokens = new HashSet<String>();
        tokens.add("ctrl");
        tokens.add("a");
        tokens.add("'");
        hotKey.setTokens(tokens);
        key.setHotkey(hotKey);
        key.setUsed(true);
        recommendKeys.add(key);
        HotkeyRes key2 = new HotkeyRes();
        Hotkey hotKey2 = new Hotkey();
        hotKey2.setName("Move");
        hotKey2.setUsage(9.0);
        hotKey2.setVideoUrl("http://baidu.com");
        Set<String> tokens2 = new HashSet<String>();
        tokens2.add("command left");
        tokens2.add("\\");
        tokens2.add("'");
        hotKey2.setTokens(tokens2);
        key2.setHotkey(hotKey2);
        key2.setUsed(true);
        recommendKeys.add(key2);
        result.setRecommendKeys(recommendKeys);
        return result;
    }

    @RequestMapping(value = "/load", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object load() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {

            map.put("result", "abc");

            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            LOG.error(e.getMessage(), e);
        }
        Gson gson = new Gson();
        return gson.toJson(map);

    }

}
