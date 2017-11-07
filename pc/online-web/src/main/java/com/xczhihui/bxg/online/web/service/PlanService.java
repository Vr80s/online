package com.xczhihui.bxg.online.web.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *   PlanService:学习计划业务层接口
 * * @author Rongcai Kang
 */
public interface PlanService {

    /**
     * 获取学习日期(开始与结束日期，以及休息日的日期)
     * @return
     */
    public List<Map<String,Object>> getUserLearPlan(Integer courseId, HttpServletRequest request);
}
