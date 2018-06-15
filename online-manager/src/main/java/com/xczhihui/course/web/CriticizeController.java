package com.xczhihui.course.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.course.service.CriticizeService;
import com.xczhihui.course.vo.CriticizeVo;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

@Controller
@RequestMapping(value = "/cloudClass/criticize")
public class CriticizeController {

    @Autowired
    private CriticizeService criticizeService;

    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("/cloudClass/criticize");
    }

    @RequestMapping(value = "/findCriticizeList", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findCriticizeList(TableVo tableVo) {
        Groups groups = Tools.filterGroup(tableVo.getsSearch());
        Group keywordGroup = groups.findByName("keyword");
        String keyword = null;
        if (keywordGroup != null) {
            keyword = keywordGroup.getPropertyValue1().toString();
        }
        Page<CriticizeVo> page = criticizeService.list(keyword, tableVo.getCurrentPage(), tableVo.getiDisplayLength());
        tableVo.fetch(page);
        return tableVo;
    }

    /**
     * 批量逻辑删除
     *
     * @param ids ids
     * @return
     */
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(@RequestParam List<String> ids) {
        ResponseObject responseObject = new ResponseObject();
        if (ids != null && !ids.isEmpty()) {
            criticizeService.deletes(ids);
        }
        return ResponseObject.newErrorResponseObject("删除完成");
    }
}
