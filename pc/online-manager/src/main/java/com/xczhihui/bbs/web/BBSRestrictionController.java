package com.xczhihui.bbs.web;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.utils.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bbs.service.BBSRestrictionService;

@Controller
@RequestMapping("/bbs/restriction")
public class BBSRestrictionController {
    @Autowired
    private BBSRestrictionService bbsRestrictionService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "bbs/restriction/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public TableVo list(TableVo tableVo) {
        return bbsRestrictionService.list(tableVo);
    }

    @RequestMapping(value = "changeGags", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject gagsStatusChange(@RequestParam String mobile, @RequestParam boolean gags, HttpServletRequest request) {
        return bbsRestrictionService.updateGags(mobile, gags);
    }

    @RequestMapping(value = "changeBlacklist", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject blacklistStatusChange(@RequestParam String mobile, @RequestParam boolean blacklist, HttpServletRequest request) {
        return bbsRestrictionService.updateBlacklist(mobile, blacklist);
    }
}
