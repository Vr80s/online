package com.xczhihui.bxg.online.web.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Criticize;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.dao.VideoDao;
import com.xczhihui.bxg.online.web.service.VideoService;
import com.xczhihui.bxg.online.web.vo.CourseApplyVo;
import com.xczhihui.bxg.online.web.vo.CourseVo;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.common.util.TimeUtil;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.enums.MessageTypeEnum;
import com.xczhihui.course.enums.RouteTypeEnum;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.util.CourseUtil;

/**
 * @Author Fudong.Sun【】
 * @Date 2016/11/2 15:18
 */
@Service
public class VideoServiceImpl extends OnlineBaseServiceImpl implements VideoService {

    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    private static final String APPLY_SUCCESS_TIPS = "恭喜您成功报名课程《{0}》~";
    @Value("weixin.course.apply.code")
    private String weixinApplySuccessMessageCode;

    @Autowired
    private VideoDao videoDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private ICommonMessageService commonMessageService;

    @Override
    public List<Map<String, Object>> getVideos(String id, String courseId, OnlineUser user, Boolean isTryLearn) {
        String userId = "";
        if (user != null) {
            userId = user.getId();
        }
        List<Map<String, Object>> knowledges;
        if (id != null) {
            /** 根据节id查询所有知识点 */
            knowledges = videoDao.getKnowledgesBySectionId(id);
        } else {
            /** 没有传入节ID，默认获取第一章第一节知识点 */
            knowledges = videoDao.getFirstKnowledges(courseId);
        }
        for (Map<String, Object> knowledge : knowledges) {
            String knowledgeId = (String) knowledge.get("id");
            /** 根据知识点Id查询视频列表，拼装到知识点集合 */
            List<Map<String, Object>> videos;
            if (isTryLearn) {
                videos = videoDao.getTryLearnVideos(knowledgeId);
            } else {
                videos = videoDao.getBuyVideos(knowledgeId, userId);
            }
            if (videos != null && videos.size() > 0) {
                knowledge.put("videos", videos);
            }
        }
        return knowledges;
    }

    /**
     * 视频播放页、试学页面、答题闯关、视频列表 姜海成
     *
     * @param courseId
     * @param userId
     * @param isTryLearn
     * @return
     */
    @Override
    public List<Map<String, Object>> getvideos(Integer courseId, String userId, Boolean isTryLearn) {

        List<Map<String, Object>> returnmap = new ArrayList<Map<String, Object>>();

        //查询所有章节知识点
        String sql = "SELECT id,name,parent_id,level,barrier_id FROM oe_chapter "
                + "WHERE is_delete=0 AND course_id=? AND level>1 ORDER BY sort";
        List<Map<String, Object>> chapters = videoDao.getNamedParameterJdbcTemplate()
                .getJdbcOperations().queryForList(sql, courseId);

        //查询本课程所有视频
        List<Map<String, Object>> videos = null;
        List<Map<String, Object>> barriers = null;//关卡
        if (isTryLearn) {//试学
            sql = "SELECT t2.id,t2.chapter_id AS chapterId,t2.name AS videoName,t2.video_id AS videoId,t2.video_time AS videoTime," +
                    "t2.video_size AS videoSize,t2.course_id AS courseId,t2.is_try_learn isLearn " +
                    " FROM oe_video t2 " +
                    " WHERE t2.course_id = ? AND t2.is_delete = 0  AND t2.status=1 ORDER BY t2.sort";
            videos = videoDao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql, courseId);
        } else {//播放页
            sql = "SELECT t2.id,t2.chapter_id AS chapterId,t2.name AS videoName,t2.video_id AS videoId,t2.video_time AS videoTime," +
                    "t2.video_size AS videoSize,t2.course_id AS courseId,t2.is_try_learn isLearn,t1.study_status " +
                    " FROM user_r_video t1, oe_video t2" +
                    " WHERE t1.video_id = t2.id AND t1.user_id = ? AND t1.course_id = ? AND t1.is_delete = 0  AND t1.status=1 ORDER BY t2.sort";
            videos = videoDao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql, userId, courseId);

