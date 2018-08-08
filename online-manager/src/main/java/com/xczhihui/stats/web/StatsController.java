package com.xczhihui.stats.web;

import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.stats.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author hejiwei
 */
@Controller
@RequestMapping("stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView indexPage() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ModelAndView modelAndView = new ModelAndView("/stats/index");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        /*calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);*/
        Date startTime = calendar.getTime();
        modelAndView.addObject("startTime", simpleDateFormat.format(startTime));
        modelAndView.addObject("endTime", simpleDateFormat.format(date));
        return modelAndView;
    }

    @RequestMapping(value = "data", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getData(@RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        /*calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);*/
        startTime = calendar.getTime();

        calendar.setTime(endTime);
        /*calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);*/
        endTime = calendar.getTime();
        return ResponseObject.newSuccessResponseObject(statsService.getStatsData(startTime, endTime));
    }
}
