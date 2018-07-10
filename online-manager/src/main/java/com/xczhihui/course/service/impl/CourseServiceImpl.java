package com.xczhihui.course.service.impl;

import java.io.IOException;
import java.util.*;

import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;
import com.xczhihui.medical.doctor.service.IMedicalDoctorAccountService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;
import org.apache.solr.client.solrj.SolrServerException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.common.support.cc.util.CCUtils;
import com.xczhihui.common.util.DateUtil;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.enums.CourseForm;
import com.xczhihui.common.util.enums.ImInformLiveStatusType;
import com.xczhihui.common.util.enums.Multimedia;
import com.xczhihui.common.util.enums.PlayBackType;
import com.xczhihui.course.dao.CourseDao;
import com.xczhihui.course.dao.CourseSubscribeDao;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.course.service.ICourseSolrService;
import com.xczhihui.course.vo.CourseVo;
import com.xczhihui.course.vo.LecturerVo;
import com.xczhihui.course.vo.MenuVo;
import com.xczhihui.online.api.service.LiveCallbackService;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.service.OnlineUserService;
import com.xczhihui.utils.subscribe.Subscribe;
import com.xczhihui.vhall.VhallUtil;
import com.xczhihui.vhall.bean.Webinar;

/**
 * CourseServiceImpl:课程业务层接口实现类
 *
 * @author Rongcai Kang
 */
@Service("courseService")
public class CourseServiceImpl extends OnlineBaseServiceImpl implements CourseService {
    Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
    @Value("${vhall.callback.url}")
    String vhall_callback_url;
    @Value("${vhall.private.key}")
    String vhall_private_key;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseSubscribeDao courseSubscribeDao;
    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private CCUtils CCUtils;
    @Autowired
    private LiveCallbackService liveCallbackService;
    @Autowired
    private ICourseSolrService courseSolrService;
    @Autowired
    private IMedicalDoctorPostsService medicalDoctorPostsService;
    @Autowired
    private IMedicalDoctorAccountService medicalDoctorAccountService;
    @Value("${env.flag}")
    private String envFlag;
    @Value("${vhall.user.id}")
    private String liveVhallUserId;

    @Override
    public Page<CourseVo> findCoursePage(CourseVo courseVo, int pageNumber, int pageSize) {
        Page<CourseVo> page = courseDao.findCloudClassCoursePage(courseVo, pageNumber, pageSize);
        return page;

    }

    /**
     * 根据菜单编号，查找对应的课程列表
     *
     * @param menuNumber 菜单编号
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize   每页显示多少行，默认20
     * @return Example 分页列表
     */
    public List<Course> findCourseListByNumber(String name, Integer menuNumber, Integer courseType, Integer pageNumber, Integer pageSize) {
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;

        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuffer and = new StringBuffer();
        if (StringUtils.hasText(name)) {
            and.append(" and name like :name");
            paramMap.put("name", name);
        }
        String sql = "";
        if (courseType != 0) {
            sql = "from Course where 1=1 and isDelete=0 and courseType=" + courseType + and + " order by  liveTime ";
        } else {
            sql = "from Course where 1=1 and isDelete=0 and menuNumber like '" + menuNumber + "%'" + and + " order by  liveTime ";
        }
        Page<Course> page = dao.findPageByHQL(sql, paramMap, pageNumber, pageSize);
        return this.sort(page.getItems());
    }


    /**
     * 根据课程ID号，查找对应的课程对象
     *
     * @param courseId 课程id
     * @return Example 分页列表
     */
    @Override
    public CourseVo getCourseById(Integer courseId) {

        CourseVo courseVo = null;

        if (courseId != null) {
            String hql = "from Course where 1=1 and isDelete=0 and id = ?";
            Course course = dao.findByHQLOne(hql, new Object[]{courseId});
            if (course != null) {
                courseVo = new CourseVo();
                courseVo.setId(course.getId());
                courseVo.setCreateTime(course.getCreateTime());
                courseVo.setCloudClassroom(course.getCloudClassroom());
                courseVo.setBigImgPath(course.getBigImgPath());
                courseVo.setDescription(course.getDescription());
                courseVo.setDetailImgPath(course.getDetailImgPath());
                courseVo.setCourseName(course.getGradeName());
                courseVo.setQqno(course.getQqno());
                courseVo.setStatus(course.getStatus());
                courseVo.setGradeQQ(course.getGradeQQ());
                courseVo.setDefaultStudentCount(course.getDefaultStudentCount());
                courseVo.setDirectId(course.getDirectId());

                courseVo.setStartTime(course.getStartTime());
                courseVo.setUserLecturerId(course.getUserLecturerId());
                courseVo.setSmallimgPath(course.getSmallImgPath());
                courseVo.setCoursePwd(course.getCoursePwd());
            }
        }
        return courseVo;
    }

    public List<Course> sort(List<Course> couVo) {
        List<Course> firstVo = new ArrayList<Course>();
        List<Course> secondVo = new ArrayList<Course>();
        long currentTime = System.currentTimeMillis();//毫秒
        for (Course vo : couVo) {
            if (vo.getLiveTime().getTime() > currentTime) {
                firstVo.add(vo);
            } else {
                secondVo.add(vo);
            }
        }
        for (Course course : secondVo) {
            firstVo.add(course);
        }
        return firstVo;
    }


    @Override
    public List<Menu> getfirstMenus(Integer type) {

        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "SELECT id,name FROM oe_menu where is_delete = 0 and name <> '全部' and status=1 and yun_status = 1 ";
        if (type != null) {
            sql = sql + " and type = " + type;
        }
        dao.findEntitiesByJdbc(MenuVo.class, sql, params);
        return dao.findEntitiesByJdbc(Menu.class, sql, params);
    }


