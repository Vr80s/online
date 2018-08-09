package com.xczhihui.bxg.online.web.controller.ftl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.bxg.online.web.controller.AbstractController;

public class AbstractFtlController extends AbstractController {

    public void doTitleKeyWords(ModelAndView view, String title, String keywords) {
        Map<String, String> tk = new HashMap<>();
        tk.put("title", title);
        tk.put("keywords", keywords);
        view.addObject("tk", tk);
    }

    public void doTitleKeyWordsAndDescription(ModelAndView view, String title, String keywords, String description) {
        Map<String, String> tk = new HashMap<>();
        tk.put("title", title);
        tk.put("keywords", keywords);
        tk.put("description", description);
        view.addObject("tk", tk);
    }

    public void doConditionEcho(ModelAndView view, Map echoMap) {
        view.addObject("echoMap", echoMap);
    }

    public OnlineUser getOnlineUserNull(HttpServletRequest request) {
        return (OnlineUser) UserLoginUtil.getLoginUser();
    }

    public ModelAndView to404(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        request.getRequestDispatcher("/web/html/404.html").forward(request, response);
//        ModelAndView mv = new ModelAndView("forward:/web/html/404.html");
        return null;
    }
}
