package com.xczhihui.bbs.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bbs.service.BBSReplyService;
import com.xczhihui.utils.TableVo;

@Controller
@RequestMapping("bbs/reply")
public class BBSReplyController {

    @Autowired
    private BBSReplyService bbsReplyService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "bbs/reply/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public TableVo list(TableVo tableVo) {
        return bbsReplyService.list(tableVo);
    }

    @RequestMapping(value = "changeDelete", method = RequestMethod.POST)
    @ResponseBody
    public int changeDeleteStatus(@RequestParam List<Integer> ids,
                                  @RequestParam boolean deleted) {
        return bbsReplyService.updateDeleteStatus(ids, deleted);
    }
}