    @Override
    public List<MenuVo> getsecoundMenus(String firstMenuNumber) {
        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "SELECT s.id as number ,s.`name` as name from score_type  s WHERE s.id<>0 ";
        if (!"".equals(firstMenuNumber) && firstMenuNumber != null) {
            params.put("firstMenuNumber", firstMenuNumber);
            sql = "SELECT s.id as number ,s.`name` as name from menu_coursetype mc,score_type  s WHERE s.id<>0 and mc.course_type_id=s.id and mc.menu_id =:firstMenuNumber";
        }
        return dao.findEntitiesByJdbc(MenuVo.class, sql, params);
    }


    @Override
    public List<LecturerVo> getLecturers() {
        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "SELECT id,name FROM oe_lecturer where is_delete=0 ";
        dao.findEntitiesByJdbc(MenuVo.class, sql, params);
        return dao.findEntitiesByJdbc(LecturerVo.class, sql, params);
    }

    @Override
    public List<CourseVo> list(String courseType) {
        String sql = "select *,grade_name as courseName from oe_course where is_delete=0 and status=1 ";
        Map<String, Object> params = new HashMap<String, Object>();
        if (courseType != null && !"".equals(courseType)) {
            sql += " and course_type = :courseType ";
            params.put("courseType", courseType);
        }
        List<CourseVo> voList = dao.findEntitiesByJdbc(CourseVo.class, sql, params);
        return voList;
    }

    @Override
    public List<CourseVo> getCourselist(String search) {
        if ("".equals(search) || null == search) {
            String sql = "select *,grade_name as courseName from oe_course where is_delete=0 ";
            Map<String, Object> params = new HashMap<String, Object>();
            List<CourseVo> voList = dao.findEntitiesByJdbc(CourseVo.class, sql, params);
            return voList;
        }
        String sql = "select *,grade_name as courseName from oe_course where is_delete=0 and grade_name like '%" + search + "%'";
        Map<String, Object> params = new HashMap<String, Object>();
        List<CourseVo> voList = dao.findEntitiesByJdbc(CourseVo.class, sql, params);
        return voList;
    }

    @Override
    public void addCourse(CourseVo courseVo) {
        checkName(courseVo.getId(), courseVo.getCourseName(), null);
        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "SELECT IFNULL(MAX(sort),0) as sort FROM oe_course ";
        List<Course> temp = dao.findEntitiesByJdbc(Course.class, sql, params);
        int sort;
        if (temp.size() > 0) {
            sort = temp.get(0).getSort().intValue() + 1;
        } else {
            sort = 1;
        }
        //当课程存在密码时，设置的当前价格失效，改为0.0
        if (courseVo.getCoursePwd() != null && !"".equals(courseVo.getCoursePwd().trim())) {
            courseVo.setCurrentPrice(0.0);
        }
        Course course = new Course();
        //课程名称
        course.setGradeName(courseVo.getCourseName());
        //课程名称模板mv//20180109  yuxin 将课程名作为模板
        course.setClassTemplate(courseVo.getCourseName());
        //学科id
        course.setMenuId(courseVo.getMenuId());
//		course.setCourseTypeId(courseVo.getCourseTypeId()); //课程类别id
        //授课方式
        course.setCourseType(courseVo.getCourseType());
        //课程时长
        course.setCourseLength(courseVo.getCourseLength());
        //原价格
        course.setOriginalCost(courseVo.getCurrentPrice());
        //现价格
        course.setCurrentPrice(courseVo.getCurrentPrice());
        if (0 == course.getCurrentPrice()) {
            //免费
            course.setIsFree(true);
        } else {
            //付费
            course.setIsFree(false);
        }
        //排序
        course.setSort(sort);
        course.setType(courseVo.getType());

        course.setLearndCount(0);
        //当前登录人
        course.setCreatePerson(courseVo.getCreatePerson());
        //当前时间
        course.setCreateTime(new Date());
        //状态
        course.setStatus("0");
        //课程描述  2018-01-20 yuxin 暂时弃用描述
//		course.setDescription(courseVo.getDescription());
        course.setIsRecommend(0);
        course.setRecommendSort(null);

        course.setDescriptionShow(0);
        //随机生成一个10-99数，统计的时候加上这个基数
        java.util.Random random = new java.util.Random();
        // 返回[0,100)集合中的整数，注意不包括10
        int result = random.nextInt(100);
        // +1后，[0,10)集合变为[52,152)集合，满足要求
        int defaultStudentCount = result + 52;
        course.setDefaultStudentCount(defaultStudentCount);
        //yuruixin-2017-08-16
        course.setMultimediaType(courseVo.getMultimediaType());
        course.setClassRatedNum(0);
        course.setServiceType(0);
        //增加密码和老师
        course.setCoursePwd(courseVo.getCoursePwd());
        course.setUserLecturerId(courseVo.getUserLecturerId());
        course.setLecturer(courseVo.getLecturer());
//		course.setOnlineCourse(courseVo.getOnlineCourse());
        course.setPv(0);
        course.setApplyId(0);
        // zhuwenbao-2018-01-09 设置课程的展示图
        // findCourseById 是直接拿小图 getCourseDetail是从大图里拿 同时更新两个 防止两者数据不一样
        course.setSmallImgPath(courseVo.getSmallimgPath());
        course.setBigImgPath(courseVo.getSmallimgPath());
        if (course.getType() == CourseForm.OFFLINE.getCode()) {
            course.setStartTime(courseVo.getStartTime());
            course.setEndTime(courseVo.getEndTime());
            course.setAddress(courseVo.getAddress());
            course.setCity(courseVo.getRealCitys());
        } else if (course.getType() == CourseForm.LIVE.getCode()) {
            course.setMultimediaType(Multimedia.VIDEO.getCode());
            course.setStartTime(courseVo.getStartTime());
            //直播布局
            course.setDirectSeeding(courseVo.getDirectSeeding());
            course.setVersion(UUID.randomUUID().toString().replace("-", ""));
            String webinarId = createWebinar(course);
            course.setDirectId(webinarId);
            //将直播状态设为2
            course.setLiveStatus(2);
        }

        course.setCollection(false);
        course.setSubtitle(courseVo.getSubtitle());

        dao.save(course);
        if (course.getType() == CourseForm.LIVE.getCode()) {
            Subscribe.setting(course.getId(), this, courseSubscribeDao);
        }
    }

