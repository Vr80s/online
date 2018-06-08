package com.xczhihui.anchor.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.anchor.service.AnchorService;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.course.vo.LineCourseApplyStudentVO;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

/**
 * 参加线下课学员
 *
 * @author hejiwei
 */
@RequestMapping("anchor/student")
@Controller
public class AnchorStudentController {

    @Autowired
    private AnchorService anchorService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request) {
        return "/anchor/anchorLineStudent";
    }

    @RequestMapping
    @ResponseBody
    public TableVo list(TableVo tableVo) {
        String search = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(search);

        String courseName = null;
        Group courseNameGroup = groups.findByName("search_courseName");
        if (courseNameGroup != null) {
            courseName = courseNameGroup.getPropertyValue1().toString();
        }

        String anchorName = null;
        Group anchorNameGroup = groups.findByName("search_anchorName");
        if (anchorNameGroup != null) {
            anchorName = anchorNameGroup.getPropertyValue1().toString();
        }
        Page<LineCourseApplyStudentVO> lineCourseApplyStudentVOPage = anchorService.list(courseName, anchorName, tableVo.getCurrentPage(), tableVo.getiDisplayLength());
        return tableVo.fetch(lineCourseApplyStudentVOPage);
    }

    @RequestMapping("updateLearned/{id}/{learned}")
    @ResponseBody
    public ResponseObject updateLearned(@PathVariable String id, @PathVariable boolean learned) {
        anchorService.updateLearned(id, learned);
        return ResponseObject.newSuccessResponseObject();
    }
}
