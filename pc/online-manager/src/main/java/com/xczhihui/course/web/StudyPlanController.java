package com.xczhihui.course.web;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.course.service.StudyPlanService;
import com.xczhihui.course.vo.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 学习计划模块控制层代码
 * @Author Fudong.Sun【】
 * @Date 2017/1/3 16:51
 */
@Controller
@RequestMapping("studyPlan")
public class StudyPlanController extends AbstractController {

    @Autowired
    StudyPlanService service;

    /**
     * 进入课程学习计划模板设置页面
     * @param request
     * @param courseId 课程ID
     * @param courseName 课程名称
     * @param totalDay 授课天数
     * @return
     */
    @RequestMapping(value = "template")
    public String template(HttpServletRequest request,Integer courseId,String courseName,Integer totalDay) {
        request.setAttribute("courseId", courseId);
        request.setAttribute("courseName", courseName);
        request.setAttribute("totalDay", totalDay);
        return "/cloudClass/plantemplate";
    }

    /**
     * @param courseId 课程ID
     * @param week 当前周
     * @return
     */
    @RequestMapping(value = "planInfo")
    @ResponseBody
    public ResponseObject planInfo(Integer courseId, Integer week) {
        return ResponseObject.newSuccessResponseObject(service.planInfo(courseId,week));
    }

    /**
     * 根据课程id获取章节知识点树
     * @param courseId
     * @return
     */
    @RequestMapping(value = "getChapterTree")
    @ResponseBody
    public List<TreeNode> getChapterTree(Integer courseId, String templateId) {
        List<TreeNode> list = service.getChapterTree(courseId,templateId);
        return list;
    }

    /**
     * 该课程是否已经生成计划模板
     * @param courseId
     * @return
     */
    @RequestMapping(value = "ifExistTemplate")
    @ResponseBody
    public ResponseObject ifExistTemplate(Integer courseId) {
        return service.ifExistTemplate(courseId);
    }

    /**
     * 生成学习计划模板
     * @param totalDay 授课天数
     * @param courseId 课程id
     * @return
     */
    @RequestMapping(value = "savePlanTemplate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject savePlanTemplate(Integer totalDay,Integer courseId) {
        return service.savePlanTemplate(totalDay,courseId);
    }

    /**
     * 查看当前模板是否生成学习计划(true：已生成，false：未生成)
     * @param courseId
     * @return
     */
    @RequestMapping(value = "ifExistPlan")
    @ResponseBody
    public ResponseObject ifExistPlan(Integer courseId) {
        return service.ifExistPlan(courseId);
    }

    /**
     * 修改授课天数
     * @param totalDay 新天数
     * @param oldDay 原天数
     * @param courseId 课程id
     * @return
     */
    @RequestMapping(value = "editStudyDay", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject editStudyDay(Integer totalDay,Integer oldDay,Integer courseId) {
        return service.updateStudyDay(totalDay, oldDay, courseId);
    }

    /**
     * 保存知识点配置
     * @param templateId
     * @param knowledgeIds
     * @return
     */
    @RequestMapping(value = "savePlanKnowledge", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject savePlanKnowledge(String templateId,String knowledgeIds) {
        Set<String> tids = new HashSet<>();
        if(knowledgeIds!=null) {
            String[] ids = knowledgeIds.split(",");
            for (String id : ids) {
                if (id.split("_").length == 2 && "kpoint".equalsIgnoreCase(id.split("_")[0])) {
                    tids.add(id.split("_")[1]);
                }
            }
        }
        service.savePlanKnowledge(templateId, tids);
        return ResponseObject.newSuccessResponseObject("保存成功！");
    }

    /**
     * 查看课程下是否配置知识点资源
     * @param courseId
     * @return
     */
    @RequestMapping(value = "ifHasResource")
    @ResponseBody
    public ResponseObject ifHasResource(Integer courseId) {
        return service.ifHasResource(courseId);
    }
}
