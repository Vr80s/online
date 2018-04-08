package com.xczhihui.course.service;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.course.vo.TreeNode;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 学习计划模块业务层接口
 * @Author Fudong.Sun【】
 * @Date 2017/1/3 16:59
 */
public interface StudyPlanService {
    /**
     * 根据周查询学习计划详情
     * @param week 第几周
     * @return
     */
    public List<Map<String,Object>> planInfo(Integer courseId,Integer week);

    /**
     * 查询课程下章节知识点树
     * @param courseId
     * @return
     */
    public List<TreeNode> getChapterTree(Integer courseId, String templateId);

    /**
     * 重新给模板配置知识点
     * @param templateId
     * @param ids
     */
    public void savePlanKnowledge(String templateId,Set<String> ids);

    /**
     * 修改授课天数
     * @param totalDay 新天数
     * @param oldDay 原天数
     * @param courseId 课程id
     */
    public ResponseObject updateStudyDay(Integer totalDay, Integer oldDay, Integer courseId);

    /**
     * 是否已经生成学习计划
     * @param courseId
     * @return
     */
    public ResponseObject ifExistPlan(Integer courseId);

    /**
     * 该课程是否已经生成计划模板
     * @param courseId
     * @return
     */
    public ResponseObject ifExistTemplate(Integer courseId);

    /**
     * 生成计划模板
     * @param totalDay 授课天数
     * @param courseId 课程id
     * @return
     */
    public ResponseObject savePlanTemplate(Integer totalDay, Integer courseId);

    /**
     * 查看课程下是否配置知识点资源
     * @param courseId
     * @return
     */
    public ResponseObject ifHasResource(Integer courseId);
}
