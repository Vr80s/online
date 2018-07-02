package com.xczhihui.online.api.service;

import java.util.List;

import com.xczhihui.online.api.dto.ProgressDto;
import com.xczhihui.online.api.vo.ExamineProgressVo;
import com.xczhihui.online.api.vo.LiveExamineInfo;
import com.xczhihui.online.api.vo.LiveExamineInfoVo;

/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/8 14:22
 */
public interface LiveExamineInfoService {

    String add(LiveExamineInfo liveExamineInfo);

    List<LiveExamineInfoVo> liseByExamineStatus(String userId, String examineStatus, int pageNumber, int pageSize);

    /**
     * 申诉次数
     *
     * @param examineId
     * @return
     */
    int appealCount(String examineId);

    LiveExamineInfoVo get(String examineId);

    /**
     * 审核进度
     *
     * @param examineId
     * @return
     */
    List<ProgressDto> appealList(String examineId);

    /**
     * 审核详情
     *
     * @param examineId
     * @return
     */
    ExamineProgressVo examineDetails(String examineId);

    void appeal(String examineId, String content);

    int getPreLiveCount(String userId);


    /**
     * Description：此处的取消审核，就直接把数据删掉了
     *
     * @param examineId
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    void cancelAudit(String examineId);
}