    @Override
    public List<CourseVo> listByMenuId(String menuId) {
        String sql = "select *,grade_name as courseName from oe_course where is_delete=0 and status=1 and menu_id=:menuId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("menuId", menuId);
        List<CourseVo> voList = dao.findEntitiesByJdbc(CourseVo.class, sql, params);
        return voList;
    }


    @Override
    public List<CourseVo> findCourseById(Integer id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("courseId", id);
        String sql = "SELECT \n" +
                "  oc.id AS id,\n" +
                "  oc.grade_name AS courseName,\n" +
                "  oc.class_template AS classTemplate,\n" +
                "  oc.subtitle,\n" +
                "  oc.lecturer,\n" +
                "  om.name AS xMenuName,\n" +
                "  st.name AS scoreTypeName,\n" +
                "  oc.multimedia_type multimediaType,\n" +
                "  oc.start_time startTime,\n" +
                "  oc.end_time endTime,\n" +
                "  oc.address,\n" +
                "  oc.course_length AS courseLength,\n" +
                "  oc.learnd_count AS learndCount,\n" +
                "  oc.course_pwd AS coursePwd,\n" +
                "  oc.grade_qq gradeQQ,\n" +
                "  oc.default_student_count defaultStudentCount,\n" +
                "  oc.create_time AS createTime,\n" +
                "  oc.status AS STATUS,\n" +
                "  oc.is_free AS isFree,\n" +
                "  oc.original_cost AS originalCost,\n" +
                "  oc.current_price AS currentPrice,\n" +
                "  oc.description AS description,\n" +
                "  oc.cloud_classroom AS cloudClassroom,\n" +
                "  oc.menu_id AS menuId,\n" +
                "  oc.course_type_id AS courseTypeId,\n" +
                "  oc.courseType AS courseType,\n" +
                "  oc.qqno,\n" +
                "  oc.grade_student_sum AS classRatedNum,\n" +
                "  ou.name AS userLecturerId,\n" +
                "  oc.smallimg_path AS smallimgPath \n" +
                "FROM\n" +
                "  oe_course oc \n" +
                "  LEFT JOIN `oe_user` ou \n" +
                "  ON ou.id=oc.`user_lecturer_id`\n" +
                "  LEFT JOIN oe_menu om \n" +
                "    ON om.id = oc.menu_id \n" +
                "  LEFT JOIN score_type st \n" +
                "    ON st.id = oc.course_type_id \n" +
                "WHERE oc.id = :courseId ";
        List<CourseVo> courseVoList = dao.findEntitiesByJdbc(CourseVo.class, sql, paramMap);
        sql = "select sum(IFNULL(t.default_student_count,0)) from oe_grade t where t.course_id = ?";
        courseVoList.get(0).setLearndCount(courseDao.queryForInt(sql, new Object[]{id}));//累计默认报名人数
        return courseVoList;
    }


    @Override
    public void updateCourse(CourseVo courseVo) {
        checkName(courseVo.getId(), courseVo.getCourseName(), null);

        Course course = dao.findOneEntitiyByProperty(Course.class, "id", courseVo.getId());
        //当课程存在密码时，设置的当前价格失效，改为0.0
        if (courseVo.getCoursePwd() != null && !"".equals(courseVo.getCoursePwd().trim())) {
            courseVo.setCurrentPrice(0.0);
        }

        //课程名称
        course.setGradeName(courseVo.getCourseName());
        //课程名称模板
        course.setClassTemplate(courseVo.getCourseName());
        //学科的id
        course.setMenuId(courseVo.getMenuId());
        //课程时长
        course.setCourseLength(courseVo.getCourseLength());
        //原价格
        course.setOriginalCost(courseVo.getCurrentPrice());
        //现价格
        course.setCurrentPrice(courseVo.getCurrentPrice());
        course.setMultimediaType(courseVo.getMultimediaType());
        //增加密码和老师
        course.setCoursePwd(courseVo.getCoursePwd());
        //作者禁止修改
//		course.setUserLecturerId(courseVo.getUserLecturerId());
        course.setAddress(courseVo.getAddress());
        course.setCity(courseVo.getRealCitys());

        course.setDefaultStudentCount(courseVo.getDefaultStudentCount());

        // findCourseById 是直接拿小图 getCourseDetail是从大图里拿 同时更新两个 防止两者数据不一样
        course.setSmallImgPath(courseVo.getSmallimgPath());
        course.setBigImgPath(courseVo.getSmallimgPath());

        if (0 == course.getCurrentPrice()) {
            course.setIsFree(true);
        } else {
            course.setIsFree(false);
        }
//		course.setLearndCount(courseVo.getLearndCount());
        course.setDefaultStudentCount(courseVo.getDefaultStudentCount());

        course.setSubtitle(courseVo.getSubtitle());
        course.setLecturer(courseVo.getLecturer());

        if (course.getType() == CourseForm.LIVE.getCode()) {
            course.setStartTime(courseVo.getStartTime());
            course.setVersion(UUID.randomUUID().toString().replace("-", ""));
        } else if (course.getType() == CourseForm.OFFLINE.getCode()) {
            course.setStartTime(courseVo.getStartTime());
            course.setEndTime(courseVo.getEndTime());
        }

        dao.update(course);

        if (course.getType() == CourseForm.LIVE.getCode()) {
            updateWebinar(course);
            Subscribe.setting(courseVo.getId(), this, courseSubscribeDao);
        }
    }

