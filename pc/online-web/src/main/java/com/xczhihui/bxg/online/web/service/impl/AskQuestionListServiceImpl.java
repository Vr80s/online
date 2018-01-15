package com.xczhihui.bxg.online.web.service.impl;/**
 * Created by admin on 2016/9/19.
 */

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.web.base.utils.MysqlUtils;
import com.xczhihui.bxg.online.web.base.utils.TimeUtil;
import com.xczhihui.bxg.online.web.dao.ASKQuestionListDao;
import com.xczhihui.bxg.online.web.dao.AskAnswerDao;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.dao.UserCenterDao;
import com.xczhihui.bxg.online.web.service.AskQuestionListService;
import com.xczhihui.bxg.online.web.service.ManagerUserService;
import com.xczhihui.bxg.online.web.vo.AskAnswerVo;
import com.xczhihui.bxg.online.web.vo.AskQuestionVo;
import com.xczhihui.bxg.online.web.vo.CourseVo;
import com.xczhihui.user.center.bean.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 提问列表页业务层实现类
 *
 * @author 康荣彩
 * @create 2016-09-19 19:23
 */
@Service
public class AskQuestionListServiceImpl extends OnlineBaseServiceImpl implements AskQuestionListService {

    @Autowired
    private ASKQuestionListDao questionListDao;
    @Autowired
    private AskAnswerDao answerDao;
    @Autowired
    private AskAnswerDao askAnswerDao;

    @Autowired
    private ManagerUserService managerUserService;

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserCenterDao  userDao;

    /**
     * 获取问题列表信息
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页显示条数
     * @param status     状态，0待回答，1回答中，2已解决
     * @param tag        标签信息
     * @param title      标题
     * @param text       纯文本内容
     * @param content    内容
     * @param menuId     学科ID号
     * @return
     */
    @Override
    public Page<AskQuestionVo> findListQuestion(OnlineUser u, Integer pageNumber, Integer pageSize, Integer menuId, String status, String tag, String title, String text, String content) {
        //处理搜索条件中的特殊字符
        title = MysqlUtils.replaceESC(title);
        tag = MysqlUtils.replaceESC(tag);
        //获取所有问题信息
        Page<AskQuestionVo> askQuestionVos = questionListDao.findListQuestion(pageNumber, pageSize, menuId, status, tag, title, text, content);
        //如果存在提问信息,再循环获取当前问题下点赞数最多的一条回答信息
        if (!CollectionUtils.isEmpty(askQuestionVos.getItems())) {
            //获取当前用户，判断登录用户是否与当前问题的提问者一致，如果一致，则是本人提问，可以删除，否则，反之
            for (AskQuestionVo askQuestionVo : askQuestionVos.getItems()) {
                askQuestionVo.setIs_delete(u != null ? u.getId().equals(askQuestionVo.getUserId()) : false);
                //将问题标签按照逗号拆分组装成数组
                String[] tags = askQuestionVo.getTags().split(",");
                askQuestionVo.setTag(tags);
                askQuestionVo.setStrTime(TimeUtil.comparisonDate(askQuestionVo.getCreate_time()));

                /**
                 * 判断问题所属学科是否是全部公开
                 * 1、如果全部公开，获取回答数据，
                 * 2、如果不公开，查询登录用户是否报名此学科并缴费，如果未报名缴费，看不到问题下回答；否则，可以看到回答问题
                 */
                if (askQuestionVo.getAsk_limit() == 1) {
                    //获取当前提问下的问题信息()
                    AskAnswerVo askAnswerVo = questionListDao.findAskAnswerByQuestionId(askQuestionVo.getId(), u);
                    if (askAnswerVo != null) {
                        askQuestionVo.setAskAnswerVo(askAnswerVo); //将当前问题的回答信息存入提问结果中
                    }
                } else {
                    if (u != null) {
                        //看当前用户是否针对当前学科有权限，如果有，可以看到回答信息，否则必须购买才可以看到
                        OnlineUser user=questionListDao.findOneEntitiyByProperty(OnlineUser.class, "loginName", u.getLoginName());
                        if(user.getMenuId().intValue()==askQuestionVo.getMent_id().intValue()) {
                            //获取当前提问下的问题信息()
                            AskAnswerVo askAnswerVo = questionListDao.findAskAnswerByQuestionId(askQuestionVo.getId(), u);
                            if (askAnswerVo != null) {
                                askQuestionVo.setAskAnswerVo(askAnswerVo); //将当前问题的回答信息存入提问结果中
                            }
                        }else
                        {
                            //查看当前用户是否购买此学科下的课程
                            boolean payStatus = courseDao.checkUserToPay(u.getId(),askQuestionVo.getMent_id());
                            if (payStatus) {
                                //获取当前提问下的问题信息()
                                AskAnswerVo askAnswerVo = questionListDao.findAskAnswerByQuestionId(askQuestionVo.getId(), u);
                                if (askAnswerVo != null) {
                                    askQuestionVo.setAskAnswerVo(askAnswerVo); //将当前问题的回答信息存入提问结果中
                                }
                            }
                        }
                    }
                }

            }
        }
        return askQuestionVos;
    }


