package com.xczhihui.barrier.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.barrier.service.BarrierService;
import com.xczhihui.barrier.vo.BarrierVo;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.course.vo.CourseVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.common.domain.TeachMethod;
import com.xczhihui.course.vo.LecturerVo;

@Controller
@RequestMapping(value = "/barrier/barrier")
public class BarrierController {

    @Autowired
    private BarrierService barrierService;

    @Autowired
    private CourseService courseService;

    /**
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request) {

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

        ModelAndView mav = new ModelAndView("/barrier/barrier");
        return mav;
    }

    /**
     * @return
     */
    @RequestMapping(value = "/barrierDetail")
    public ModelAndView barrierDetail(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/barrier/barrierDetail");
        return mav;
    }

    /**
     * @return
     */
    @RequestMapping(value = "/barrierDetailAdd")
    public ModelAndView barrierDetailAdd(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/barrier/barrierDetailAdd");
        return mav;
    }

    /**
     * @return
     */
    @RequestMapping(value = "/barrierDetailEdit")
    public ModelAndView barrierDetailEdit(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/barrier/barrierDetailEdit");
        return mav;
    }

    //@RequiresPermissions("menu:barrier:barrier")
    @RequestMapping(value = "/findBarrierList", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findBarrierList(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group courseIdGroup = groups.findByName("search_courseId");
        Group chapterIdGroup = groups.findByName("search_chapterId");
//         Group chapterLevelGroup = groups.findByName("search_chapterLevel");

        BarrierVo searchVo = new BarrierVo();
        if (courseIdGroup != null) {
            searchVo.setCourseId(courseIdGroup.getPropertyValue1().toString());
        }

        if (chapterIdGroup != null) {
            searchVo.setChapterId(chapterIdGroup.getPropertyValue1().toString());
        }

        Page<BarrierVo> page = barrierService.findBarrierPage(searchVo, currentPage, pageSize);

        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    //@RequiresPermissions("menu:barrier:barrier")
    @RequestMapping(value = "findCourseList")
    @ResponseBody
    public TableVo findCourseList(TableVo tableVo) {
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

        Group sortType = groups.findByName("sortType");
        if (sortType != null) {
            searchVo.setSortType(Integer.valueOf(sortType.getPropertyValue1().toString()));
        }

        Page<CourseVo> page = barrierService.findCoursePage(searchVo, currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    /**
     * 添加
     *
     * @param vo
     * @return
     */
    //@RequiresPermissions("menu:barrier:barrier")
    @RequestMapping(value = "/addBarrier", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addBarrier(BarrierVo barrierVo, HttpServletRequest request) {
        barrierVo.setCreatePerson(ManagerUserUtil.getUsername());
        //获取到题的数据
        String[] strategyCnt = request.getParameterValues("strategyCnt");//每个题型多少道
        String[] strategyTotalScore = request.getParameterValues("strategyTotalScore");//每个题型多少分
        String[] strategyScore = request.getParameterValues("strategyScore");//每道题度搜阿红分

        barrierVo.setStrategyCnt(strategyCnt);
        barrierVo.setStrategyTotalScore(strategyTotalScore);
        barrierVo.setStrategyScore(strategyScore);

        barrierVo.setCreatePerson(ManagerUserUtil.getUsername());
        barrierService.addBarrier(barrierVo);

        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    /**
     * 编辑
     *
     * @param vo
     * @return
     */
    //@RequiresPermissions("menu:barrier:barrier")
    @RequestMapping(value = "getBarrierById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject getBarrierById(BarrierVo barrierVo) {
        ResponseObject responseObj = new ResponseObject();
        responseObj.setResultObject(barrierService.getBarrierDetail(barrierVo));
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("修改成功");
        return responseObj;
    }

    /**
     * 编辑
     *
     * @param vo
     * @return
     */
    //@RequiresPermissions("menu:barrier:barrier")
    @RequestMapping(value = "updateBarrierById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateBarrierById(BarrierVo barrierVo) {
        barrierService.updateBarrier(barrierVo);
        return ResponseObject.newSuccessResponseObject("修改成功！");
    }

    /**
     * 批量逻辑删除
     *
     * @param Integer id
     * @return
     */
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) {
        if (ids != null) {
            String[] _ids = ids.split(",");
            barrierService.deletes(_ids);
        }
        return ResponseObject.newSuccessResponseObject("删除完成!");
    }


    /**
     * 根据知识点获取可以分配到的题
     *
     * @param Integer id
     * @return
     */
    @RequestMapping(value = "getQuestions", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject getQuestions(String kpointIds) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setResultObject(barrierService.getQuestions(kpointIds));
        responseObject.setSuccess(true);
        return responseObject;
    }

    /**
     * 获取到上级关卡
     *
     * @param Integer id
     * @return
     */
    @RequestMapping(value = "getBarriersSelect", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject getBarriersSelect(String courseId, String id) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setResultObject(barrierService.getBarriersSelect(courseId, id));
        responseObject.setSuccess(true);
        return responseObject;
    }

    /**
     * 启用关卡
     *
     * @param String id
     * @return
     */
    @RequestMapping(value = "useBarrier", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject useBarrier(String courseId) {
        barrierService.update_UseBarrier(courseId);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }
}
