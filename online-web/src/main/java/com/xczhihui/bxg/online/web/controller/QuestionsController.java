package com.xczhihui.bxg.online.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Description：问答模块
 * creed: Talk is cheap,show me the code
 * @author name：yuxin
 * @Date: 2018/6/20 0020 下午 4:51
 **/
@Controller
@RequestMapping(value = "/questions")
public class QuestionsController {


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("questions");
        return mav;
    }

    @RequestMapping(value = "/{questionId}", method = RequestMethod.GET)
    public ModelAndView qusDetail(@PathVariable String questionId) {
        ModelAndView mav = new ModelAndView("QusDetail");
        mav.addObject("qid", questionId);
        return mav;
    }

}