            //查询本课程此用户的所有关卡
            sql = "SELECT t1.*,t2.`name`,t2.kpoint_id FROM oe_barrier_user t1,oe_barrier t2 WHERE t1.barrier_id=t2.id AND t1.user_id=? "
                    + " AND t1.course_id=? AND t2.is_delete=0 AND t2.status = 1 ";
            barriers = videoDao.getNamedParameterJdbcTemplate()
                    .getJdbcOperations().queryForList(sql, userId, courseId);
        }

        //组装树形结构
        for (Map<String, Object> zhangmap : chapters) {
            //循环章
            if (((Integer) zhangmap.get("level")) == 2) {
                boolean zhangIsTry = false;
                List<Map<String, Object>> zhangsons = new ArrayList<Map<String, Object>>();
                //循环取节>>>>>
                for (Map<String, Object> jiemap : chapters) {
                    if (jiemap.get("parent_id").equals(zhangmap.get("id"))) {
                        boolean jieIsTry = false;
                        List<Map<String, Object>> jiesons = new ArrayList<Map<String, Object>>();
                        //循环取知识点>>>>>
                        for (Map<String, Object> zhishidianmap : chapters) {
                            if (zhishidianmap.get("parent_id").equals(jiemap.get("id"))) {
                                boolean zhishidianIsTry = false;

                                zhishidianmap.put("hasBarrier", "0");
                                Boolean videoHasLoker = false;//该关卡视频上有没有关卡
                                String barrierId = null;//barrierId = null表示这个知识点下的视频能看，否则不能看

                                if (barriers != null && barriers.size() > 0) {
                                    //取这个知识点上的关卡>>>>>
                                    for (Map<String, Object> barriermap : barriers) {
                                        Object bo = barriermap.get("kpoint_id");
                                        if (bo != null && bo.equals(zhishidianmap.get("id").toString())) {
                                            zhishidianmap.put("hasBarrier", "1");
                                            zhishidianmap.put("barrierName", barriermap.get("name"));
                                            zhishidianmap.put("barrierStatus", barriermap.get("barrier_status"));
                                            zhishidianmap.put("lockStatus", barriermap.get("lock_status"));
                                            break;
                                        }
                                    }
                                    //<<<<<取知这个识点上的关卡

                                    //判断这个知识点下的视频能不能看 >>>>>
                                    Object pbo = zhishidianmap.get("barrier_id");
                                    if (pbo != null && !"".equals(pbo.toString())) {
                                        videoHasLoker = true;//有关卡
                                        for (Map<String, Object> barriermap : barriers) {
                                            if (barriermap.get("barrier_id").equals(pbo.toString())) {
                                                if ("0".equals(barriermap.get("lock_status").toString())) {
                                                    barrierId = barriermap.get("id").toString();
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    //<<<<<判断这个知识点下的视频能不能看
                                }

                                //循环取视频>>>>>
                                List<Map<String, Object>> zhishidiansons = new ArrayList<Map<String, Object>>();
                                for (Map<String, Object> videomap : videos) {
                                    if (videomap.get("chapterId").equals(zhishidianmap.get("id"))) {
                                        if ((Boolean) videomap.get("isLearn") && !zhishidianIsTry && !jieIsTry && !zhangIsTry) {
                                            zhishidianIsTry = true;
                                            jieIsTry = true;
                                            zhangIsTry = true;
                                        }
                                        //该关卡视频上有没有关卡、是否可以看
                                        if (videoHasLoker) {//有关卡
                                            videomap.put("lockStatus", "1");//开锁
                                            if (barrierId != null) {
                                                videomap.put("lockStatus", "0");//关锁
                                                videomap.put("barrierId", barrierId);
                                            }
                                        }
                                        zhishidiansons.add(videomap);
                                    }
                                }
                                //<<<<<循环取视频
                                zhishidianmap.put("videos", zhishidiansons);
                                zhishidianmap.put("isLearn", zhishidianIsTry);
                                jiesons.add(zhishidianmap);
                            }
                        }
                        jiemap.put("isLearn", jieIsTry);
                        jiemap.put("chapterSons", jiesons);
                        //<<<<<循环取知识点
                        zhangsons.add(jiemap);
                    }
                }
                zhangmap.put("isLearn", zhangIsTry);
                zhangmap.put("chapterSons", zhangsons);
                //<<<<<循环取节
                returnmap.add(zhangmap);
            }
        }
        return returnmap;
    }

    @Override
    public Page<Criticize> getVideoCriticize(String videoId, Integer name, Integer pageNumber, Integer pageSize, String userId) {

        return videoDao.getUserOrCourseCriticize(videoId, name, pageNumber, pageSize, userId);
    }

    @Override
    public void updateStudyStatus(String studyStatus, String videoId, String userId) {
        videoDao.updateStudyStatus(studyStatus, videoId, userId);
    }

    @Override
    public List<Map<String, Object>> getLearnedUser(String id) {
        return videoDao.getLearnedUser(id);
    }

    @Override
    public List<Map<String, Object>> getPurchasedUser(String id) {
        return videoDao.getPurchasedUser(id);
    }

    /**
     * 免费课程 用户报名时，将课程下所有视频插入用户视频中间表中
     *
     * @param courseId 课程id
     * @return
     */
    @Override
    public String saveEntryVideo(Integer courseId, String password, HttpServletRequest request) {
        CourseApplyVo courseApplyVo = courseDao.getCourseApplyByCourseId(courseId);
//         if (courseApplyVo !=null && Double.valueOf(courseApplyVo.getOriginalCost())==0 && Double.valueOf(courseApplyVo.getCurrentPrice())==0){
//    	   if(courseApplyVo.getCoursePwd()!=null&&!"".equals(courseApplyVo.getCoursePwd().trim())){
//    		   if(password==null || !password.equals(courseApplyVo.getCoursePwd())) {
//                   return "密码错误";
//               }
//    	   }
        if (courseApplyVo != null && Double.valueOf(courseApplyVo.getCurrentPrice()) == 0) {
            videoDao.saveEntryVideo(courseId, request);
            sendApplySuccessMessage(courseApplyVo);
            return "报名成功";
        }
        throw new RuntimeException(String.format("课程下架或非法操作"));
    }

    private void sendApplySuccessMessage(CourseApplyVo courseApplyVo) {
        try {
            String messageId = CodeUtil.getRandomUUID();
            String courseId = String.valueOf(courseApplyVo.getId());
            String courseName = courseApplyVo.getCourseName();
            Date startTime = courseApplyVo.getStartTime();
            String content = MessageFormat.format(APPLY_SUCCESS_TIPS, courseName);
            String userId = courseApplyVo.getUserId();
            commonMessageService.saveMessage(
                    new BaseMessage.Builder(MessageTypeEnum.COURSE.getVal())
                            .buildWeb(content)
                            .buildWeixin(weixinApplySuccessMessageCode, ImmutableMap.of("first", content, "keyword1",
                                    courseName, "keyword2", startTime != null ? TimeUtil.getYearMonthDayHHmm(startTime) : "", "remark", "点击查看"))
                            .detailId(courseId)
                            .build(userId, CourseUtil.getRouteType(courseApplyVo.getCollection(), courseApplyVo.getType()), userId));
        } catch (Exception e) {
            logger.error("报名成功时，推送消息异常: courseId: {}", courseApplyVo.getId());
            e.printStackTrace();
        }
    }

    @Override
    public String findVideosByCourseId(Integer courseId) {
        /*20170810---yuruixin*/
        CourseVo course = courseDao.findCourseOrderById(courseId);
        if (course.getType() == null && (course.getDirectId() == null || "".equals(course.getDirectId().trim()))) {
            throw new RuntimeException(String.format("视频正在来的路上，请稍后购买"));
        }
        return "购买";
    }

}