    /**
     * Description：校验是否重名
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 5:44 2018/1/21 0021
     **/
    @Override
    public void checkName(Integer id, String courseName, Integer applyCourseId) {
        List<Course> entitys = findByName(courseName);
        for (Course entity : entitys) {
            if (id == null) {
                id = 0;
            }
            if (applyCourseId == null) {
                applyCourseId = 0;
            }
            if (!entity.isDelete() && entity.getId().intValue() != id) {
                List<Integer> ids = new ArrayList<>();
                ids.add(applyCourseId);
                getApplyIdsByChild(applyCourseId, ids);
                if (entity.getApplyId() == null || !ids.contains(entity.getApplyId())) {
                    throw new RuntimeException(courseName + ":课程名称已存在！");
                }
            }
        }
    }

    private void getApplyIdsByChild(Integer applyId, List<Integer> ids) {
        CourseApplyInfo cai = dao.findOneEntitiyByProperty(CourseApplyInfo.class, "id", applyId);
        if (cai.getOldApplyInfoId() != null) {
            ids.add(cai.getOldApplyInfoId());
            getApplyIdsByChild(cai.getOldApplyInfoId(), ids);
        }
    }

    @Override
    public void updateRecImgPath(CourseVo courseVo) {
        Course course = dao.findOneEntitiyByProperty(Course.class, "id", courseVo.getId());
        course.setRecImgPath(courseVo.getRecImgPath());
        dao.update(course);
    }


    @Override
    public boolean updateStatus(Integer id) {
        String hql = "from Course where 1=1 and isDelete=0 and id = ?";
        Course course = dao.findByHQLOne(hql, new Object[]{id});
        course.setReleaseTime(new Date());
        boolean status = true;
        if (course.getStatus() != null && "1".equals(course.getStatus())) {
            course.setStatus("0");
            status = false;
        } else {
            course.setStatus("1");
        }
        if(course.getStatus().equals("1")){
            //更新动态
            medicalDoctorPostsService.addDoctorPosts(course.getUserLecturerId(),course.getId(),null,course.getGradeName(),course.getSubtitle());
        }
        dao.update(course);
        return status;
    }

    @Override
    public void deleteCourseById(Integer id) {
        //校验是否被引用
        String hqlPre = "from Grade where  isDelete=0 and courseId = ?";
        Grade grade = dao.findByHQLOne(hqlPre, new Object[]{id.toString()});
        if (grade != null) {
            throw new RuntimeException("该数据被引用，无法删除！");
        }

        courseDao.deleteById(id);
    }

    @Override
    public void updateSortUp(Integer id) {
        String hqlPre = "from Course where  isDelete=0 and id = ?";
        Course coursePre = dao.findByHQLOne(hqlPre, new Object[]{id});
        Integer coursePreSort = coursePre.getSort();

        String hqlNext = "from Course where sort > (select sort from Course where id= ? ) and type=2  and isDelete=0 order by sort asc";
        Course courseNext = dao.findByHQLOne(hqlNext, new Object[]{id});
        Integer courseNextSort = courseNext.getSort();

        coursePre.setSort(courseNextSort);
        courseNext.setSort(coursePreSort);

        dao.update(coursePre);
        dao.update(courseNext);
    }

    @Override
    public void updateSortDown(Integer id) {
        String hqlPre = "from Course where  isDelete=0 and id = ?";
        Course coursePre = dao.findByHQLOne(hqlPre, new Object[]{id});
        Integer coursePreSort = coursePre.getSort();
        String hqlNext = "from Course where sort < (select sort from Course where id= ? ) and type=2  and isDelete=0 order by sort desc";
        Course courseNext = dao.findByHQLOne(hqlNext, new Object[]{id});
        Integer courseNextSort = courseNext.getSort();

        coursePre.setSort(courseNextSort);
        courseNext.setSort(coursePreSort);

        dao.update(coursePre);
        dao.update(courseNext);

    }

    @Override
    public void deletes(String[] ids) {
        // TODO Auto-generated method stub
        //校验是否被引用
        for (String id : ids) {
            String hqlPre = "from Grade where  isDelete=0 and courseId = ?";
            Grade grade = dao.findByHQLOne(hqlPre, new Object[]{id});
            if (grade != null) {
                throw new RuntimeException("该数据被引用，无法删除！");
            }
        }

        for (String id : ids) {
            String hqlPre = "from Course where  isDelete=0 and id = ?";
            Course course = dao.findByHQLOne(hqlPre, new Object[]{Integer.valueOf(id)});
            if (course != null) {
                course.setDelete(true);
                dao.update(course);
            }
        }

    }


    @Override
    public List<TeachMethod> getTeachMethod() {
        return courseDao.getTeachMethod();
    }


    @Override
    public List<Course> getCourse() {
        return courseDao.getCourse();
    }

    @Override
    public List<Grade> getGrade(String gradeIds) {
        return courseDao.getGrade(gradeIds);
    }

    @Override
    public void updateCourseDetail(String courseId, String smallImgPath, String detailImgPath, String courseDetail,
                                   String courseOutline, String commonProblem, String lecturerDescription) {
        Course c = courseDao.findOneEntitiyByProperty(Course.class, "id", Integer.valueOf(courseId));

        // zhuwenbao 2018-01-09 在课程详情页面已经移除调smallImgPath选项 不加判断的话会将之前的smallImgPath设置为null
        if (smallImgPath != null && !"".equals(smallImgPath.trim())) {
            c.setSmallImgPath(smallImgPath);
            c.setBigImgPath(smallImgPath);
        }
        c.setDetailImgPath(detailImgPath);
        c.setCourseDetail(courseDetail);
        c.setCourseOutline(courseOutline);
        c.setCommonProblem(commonProblem);
        c.setLecturerDescription(lecturerDescription);
        courseDao.update(c);
    }


