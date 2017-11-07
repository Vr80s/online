package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.UserPaperService;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户作业/试卷控制层类
 *
 * @author 康荣彩
 * @create 2017-02-28 21:04
 */
@RestController
@RequestMapping(value = "/paper")
public class UserPaperController {

    @Autowired
    private UserPaperService paperService;



    /**
     * 获取用户所有作业
     * @param paperStatus 0:作业 1:试卷
     * @param pageNumber 当前也码
     * @param pageSize 每页条数
     * @param request  当前登录用户
     * @return
     */
    @RequestMapping(value = "/findMyCurrentPage",method= RequestMethod.GET)
    public ResponseObject findMyCurrentPage(Integer paperStatus, Integer pageNumber, Integer pageSize, HttpServletRequest request){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if (user == null) {
            return ResponseObject.newErrorResponseObject("请登录！");
        }
        return ResponseObject.newSuccessResponseObject(paperService.findMyCurrentPage(paperStatus, pageNumber, pageSize,user));

    }


    /**
     * 获取用户历史作业
     * @param paperStatus 0:作业 1:试卷
     * @param pageNumber 当前也码
     * @param pageSize 每页条数
     * @param request  当前登录用户
     * @return
     */
    @RequestMapping(value = "findMyHistoryPage",method= RequestMethod.GET)
    public ResponseObject findMyHistoryPage(Integer paperStatus,String startTime,String endTime,Integer pageNumber, Integer pageSize,HttpServletRequest request){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if (user == null) {
            return ResponseObject.newErrorResponseObject("请登录！");
        }
        return ResponseObject.newSuccessResponseObject(paperService.findMyHistoryPage(paperStatus, startTime, endTime, pageNumber, pageSize, user));
    }

    /**
     * 获取我的未完成作业试卷页面试题信息
     * @param paperId  试卷id
     * @return
     */
    @RequestMapping(value = "getMyPaper",method= RequestMethod.GET)
    public ResponseObject getMyPaper(String paperId,HttpServletRequest request){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if (user == null) {
            return ResponseObject.newErrorResponseObject("请登录！");
        }
        //记录学生第一次写作业的时间
        paperService.updateStartTimeAboutPaper(paperId,user);
        return ResponseObject.newSuccessResponseObject(paperService.getMyPaper(paperId,user));
    }

    /**
     * 保存我的回答 康荣彩
     * @param
     */
    @RequestMapping(value = "updateQuestionById",method= RequestMethod.POST)
    public ResponseObject updateQuestionById(String questionId,String answer,String attachment,HttpServletRequest request){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if (user == null) {
            return ResponseObject.newErrorResponseObject("请登录！");
        }
        if(StringUtils.isBlank(questionId)){
            return ResponseObject.newErrorResponseObject("参数错误!");
        }
        paperService.updateQuestionById(questionId,answer,attachment);
        return  ResponseObject.newSuccessResponseObject("操作成功");
    }

    /**
     * 提交作业  康荣彩
     * @param paperId  试卷id
     */
    @RequestMapping(value = "submitPaper",method= RequestMethod.POST)
    public ResponseObject savePaper(String paperId,HttpServletRequest request){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if (user == null) {
            return ResponseObject.newErrorResponseObject("请登录！");
        }
        if(StringUtils.isBlank(paperId)){
            return ResponseObject.newErrorResponseObject("参数错误!");
        }
        paperService.savePaper(paperId,user);
        return  ResponseObject.newSuccessResponseObject("作业提交成功!");
    }

    /**
     * 查看我的试卷或成绩
     * @param paperId  试卷id
     * @return
     */
    @RequestMapping(value = "findMyPaperOrScore",method= RequestMethod.GET)
    public ResponseObject findMyPaperOrScore(String paperId,HttpServletRequest request){
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if (user == null) {
            return ResponseObject.newErrorResponseObject("请登录！");
        }
        return  ResponseObject.newSuccessResponseObject(paperService.findMyPaperOrScore(paperId,user));
    }
}
