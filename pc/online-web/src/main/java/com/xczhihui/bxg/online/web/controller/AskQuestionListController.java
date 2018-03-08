package com.xczhihui.bxg.online.web.controller;/**
 * Created by admin on 2016/9/19.
 */

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.AskQuestionListService;
import com.xczhihui.bxg.online.web.vo.AskQuestionVo;
import com.xczhihui.user.center.bean.Token;
import com.xczhihui.user.center.web.utils.UCCookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 提问列表控制层
 *
 * @author 康荣彩
 * @create 2016-09-19 17:58
 */
@RestController
@RequestMapping(value = "/online/questionlist")
public class AskQuestionListController {

    @Autowired
    private AskQuestionListService questionListService;

    /**
     * 获取问题列表信息
     * @param pageNumber 当前页码
     * @param pageSize  每页显示条数
     * @param status    状态，0待回答，1回答中，2已解决
     * @param tag  标签信息
     * @param title 标题
     * @param text 纯文本内容
     * @param content 内容
     * @param menuId 学科ID号
     * @return
     */
    @RequestMapping(value = "/getQuestionList",method= RequestMethod.GET)
    public ResponseObject getQuestionList(Integer pageNumber, Integer pageSize,Integer menuId, String status, String tag, String title, String text, String content,HttpSession s) {
		OnlineUser u =  (OnlineUser)s.getAttribute("_user_");
        return ResponseObject.newSuccessResponseObject(questionListService.findListQuestion(u,pageNumber,pageSize,menuId,status,tag,title,text,content));
    }

    /**
     * 获取问题数据，根据问题ID号
     * @param questionId 问题ID号
     */
    @RequestMapping(value = "/findQuestionById",method= RequestMethod.GET)
    public  ResponseObject  findQuestionById( String   questionId,HttpServletRequest request){
        Token token=  UCCookieUtil.readTokenCookie(request);
        return ResponseObject.newSuccessResponseObject(questionListService.findQuestionById(token, questionId, request));
    }

    /**
     * 获取问题数据，根据问题ID号
     * @param questionId 问题ID号
     */
    @RequestMapping(value = "/findAdminQuestionById",method= RequestMethod.GET)
    public  ResponseObject  findAdminQuestionById( String   questionId,HttpServletRequest request){
        return ResponseObject.newSuccessResponseObject(questionListService.findAdminQuestionById(questionId,request));
    }

    /**
     * 修改问题信息的浏览数
     * @param qu 问题的id号
     */
    @RequestMapping(value = "/updateBrowseSum",method= RequestMethod.POST)
    public ResponseObject updateBrowseSum(AskQuestionVo  qu){
        return ResponseObject.newSuccessResponseObject(questionListService.updateBrowseSum(qu));
    }

    /**
     * 添加提问信息
     * @param qu 参数的封装类
     */
    @RequestMapping(value = "/saveQuestion",method= RequestMethod.POST)
    public ResponseObject saveQuestion(AskQuestionVo qu,HttpServletRequest request){
        return ResponseObject.newSuccessResponseObject(questionListService.saveQuestion(qu, request));
    }

    /**
     * 查找类似问题,根据提问标题
     * @param title 提问标题信息
     * @return 类似问题集合
     */
    @RequestMapping(value = "/findSimilarProblemByTitle",method= RequestMethod.GET)
    public ResponseObject findSimilarProblemByTitle(String title){
        return  ResponseObject.newSuccessResponseObject(questionListService.findSimilarProblemByTitle(title));
    }

    /**
     * 查找与当前问题相关的课程信息（筛选条件是：课程所属学科与问题所属学科一样）
     * @param menuId 学科id号
     * @return
     */
    @RequestMapping(value = "/getCourseByMenuId",method= RequestMethod.GET)
    public ResponseObject getCourseByMenuId( Integer menuId) {
        return    ResponseObject.newSuccessResponseObject(questionListService.getCourseByMenuId(menuId));
    }


    /**
     * 热门回答(按照回答数量高排序)
     * @param
     * @return
     */
    @RequestMapping(value = "/getHotAnswer",method= RequestMethod.GET)
    public ResponseObject getHotAnswer(){
        return    ResponseObject.newSuccessResponseObject(questionListService.getHotAnswer());
    }


    /**
     * 相似问题(按问题标签搜索)
     * @param
     * @return
     */
    @RequestMapping(value = "/getSameProblem",method= RequestMethod.GET)
    public ResponseObject getSameProblem(String [] tags,Integer  menuId,String qid){
        return    ResponseObject.newSuccessResponseObject(questionListService.getSameProblem(tags,menuId,qid));
    }

    /**
     * 删除问题信息
     * @param questionId 问题id号
     * @return
     */
    @RequestMapping(value = "/deleteQuestionById",method= RequestMethod.POST)
    public ResponseObject deleteQuestionById(String  questionId,HttpServletRequest request) {

        //获取当前登录用户信息
        OnlineUser u = (OnlineUser) UserLoginUtil.getLoginUser(request);
        return    ResponseObject.newSuccessResponseObject(questionListService.deleteQuestionById(request,u,questionId));
    }


    /**
     *
     * @param videoId  视频id
     * @param type : 1、全部问题  2、我的问题
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/findVideoQuestion",method= RequestMethod.GET)
    public ResponseObject findVideoQuestion(String videoId,Integer type,Integer pageNumber, Integer pageSize,HttpServletRequest request) {
        return    ResponseObject.newSuccessResponseObject(questionListService.findVideoQuestion(videoId, type, pageNumber, pageSize, request));
    }


    /**
     * 修改问题信息内容
     * @param questionVo
     */
    @RequestMapping(value = "/updateQuestion",method= RequestMethod.POST)
    public ResponseObject updateQuestion(AskQuestionVo  questionVo){
         questionListService.updateQuestion(questionVo);
         return  ResponseObject.newSuccessResponseObject("操作成功");
    }
}
