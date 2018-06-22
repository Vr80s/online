package com.xczhihui.operate.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/operate")
public class BannersController {

    /**
     * @return
     */
    @RequestMapping(value = "/banner3/index")
    public ModelAndView index3() {
        ModelAndView mav = new ModelAndView("redirect:/operate/banner2/index");
        mav.addObject("title", "头条banner");
        mav.addObject("type", 3);
        return mav;
    }

    /**
     * @return
     */
    @RequestMapping(value = "/banner4/index")
    public ModelAndView index4() {
        ModelAndView mav = new ModelAndView("redirect:/operate/banner2/index");
        mav.addObject("title", "创业banner");
        mav.addObject("type", 4);
        return mav;
    }

    /**
     * @return
     */
    @RequestMapping(value = "/banner5/index")
    public ModelAndView index5() {
        ModelAndView mav = new ModelAndView("redirect:/operate/banner2/index");
        mav.addObject("title", "海外banner");
        mav.addObject("type", 5);
        return mav;
    }

    /**
     * @return
     */
    @RequestMapping(value = "/banner6/index")
    public ModelAndView index6() {
        ModelAndView mav = new ModelAndView("redirect:/operate/banner2/index");
        mav.addObject("title", "医师banner");
        mav.addObject("type", 6);
        return mav;
    }

    /**
     * @return
     */
    @RequestMapping(value = "/banner7/index")
    public ModelAndView index7() {
        ModelAndView mav = new ModelAndView("redirect:/operate/banner2/index");
        mav.addObject("title", "医馆banner");
        mav.addObject("type", 7);
        return mav;
    }
}
