package com.xczhihui.bxg.online.web.controller.ftl;

import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class AbstractFtlController extends AbstractController {

    public void doTitleKeyWords(ModelAndView view, String title, String keywords){
        Map<String,String> tk = new HashMap<>();
        tk.put("title", title);
        tk.put("keywords", keywords);
        view.addObject("tk", tk);
    }

    public void doTitleKeyWordsAndDescription(ModelAndView view, String title, String keywords, String description){
        Map<String,String> tk = new HashMap<>();
        tk.put("title", title);
        tk.put("keywords", keywords);
        tk.put("description", description);
        view.addObject("tk", tk);
    }

    public void doConditionEcho(ModelAndView view, Map echoMap){
        view.addObject("echoMap", echoMap);
    }

    public OnlineUser getOnlineUserNull(HttpServletRequest request){
        return (OnlineUser) UserLoginUtil.getLoginUser();
    }
}
