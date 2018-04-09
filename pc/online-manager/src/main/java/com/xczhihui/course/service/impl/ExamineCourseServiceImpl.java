package com.xczhihui.course.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.course.dao.ExamineCourseDao;
import com.xczhihui.course.service.ExamineCourseService;
import com.xczhihui.course.service.PublicCourseService;
import com.xczhihui.course.vo.*;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.service.OnlineUserService;
import com.xczhihui.utils.CountUtils;
import com.xczhihui.vhall.VhallUtil;
import com.xczhihui.vhall.bean.Webinar;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service("examineCourseServiceImpl")
public class ExamineCourseServiceImpl extends OnlineBaseServiceImpl implements ExamineCourseService {

    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private ExamineCourseDao examineCourseDao;
    @Autowired
    private PublicCourseService publicCourseService;

    @Value("${ENV_FLAG}")
    private String envFlag;
    @Value("${LIVE_VHALL_USER_ID}")
    private String liveVhallUserId;
//	@Value("${rate}")
//	private double rate;

    @Override
    public List<Menu> getMenus() {

        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "SELECT id,name FROM oe_menu where is_delete = 0 and name <> '全部' and status=1 and yun_status = 1";
        dao.findEntitiesByJdbc(MenuVo.class, sql, params);
        return dao.findEntitiesByJdbc(Menu.class, sql, params);
    }

    @Override
    public Page<LiveExamineInfoVo> findCoursePage(
            LiveExamineInfoVo liveExamineInfoVo, int pageNumber, int pageSize) {
        Page<LiveExamineInfoVo> page = examineCourseDao.findCloudClassCoursePage(liveExamineInfoVo, pageNumber, pageSize);
        return page;
    }

