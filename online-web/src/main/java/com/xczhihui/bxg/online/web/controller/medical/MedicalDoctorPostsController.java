package com.xczhihui.bxg.online.web.controller.medical;

import static com.xczhihui.bxg.online.web.controller.AbstractController.getCurrentUser;

import com.xczhihui.course.model.Course;
import com.xczhihui.course.service.ICourseService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.anchor.model.CourseApplyResource;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;
import com.xczhihui.medical.doctor.service.IMedicalDoctorAccountService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;

import java.util.List;

/**
 * Description：医师控制器
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/25 11:32
 **/
@RestController
@RequestMapping("/doctor/posts")
public class MedicalDoctorPostsController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(MedicalDoctorPostsController.class);
    @Autowired
    private IMedicalDoctorPostsService medicalDoctorPostsService;
    @Autowired
    private AttachmentCenterService service;
    @Autowired
    private IMedicalDoctorAccountService medicalDoctorAccountService;
    @Autowired
    private ICourseApplyService courseApplyService;
    @Autowired
    private ICourseService courseService;

    /**
     * 医师动态列表
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseObject doctorPostsList(@RequestParam("pageNumber") Integer pageNumber,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam(required = false) Integer type, @RequestParam("doctorId") String doctorId) {
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        Page<MedicalDoctorPosts> page = new Page<>();
        page.setCurrent(pageNumber);
        page.setSize(pageSize);
        Page<MedicalDoctorPosts> list = medicalDoctorPostsService.selectMedicalDoctorPostsPage(page, type, doctorId, userId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 添加医师动态
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseObject addDoctorPosts(MedicalDoctorPosts medicalDoctorPosts) {
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        MedicalDoctorAccount mha = medicalDoctorAccountService.getByUserId(userId);
        medicalDoctorPosts.setDoctorId(mha.getDoctorId());
        medicalDoctorPostsService.addMedicalDoctorPosts(medicalDoctorPosts);
        if (medicalDoctorPosts.getVideo() != null && !medicalDoctorPosts.getVideo().equals("")) {
            CourseApplyResource car = new CourseApplyResource();
            car.setTitle(medicalDoctorPosts.getTitle());
            car.setResource(medicalDoctorPosts.getVideo());
            car.setMultimediaType(1);
            car.setSourceType(2);
            car.setUserId(userId);
            courseApplyService.saveCourseApplyResource(car);
        }
        return ResponseObject.newSuccessResponseObject("添加成功");
    }

    /**
     * 编辑医师动态
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseObject updateDoctorPosts(@RequestBody MedicalDoctorPosts medicalDoctorPosts) {
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        medicalDoctorPostsService.updateMedicalDoctorPosts(medicalDoctorPosts);
        return ResponseObject.newSuccessResponseObject("编辑成功");
    }

    /**
     * 删除医师动态
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseObject deleteDoctorPosts(@PathVariable("id") Integer id) {
        medicalDoctorPostsService.deleteMedicalDoctorPosts(id);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    /**
     * 医师动态置顶/取消置顶
     */
    @RequestMapping(value = "{id}/{stick}", method = RequestMethod.POST)
    public ResponseObject updateStickDoctorPosts(@PathVariable("id") Integer id, @PathVariable("stick") Boolean stick) {
        medicalDoctorPostsService.updateStickMedicalDoctorPosts(id, stick);
        if (stick) {
            return ResponseObject.newSuccessResponseObject("置顶成功");
        } else {
            return ResponseObject.newSuccessResponseObject("取消置顶成功");
        }
    }

    /**
     * 获取医师动态
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseObject getDoctorPostsById(@PathVariable("id") Integer id) {
        MedicalDoctorPosts mdp = medicalDoctorPostsService.getMedicalDoctorPostsById(id);
        return ResponseObject.newSuccessResponseObject(mdp);
    }


    /**
     * Description：初始化课程动态
     * creed: Talk is cheap,show me the code
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/7/3 10:38
     **/
    @RequestMapping( value = "/initialization",method = RequestMethod.POST)
    public ResponseObject initializationData() {
        List<Course> list = courseService.getAllCourseByStatus();
        list.forEach(course ->{
            String userId = course.getUserLecturerId();
            MedicalDoctorAccount mha = medicalDoctorAccountService.getByUserId(userId);
            List<MedicalDoctorPosts> doctorPostsList = medicalDoctorPostsService.getMedicalDoctorPostsByCourseId(course.getId());
            if(mha != null && doctorPostsList.size() <= 0){
                MedicalDoctorPosts mdp = new MedicalDoctorPosts();
                mdp.setContent(course.getGradeName()+","+course.getSubtitle());
                mdp.setType(5);
                mdp.setTitle(course.getGradeName());
                mdp.setDoctorId(mha.getDoctorId());
                mdp.setCourseId(course.getId());
                mdp.setCreateTime(course.getReleaseTime());
                mdp.setUpdateTime(course.getReleaseTime());
                medicalDoctorPostsService.addMedicalDoctorPosts(mdp);
            }
        });

        return ResponseObject.newSuccessResponseObject("初始化成功");
    }
}