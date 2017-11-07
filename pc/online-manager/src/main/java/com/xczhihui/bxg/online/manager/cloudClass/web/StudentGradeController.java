package com.xczhihui.bxg.online.manager.cloudClass.web;

import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.Apply;
import com.xczhihui.bxg.online.common.domain.Grade;
import com.xczhihui.bxg.online.common.domain.TrackRecord;
import com.xczhihui.bxg.online.manager.cloudClass.service.*;
import com.xczhihui.bxg.online.manager.cloudClass.vo.*;
import com.xczhihui.bxg.online.manager.utils.*;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 班级控制层实现类
 * @author Rongcai Kang
 */
@RestController
@RequestMapping(value = "/cloudClass/studentGrade")
public class StudentGradeController {


    @Autowired
    private ApplyService applyService;


    @Autowired
    private GradeService service;

    @Autowired
    private CloudClassMenuService menuService;

    @Autowired
    private TeachMethodService teachMethodService;;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ScoreTypeService scoreTypeService;

    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private TrackRecordService trackRecordService;

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request){
        request.setAttribute("menus", menuService.list());
        request.setAttribute("scoreTypes", scoreTypeService.list());
        request.setAttribute("teachMethods", teachMethodService.list());
        request.setAttribute("courses", courseService.list(""));
        ModelAndView mav=new ModelAndView("cloudClass/student_grade");
        return mav;
    }

    @RequestMapping(value = "/student/deletes")
    public ResponseObject studentDeletes(HttpServletRequest request,String ids){
        if(ids!=null)
        {
            String idArr[]=ids.split(",");
            applyService.deleteStudents(idArr);
        }
       return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping(value = "/student/changePayment")
    public ResponseObject changePayment(HttpServletRequest request,ApplyVo vo){
        applyService.updateChangePayment(vo);
        return ResponseObject.newSuccessResponseObject(null);
    }


    @RequestMapping(value = "/student/record/save")
    public ResponseObject saveStudentRecord(HttpServletRequest request,TrackRecordVo vo) throws InvocationTargetException, IllegalAccessException {
        vo.setCreatePerson(UserHolder.getCurrentUser().getId());
        vo.setCreateTime(new Date());
        vo.setIsDelete(false);
        vo.setStatus(true);
        trackRecordService.save(vo);
        return ResponseObject.newSuccessResponseObject(null);
    }



    @RequestMapping(value = "/student/record/update")
    public ResponseObject updateStudentRecord(HttpServletRequest request,TrackRecordVo vo) throws InvocationTargetException, IllegalAccessException {
        TrackRecord entity=trackRecordService.findById(vo.getId());
        entity.setCreatePerson(UserHolder.getCurrentUser().getId());
        entity.setCreateTime(new Date());
        entity.setRecordTime(vo.getRecordTime());
        entity.setRecordContent(vo.getRecordContent());
        entity.setLecturerId(vo.getLecturerId());
        trackRecordService.update(entity);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping(value = "/student/record/delete/{id}")
    public ResponseObject deleteStudentRecord(HttpServletRequest request,@PathVariable String id) throws InvocationTargetException, IllegalAccessException {
        trackRecordService.deleteById(id);
        return ResponseObject.newSuccessResponseObject(null);
    }
    @RequestMapping(value = "/student")
    public ModelAndView student(HttpServletRequest request,String page,String gradeId){
        ModelAndView mav=new ModelAndView("cloudClass/student");
        mav.addObject("gradeId", gradeId);
        if(StringUtils.isNotEmpty(gradeId)) {
            Grade grade = service.findById(Integer.parseInt(gradeId));
            mav.addObject("gradeName", grade.getName());
        }
        mav.addObject("page", page);
        mav.addObject("lecturer",lecturerService.listByGradeId(gradeId));
        return mav;
    }

    /**
     * 跟踪记录
     * @return
     */
    @RequestMapping(value = "/findTrackRecordList")
    @ResponseBody
    public TableVo findTrackRecordList(TableVo tableVo){
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group applyId = groups.findByName("applyId");
        Group gradeId = groups.findByName("gradeId");

        TrackRecordVo trackRecordVo=new TrackRecordVo();

        if(applyId!=null){
            trackRecordVo.setApplyId(applyId.getPropertyValue1().toString());
        }
        if(gradeId!=null)
        {
            trackRecordVo.setGradeId(Integer.parseInt(gradeId.getPropertyValue1().toString()));
        }
        Page<TrackRecordVo> page= trackRecordService.findPage(trackRecordVo,currentPage,pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    /**
     * 获取班级列表信息，根据课程ID号查找
     * @return
     */
    @RequestMapping(value = "/findStudentList")
    @ResponseBody
    public TableVo findStudentList(TableVo tableVo){
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group gradeId = groups.findByName("gradeId");
        Group NameSearch = groups.findByName("NameSearch");
//        Group isPayment = groups.findByName("isPayment");

        ApplyVo searchVo=new ApplyVo();

        if(gradeId!=null){
            searchVo.setGradeId(gradeId.getPropertyValue1().toString());
        }
        if(NameSearch!=null){
            searchVo.setRealName(NameSearch.getPropertyValue1().toString());
        }
//        if(isPayment!=null){
//            searchVo.setIsPayment(isPayment.getPropertyValue1().toString());
//        }
        Page<ApplyVo> page= applyService.findPage(searchVo, currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    /**
     * 获取班级列表信息，根据课程ID号查找
     * @return
     */
    @RequestMapping(value = "/findGradeList")
    @ResponseBody
    public TableVo findGradeList(TableVo tableVo){
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group sname = groups.findByName("sname");
        Group menuId = groups.findByName("menuId");
        Group scoreTypeId = groups.findByName("scoreTypeId");
        Group teachMethodId = groups.findByName("teachMethodId");
        Group courseId = groups.findByName("courseId");
        Group gradeStatus = groups.findByName("gradeStatus");
        Group time_startGroup = groups.findByName("curriculumTime");
        Group time_endGroup = groups.findByName("stopTime");

        GradeDetailVo searchVo=new GradeDetailVo();

        if(time_startGroup!=null){
            searchVo.setCurriculumTime(DateUtil.parseDate(time_startGroup.getPropertyValue1().toString(), "yyyy-MM-dd"));
        }
        if(time_startGroup!=null){
            searchVo.setStopTime(DateUtil.parseDate(time_endGroup.getPropertyValue1().toString(), "yyyy-MM-dd"));
        }
        if(sname!=null){
            searchVo.setName(sname.getPropertyValue1().toString());
        }
        if(menuId!=null){
            searchVo.setMenuId(menuId.getPropertyValue1().toString());
        }
        if(scoreTypeId!=null){
            searchVo.setScoreTypeId(scoreTypeId.getPropertyValue1().toString());
        }
        if(teachMethodId!=null){
            searchVo.setTeachMethodId(teachMethodId.getPropertyValue1().toString());
        }
        if(courseId!=null){
            searchVo.setCourse_id(Integer.parseInt(courseId.getPropertyValue1().toString()));
        }
        if(gradeStatus!=null){
            searchVo.setGradeStatus(Integer.parseInt(gradeStatus.getPropertyValue1().toString()));
        }
        Page<GradeVo> page= service.findGradeList(searchVo, currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }



}
