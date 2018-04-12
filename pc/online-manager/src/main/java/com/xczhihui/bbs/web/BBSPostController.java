package com.xczhihui.bbs.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bbs.service.BBSPostService;
import com.xczhihui.utils.TableVo;

@RequestMapping("bbs/post")
@Controller
public class BBSPostController {

    @Autowired
    private BBSPostService bbsPostService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(TableVo tableVo) {
        return "bbs/post/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public TableVo list(TableVo tableVo) {
        return bbsPostService.list(tableVo);
    }

    @RequestMapping(value = "changeGood", method = RequestMethod.POST)
    @ResponseBody
    public int changeGoodStatus(@RequestParam List<Integer> ids) {
        return bbsPostService.updateGoodStatus(ids);
    }

    @RequestMapping(value = "changeTop", method = RequestMethod.POST)
    @ResponseBody
    public int changeTopStatus(@RequestParam List<Integer> ids) {
        return bbsPostService.updateTopStatus(ids);
    }

    @RequestMapping(value = "changeDelete", method = RequestMethod.POST)
    @ResponseBody
    public int changeDeleteStatus(@RequestParam List<Integer> ids,
                                  @RequestParam boolean deleted) {
        return bbsPostService.updateDeleteStatus(ids, deleted);
    }

    @RequestMapping(value = "changeHot", method = RequestMethod.POST)
    @ResponseBody
    public int changeHotStatus(@RequestParam List<Integer> ids) {
        return bbsPostService.updateHotStatus(ids);
    }
}
