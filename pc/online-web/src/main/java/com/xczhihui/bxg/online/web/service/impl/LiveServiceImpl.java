package com.xczhihui.bxg.online.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xczhihui.bxg.online.common.domain.ApplyGradeCourse;
import com.xczhihui.bxg.online.web.dao.ApplyGradeCourseDao;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.support.service.impl.RedisCacheService;
import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.LiveDao;
import com.xczhihui.bxg.online.web.service.LiveService;
import com.xczhihui.bxg.online.web.vo.OpenCourseVo;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.ItcastUser;

/**
 *   公开直播课业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class LiveServiceImpl  extends OnlineBaseServiceImpl implements LiveService{

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LiveDao  dao;
    @Resource(name = "cacheService")
    private RedisCacheService cacheService;

	@Autowired
	private UserCenterAPI userCenterAPI;
	@Autowired
	private ApplyGradeCourseDao applyGradeCourseDao;
	
	@Value("${env.flag}")
    private String env;
	@Value("${rate}")
    private int rate;
	@Value("${gift.im.room.postfix}")
	private String postfix;
	@Value("${gift.im.boshService}")
	private String boshService;//im服务地址
	@Value("${gift.im.host}")
	private  String host;
	

    /**
     * 首页获取公开直播课
     * @param  num:条数
     * @return
     */
    @Override
    public List<OpenCourseVo> getOpenCourse(Integer num){
        return  dao.getOpenCourse(num);
    }

    @Override
    public OpenCourseVo getIndexLive() {
//    	List<OpenCourseVo> openCourses = getOpenCourse(null,null);
    	List<OpenCourseVo> openCourses = getOpenCourse(null,null);
    	if(openCourses.size()>0){
    		for (OpenCourseVo openCourseVo : openCourses) {
//				if(openCourseVo.getBroadcastState()!=2){
					return openCourseVo;
//				}
			}
    	}
//        return dao.getIndexLive();
        return null;
    }

    /**
     * 修改直播课程的浏览数
     *
     * @param courseId 鲜花数
     */
    @Override
    public int updateBrowseSum(Integer courseId) {
        return dao.updateBrowseSum(courseId);
    }

    /**
     * 获取直播课程信息，根据课程id查询课程
     * @param courseId 课程id号
     * @param user
     */
    @Override
    public Map<String, Object> getOpenCourseById(Integer courseId, OnlineUser user) {
        if(user == null){
            throw new RuntimeException("用户未登录");
        }else{
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", user.getId());
            paramMap.put("courseId", courseId);
            List<Map<String, Object>> argc = dao.getNamedParameterJdbcTemplate()
                    .queryForList("SELECT id FROM `apply_r_grade_course` argc WHERE argc.course_id = :courseId AND argc.`user_id`=:userId", paramMap);
            if(argc.size()==0){
                List<Map<String, Object>> selfCourse = dao.getNamedParameterJdbcTemplate()
                        .queryForList("SELECT id FROM `oe_course` oc WHERE oc.id = :courseId AND oc.`user_lecturer_id`=:userId", paramMap);
                if(selfCourse.size()==0){
                    ApplyGradeCourse applyGradeCourse = applyGradeCourseDao.findCollectionCourseByCourseIdAndUserId(courseId,user.getId());
                    if(applyGradeCourse == null) {
                        logger.info("用户{}未购买该课程{}",user.getId(),courseId);
                        throw new RuntimeException("用户未购买该课程");
                    }
                }
            }
        }
        return  dao.getOpenCourseById(courseId);
    }

    @Override
    public ModelAndView livepage
            (String courseId, HttpServletRequest request,HttpServletResponse response) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        BxgUser user = UserLoginUtil.getLoginUser(request);
        paramMap.put("courseId", courseId);
        if(user==null){
            return  new ModelAndView("redirect:/course/courses/"+courseId);
        }else{
            paramMap.put("userId", user.getId());
            List<Map<String, Object>> argc = dao.getNamedParameterJdbcTemplate()
                    .queryForList("SELECT id FROM `apply_r_grade_course` argc WHERE argc.course_id = :courseId AND argc.`user_id`=:userId", paramMap);
            if(argc.size()==0){
                ApplyGradeCourse applyGradeCourse = applyGradeCourseDao.findCollectionCourseByCourseIdAndUserId(Integer.valueOf(courseId), user.getId());
                if(applyGradeCourse != null) {
                    return  new ModelAndView("redirect:/course/courses/"+courseId);
                }
            }
        }

        List<Map<String, Object>> courses = dao.getNamedParameterJdbcTemplate()
                .queryForList("select type,is_free,description, IF(ISNULL(`course_pwd`), 0, 1) coursePwd,"
                		+ "direct_id,live_status liveStatus,user_lecturer_id as userLecturerId  from oe_course where id=:courseId", paramMap);
        Map<String, Object> course =  courses.get(0);
        String description = (String) course.get("description");
        Integer liveStatus = (Integer) course.get("liveStatus");
        if(description==null) {
            description = "";
        }
        description=description.replaceAll("\n", "");
        OnlineUser u = (OnlineUser) UserLoginUtil.getLoginUser(request);
        //ModelAndView mv = null;
       /* if(liveStatus==1){
            mv = new ModelAndView("live_success_page");
        }else{
            mv = new ModelAndView("live_success_other_page");
        }*/
        ModelAndView  mv = null;
        
        if(liveStatus==1 || liveStatus==3){ // 直播中和直播回放去这个页面
            mv = new ModelAndView("live_success_page");
        }else if(liveStatus==2){			  //直播还没 	
            mv = new ModelAndView("live_success_other_page");
        }
        //oc.user_lecturer_id as userLecturerId
        mv.addObject("lecturerId",course.get("userLecturerId"));
        mv.addObject("userId",u.getId());
        mv.addObject("courseId",courseId);
        mv.addObject("liveStatus",liveStatus);
        mv.addObject("roomId",course.get("direct_id"));
        mv.addObject("roomJId",courseId+postfix);
        mv.addObject("boshService",boshService);
        mv.addObject("now",new Date().getTime());
        mv.addObject("description",description);
        mv.addObject("email", user == null ? null : user.getId()+"@xczh.com");
        mv.addObject("name", user == null ? null : user.getName());
        mv.addObject("k", "yrxk");//TODO 此处暂时写死
        ItcastUser iu = userCenterAPI.getUser(user.getLoginName());
        mv.addObject("guId", iu.getId());
        mv.addObject("guPwd", iu.getPassword());

        mv.addObject("env", env);
        mv.addObject("host", host);
        mv.addObject("rate", rate);
        
        Map<String,String> map = new HashMap<String,String>();
        map.put("llala", "llal");
        return mv;
    }

	@Override
	public List<OpenCourseVo> getOpenCourse(Integer num, String id) {
		return  dao.getOpenCourse(num,id);
	}

	//查看学习记录是否存在
	
	
}
