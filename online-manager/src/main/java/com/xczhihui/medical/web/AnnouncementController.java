package com.xczhihui.medical.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.medical.service.AnnouncementService;
import com.xczhihui.utils.TableVo;

/**
 * 公告管理
 *
 * @author hejiwei
 */
@RequestMapping("hospital/announcement")
@Controller
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public TableVo list(TableVo tableVo) {
        return tableVo.fetch(announcementService.list(tableVo.getCurrentPage(), tableVo.getiDisplayLength(), null));
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(TableVo tableVo) {
        return "medical/announcement-list";
    }
}
