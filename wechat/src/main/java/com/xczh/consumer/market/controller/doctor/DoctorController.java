package com.xczh.consumer.market.controller.doctor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.interceptor.HeaderInterceptor;
import com.xczh.consumer.market.utils.APPUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.DoctorSortOrderType;
import com.xczhihui.common.util.enums.DoctorType;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;
import com.xczhihui.medical.banner.model.OeBanner;
import com.xczhihui.medical.banner.service.PcBannerService;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.department.service.IMedicalDepartmentService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorSolrService;
import com.xczhihui.medical.doctor.vo.DoctorQueryVo;
import com.xczhihui.medical.doctor.vo.MedicalDoctorSolrVO;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;

/**
 * Description：医师页面
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/3/26 0026 上午 11:59
 **/
@RestController
@RequestMapping(value = "/xczh/doctors")
public class DoctorController {

    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;

    @Autowired
    private IMedicalDepartmentService medicalDepartmentService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IAnchorInfoService anchorInfoService;

    @Autowired
    private IMedicalDoctorSolrService medicalDoctorSolrService;

    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    
    @Autowired
    private PcBannerService bannerService;
    
    
    /**
     * banner图
     *
     * @return
     */
    @RequestMapping("banner")
    public ResponseObject banner(HttpServletRequest request) {
        Page<OeBanner> page =  bannerService.page(new Page<>(1, 3),6);
        
        if(HeaderInterceptor.ONLY_THREAD.get()) {
            return ResponseObject.newSuccessResponseObject(null);
        }
        
        page.getRecords().forEach(bannerVo -> {
            String routeType = bannerVo.getRouteType();
            if (StringUtils.isNotBlank(routeType)) {
                String url = MultiUrlHelper.getUrl(routeType,  APPUtil.getMobileSource(request), MultiUrlHelper.handleParam(returnOpenidUri, bannerVo.getLinkParam(), routeType));
                bannerVo.setTarget(url);
            } else {
                bannerVo.setTarget("");
            }
        });
        
        return ResponseObject.newSuccessResponseObject(page.getRecords());
    }
    
    
    /**
     * 热门搜索换一批
     * @return
     */
    @RequestMapping("hotInBatch")
    public ResponseObject hotInBatch() {
        
        return ResponseObject.newSuccessResponseObject(medicalDoctorBusinessService.selectHotInBatch(new Page<MedicalDoctorVO>(1,3)));
    }
    
    /**
     * 医师分类页面
     *
     * @return
     */
    @RequestMapping("category")
    public ResponseObject category() {
        
        return ResponseObject.newSuccessResponseObject(medicalDoctorBusinessService.doctorCategoryList());
    }
    /**
     * 搜索列表接口
     *
     * @param pageNumber
     * @param pageSize
     * @param dqv
     * @return
     */
    @RequestMapping(value = "list")
    public ResponseObject list(@RequestParam(value = "pageNumber", required = false)Integer pageNumber,
                Integer pageSize,DoctorQueryVo dqv) throws IOException, SolrServerException {

        pageNumber = (pageNumber == null ? 1 : pageNumber);
        pageSize = (pageSize == null ? 10 : pageSize);
        /*
         * 构造下查询bean
		 */
        dqv.bulid();

        com.baomidou.mybatisplus.plugins.Page page = new Page<>();
        Page<MedicalDoctorSolrVO> doctors = medicalDoctorSolrService.selectDoctorListBySolr(new Page<>(pageNumber, pageSize), dqv);

        return ResponseObject.newSuccessResponseObject(doctors.getRecords());
    }

    /**
     * 分类
     */
    @RequestMapping("screen")
    public ResponseObject schoolClass()
            throws Exception {

        Map<String, Object> mapAll = new HashMap<String, Object>();
        //名医类型
        mapAll.put("doctorTypes", DoctorType.getDoctorTypeList());
        //科室
        Page page = new Page(0, Integer.MAX_VALUE);
        Page<MedicalDepartment> departments = medicalDepartmentService.page(page);
        mapAll.put("departments", departments.getRecords());
        //智能排序
        mapAll.put("sortTypes", DoctorSortOrderType.getDoctorSortOrderTypeList());
        return ResponseObject.newSuccessResponseObject(mapAll);
    }


    /**
     * Description：医师主页    -- 课程列表
     *
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("doctorCourse")
    public ResponseObject doctorCourseList(@RequestParam("userId") String userId) {

        List<Map<String, Object>> alllist =  courseService.doctorCourseList(userId,HeaderInterceptor.ONLY_THREAD.get());
        
        return ResponseObject.newSuccessResponseObject(alllist);
    }
    
    /**
     * Description：医师 最近的一次直播
     *
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("recentlyLive")
    public ResponseObject recentlyLive(@RequestParam("userId") String userId)
            throws Exception {

        CourseLecturVo cv = courseService.selectDoctorLiveRoomRecentCourse(userId,
                HeaderInterceptor.ONLY_THREAD.get());
        return ResponseObject.newSuccessResponseObject(cv);
    }


    /**
     * Description：医师 主播状态
     *
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("doctorStatus")
    public ResponseObject doctorStatus(@RequestParam("doctorId") String doctorId)
            throws Exception {

        return ResponseObject.newSuccessResponseObject(anchorInfoService.anchorPermissionStatusByDoctorId(doctorId));
    }


    /**
     * Description：医师 介绍
     *
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("introduction")
    public ResponseObject introduction(@RequestParam("doctorId") String doctorId)
            throws Exception {
    	/*
    	 * 医师详情
    	 */
        Map<String, Object> map = medicalDoctorBusinessService.selectDoctorWorkTimeAndDetailsById(doctorId);
        if (map != null) {
            map.put("doctorDetailsUrl", returnOpenidUri + "/xcview/html/person_fragment.html?type=5&typeId=" + doctorId);
        }
        return ResponseObject.newSuccessResponseObject(map);
    }

    
    /**
     * Description：医师主页    -- 课程列表
     *
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("doctorCourseType")
    public ResponseObject doctorCourseType(@RequestParam("userId") String userId, Integer type,
           @RequestParam(value = "pageNumber", required = false)Integer pageNumber,
            Integer pageSize) throws Exception {

        pageNumber = (pageNumber == null ? 1 : pageNumber);
        pageSize = (pageSize == null ? 10 : pageSize);
        
        Page<CourseLecturVo>  page =  courseService.selectLecturerAllCourseByType(new Page<CourseLecturVo>(pageNumber, pageSize),
                userId,type,HeaderInterceptor.ONLY_THREAD.get());
        
        return ResponseObject.newSuccessResponseObject(page.getRecords());
    }
}
