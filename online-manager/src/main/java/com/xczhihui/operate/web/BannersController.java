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
    @RequestMapping(value = "/banner/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("redirect:/operate/banner2/index");
        return mav;
    }

}