    @Override
    public Map<String, String> getCourseDetail(String courseId) {
        Course c = courseDao.get(Integer.valueOf(courseId), Course.class);
        if (c != null) {
            Map<String, String> retn = new HashMap<String, String>();
            retn.put("detailImgPath", c.getDetailImgPath());
            retn.put("courseDetail", c.getCourseDetail());
            retn.put("courseOutline", c.getCourseOutline());
            retn.put("commonProblem", c.getCommonProblem());
            retn.put("lecturerDescription", c.getLecturerDescription());
            retn.put("gradeName", c.getGradeName());
            retn.put("descriptionShow", c.getDescriptionShow().toString());
            /*2017-08-14---yuruixin*/
            String imgStr = c.getBigImgPath();
            String img0 = null;
            String img1 = null;
            String img2 = null;
            if (imgStr != null) {
                String[] imgArr = imgStr.split("dxg");
                img0 = imgArr[0];
                if (imgArr.length > 1) {
                    img1 = imgArr[1];
                }
                if (imgArr.length > 2) {
                    img2 = imgArr[2];
                }
            }
            retn.put("smallImgPath", img0);
            retn.put("smallImgPath1", img1);
            retn.put("smallImgPath2", img2);
            /*2017-08-14---yuruixin*/
            return retn;
        }
        return null;
    }


    @Override
    public List<ScoreType> getScoreType() {
        return courseDao.getScoreType();
    }


    @Override
    public void addPreview(String courseId, String smallImgPath, String detailImgPath, String courseDetail,
                           String courseOutline, String commonProblem) {
        if (courseDetail == null) {
            courseDetail = "";
        }
        CoursePreview c = courseDao.get(Integer.valueOf(courseId), CoursePreview.class);
        if (c != null) {
            courseDao.getNamedParameterJdbcTemplate().getJdbcOperations().execute("delete from oe_course_preview where id = " + courseId);
        }
        courseDao.getNamedParameterJdbcTemplate().getJdbcOperations().execute("INSERT INTO oe_course_preview ( " +
                "	id, " +
                "	create_person, " +
                "	create_time, " +
                "	is_delete, " +
                "	bigimg_path, " +
                "	cloud_classroom, " +
                "	description, " +
                "	detailimg_path, " +
                "	grade_name, " +
                "	graduate_time, " +
                "	live_time, " +
                "	smallimg_path, " +
                "	sort, " +
                "	courseType, " +
                "	STATUS, " +
                "	learnd_count, " +
                "	original_cost, " +
                "	current_price, " +
                "	course_length, " +
                "	menu_id, " +
                "	course_type_id, " +
                "	is_free, " +
                "	class_template, " +
                "	course_detail, " +
                "	course_outline, " +
                "	common_problem, " +
                "	lecturer_id, " +
                "	is_recommend, " +
                "	recommend_sort, " +
                "	qqno, " +
                "	description_show " +
                ") SELECT " +
                "	id, " +
                "	create_person, " +
                "	create_time, " +
                "	is_delete, " +
                "	bigimg_path, " +
                "	cloud_classroom, " +
                "	description, " +
                "	detailimg_path, " +
                "	grade_name, " +
                "	graduate_time, " +
                "	live_time, " +
                "	smallimg_path, " +
                "	sort, " +
                "	courseType, " +
                "	1, " +
                "	learnd_count, " +
                "	original_cost, " +
                "	current_price, " +
                "	course_length, " +
                "	menu_id, " +
                "	course_type_id, " +
                "	is_free, " +
                "	class_template, " +
                "	course_detail, " +
                "	course_outline, " +
                "	common_problem, " +
                "	lecturer_id, " +
                "	is_recommend, " +
                "	recommend_sort, " +
                "	qqno, " +
                "	description_show " +
                "FROM " +
                "	oe_course where id=" + courseId);
        courseDao.getNamedParameterJdbcTemplate().getJdbcOperations().
                update("UPDATE oe_course_preview SET smallimg_path=?,bigimg_path=?,detailimg_path=?,course_detail=?,course_outline=?,common_problem=? WHERE id=?",
                        smallImgPath, smallImgPath, detailImgPath, courseDetail, courseOutline, commonProblem, Integer.valueOf(courseId));
    }


    @Override
    public boolean updateRec(String[] ids, int isRecommend) {
        List<String> ids2 = new ArrayList();
        //如果是要推荐 那么就验证 推荐数量是否大于4
        if (isRecommend == 1) {
            //校验是否被引用
            String hqlPre = "from Course where isDelete=0 and isRecommend = 1";
            List<Course> list = dao.findByHQL(hqlPre);
            if (list.size() > 0) {//只有原来大于0才执行
                for (int i = 0; i < ids.length; i++) {
                    int j = 0;
                    Iterator<Course> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        //剔除本次推荐的与已经推荐的重复的

                        Course course = iterator.next();
                        if (course.getId() == Integer.parseInt(ids[i])) {//如果存在就把他剔除掉从list中
                            j = 1;
                        }
                    }

                    if (j == 0) {
                        ids2.add(ids[i]);
                    }
                }
            } else {
                for (int i = 0; i < ids.length; i++) {
                    ids2.add(ids[i]);
                }
            }
            //已经存在的数量 +  即将添加的数量
            if ((list.size() + ids2.size()) > 12) {
                //取消推荐数目限制
            }
        } else {//如果是取消推荐
            for (int i = 0; i < ids.length; i++) {
                ids2.add(ids[i]);
            }
        }

