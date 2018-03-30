package com.xczhihui.bxg.online.manager.bbs.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.BBSLabel;
import com.xczhihui.bxg.online.manager.bbs.service.BBSLabelService;
import com.xczhihui.bxg.online.manager.bbs.vo.BBSLabelVo;
import com.xczhihui.bxg.online.manager.utils.TableVo;

@RequestMapping("bbs/label")
@Controller
public class BBSLabelController {

    @Autowired
    private BBSLabelService bbsLabelService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "/bbs/label/index";
    }

    /**
     * TODO 不建议使用POST来获取数据，此处为兼容前端通用请求方法
     *
     * @param tableVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public TableVo list(TableVo tableVo) {
        return bbsLabelService.list(tableVo);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public int batchDelete(@RequestParam List<Integer> ids) {
        return bbsLabelService.delete(ids);
    }

    @RequestMapping(value = "changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public int changeStatus(@RequestParam List<Integer> ids) {
        return bbsLabelService.updateStatus(ids);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public boolean save(BBSLabel bbsLabel) {
        return bbsLabelService.create(bbsLabel);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public boolean update(BBSLabel bbsLabel) {
        return bbsLabelService.update(bbsLabel);
    }
}