    /**
     * 获取问题数据，根据问题ID号
     * @param questionId 问题ID号
     */
    @Override
    public AskQuestionVo findQuestionById(Token token, String questionId, HttpServletRequest request) {
        //获取当前问题信息
        AskQuestionVo questionVo = questionListDao.findQuestionById(questionId);
        if (questionVo != null) {
            //将问题标签按照逗号拆分组装成数组
            String[] tags = questionVo.getTags().split(",");
            questionVo.setTag(tags);
            questionVo.setStrTime(TimeUtil.comparisonDate(questionVo.getCreate_time()));
            //未登录情况下： 控制此问题下回答信息是否显示 true 显示   false 未显示
            questionVo.setIsShowAnswer(questionVo.getAsk_limit() == 1 ? true : false);
            //获取当前登陆用户信息
            OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
            if (loginUser != null) {
                //查找用户收藏状态
                boolean statu = questionListDao.findCollectionByQidAndUserId(questionId, loginUser.getId());
                questionVo.setCollectStatu(statu);
                //是否本人提问
                questionVo.setIsMyself(loginUser.getId().equals(questionVo.getUserId()) ? true : false);
                /**
                 * 登录情况下： 控制此问题下回答信息是否显示 true 显示   false 未显示
                 * 首先，当登陆用户针对当前学科有权限，那么他可以看到回答信息
                 * 其次，当前登录用户没有当前学科的权限，用户购买此学科下的课程，他也可以看到回答信息
                 */
                if (!questionVo.isShowAnswer()) {
                       OnlineUser u=questionListDao.findOneEntitiyByProperty(OnlineUser.class, "id", loginUser.getId());
                       if(questionVo.getMent_id().equals(u.getMenuId())){
                           questionVo.setIsShowAnswer(true);
                       }else{
                           boolean payStatus = courseDao.checkUserToPay(loginUser.getId(), questionVo.getMent_id());
                           questionVo.setIsShowAnswer(payStatus);
                       }
                }
            }
            //查看此问题是否被投诉
            boolean accuseStatus = questionListDao.findAccuseByTargetId(questionId);
            questionVo.setAccused(accuseStatus);
        }
        return questionVo;
    }


