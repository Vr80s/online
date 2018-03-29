package com.xczhihui.bxg.online.manager.bbs.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.manager.bbs.service.BBSRestrictionService;
import com.xczhihui.bxg.online.manager.utils.TableVo;

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
        BxgUser loginUser = UserLoginUtil.getLoginUser(request);
        return bbsRestrictionService.updateGags(mobile, gags, loginUser);
    }

    @RequestMapping(value = "changeBlacklist", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject blacklistStatusChange(@RequestParam String mobile, @RequestParam boolean blacklist, HttpServletRequest request) {
        BxgUser loginUser = UserLoginUtil.getLoginUser(request);
        return bbsRestrictionService.updateBlacklist(mobile, blacklist, loginUser);
    }
}
