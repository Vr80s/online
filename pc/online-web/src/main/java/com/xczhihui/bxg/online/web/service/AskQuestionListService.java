package com.xczhihui.bxg.online.web.service;/**
 * Created by admin on 2016/9/19.
 */

import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.AskQuestionVo;
import com.xczhihui.bxg.online.web.vo.CourseVo;
import com.xczhihui.user.center.bean.Token;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 提问列表页业务层接口类
 *
 * @author 康荣彩
 * @create 2016-09-19 19:21
 */
public interface AskQuestionListService {

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
    public Page<AskQuestionVo> findListQuestion(OnlineUser u, Integer pageNumber, Integer pageSize,Integer menuId,String status,String tag,String title,String text,String content);


    /**
     * 获取问题数据，根据问题ID号
     * @param questionId 问题ID号
     */
    public  AskQuestionVo  findQuestionById( Token token, String   questionId,HttpServletRequest request);
    /**
     * 修改问题信息的浏览数
     * @param id 问题的id号
     */
    public  String  updateBrowseSum(AskQuestionVo  qu);

    /**
     * 添加提问信息
     * @param qu 参数封装对象
     */
    public  String   saveQuestion(AskQuestionVo  qu ,HttpServletRequest request);

    /**
     * 查找类似问题,根据提问标题
     * @param title 提问标题信息
     * @return 类似问题集合
     */
    public List<AskQuestionVo> findSimilarProblemByTitle(String title);


    /**
     * 查找与当前问题相关的课程信息（筛选条件是：课程所属学科与问题所属学科一样）
     * @param menuId 学科id号
     * @return
     */
    public List<CourseVo> getCourseByMenuId( Integer menuId);

    /**
     * 热门回答(按照回答数量高排序)
     * @param
     * @return
     */
    public List<AskQuestionVo> getHotAnswer();


    /**
     * 相似问题(按问题标签搜索)
     * @param
     * @return
     */
    public List<AskQuestionVo> getSameProblem(String [] tags,Integer  menuId,String qId);

    /**
     * 删除问题信息
     * @param questionId 问题id号
     * @param user
     * @return
     */
    public String deleteQuestionById( OnlineUser u, String questionId, User user) ;

    /**
     * 管理员获取问题数据，根据问题ID号
     * @param questionId 问题ID号
     */
    public  AskQuestionVo  findAdminQuestionById( String   questionId,HttpServletRequest request);

    /**
     *
     * @param videoId  视频id
     * @param type : 1、全部问题  2、我的问题
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<AskQuestionVo> findVideoQuestion(String videoId,Integer type,Integer pageNumber, Integer pageSize,HttpServletRequest request);

    /**
     * 修改问题信息内容
     * @param questionVo
     */
    public void updateQuestion(AskQuestionVo  questionVo);
}
