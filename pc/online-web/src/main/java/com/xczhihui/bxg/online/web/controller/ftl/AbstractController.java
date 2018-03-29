package com.xczhihui.bxg.online.web.controller.ftl;

import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class AbstractController {

    public void doTitleKeyWords(ModelAndView view, String title, String keywords){
        Map<String,String> tk = new HashMap<>();
        tk.put("title", title);
        tk.put("keywords", keywords);
        view.addObject("tk", tk);
    }

    public void doConditionEcho(ModelAndView view, Map echoMap){
        view.addObject("echoMap", echoMap);
    }
}