        String sql = "select ifnull(min(recommend_sort),0) from oe_course where  is_delete=0 and is_recommend = 1";
        int i = dao.queryForInt(sql, null);//最小的排序

        for (String id : ids2) {
            if (id == "" || id == null) {
                continue;
            }
            i = i - 1;
            String hqlPre = "from Course where  isDelete = 0 and id = ?";
            Course course = dao.findByHQLOne(hqlPre, new Object[]{Integer.valueOf(id)});
            if (course != null) {
                course.setIsRecommend(isRecommend);
                course.setRecommendSort(i);
                dao.update(course);
            }
        }
        return true;
    }

    @Override
    public boolean updateCityRec(String[] ids, int isRecommend) {
        for (String id : ids) {
            if (id == "" || id == null) {
                continue;
            }
            String hqlPre = "from OffLineCity where  isDelete = 0 and id = ?";
            OffLineCity course = dao.findByHQLOne(hqlPre, new Object[]{Integer.valueOf(id)});
            if (course != null) {
                course.setIsRecommend(isRecommend);
                dao.update(course);
            }
        }
        return true;
    }

    @Override
    public void updateSortUpRec(Integer id) {
        String hqlPre = "from Course where  isDelete=0 and id = ?";
        Course coursePre = dao.findByHQLOne(hqlPre, new Object[]{id});
        Integer coursePreSort = coursePre.getRecommendSort();

        String hqlNext = "from Course where recommendSort > (select recommendSort from Course where id= ? )  and isDelete=0 and isRecommend = 1 order by recommendSort asc";
        Course courseNext = dao.findByHQLOne(hqlNext, new Object[]{id});
        Integer courseNextSort = courseNext.getRecommendSort();

        coursePre.setRecommendSort(courseNextSort);
        courseNext.setRecommendSort(coursePreSort);

        dao.update(coursePre);
        dao.update(courseNext);

    }

    @Override
    public void updateCitySortUp(Integer id) {
        String hqlPre = "from OffLineCity where  isDelete=0 and id = ?";
        OffLineCity coursePre = dao.findByHQLOne(hqlPre, new Object[]{id});

        if (coursePre.getIsRecommend() == 0) {
            throw new RuntimeException("排序只能选择推荐的线下课");
        }

        Integer coursePreSort = coursePre.getSort();
        String hqlNext = "from OffLineCity where sort > (select sort from OffLineCity where id= ? )  and isDelete=0  and isRecommend = 1 order by sort asc";
        OffLineCity courseNext = dao.findByHQLOne(hqlNext, new Object[]{id});
        Integer courseNextSort = courseNext.getSort();

        coursePre.setSort(courseNextSort);
        courseNext.setSort(coursePreSort);

        dao.update(coursePre);
        dao.update(courseNext);

    }

    @Override
    public void updateCitySortDown(Integer id) {
        String hqlPre = "from OffLineCity where  isDelete=0 and id = ?";
        OffLineCity coursePre = dao.findByHQLOne(hqlPre, new Object[]{id});

        if (coursePre.getIsRecommend() == 0) {
            throw new RuntimeException("排序只能选择推荐的线下课");
        }

        Integer coursePreSort = coursePre.getSort();

        String hqlNext = "from OffLineCity where sort < (select sort from OffLineCity where id= ? ) and isRecommend = 1 and isDelete=0 order by sort desc";
        OffLineCity courseNext = dao.findByHQLOne(hqlNext, new Object[]{id});

        if (courseNext == null) {
            throw new RuntimeException("此排序已是最小值了");
        }
        Integer courseNextSort = courseNext.getSort();

        coursePre.setSort(courseNextSort);
        courseNext.setSort(coursePreSort);

        dao.update(coursePre);
        dao.update(courseNext);
    }

    @Override
    public void updateSortDownRec(Integer id) {
        String hqlPre = "from Course where  isDelete=0 and id = ?";
        Course coursePre = dao.findByHQLOne(hqlPre, new Object[]{id});
        Integer coursePreSort = coursePre.getRecommendSort();

        String hqlNext = "from Course where recommendSort < (select recommendSort from Course where id= ? ) and isRecommend = 1 and isDelete=0 order by recommendSort desc";
        Course courseNext = dao.findByHQLOne(hqlNext, new Object[]{id});
        Integer courseNextSort = courseNext.getRecommendSort();

        coursePre.setRecommendSort(courseNextSort);
        courseNext.setRecommendSort(coursePreSort);

        dao.update(coursePre);
        dao.update(courseNext);
    }


    @Override
    public List<Course> findByName(String name) {
        List<Course> courses = dao.findEntitiesByProperty(Course.class, "gradeName", name);
        return courses;
    }

    @Override
    public void updateDescriptionShow(CourseVo courseVo) {
        String hqlPre = "from Course where id = ?";
        Course course = dao.findByHQLOne(hqlPre, new Object[]{courseVo.getId()});
        course.setDescriptionShow(courseVo.getDescriptionShow());
        dao.update(course);
    }

    @Override
    public Object lectereListByCourseIdAndRoleType(int roleType, String courseId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleType", roleType);
        params.put("courseId", courseId);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT " +
                "	ol.id, " +
                "	ol. name, " +
                "	CASE " +
                "WHEN grl.lecturer_id > 0 THEN " +
                "	1 " +
                "ELSE " +
                "	0 " +
                "END status " +
                "FROM " +
                "	oe_lecturer ol " +
                "LEFT JOIN course_r_lecturer grl ON ( " +
                "	ol.id = grl.lecturer_id " +
                "	AND grl.course_id =:courseId " +
                ") " +
                "JOIN oe_course oc ON ( " +
                "	ol.menu_id = oc.menu_id " +
                "	AND oc.id =:courseId " +
                ") " +
                "WHERE " +
                "	ol.role_type =:roleType " +
                " AND ol.is_delete = 0 ");

        return dao.getNamedParameterJdbcTemplate().queryForList(sql.toString(), params);
    }

    @Override
    public void saveTeachers(String gradeId, String courseId, String userName,
                             List<String> roleTypes) {
        Map<String, Object> param = new HashMap<String, Object>();
        String sql = "DELETE FROM course_r_lecturer WHERE course_id=:courseId ";
        param.put("courseId", courseId);
        dao.getNamedParameterJdbcTemplate().update(sql, param);
        if (roleTypes != null && roleTypes.size() > 0) {
            for (String roleType1 : roleTypes) {
                Map<String, Object> roleType1Param = new HashMap<String, Object>();
                roleType1Param.put("courseId", courseId);
                roleType1Param.put("lecturerId", roleType1);
                roleType1Param.put("createPerson", userName);
                roleType1Param.put("createTime", new Date());
                roleType1Param.put("id", UUID.randomUUID().toString().replace("-", ""));
                String insertRoleType1 = "INSERT INTO course_r_lecturer(id,course_id,lecturer_id,is_delete,create_person,create_time) VALUES(:id,:courseId,:lecturerId,0,:createPerson,:createTime) ";
                dao.getNamedParameterJdbcTemplate().update(insertRoleType1, roleType1Param);
            }
        }
    }

    @Override
    public Course findOpenCourseById(Integer id) {
        Course course = dao.findOneEntitiyByProperty(Course.class, "id", id);
        return course;
    }

    @Override
    public Course findOpenCourseById(Integer id, String version) {
        Course course = dao.findOneEntitiyByProperty(Course.class, "id", id);
        if (course != null && version != null && version.equals(course.getVersion()) && !course.isDelete() && "1".equals(course.getStatus())) {
            return course;
        }
        return null;
    }

    @Override
    public void updateSentById(Integer id) {
        Course course = dao.findOneEntitiyByProperty(Course.class, "id", id);
        course.setSent(true);
        dao.update(course);
    }


    @Override
    public Course getPublicCourseById(Integer courseId) {
        String hql = "from Course where 1=1 and isDelete=0 and id = ?";
        Course course = dao.findByHQLOne(hql, new Object[]{courseId});
        return course;
    }

    @Override
    public void addCourseCity(String city) {
        /**
         * 添加前，看存在此城市，如果存在那么就不添加
         */
        if (!findCourseCityByName(city)) {
            Map<String, Object> params1 = new HashMap<String, Object>();
            String sql = "SELECT IFNULL(MAX(sort),0) as sort FROM oe_offline_city ";
            List<OffLineCity> temp = dao.findEntitiesByJdbc(OffLineCity.class, sql, params1);
            int sort;
            if (temp.size() > 0) {
                sort = temp.get(0).getSort().intValue() + 1;
            } else {
                sort = 1;
            }
            String savesql = " insert into oe_offline_city (create_person,create_time,city_name,sort)" +
                    " values ('" + ManagerUserUtil.getId() + "',now(),'" + city + "','" + sort + "')";
            Map<String, Object> params = new HashMap<String, Object>();
            dao.getNamedParameterJdbcTemplate().update(savesql, params);
        }
    }

    @Override
    public Boolean findCourseCityByName(String city) {
        Map<String, Object> params = new HashMap<String, Object>();
        List<Integer> list = dao.getNamedParameterJdbcTemplate().
                queryForList("select count(*) as c from oe_offline_city o where  o.city_name = '" + city + "'", params, Integer.class);
        if (list != null && null != list.get(0) && list.get(0) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Page<OffLineCity> getCourseCityList(OffLineCity searchVo, Integer pageNumber, Integer pageSize) {
        String sql = "select  *  from  oe_offline_city where  is_delete = 0 and status = 1 ";
        if (searchVo.getCityName() != null && !"".equals(searchVo.getCityName())) {
            sql += " and city_name = '" + searchVo.getCityName() + "'";
        }
        sql += " order by is_recommend desc,sort desc ";
        Map<String, Object> params = new HashMap<String, Object>();
        Page<OffLineCity> courseVos = dao.findPageBySQL(sql, params, OffLineCity.class, pageNumber, pageSize);
        return courseVos;
    }

    @Override
    public void updateCourseCityStatus(Integer courseId) {
        String hql = "from Course where 1=1 and isDelete=0 and id = ?";
        Course course = dao.findByHQLOne(hql, new Object[]{courseId});

        Map<String, Object> params = new HashMap<String, Object>();
        /*
         * 课程中所有关于这个城市的
         */
        List<Integer> list = dao.getNamedParameterJdbcTemplate().
                queryForList("select count(*) as c from oe_course o where  o.status=1 and "
                        + "o.city = '" + course.getCity() + "'", params, Integer.class);

        Integer status = 0;
        if (list != null && null != list.get(0) && list.get(0) > 0) {
            status = 1;
        }
        String savesql = " update  oe_offline_city  set status = '" + status + "' where city_name = '" + course.getCity() + "' ";
        dao.getNamedParameterJdbcTemplate().update(savesql, params);
    }

    @Override
    public void deleteCourseCityStatus(Integer courseId) {

        String hql = "from Course where 1=1 and isDelete=0 and id = ?";
        Course course = dao.findByHQLOne(hql, new Object[]{courseId});

        Map<String, Object> params = new HashMap<String, Object>();

        //查出所有为禁用的
        List<Integer> list = dao.getNamedParameterJdbcTemplate().
                queryForList("select count(*) as c from oe_course o where o.is_delete=0  and "
                        + "o.city = '" + course.getCity() + "'", params, Integer.class);

        Integer status = 1;
        if (list != null && null != list.get(0) && list.get(0) > 0) {
            status = 0;
        }
        String savesql = " update  oe_offline_city  set  is_delete= '" + status + "' where city_name = '" + course.getCity() + "' ";
        dao.getNamedParameterJdbcTemplate().update(savesql, params);
    }

    @Override
    public void updateCourseCity(OffLineCity offLineCity) {
        String savesql = " update  oe_offline_city  set icon = '" + offLineCity.getIcon() + "' where id = '" + offLineCity.getId() + "' ";
        Map<String, Object> params = new HashMap<String, Object>();
        dao.getNamedParameterJdbcTemplate().update(savesql, params);
    }

    @Override
    public Course findCourseInfoById(Integer id) {
        Course course = dao.get(id, Course.class);
        course.setCurrentPrice(course.getCurrentPrice() * 10);
        if ((course.getCollection() == null || !course.getCollection()) && course.getDirectId() != null && course.getType() == CourseForm.VOD.getCode()) {
            String audioStr = "";
            if (course.getMultimediaType() == Multimedia.AUDIO.getCode()) {
                audioStr = "_2";
            }

            String playCode = CCUtils.getPlayCode(course.getDirectId(), audioStr, "600", "490");

            course.setPlayCode(playCode);
        }

        if (course.getCollection() != null && course.getCollection()) {
            List<Course> courses = courseDao.getCourseByCollectionId(course.getId());
            course.setCourseInfoList(courses);
        }
        DetachedCriteria menudc = DetachedCriteria.forClass(Menu.class);
        menudc.add(Restrictions.eq("id", Integer.valueOf(course.getMenuId())));
        Menu menu = dao.findEntity(menudc);
        if (menu != null) {
            course.setCourseMenu(menu.getName());
        }
        return course;
    }

    @Override
    public void updateRecommendSort(Integer id, Integer recommendSort, String recommendTime) {
        String hqlPre = "from Course where  isDelete = 0 and id = ?";
        Course course = dao.findByHQLOne(hqlPre, new Object[]{id});
        if (course != null) {
            if (recommendTime != null && !recommendTime.equals("")) {
                course.setSortUpdateTime(DateUtil.parseDate(recommendTime, "yyyy-MM-dd HH:mm:ss"));
            } else {
                course.setSortUpdateTime(null);
            }
            course.setRecommendSort(recommendSort);
            dao.update(course);
        }
    }

    @Override
    public List<Integer> updateDefaultSort() {
        List<Integer> updateRecommendIds = getUpdateRecommendId();
        updateRecommendIds.forEach(id -> updateDefaultSort(id));
        return updateRecommendIds;
    }

    @Override
    public void updateDefaultSort(Integer id) {
        String sql = " UPDATE  oe_course  SET recommend_sort=0 WHERE id = :id";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        dao.getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    public List<Integer> getUpdateRecommendId() {
        String sql = "SELECT id FROM oe_course WHERE sort_update_time<= NOW() AND recommend_sort != 0";
        List<Integer> ids = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
                .queryForList(sql, Integer.class);
        return ids;
    }

    public String createWebinar(Course entity) {
        Webinar webinar = new Webinar();
        webinar.setSubject(entity.getGradeName());
        webinar.setIntroduction(entity.getDescription());
        Date start = entity.getStartTime();
        String start_time = start.getTime() + "";
        start_time = start_time.substring(0, start_time.length() - 3);
        webinar.setStart_time(start_time);
        webinar.setHost(entity.getLecturer());
        webinar.setLayout(entity.getDirectSeeding().toString());
        OnlineUser u = onlineUserService.getOnlineUserByUserId(entity.getUserLecturerId());
        webinar.setUser_id(u.getVhallId());
        String webinarId = VhallUtil.createWebinar(webinar);

        VhallUtil.setActiveImage(webinarId, VhallUtil.downUrlImage(entity.getSmallImgPath(), "image"));
        VhallUtil.setCallbackUrl(webinarId, vhall_callback_url, vhall_private_key);
        return webinarId;
    }

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
        webinar.setHost(entity.getLecturer());
        webinar.setLayout(entity.getDirectSeeding() + "");
        return VhallUtil.updateWebinar(webinar);
    }


    @Override
    public void updatedefaultStudent(Integer id, Integer defaultStudentCount) {
        String hqlPre = "from Course where  isDelete = 0 and id = ?";
        Course course = dao.findByHQLOne(hqlPre, new Object[]{id});
        if (defaultStudentCount > 10000000) {
            throw new RuntimeException("目前支持上传的最大值为：" + 10000000);
        }
        if (course != null) {
            course.setDefaultStudentCount(defaultStudentCount);
            dao.update(course);
        }
    }

    @Override
    public void updatePlaybackState(Integer courseId, int i) {
        String hql = "from Course where 1=1 and isDelete=0 and id = ?";
        Course course = dao.findByHQLOne(hql, new Object[]{courseId});
        course.setReleaseTime(new Date());
        course.setPlayBackType(i);

        if (i == PlayBackType.GENERATION_FAILURE.getCode()) { //回放生产失败
            course.setStatus("0");
            logger.info("回放生产失败");
            liveCallbackService.liveCallbackImRadio(course.getId() + "",
                    ImInformLiveStatusType.PLAYBACK_FAILURE.getCode());
        } else if (i == PlayBackType.GENERATION_SUCCESS.getCode()) { //回放生产成功
            course.setStatus("1");
            liveCallbackService.liveCallbackImRadio(course.getId() + "",
                    ImInformLiveStatusType.PLAYBACK_SUCCES.getCode());
            logger.info("回放生产成功");

        }
        dao.update(course);
    }


    @Override
    public void initCourseSolrDataById(Integer courseId) throws IOException, SolrServerException {
        courseSolrService.initCourseSolrDataById(courseId);
    }
}
