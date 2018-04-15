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

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.support.service.impl.RedisCacheService;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
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
	
	@Value("${ENV_FLAG}")
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
     * 修改鲜花数
     * @param courseId 课程ID号
     */
    @Override
    public Map<String,Object> updateFlowersNumber(Integer courseId, OnlineUser u) {
        Map<String,Object> returnMap = new HashMap<>();
        //只有当前用户登录才可以送花
        if(u == null){
            throw new RuntimeException("请登录");
        }
        //获取当前直播课程信息
        Map<String, Object> openCourseVo= dao.getOpenCourseById(courseId);
        if (openCourseVo == null){
            throw  new RuntimeException("直播课程不存在");
        }
        //是否可以送花
        Boolean  state=checkTime(openCourseVo.get("id").toString(), u);
        String key=openCourseVo.get("id").toString()+u.getLoginName().trim();
        long currentTime = System.currentTimeMillis() / 1000;
        if (state){
            int num=dao.updateFlowersNumber(courseId);
            cacheService.set(key, String.valueOf(currentTime), 30);
            returnMap.put("state",state);
            returnMap.put("num",num);
            returnMap.put("message","请在30秒之后再送花！");
        }else {
            //上次送花时间
            String value=cacheService.get(key);
            //送花剩余时间
            long timeCha=30-(currentTime-Long.valueOf(value));
            returnMap.put("state",false);
            returnMap.put("message","请在"+timeCha+"秒之后再送花！");
        }
        return  returnMap;
    }


    /**
     * 检测每个用户送花时间距离上次送花时间的差距是否是30秒
     *
     * @param courseId
     * @return true:可以送花，false：不可以送花
     */
    @Override
    public Boolean checkTime(String courseId, OnlineUser u){
        //从缓存中取当前登录用户上次送花时间
        String key=courseId+u.getLoginName().trim();
        String value=cacheService.get(key);
        if(value != null) { //判断当前时间与上次送花时间是否相差30秒
            long currentTime = System.currentTimeMillis() / 1000;
            long oldTime = Long.valueOf(value);
            if ((currentTime - oldTime) > 30) {
                return true;
            }
            return false;
        }
          return  true;
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

    /**
     * 更新在线人数
     * @param courseId  课程id
     * @param personNumber 当前在线人数
     */
    @Override
    public void  saveOnUserCount(Integer courseId, Integer personNumber){
        dao.saveOnUserCount(courseId,personNumber);
    }


    /**
     * 获取一周的课程表数据
     * @param currentTime 前端传过来的时间
     */
    @Override
    public  List<List<OpenCourseVo>>   getCourseTimetable(long currentTime){
        //存放一周内每天课程集合
        List<List<OpenCourseVo>>  cours= new ArrayList<List<OpenCourseVo>>();
        //一周7天，每天的课程集合
        List<OpenCourseVo> one = new ArrayList<OpenCourseVo>();
        List<OpenCourseVo> two = new ArrayList<OpenCourseVo>();
        List<OpenCourseVo> three = new ArrayList<OpenCourseVo>();
        List<OpenCourseVo> four = new ArrayList<OpenCourseVo>();
        List<OpenCourseVo> five = new ArrayList<OpenCourseVo>();
        List<OpenCourseVo> six = new ArrayList<OpenCourseVo>();
        List<OpenCourseVo> seven = new ArrayList<OpenCourseVo>();
        //这一周内所有的公开直播课
        List<OpenCourseVo> courList = dao.getCourseTimetable(currentTime);
        if(!CollectionUtils.isEmpty(courList)){
                //循环所有课程存放在对应的日期下的集合中
                for (OpenCourseVo cou :courList){
                    switch (cou.getDay())
                    {
                        case 1: one.add(cou);
                            break;
                        case 2: two.add(cou);
                            break;
                        case 3:three.add(cou);
                            break;
                        case 4:four.add(cou);
                            break;
                        case 5:five.add(cou);
                            break;
                        case 6:six.add(cou);
                            break;
                        default:seven.add(cou);
                    }
                }
                //将每一天课程集合放在一个集合中
                cours.add(one);
                cours.add(two);
                cours.add(three);
                cours.add(four);
                cours.add(five);
                cours.add(six);
                cours.add(seven);
        }
        return  cours;
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
                .queryForList("select type,is_free,description, IF(ISNULL(`course_pwd`), 0, 1) coursePwd,direct_id,live_status liveStatus from oe_course where id=:courseId", paramMap);
        Map<String, Object> course =  courses.get(0);
        String description = (String) course.get("description");
        Integer liveStatus = (Integer) course.get("liveStatus");
        if(description==null) {
            description = "";
        }
        description=description.replaceAll("\n", "");
        OnlineUser u = (OnlineUser) UserLoginUtil.getLoginUser(request);
        ModelAndView mv = null;
        if(liveStatus==1){
            mv = new ModelAndView("live_success_page");
        }else{
            mv = new ModelAndView("live_success_other_page");
        }
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
        return mv;
    }

	@Override
	public List<OpenCourseVo> getOpenCourse(Integer num, String id) {
		return  dao.getOpenCourse(num,id);
	}

}
