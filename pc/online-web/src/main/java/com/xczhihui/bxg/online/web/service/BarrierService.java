package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.online.web.vo.BarrierQuestionVo;
import com.xczhihui.bxg.online.web.vo.BarrierVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 关卡试卷业务层接口
 *
 * @author 康荣彩
 * @create 2016-11-02 19:21
 */
public interface BarrierService {

    /**
     * 获取关卡基本信息
     * @param id 关卡id
     * @return
     */
    public BarrierVo getBarrierBasicInfo(String id,Integer examStatu,HttpServletRequest request);

    /**
     * 获取当前用户考试卷
     * @param userId
     * @param barrierId
     * @return
     */
    public List<Map<String, Object>> createUserTestPaper(String userId,String barrierId);

    /**
     * 保存我的回答
     * @param questionId
     * @param answer
     * @param userId
     */
    public void updateQuestionById(String questionId,String answer,String userId);
    
    /**
     * 提交试卷
     * @param userId
     * @param recordId
     * @throws Exception 
     */
	public Map<String, Object> addSubmitPaper(String userId, String recordId) throws Exception;

    /**
     * 获得试卷
     * @param userId
     * @param barrierId
     * @return
     */
    public List<BarrierQuestionVo> getCurrentPaper(String userId,String barrierId,Integer examStatu);

    /**
     * 获取最新一次闯关关卡基本信息
     * @param id 关卡id
     * @return
     */
    public BarrierVo  getNewBarrierBasicInfo(String id,HttpServletRequest request);
}