    /**
     * Description：创建直播间
     *
     * @param entity
     * @return String
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    @Override
    public String createWebinar(Course entity) {
        Webinar webinar = new Webinar();
        webinar.setSubject(entity.getGradeName());
        webinar.setIntroduction(entity.getDescription());
        Date start = entity.getStartTime();
        String start_time = start.getTime() + "";
        start_time = start_time.substring(0, start_time.length() - 3);
        webinar.setStart_time(start_time);
        OnlineUser ou = getLecturer(entity.getUserLecturerId());
        String teacherName = null;
        if (ou != null) {
            teacherName = ou.getName();
        }
        webinar.setHost(teacherName);
        webinar.setLayout(entity.getDirectSeeding().toString());
        OnlineUser u = onlineUserService.getOnlineUserByUserId(entity.getUserLecturerId());
        webinar.setUser_id(u.getVhallId());
        if ("dev".equals(envFlag) || "test".equals(envFlag)) {
//			webinar.setUser_id(liveVhallUserId);
        } else {

        }
        String webinarId = VhallUtil.createWebinar(webinar);

        VhallUtil.setActiveImage(webinarId, VhallUtil.downUrlImage(entity.getSmallImgPath(), "image"));
        return webinarId;
    }

    public OnlineUser getLecturer(String userLecturerId) {
        DetachedCriteria dc = DetachedCriteria.forClass(OnlineUser.class);
        dc.add(Restrictions.eq("id", userLecturerId));
        OnlineUser user = dao.findEntity(dc);
        return user;
    }

    @Override
    public String updateWebinar(Course entity) {
        //更新封面
        VhallUtil.setActiveImage(entity.getDirectId(), VhallUtil.downUrlImage(entity.getSmallImgPath(), "image"));

        Webinar webinar = new Webinar();
        webinar.setId(entity.getDirectId() + "");
        webinar.setSubject(entity.getGradeName());
        webinar.setIntroduction(entity.getDescription());
        Date start = entity.getStartTime();
        String start_time = start.getTime() + "";
        start_time = start_time.substring(0, start_time.length() - 3);
        webinar.setStart_time(start_time);
        OnlineUser ou = getLecturer(entity.getUserLecturerId());
        String teacherName = null;
        if (ou != null) {
            teacherName = ou.getName();
        }
        webinar.setHost(teacherName);
        webinar.setLayout(entity.getDirectSeeding() + "");
        return VhallUtil.updateWebinar(webinar);
    }


    /**
     * Description：创建直播间
     *
     * @param entity
     * @return String
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    @Override
    public String reCreateWebinar(CourseVo entity) {
        Webinar webinar = new Webinar();
        webinar.setSubject(entity.getCourseName());
        webinar.setIntroduction(entity.getDescription());
        Date start = entity.getStartTime();
        String start_time = start.getTime() + "";
        start_time = start_time.substring(0, start_time.length() - 3);
        webinar.setStart_time(start_time);
        OnlineUser ou = getLecturer(entity.getUserLecturerId());
        String teacherName = null;
        if (ou != null) {
            teacherName = ou.getName();
        }
        webinar.setHost(teacherName);
        webinar.setLayout(ou.getDistrict());
        String webinarId = VhallUtil.createWebinar(webinar);

        VhallUtil.setActiveImage(webinarId, VhallUtil.downUrlImage(entity.getSmallimgPath(), "image"));
        return webinarId;
    }

    @Override
    public void updateLiveStatus(ChangeCallbackVo changeCallbackVo) {

        String hql = "from Course where direct_id = ?";
        Course course = dao.findByHQLOne(hql, new Object[]{changeCallbackVo.getWebinarId()});
        if (course != null) {
            switch (changeCallbackVo.getEvent()) {
                case "start":
                    course.setLiveStatus(2);
                    break;
                case "stop":
                    course.setLiveStatus(3);
                    break;
                default:
                    break;
            }
            dao.update(course);
        }

    }

    @Override
    public void updateCourse(LiveExamineInfoVo le) {
        //当课程存在密码时，设置的当前价格失效，改为0.0 yuruixin20170819
        if (le.getPassword() != null && !"".equals((le.getPassword().trim()))) {
            le.setPrice(new BigDecimal("0"));
        }
        LiveExamineInfo entity = dao.findOneEntitiyByProperty(LiveExamineInfo.class, "id", le.getId());

        entity.setLogo(le.getLogo());//课程展示图
        entity.setTitle(le.getTitle());   //课程名称
        entity.setContent(le.getContent());
        entity.setType(le.getType());//学科的id
        entity.setSeeMode(le.getSeeMode());  //观看方式 0公开 1 付费 2 密码
        entity.setWhenLong(le.getWhenLong()); //课程时长
        entity.setStartTime(le.getStartTime());//直播开始时间
        entity.setUserId(le.getUserId());      //讲师id
        entity.setExamineStatus(le.getExamineStatus());//审核状态
        entity.setIsFree(le.getIsFree());//直播间ID
        entity.setPassword(le.getPassword());//链接
        entity.setPrice(le.getPrice());   //价格

        //dao.update(entity);
        //
        this.synchronizingCourse(entity);
    }

    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + " days " + hours + " hours " + minutes + " minutes "
                + seconds + " seconds ";
    }

    @Override
    public void synchronizingCourse(LiveExamineInfo le) {
        /**
         * 查找是否已经存在这个直播申请了。如果存在那么需要查一下啦
         */
        Course course = publicCourseService.findCourseVoByLiveExanmineId(le.getId());
        if (course == null && "1".equals(le.getExamineStatus())) { //保存课程，并且生成课程id
            //保存了
            Course entity = new Course();

            //entity.setApplyId(le.getId());
            entity.setLiveSource(2);//设置直播源

            entity.setGradeName(le.getTitle()); //课程名称
            entity.setMenuId(Integer.parseInt(le.getType())); //学科的id

            //时间转换
            long l = Long.parseLong(le.getWhenLong()) / (1000 * 60 * 60);


	      /*  double f = Double.parseDouble(l+"");
			BigDecimal b = new BigDecimal(f);
			double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();*/
//			BigDecimal bd=new BigDecimal(l+"");//建议使用String参数
//			//BigDecimal bd_half_even = bd.setScale(2,RoundingMode.HALF_EVEN);
//			BigDecimal bd_half_up = bd.setScale(2,RoundingMode.HALF_UP);
            //System.out.println(bd_half_even);

            double bd_half_up = CountUtils.div(
                    Double.parseDouble(le.getWhenLong()), 3600000d, 2);
            System.out.println(bd_half_up);
            entity.setCourseLength(bd_half_up + ""); //课程时长
            entity.setCreateTime(new Date()); //当前时间
            entity.setStatus('1' + ""); //状态
            entity.setStartTime(le.getStartTime());//直播开始时间

            l = Long.parseLong(le.getWhenLong()) + le.getStartTime().getTime();
            Date dend = new Date(l);
            entity.setEndTime(dend);//直播结束时间

            entity.setType(1);//课程分类 1:公开直播课
            entity.setSmallImgPath(le.getLogo());//课程展示图
            entity.setBigImgPath(le.getLogo());
            entity.setDescriptionShow(0);//'不展示(0)，展示（1）'
            //entity.setDirectSeeding(courseVo.getDirectSeeding());//直播布局
            entity.setEndLineNumber(0);//结束时在线人数
            entity.setFlowersNumber(0);//鲜花数
            entity.setHighestNumberLine(0);//最高在线人数
            entity.setPv(0);//浏览数
            entity.setIsRecommend(0);//不推荐(0)，推荐（1）
            entity.setLearndCount(0);//已学人数
            entity.setDelete(false);//不删除
            entity.setDefaultStudentCount(0);
			/*2017.08.10  yuruixin*/
            //观看方式 0公开 1 付费 2 密码

            //默认原价格都是0
            entity.setOriginalCost(0d);

            if ("0".equals(le.getSeeMode())) {
                entity.setIsFree(true); //免费
                entity.setCurrentPrice(0d);
            } else if ("1".equals(le.getSeeMode())) {
                entity.setIsFree(false); //付费
                entity.setCurrentPrice(le.getPrice().doubleValue());
            } else if ("2".equals(le.getSeeMode())) {
                entity.setIsFree(true); //密码
                //新增课程密码
                entity.setCoursePwd(le.getPassword());
                entity.setCurrentPrice(0d);
            }
            entity.setDescription(le.getContent());
			/*2017.08.10  yuruixin*/
            if (entity.getClassRatedNum() == null) {
                entity.setClassRatedNum(0);
            }
            if (entity.getServiceType() == null) {
                entity.setServiceType(0);
            }
            entity.setUserLecturerId(le.getUserId());
            entity.setVersion(UUID.randomUUID().toString().replace("-", ""));
            entity.setDirectSeeding(1);//视频类型，默认单视频
            //创建直播间
            entity.setDefaultStudentCount(0);
            entity.setClassRatedNum(0);
            entity.setLiveStatus(2);//将直播状态预告

            entity.setVersion(UUID.randomUUID().toString().replace("-", ""));
            String webinarId = createWebinar(entity);
            entity.setDirectId(webinarId);
            entity.setExamineId(le.getId() + "");
            System.out.println("=============================");
            System.out.println(entity.toString());
            dao.save(entity);
            System.out.println("=============================");

        } else if (course != null && "2".equals(le.getExamineStatus())) { //更新  直播课程状态变为0
            //更新课程仅仅是把状态变为：0
            course.setStatus("0");
            dao.update(course);
        }
    }


    /**
     * 通过审核
     */
    @Override
    public String updateApply(String id) {
        LiveExamineInfo lei = dao.findOneEntitiyByProperty(LiveExamineInfo.class, "id", id);

        /**
         * 判断这个用户是否拥有讲师权限啦
         */
        OnlineUser ou = onlineUserService.getOnlineUserByUserId(lei.getUserId());
        //is_lecturer   是否是讲师：0,用户，1既是用户也是讲师
        if (ou.getIsLecturer() != 1) {
            return "申请人不具备讲师权限";
        }
        lei.setExamineStatus("1"); //通过啦
        lei.setAuditPerson(ManagerUserUtil.getId()); //审核人id
        lei.setAuditTime(new Date());     //审核时间
        dao.update(lei);
        /**
         * 同步审核信息到课程列表中
         */
        synchronizingCourse(lei);
        return "修改成功";
    }

    @Override
    public LiveExamineInfo findExamineById(String id) {
        return dao.findOneEntitiyByProperty(LiveExamineInfo.class, "id", id);
    }

    @Override
    public Page<LiveExamineInfoVo> findAppealListPage(
            LiveExamineInfoVo liveExamineInfoVo, int pageNumber, int pageSize) {
        Page<LiveExamineInfoVo> page = examineCourseDao.findCloudClassCoursePage1(liveExamineInfoVo, pageNumber, pageSize);
        return page;
    }

    @Override
    public void updateBohuiApply(LiveAppealInfoVo lai) {
        /**
         * 设置为没有审核
         */
        LiveAppealInfo entity = new LiveAppealInfo();
        entity.setExamineId(lai.getExamineId());          //申请的id
        entity.setReviewerPerson(ManagerUserUtil.getId());              //审核人
        entity.setReviewerTime(new Date());               //审核时间
        entity.setAgainstReason(lai.getAgainstReason());  //驳回理由
        dao.save(entity);
        LiveExamineInfo lei = findExamineById(lai.getExamineId());
        /**
         * 看状态啦。	 0未审核  1 审核通过   2 审核未通过    3 申诉中   4 申诉失败
         */
        if ("0".equals(lei.getExamineStatus())) {
            lei.setExamineStatus("2");          //2 	审核未通过
        } else if ("3".equals(lei.getExamineStatus())) {
            lei.setExamineStatus("4");          //4 	审核失败
        }
        lei.setAuditPerson(ManagerUserUtil.getId()); //审核人
        lei.setAuditTime(new Date());     //审核时间
        dao.update(lei);
    }

    @Override
    public void deletes(String[] _ids) {
		/*
		 * 修改这个的状态
		 */
        for (String id : _ids) {
            LiveExamineInfo lei = findExamineById(id);
            if (lei != null) {
            	
               /*
                * 审核状态 0未审核 1 审核通过 2 审核未通过
                * 
                * 如果删除了审核通过的那么就让此课程不显示在前台。对应中的直播课程也变为无效。
                * 如果删除了未审核的
                * 如果删除了审核未通过的那么就让此课程变为未审核的状态。对应的申诉中的信息也变成了无效的。
                */
                if (lei.getExamineStatus() != null && "1".equals(lei.getExamineStatus())) {
                    lei.setIsDelete(true);
                    dao.update(lei);
//            	   courseService.deleteCourseByExamineId(lei.getId(),true);
                }
            }
        }
    }

    @Override
    public void updateRecoverys(String[] _ids) {
		/*
		 * 修改这个的状态
		 */
        for (String id : _ids) {
            LiveExamineInfo lei = findExamineById(id);
            if (lei != null) {
                if (lei.getExamineStatus() != null && "1".equals(lei.getExamineStatus())) {
                    lei.setIsDelete(false);
                    dao.update(lei);
//              	    courseService.deleteCourseByExamineId(lei.getId(),false);
                }
            }
        }
    }


    @Override
    public LiveAppealInfo findAppealInfoById(Integer id) {
        return dao.findOneEntitiyByProperty(LiveAppealInfo.class, "id", id);
    }

    @Override
    public void deletesAppeal(String[] _ids) {
        for (String id : _ids) {
            LiveAppealInfo lei = findAppealInfoById(Integer.parseInt(id));
            if (lei != null) {
                lei.setDelete(true);
                dao.update(lei);
            }
        }
    }

    @Override
    public void updateAppeal(String[] _ids) {
        // TODO Auto-generated method stub
        for (String id : _ids) {
            LiveAppealInfo lei = findAppealInfoById(Integer.parseInt(id));
            if (lei != null) {
                lei.setDelete(false);
                dao.update(lei);
            }
        }
    }

    @Override
    public void updateCxBoHui(String id) {
        // TODO Auto-generated method stub

        //将驳回理由设置删除掉，然后将状态变为未审核状态
        //查找这个数据

        //0未审核  1 审核通过   2 审核未通过    3 申诉中   4 申诉失败

        LiveExamineInfo lei = findExamineById(id);
        if ("2".equals(lei.getExamineStatus())) {//审核失败
            lei.setExamineStatus("0");
            dao.update(lei);
        }

        if ("4".equals(lei.getExamineStatus())) {//审核失败
            lei.setExamineStatus("3");
            dao.update(lei);
        }

        List<LiveAppealInfo> list = examineCourseDao.findeAppealInfosByExamineId(id);
        for (LiveAppealInfo liveAppealInfo : list) {
            liveAppealInfo.setDelete(true);
            dao.update(liveAppealInfo);
        }
    }

    @Override
    public List<LiveAppealInfoVo> getApplysByExamId(String id) {
        // TODO Auto-generated method stub
        List<LiveAppealInfoVo> list = examineCourseDao.findeAppealInfoVosByExamineId(id);
        return list;
    }


}
