package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.UserPaperDao;
import com.xczhihui.bxg.online.web.service.UserPaperService;
import com.xczhihui.bxg.online.web.vo.PaperVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户
 * @author Rongcai Kang
 */
@Service
public class UserPaperServiceImpl  extends OnlineBaseServiceImpl implements UserPaperService {

    @Autowired
    private UserPaperDao paperDao;


    /**
     * 获取用户所有作业
     * @param paperStatus 0:作业 1:试卷
     * @param pageNumber 当前也码
     * @param pageSize 每页条数
     * @param user  当前登录用户
     * @return
     */
    public Page<PaperVo> findMyCurrentPage(Integer paperStatus, Integer pageNumber, Integer pageSize, OnlineUser user) {
        return  paperDao.findMyCurrentPage(paperStatus,pageNumber,pageSize,user);
    }

    /**
     * 获取用户历史作业
     * @param paperStatus 0:作业 1:试卷
     * @param pageNumber 当前也码
     * @param pageSize 每页条数
     * @param user  当前登录用户
     * @return
     */
    public Page<PaperVo>  findMyHistoryPage(Integer paperStatus,String startTime,String endTime,Integer pageNumber, Integer pageSize,OnlineUser user){
        return  paperDao.findMyHistoryPage(paperStatus, startTime, endTime, pageNumber, pageSize, user);
    }

    /**
     * 获取我的未完成作业试卷页面试题信息
     * @param paperId  试卷id
     * @return
     */
    public Map<String,Object> getMyPaper(String paperId,OnlineUser user){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("baseInfo", paperDao.findPaperBaseInfo(paperId,user));
        resultMap.put("paper",paperDao.getMyPaper(paperId,user));
        return  resultMap;
    }

    /**
     * 当用户第一次进行写作业时，记录用户第一次进入卷子的时间
     * @param paperId
     * @param user
     */
    public  void  updateStartTimeAboutPaper(String paperId,OnlineUser user){
       paperDao.updateStartTimeAboutPaper(paperId,user);
    }
    /**
     * 保存我的回答 康荣彩
     * @param
     */
    public void updateQuestionById(String questionId,String answer, String attachment){
        paperDao.updateQuestionById(questionId, answer,attachment);
    }


    /**
     * 提交作业  康荣彩
     * @param paperId  试卷id
     */
    public void savePaper(String paperId,OnlineUser user){
        paperDao.savePaper(paperId,user);
    }

    /**
     * 查看我的试卷或成绩
     * @param paperId  试卷id
     * @return
     */
    public  Map<String,Object> findMyPaperOrScore(String paperId,OnlineUser user){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        //获取作业页面的基本信息
        List<Map<String,Object>> paperInfos = paperDao.findPaperBaseInfo(paperId,user);
        resultMap.put("paperInfo",paperInfos);
        //获取作业试题
        List<Map<String,Object>> questions= paperDao.findMyPaper(paperId,user);
        resultMap.put("question",questions);
        List<Map<String,Object>>  scores=null;
        if (paperInfos.size() > 0 && paperInfos.get(0).get("status").toString().equals("3")){
            scores = paperDao.findMyScore(paperId,user);
        }
        resultMap.put("score",scores);
        return  resultMap;
    }
}
