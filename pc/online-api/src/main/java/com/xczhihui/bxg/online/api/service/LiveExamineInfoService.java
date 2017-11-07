package com.xczhihui.bxg.online.api.service;

import com.xczhihui.bxg.online.api.dto.ProgressDto;
import com.xczhihui.bxg.online.api.po.ExamineProgressVo;
import com.xczhihui.bxg.online.api.po.LiveExamineInfo;
import com.xczhihui.bxg.online.api.po.LiveExamineInfoVo;

import java.util.List;

/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/8 14:22
 */
public interface LiveExamineInfoService {

    String add(LiveExamineInfo liveExamineInfo);

    List<LiveExamineInfoVo> liseByExamineStatus(String userId, String examineStatus, int pageNumber, int pageSize);

    /**
     * 申诉次数
     * @param examineId
     * @return
     */
    int appealCount(String examineId);

    LiveExamineInfoVo get(String examineId);

    /**
     * 审核进度
     * @param examineId
     * @return
     */
    List<ProgressDto> appealList(String examineId);

    /**
     * 审核详情
     * @param examineId
     * @return
     */
    ExamineProgressVo examineDetails(String examineId);

    void appeal(String examineId, String content);

    int getPreLiveCount(String userId);


}
