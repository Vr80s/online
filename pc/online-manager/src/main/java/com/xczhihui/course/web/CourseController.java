package com.xczhihui.course.web;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.xczhihui.course.service.CourseService;
import com.xczhihui.user.service.OnlineUserService;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.common.support.domain.Attachment;
import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.common.support.service.AttachmentType;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.common.domain.TeachMethod;
import com.xczhihui.course.vo.CourseVo;
import com.xczhihui.course.vo.LecturerVo;
import com.xczhihui.course.vo.MenuVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.utils.Group;

/**
 * 课程管理控制层实现类
 *
 * @author yxd
 */

@Controller
@RequestMapping("cloudclass/course")
public class CourseController extends AbstractController {
    protected final static String CLOUD_CLASS_PATH_PREFIX = "/cloudClass/";
    @Autowired
    private CourseService courseService;
    @Autowired
    private AttachmentCenterService att;
    @Value("${web.url}")
    private String weburl;

    @Autowired
    private OnlineUserService onlineUserService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request) {

        List<Menu> menuVos = courseService.getfirstMenus(null);
        request.setAttribute("menuVo", menuVos);

        //在列表初始化时查找出课程类别
        List<ScoreType> scoreTypeVos = courseService.getScoreType();
        request.setAttribute("scoreTypeVo", scoreTypeVos);

        //在列表初始化时查找出授课方式
        List<TeachMethod> teachMethodVos = courseService.getTeachMethod();
        request.setAttribute("teachMethodVo", teachMethodVos);

        List<LecturerVo> lecturers = courseService.getLecturers();
        request.setAttribute("lecturerVo", lecturers);

        List<Map<String, Object>> mapList = onlineUserService.getAllUserLecturer();
        for (Map<String, Object> map : mapList) {
            String str = "昵称:" + map.get("name").toString() + ",帐号:" + map.get("logo").toString();
            map.put("name", str);
        }
        request.setAttribute("mapList", mapList);

        return CLOUD_CLASS_PATH_PREFIX + "/course";
    }

    @RequestMapping(value = "courseDetail")
    public String courseDetail(HttpServletRequest request) {
        List<Menu> menuVos = courseService.getfirstMenus(null);
        request.setAttribute("menuVo", menuVos);

        //在列表初始化时查找出课程类别
        List<ScoreType> scoreTypeVos = courseService.getScoreType();
        request.setAttribute("scoreTypeVo", scoreTypeVos);

        //在列表初始化时查找出授课方式
        List<TeachMethod> teachMethodVos = courseService.getTeachMethod();
        request.setAttribute("teachMethodVo", teachMethodVos);

        List<LecturerVo> Lecturers = courseService.getLecturers();
        request.setAttribute("lecturerVo", Lecturers);

        request.setAttribute("weburl", weburl);
        return CLOUD_CLASS_PATH_PREFIX + "/courseDetail";
    }

    @RequestMapping(value = "videoRes")
    public ModelAndView videoRes(HttpServletRequest request, String page, String courseId, String courseName) {
        ModelAndView mav = new ModelAndView("cloudClass/videoRes");
        mav.addObject("weburl", weburl);
        mav.addObject("page", page);
        mav.addObject("courseId", courseId);
        mav.addObject("courseName", courseName);
        return mav;
    }

    //@RequiresPermissions("course:menu:course")
    @RequestMapping(value = "list")
    @ResponseBody
    public TableVo courses(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);

        CourseVo searchVo = new CourseVo();
        Group courseName = groups.findByName("search_courseName");

        if (courseName != null) {
            searchVo.setCourseName(courseName.getPropertyValue1().toString());
        }

        Group menuId = groups.findByName("search_menu");
        if (menuId != null) {
            searchVo.setMenuId(Integer.valueOf(menuId.getPropertyValue1().toString()));
        }

        Group scoreTypeId = groups.findByName("search_scoreType");
        if (scoreTypeId != null) {
            searchVo.setCourseTypeId(scoreTypeId.getPropertyValue1().toString());
        }

        Group courseType = groups.findByName("search_courseType");

        if (courseType != null) {
            searchVo.setCourseType(courseType.getPropertyValue1().toString());
        }

        Group serviceType = groups.findByName("search_service_type");
        if (serviceType != null) {
            searchVo.setServiceType(Integer.parseInt(serviceType.getPropertyValue1().toString()));
        }

        Group isRecommend = groups.findByName("search_isRecommend");

        if (isRecommend != null) {
            searchVo.setIsRecommend(Integer.parseInt(isRecommend.getPropertyValue1().toString()));
        }

        Group status = groups.findByName("search_status");

        if (status != null) {
            searchVo.setStatus(status.getPropertyValue1().toString());
        }

        Group courseId = groups.findByName("search_courseId");
        if (courseId != null) {
            searchVo.setId(Integer.valueOf(courseId.getPropertyValue1().toString()));
        }

        Group type = groups.findByName("type");
        if (type != null) {
            searchVo.setType(1);
        }

        Group multimediaType = groups.findByName("search_multimediaType");
        if (multimediaType != null) {
            searchVo.setMultimediaType(Integer.valueOf(multimediaType.getPropertyValue1().toString()));
        }

        Page<CourseVo> page = courseService.findCoursePage(searchVo, currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;

    }

    @RequestMapping(value = "getSecoundMenu", method = RequestMethod.POST)
    @ResponseBody
    public Object getSecoundMenu(String firstMenuNumber) {
        //System.out.println("firstMenuNumber:"+firstMenuNumber);
        List<MenuVo> menuVo = courseService.getsecoundMenus(firstMenuNumber);
        return menuVo;
    }



    /**
     * 查看
     *
     * @param id
     * @return
     */
    //@RequiresPermissions("course:menu:course")
    @RequestMapping(value = "findCourseById", method = RequestMethod.GET)
    @ResponseBody
    public List<CourseVo> findCourseById(Integer id) {
        return courseService.findCourseById(id);
    }

    /**
     * 修改状态(禁用or启用)
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(Integer id) {
        courseService.updateStatus(id);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    /**
     * 修改图片
     *
     * @param courseVo
     * @return
     */
    @RequestMapping(value = "updateRecImgPath", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateRecImgPath(CourseVo courseVo) {
        ResponseObject responseObj = new ResponseObject();
        courseService.updateRecImgPath(courseVo);
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("设置成功！");
        return responseObj;
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteCourseById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteCourseById(Integer id) {
        courseService.deleteCourseById(id);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }



    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException, IllegalAccessException {
        ResponseObject responseObject = new ResponseObject();
        if (ids != null) {
            String[] _ids = ids.split(",");
            courseService.deletes(_ids);
        }
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("删除成功!");
        return responseObject;
    }


    /**
     * 城市推荐
     *
     * @param ids
     * @param isRec
     * @return
     */
    @RequestMapping(value = "updateCityRec", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateCityRec(String ids, int isRec) {
        ResponseObject responseObject = new ResponseObject();
        if (ids != null) {
            String[] _ids = ids.split(",");
            if (courseService.updateCityRec(_ids, isRec)) {
                responseObject.setSuccess(true);
                responseObject.setErrorMessage("操作成功!");
            } else {
                responseObject.setSuccess(false);
                responseObject.setErrorMessage("推荐失败!");
            }
        }
        return responseObject;
    }

    @RequestMapping(value = "getCourseDetail", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject getCourseDetail(String courseId) {
        return ResponseObject.newSuccessResponseObject(courseService.getCourseDetail(courseId));
    }

    /**
     * 添加课程详情
     *
     * @param courseId
     * @param smallImgPath
     * @param courseDetail
     * @param courseOutline
     * @param commonProblem
     * @return
     */
    @RequestMapping(value = "updateCourseDetail", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateCourseDetail(String courseId, String smallImgPath, String smallImgPath1, String smallImgPath2, String courseDetail,
                                             String courseOutline, String commonProblem, String lecturerDescription) {
        if (smallImgPath1 != null) {
            smallImgPath += "dxg" + smallImgPath1;
        }
        if (smallImgPath2 != null) {
            smallImgPath += "dxg" + smallImgPath2;
        }
        courseService.updateCourseDetail(courseId, smallImgPath, null, courseDetail, courseOutline, commonProblem, lecturerDescription);
        return ResponseObject.newSuccessResponseObject("修改成功！");
    }

    @RequestMapping(value = "addPreview", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addPreview(String courseId, String smallImgPath, String smallImgPath1, String smallImgPath2, String courseDetail,
                                     String courseOutline, String commonProblem) {
        if (smallImgPath1 != null) {
            smallImgPath += "dxg" + smallImgPath1;
        }
        if (smallImgPath2 != null) {
            smallImgPath += "dxg" + smallImgPath2;
        }
        courseService.addPreview(courseId, smallImgPath, null, courseDetail, courseOutline, commonProblem);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    @RequestMapping(value = "uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject uploadImg(String content) {
        String str = content.split("base64,")[1];
        byte[] b = org.apache.commons.codec.binary.Base64.decodeBase64(str);
        Attachment a = att.addAttachment(ManagerUserUtil.getId(), AttachmentType.ONLINE, "1.png", b, "image/png");
        if (a.getError() != 0) {
            return ResponseObject.newErrorResponseObject("上传失败！");
        }
        return ResponseObject.newSuccessResponseObject(a);
    }

    /**
     * 获得模板内容
     *
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getTemplate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getTemplate(String type, HttpSession session) throws Exception {
        String path = session.getServletContext().getRealPath("/template");
        File f = null;
        if ("detail".equals(type)) {
            f = new File(path + File.separator + "course_detail.html");
        } else if ("outline".equals(type)) {
            f = new File(path + File.separator + "/course_outline.html");
        } else if ("problem".equals(type)) {
            f = new File(path + File.separator + "/course_common_problem.html");
        }
        return ResponseObject.newSuccessResponseObject(FileUtil.readAsString(f));
    }

    /**
     * 设置是否展示课程介绍
     *
     * @param courseVo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "descriptionShow", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject descriptionShow(CourseVo courseVo) throws Exception {
        ResponseObject responseObj = new ResponseObject();
        courseService.updateDescriptionShow(courseVo);
        try {
            responseObj.setSuccess(true);
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("保存失败请重试！");
        }

        return responseObj;
    }

    @RequestMapping(value = "getCourseVoList", method = RequestMethod.POST)
    @ResponseBody
    public List<CourseVo> getKsystem(String search) {
        return courseService.getCourselist(search);
    }

    /**
     * 同步CC视频分类
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "updateCategoryInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateCategoryInfo(String courseId) throws Exception {
        courseService.updateCategoryInfo(courseId);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    /**
     * 同步课程视频信息（无章节知识点版）
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "updateCourseVideo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateCourseVideo(String id) {


        return ResponseObject.newSuccessResponseObject(courseService.updateCourseVideo(id));
    }

    @RequestMapping(value = "courseInfoDetail")
    public String courseInfoDetail(HttpServletRequest request, Integer id) {
        Course course = courseService.findCourseInfoById(id);
        request.setAttribute("course", course);
        request.setAttribute("courseForm", course.getType());
        return CLOUD_CLASS_PATH_PREFIX + "/courseInfoDetail";
    }

    /**
     * Description：设置推荐值
     * creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/3/9 14:13
     **/
    @RequestMapping(value = "updateRecommendSort", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateRecommendSort(Integer id, Integer recommendSort, String recommendTime) {
        ResponseObject responseObject = new ResponseObject();

        courseService.updateRecommendSort(id, recommendSort, recommendTime);
        responseObject.setSuccess(true);
        responseObject.setResultObject("修改成功!");
        return responseObject;
    }

    /** 更改默认人数
     * Description：
     * creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/3/9 14:13
     **/
    @RequestMapping(value = "updatedefaultStudent", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updatedefaultStudent(Integer id, Integer recommendSort) {
        ResponseObject responseObject = new ResponseObject();
        courseService.updatedefaultStudent(id, recommendSort);
        responseObject.setSuccess(true);
        responseObject.setResultObject("修改成功!");
        return responseObject;
    }
    

    

}
