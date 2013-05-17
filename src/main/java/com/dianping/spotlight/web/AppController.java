package com.dianping.spotlight.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;

@Controller
public class AppController {
    private static final Logger LOG = LoggerFactory.getLogger(AppController.class);

    @RequestMapping(value = { "/" })
    public ModelAndView home() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("homeActive", true);
        return new ModelAndView("home", map);
    }

    @RequestMapping(value = { "/upload" })
    public RedirectView upload() {
        Map<String, Object> map = new HashMap<String, Object>();
        //保存文件

        map.put("homeActive", true);
        return new RedirectView("");
    }

    @RequestMapping(value = { "/result" })
    public ModelAndView result() {
        Map<String, Object> map = new HashMap<String, Object>();
        //加载文件
        
        
        map.put("homeActive", true);
        return new ModelAndView("index", map);
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