    /**
     * 管理员获取问题数据，根据问题ID号
     *
     * @param questionId 问题ID号
     */
    @Override
    public AskQuestionVo findAdminQuestionById(String questionId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("_adminUser_");
        //获取当前问题信息
        AskQuestionVo questionVo = questionListDao.findQuestionById(questionId);
        if (questionVo != null) {
            //将问题标签按照逗号拆分组装成数组
            String[] tags = questionVo.getTags().split(",");
            questionVo.setTag(tags);
            questionVo.setStrTime(TimeUtil.comparisonDate(questionVo.getCreate_time()));
            //未登录情况下： 控制此问题下回答信息是否显示 true 显示   false 未显示
            questionVo.setIsShowAnswer(questionVo.getAsk_limit() == 1 ? true : false);
            //看此用户是否是管理员
            if (user != null) {
                questionVo.setIsShowAnswer(true);
            }
            //查看此问题是否被投诉
            boolean accuseStatus = questionListDao.findAccuseByTargetId(questionId);
            questionVo.setAccused(accuseStatus);
        }
        return questionVo;
    }

    /**
     * 修改问题信息的浏览数
     *
     * @param qu 问题的id号
     */
    @Override
    public String updateBrowseSum(AskQuestionVo qu) {
        questionListDao.updateBrowseSum(qu);
        return "浏览数增加成功";
    }

    /**
     * 添加提问信息
     *
     * @param qu 参数封装对象
     */
    @Override
    public String saveQuestion(AskQuestionVo qu, HttpServletRequest request) {
        //获取当前登陆用户信息
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
        loginUser =  userDao.get(loginUser.getId(),OnlineUser.class);
        if (loginUser != null) {
            qu.setCreate_nick_name(loginUser.getName());
            qu.setCreate_head_img(loginUser.getSmallHeadPhoto());
            qu.setCreate_person(loginUser.getLoginName());
            qu.setUserId(loginUser.getId());
            qu.setVideoId(qu.getVideoId());
            questionListDao.saveQuestion(qu);
            return "提问成功!";
        }
        return "请先登陆!";
    }


    /**
     * 查找类似问题,根据提问标题
     *
     * @param title 提问标题信息
     * @return 类似问题集合
     */
    @Override
    public List<AskQuestionVo> findSimilarProblemByTitle(String title) {
        if (title.equals("")) {
            return null;
        }
        return questionListDao.findSimilarProblemByTitle(title);
    }

    /**
     * 查找与当前问题相关的课程信息（筛选条件是：课程所属学科与问题所属学科一样）
     *
     * @param menuId 学科id号
     * @return
     */
    @Override
    public List<CourseVo> getCourseByMenuId(Integer menuId) {
        return questionListDao.getCourseByMenuId(menuId);
    }


    /**
     * 热门回答(按照回答数量高排序)
     *
     * @param
     * @return
     */
    @Override
    public List<AskQuestionVo> getHotAnswer() {
        return questionListDao.getHotAnswer();
    }


    /**
     * 相似问题(按问题标签搜索)
     *
     * @param
     * @return
     */
    @Override
    public List<AskQuestionVo> getSameProblem(String[] tags, Integer menuId, String qId) {
        return questionListDao.getSameProblem(tags, menuId, qId);
    }


    /**
     * 删除问题信息
     *
     * @param questionId 问题id号
     * @return
     */
    @Override
    public String deleteQuestionById(HttpServletRequest request, OnlineUser u, String questionId) {
        //再删除问题信息
        questionListDao.deleteQuestionById(request, questionId, u);
        //先去删除回答及评论信息
        answerDao.deleteAnswerById(request, u, "", questionId);

        return "操作成功！";
    }

    /**
     *
     * @param videoId  视频id
     * @param type : 1、全部问题  2、我的问题
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public Page<AskQuestionVo> findVideoQuestion(String videoId, Integer type, Integer pageNumber, Integer pageSize, HttpServletRequest request) {
         //获取当前登陆用户信息
         OnlineUser u = (OnlineUser) UserLoginUtil.getLoginUser(request);
         if(u != null){
             return  questionListDao.findVideoQuestion(videoId,type,pageNumber,pageSize,u);
         }
         return  null;
    }


    /**
     * 修改问题信息内容
     * @param questionVo
     */
    @Override
    public void updateQuestion(AskQuestionVo  questionVo){
         questionListDao.updateQuestion(questionVo);
    }
}
