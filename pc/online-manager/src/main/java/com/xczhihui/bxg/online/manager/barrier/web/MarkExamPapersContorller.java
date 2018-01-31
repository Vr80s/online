package com.xczhihui.bxg.online.manager.barrier.web;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.barrier.service.MarkExamPapersService;
import com.xczhihui.bxg.online.manager.barrier.vo.MarkBarrierVo;
import com.xczhihui.bxg.online.manager.barrier.vo.MarkGradeVo;
import com.xczhihui.bxg.online.manager.barrier.vo.MarkRecordVo;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * 批阅试卷模块控制层代码
 * @Author Fudong.Sun【】
 * @Date 2016/12/22 10:33
 */
@RestController
@RequestMapping(value = "/markexampapers")
public class MarkExamPapersContorller {
    @Autowired
    MarkExamPapersService service;
    @Autowired
    private CourseService courseService;

    /**
     * 班级列表
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView mav=new ModelAndView("markexampapers/classes");
        mav.addObject("courses",service.findCourses());
        return mav;
    }
    /**
     * 获取班级列表信息，根据课程ID，班级名查找
     * @return
     */
    //@RequiresPermissions("markexam:grade")
    @RequestMapping(value = "/findGradeList")
    public TableVo findGradeList(TableVo tableVo){
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group sname = groups.findByName("grade_name");
        Group courseId = groups.findByName("course_id");

        MarkGradeVo searchVo=new MarkGradeVo();
        if(sname!=null){
            searchVo.setGrade_name(sname.getPropertyValue1().toString());
        }
        if(courseId!=null){
            searchVo.setCourse_id(Integer.parseInt(courseId.getPropertyValue1().toString()));
        }

        Page<MarkGradeVo> page= service.findGradeList(searchVo, currentPage, pageSize);
        for (MarkGradeVo vo : page.getItems()) {
            float pass_rate = 0f;
            if(vo.getJoin_num()!=0) {
                pass_rate = (float) vo.getPass_num() / vo.getJoin_num() * 100;
            }
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
            vo.setPass_rate(df.format(pass_rate));
        }
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }
    /**
     * 班级下关卡列表页面
     * @return
     */
    @RequestMapping(value = "/barrier/index")
    public ModelAndView barriers(HttpServletRequest request,Integer grade_id,String grade_name){
        ModelAndView mav=new ModelAndView("markexampapers/barriers");
        mav.addObject("grade_id",grade_id);
        mav.addObject("grade_name",grade_name);
        return mav;
    }
    /**
     * 获取班级下关卡列表
     * @return
     */
    @RequestMapping(value = "/findbarriers")
    public TableVo findbarriers(TableVo tableVo){
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Integer grade_id = Integer.parseInt(groups.findByName("grade_id").getPropertyValue1().toString());

        Page<MarkBarrierVo> page= service.findbarriers(grade_id,currentPage, pageSize);
        for (MarkBarrierVo vo : page.getItems()) {
            float pass_rate = 0f;
            if(vo.getJoin_num()!=0) {
                pass_rate = (float) vo.getPass_num() / vo.getJoin_num() * 100;
            }
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
            vo.setPass_rate(df.format(pass_rate));
        }
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }
    /**
     * 学员闯关记录列表页面
     * @return
     */
    @RequestMapping(value = "/record/index")
    public ModelAndView barrierRecord(HttpServletRequest request,Integer grade_id,String barrier_id,String grade_name,String barrier_name){
        ModelAndView mav=new ModelAndView("markexampapers/records");
        mav.addObject("grade_id",grade_id);
        mav.addObject("grade_name",grade_name);
        mav.addObject("barrier_id",barrier_id);
        mav.addObject("barrier_name",barrier_name);
        return mav;
    }
    /**
     * 获取班级下关卡列表
     * @return
     */
    @RequestMapping(value = "/barrier/record")
    public TableVo findBarrierRecord(TableVo tableVo){
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Integer grade_id = Integer.parseInt(groups.findByName("grade_id").getPropertyValue1().toString());
        String barrier_id = groups.findByName("barrier_id").getPropertyValue1().toString();
        Integer barrier_status = Integer.parseInt(groups.findByName("barrier_status").getPropertyValue1().toString());
        String user_name = groups.findByName("user_name").getPropertyValue1().toString();

        Page<MarkRecordVo> page= service.findBarrierRecord(barrier_status,user_name,grade_id,barrier_id,currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }
    /**
     * 学员闯关记录列表页面
     * @return
     */
    @RequestMapping(value = "/examPaper/index")
    public ModelAndView examPaper(HttpServletRequest request,Integer grade_id,String barrier_id,
                                  String grade_name,String barrier_name,String user_id,String user_name){
        ModelAndView mav=new ModelAndView("markexampapers/exampaper");
        mav.addObject("grade_id",grade_id);
        mav.addObject("grade_name",grade_name);
        mav.addObject("barrier_id",barrier_id);
        mav.addObject("barrier_name",barrier_name);
        mav.addObject("user_id",user_id);
        mav.addObject("user_name",user_name);
        mav.addObject("examNum",service.getExamNum(user_id,barrier_id));
        return mav;
    }
    /**
     * 根据闯关记录查询闯关试卷
     * @param record_id
     * @return
     */
    @RequestMapping(value = "/getExamPaper")
    public Map<String,Object> getExamPaper(String record_id){
        return service.getExamPaper(record_id);
    }
}
