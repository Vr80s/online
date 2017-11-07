package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.PaperVo;

import java.util.Map;

/**
 * 用户
 * @author Rongcai Kang
 */
public interface UserPaperService {


    /**
     * 获取用户所有作业
     * @param paperStatus 0:作业 1:试卷
     * @param pageNumber 当前也码
     * @param pageSize 每页条数
     * @param user  当前登录用户
     * @return
     */
    public Page<PaperVo> findMyCurrentPage(Integer paperStatus,Integer pageNumber, Integer pageSize,OnlineUser user);


    /**
     * 获取用户历史作业
     * @param paperStatus 0:作业 1:试卷
     * @param pageNumber 当前也码
     * @param pageSize 每页条数
     * @param user  当前登录用户
     * @return
     */
    public Page<PaperVo>  findMyHistoryPage(Integer paperStatus,String startTime,String endTime,Integer pageNumber, Integer pageSize,OnlineUser user);

    /**
     * 获取我的未完成作业试卷页面试题信息
     * @param paperId  试卷id
     * @return
     */
    public  Map<String,Object> getMyPaper(String paperId,OnlineUser user);

    /**
     * 当用户第一次进行写作业时，记录用户第一次进入卷子的时间
     * @param paperId
     * @param user
     */
    public  void  updateStartTimeAboutPaper(String paperId,OnlineUser user);

    /**
     * 保存我的回答 康荣彩
     * @param
     */
    public void updateQuestionById(String questionId,String answer, String attachment);

    /**
     * 提交作业  康荣彩
     * @param paperId  试卷id
     */
    public void savePaper(String paperId,OnlineUser user);


    /**
     * 查看我的试卷或成绩
     * @param paperId  试卷id
     * @return
     */
    public  Map<String,Object> findMyPaperOrScore(String paperId,OnlineUser user);
}
