package com.xczhihui.bxg.online.manager.cloudClass.web;

import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.Grade;
import com.xczhihui.bxg.online.manager.cloudClass.service.*;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.GradeDetailVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.GradeVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.MenuVo;
import com.xczhihui.bxg.online.manager.utils.*;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 班级控制层实现类
 * @author Rongcai Kang
 */
@RestController
@RequestMapping(value = "/cloudClass/grade")
public class GradeController {

    @Autowired
    private GradeService service;

    @Autowired
    private CloudClassMenuService menuService;

    @Autowired
    private ScoreTypeService scoreTypeService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeachMethodService teachMethodService;

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request){

        List<CourseVo> course = service.findCourseList(null, null);// 课程信息
        request.setAttribute("course", course);
        List<MenuVo> menuVos=menuService.list();
        request.setAttribute("menus",menuVos);
        request.setAttribute("scoreTypes",scoreTypeService.list());
        request.setAttribute("courses", courseService.list("0"));//职业课
        request.setAttribute("coursesMicro", courseService.list("1"));//微课
        request.setAttribute("teachMethods", teachMethodService.list());
        ModelAndView mav=new ModelAndView("cloudClass/grade");
        return mav;
    }

    //@RequiresPermissions("cloudClass:menu:grade:teachmethod:list")
    @RequestMapping(value = "/teachMethod",method= RequestMethod.POST)
    public ResponseObject teachMethod(HttpServletRequest request,String courseId){
        return ResponseObject.newSuccessResponseObject(teachMethodService.findByCourseId(courseId));
    }

    //@RequiresPermissions("cloudClass:menu:grade:course:list")
    @RequestMapping(value = "/courseList",method= RequestMethod.POST)
    public ResponseObject courseList(HttpServletRequest request,String menuId,String courseTypeId ){
        return ResponseObject.newSuccessResponseObject(courseService.findByMenuId(menuId, courseTypeId));
    }
    /**
     * 获取班级列表信息，根据课程ID号查找
     * @return
     */
    //@RequiresPermissions("cloudClass:menu:grade")
    @RequestMapping(value = "/findGradeList")
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
        if(page.getItems()!=null&&page.getItems().size()>0)
        {
            for(GradeVo gradeVo:page.getItems())
            {
            	if(gradeVo.getName().split(gradeVo.getClassTemplate()).length>1&&gradeVo.getName().split(gradeVo.getClassTemplate())[1].split("期").length>0){
            		 String nameNumber =gradeVo.getName().split(gradeVo.getClassTemplate())[1].split("期")[0];//此处数据固定模版xxx(数字)期
            		 gradeVo.setNameNumber(nameNumber);
            	}
               
                
                /* String nameNumber="";
               for(int i=0;i<name.length();i++)
                {
                    switch (name.charAt(i))
                    {
                        case '0':nameNumber+=name.charAt(i);break;
                        case '1':nameNumber+=name.charAt(i);break;
                        case '2':nameNumber+=name.charAt(i);break;
                        case '3':nameNumber+=name.charAt(i);break;
                        case '4':nameNumber+=name.charAt(i);break;
                        case '5':nameNumber+=name.charAt(i);break;
                        case '6':nameNumber+=name.charAt(i);break;
                        case '7':nameNumber+=name.charAt(i);break;
                        case '8':nameNumber+=name.charAt(i);break;
                        case '9':nameNumber+=name.charAt(i);break;
                    }
                }*/
                
            }
        }
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
    //@RequiresPermissions("cloudClass:menu:grade")
    @RequestMapping(value = "/findMicroGradeList")
    public TableVo findMicroGradeList(TableVo tableVo){
    	int pageSize = tableVo.getiDisplayLength();
    	int index = tableVo.getiDisplayStart();
    	int currentPage = index / pageSize + 1;
    	String params = tableVo.getsSearch();
    	Groups groups = Tools.filterGroup(params);
    	Group sname = groups.findByName("snameMicro");
    	Group menuId = groups.findByName("menuIdMicro");
    	Group scoreTypeId = groups.findByName("scoreTypeIdMicro");
//    	Group teachMethodId = groups.findByName("teachMethodIdMicro");
    	Group courseId = groups.findByName("courseIdMicro");
//    	Group gradeStatus = groups.findByName("gradeStatusMicro");
    	
    	
//    	Group time_startGroup = groups.findByName("curriculumTimeMicro");
//    	Group time_endGroup = groups.findByName("stopTimeMicro");
    	
    	GradeDetailVo searchVo=new GradeDetailVo();
    	
//    	if(time_startGroup!=null){
//    		searchVo.setCurriculumTime(DateUtil.parseDate(time_startGroup.getPropertyValue1().toString(), "yyyy-MM-dd"));
//    	}
//    	if(time_startGroup!=null){
//    		searchVo.setStopTime(DateUtil.parseDate(time_endGroup.getPropertyValue1().toString(), "yyyy-MM-dd"));
//    	}
    	if(sname!=null){
    		searchVo.setName(sname.getPropertyValue1().toString());
    	}
    	if(menuId!=null){
    		searchVo.setMenuId(menuId.getPropertyValue1().toString());
    	}
    	if(scoreTypeId!=null){
    		searchVo.setScoreTypeId(scoreTypeId.getPropertyValue1().toString());
    	}
//    	if(teachMethodId!=null){
//    		searchVo.setTeachMethodId(teachMethodId.getPropertyValue1().toString());
//    	}
    	if(courseId!=null){
    		searchVo.setCourse_id(Integer.parseInt(courseId.getPropertyValue1().toString()));
    	}
//    	if(gradeStatus!=null){
//    		searchVo.setGradeStatus(Integer.parseInt(gradeStatus.getPropertyValue1().toString()));
//    	}
    	
    	Page<GradeVo> page= service.findMicroGradeList(searchVo, currentPage, pageSize);
    	if(page.getItems()!=null&&page.getItems().size()>0)
    	{
    		for(GradeVo gradeVo:page.getItems())
    		{
    			if(gradeVo.getName().split(gradeVo.getClassTemplate()).length>1&&gradeVo.getName().split(gradeVo.getClassTemplate())[1].split("期").length>0){
    				String nameNumber =gradeVo.getName().split(gradeVo.getClassTemplate())[1].split("期")[0];//此处数据固定模版xxx(数字)期
    				gradeVo.setNameNumber(nameNumber);
    			}
    			
    			
    			/* String nameNumber="";
               for(int i=0;i<name.length();i++)
                {
                    switch (name.charAt(i))
                    {
                        case '0':nameNumber+=name.charAt(i);break;
                        case '1':nameNumber+=name.charAt(i);break;
                        case '2':nameNumber+=name.charAt(i);break;
                        case '3':nameNumber+=name.charAt(i);break;
                        case '4':nameNumber+=name.charAt(i);break;
                        case '5':nameNumber+=name.charAt(i);break;
                        case '6':nameNumber+=name.charAt(i);break;
                        case '7':nameNumber+=name.charAt(i);break;
                        case '8':nameNumber+=name.charAt(i);break;
                        case '9':nameNumber+=name.charAt(i);break;
                    }
                }*/
    			
    		}
    	}
    	int total = page.getTotalCount();
    	tableVo.setAaData(page.getItems());
    	tableVo.setiTotalDisplayRecords(total);
    	tableVo.setiTotalRecords(total);
    	return tableVo;
    }


    /**
     * 增加班级信息
     * @param grade 班级对象
     * @return
     */
    //@RequiresPermissions("cloudClass:menu:grade")
    @RequestMapping(value = "/addGrade",method= RequestMethod.POST)
    public ResponseObject add(Grade grade){
        service.addGrade(grade);
        return ResponseObject.newSuccessResponseObject(grade);
    }
    /**
     * 老师列表
     * @return
     */
    //@RequiresPermissions("cloudClass:menu:grade:teachers")
    @RequestMapping(value = "/teachers",method= RequestMethod.GET)
    public ResponseObject teachers(String gradeId,String courseId){
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("teachers_courseName",service.findMenuByGradeId(gradeId).get("name"));
        result.put("roleType1",service.lectereListByGradeIdAndRoleType(gradeId,1,courseId));
        result.put("roleType2",service.lectereListByGradeIdAndRoleType(gradeId,2,courseId));
        result.put("roleType3", service.lectereListByGradeIdAndRoleType(gradeId,3,courseId));
        return ResponseObject.newSuccessResponseObject(result);
    }

    /**
     * 增加班级信息
     * @return
     */
    //@RequiresPermissions("cloudClass:menu:grade:teachers:save")
    @RequestMapping(value = "/teachers/save",method= RequestMethod.POST)
    public ResponseObject saveTeachers(HttpServletRequest request,String gradeId,String courseId,String[] roleType1,String[] roleType2,String[] roleType3){
        List<String> roleTypes=new ArrayList<String>();
        if(roleType1!=null)
        {
            for(String _roleType1:roleType1)
            {
                roleTypes.add(_roleType1);
            }
        }
        if(roleType2!=null)
        {
            for(String _roleType2:roleType2)
            {
                roleTypes.add(_roleType2);
            }
        }
        if(roleType3!=null)
        {
            for(String _roleType3:roleType3)
            {
                roleTypes.add(_roleType3);
            }
        }
        service.saveTeachers(gradeId, courseId, UserLoginUtil.getLoginUser(request).getName(), roleTypes);
        return ResponseObject.newSuccessResponseObject(null);
    }


    //@RequiresPermissions("cloudClass:menu:grade")
    @RequestMapping(value = "/deleteGrades", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(HttpServletRequest request) throws ServletRequestBindingException {
        String id = ServletRequestUtils.getRequiredStringParameter(request, "ids");
        if(id != null){
            String[] strids = id.split(",");
            this.service.deleteGrades(strids);
        }
        ResponseObject responseObject=new ResponseObject();
        responseObject.setErrorMessage("删除成功!");
        responseObject.setSuccess(true);
        return responseObject;
    }


    /**
     * 根据班级ID号  查找班级信息
     * @param gradeId 班级id号
     * @return
     */
    //@RequiresPermissions("cloudClass:menu:grade")
    @RequestMapping(value = "/findById",method= RequestMethod.GET)
    public List<GradeDetailVo> getGradeById(Integer gradeId) {
        return service.getGradeById(gradeId);
    }

    /**
     * 根据班级ID号，将对应的班级禁用
     * @param gradeId 班级id
     * @return
     */
    /**
     * 根据班级ID号  禁用班级信息
     * @param gradeId 班级id号
     * @return
     */
    //@RequiresPermissions("cloudClass:menu:grade")
    @RequestMapping(value = "/updateGradeStatus",method= RequestMethod.POST)
    public int updateGradeStatus( Integer gradeId,Integer isGradeStatus){
       return service.updateGradeStatus(gradeId, isGradeStatus);
    }


    /**
     * 根据班级ID号，将对应的班级禁用
     * @param gradeId 班级id
     * @return
     */
    /**
     * 根据班级ID号  禁用班级信息
     * @return
     */
    //@RequiresPermissions("cloudClass:menu:grade:new:save")
    @RequestMapping(value = "/saveGrade",method= RequestMethod.POST)
    public ResponseObject saveGrade(HttpServletRequest request){
    	ResponseObject responseObj = new ResponseObject();
        Grade grade=new Grade();
        String classTemplate=request.getParameter("classTemplate");
        String menuId=request.getParameter("menuId");
        String scoreTypeId=request.getParameter("scoreTypeId");
        String courseId=request.getParameter("courseId");
        String nameNumber=request.getParameter("nameNumber");
        String studentAmount=request.getParameter("studentAmount");
        String curriculumTime=request.getParameter("curriculumTime");
        String stopTime=request.getParameter("stopTime");
        String workDaySum=request.getParameter("workDaySum");//上课天数
        String restDaySum=request.getParameter("restDaySum");//休息天数
        String qqno=request.getParameter("qqno");//QQ群
//        String defaultStudentCount=request.getParameter("defaultStudentCount");
        
        if(classTemplate==null) {
            classTemplate = "";
        }
        if(nameNumber==null) {
            nameNumber = "";
        }
        grade.setName(classTemplate + nameNumber + "期");
        grade.setStudentAmount(Integer.valueOf(studentAmount));
        grade.setCourseId(courseId);
        grade.setCurriculumTime(TimeUtil.parseDate(curriculumTime));
        grade.setStopTime(TimeUtil.parseDate(stopTime));
        grade.setStatus(1);
        grade.setGradeStatus(1);
        if(workDaySum != null && workDaySum != ""){
	        grade.setWorkDaySum(Integer.parseInt(workDaySum));
	        
        }else{
        	grade.setWorkDaySum(null);
        }
        if(restDaySum != null && restDaySum != ""){
        	grade.setRestDaySum(Integer.parseInt(restDaySum));
        	
        }else{
	        grade.setRestDaySum(null);
        }
        grade.setQqno(qqno);
//        grade.setDefaultStudentCount(Integer.valueOf(defaultStudentCount));
        List<Grade> tempList =service.getByName(grade.getName());
        for(Grade temp :tempList){
        	if(temp!=null&&!temp.isDelete()){
        		responseObj.setSuccess(false);
        		responseObj.setErrorMessage("班级名称重复！");
        		return responseObj;
        	}
        }
        try{
        	 service.addGrade(grade);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("新增成功");
       }catch(Exception e){
    	   	e.printStackTrace();
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("新增失败");
       }
        return responseObj;
    }

    /**
     * 根据班级ID号  禁用班级信息
     * @return
     */
    //@RequiresPermissions("cloudClass:menu:grade:new:update")
    @RequestMapping(value = "/updateGrade",method= RequestMethod.POST)
    public ResponseObject updateGrade(HttpServletRequest request){
    	ResponseObject responseObj = new ResponseObject();
        String id=request.getParameter("id");
        String classTemplate=request.getParameter("classTemplate");
        String menuId=request.getParameter("menuId");
        String scoreTypeId=request.getParameter("scoreTypeId");
        String courseId=request.getParameter("courseId");
        String nameNumber=request.getParameter("nameNumber");
        String studentAmount=request.getParameter("studentAmount");
        String curriculumTime=request.getParameter("curriculumTime");
        String stopTime=request.getParameter("stopTime");
        String workDaySum=request.getParameter("workDaySum");//上课天数
        String restDaySum=request.getParameter("restDaySum");//休息天数
        String qqno=request.getParameter("qqno");//QQ群
        String defaultStudentCount=request.getParameter("defaultStudentCount");
        
        if(classTemplate==null) {
            classTemplate = "";
        }
        if(nameNumber==null) {
            nameNumber = "";
        }
        Grade grade=service.findById(Integer.parseInt(id));
        grade.setName(classTemplate + nameNumber + "期");
        List<Grade> gradeByNameList=service.getByName(grade.getName());
        grade.setStudentAmount(Integer.valueOf(studentAmount));
        grade.setCourseId(courseId);
        grade.setCurriculumTime(TimeUtil.parseDate(curriculumTime));
        grade.setStopTime(TimeUtil.parseDate(stopTime));
        grade.setStatus(1);
        grade.setGradeStatus(1);

        if(workDaySum != null && workDaySum != ""){
	        grade.setWorkDaySum(Integer.parseInt(workDaySum));
	        
        }else{
        	grade.setWorkDaySum(null);
        }
        if(restDaySum != null && restDaySum != ""){
        	grade.setRestDaySum(Integer.parseInt(restDaySum));
        	
        }else{
	        grade.setRestDaySum(null);
        }

        grade.setQqno(qqno);
        grade.setDefaultStudentCount(Integer.valueOf(defaultStudentCount));
        
        for(Grade gradeByName:gradeByNameList){
        	if(gradeByName!=null&&!gradeByName.getId().equals(grade.getId())&&!gradeByName.isDelete()){
        		responseObj.setSuccess(false);
        		responseObj.setErrorMessage("班级名称重复！");
        		return responseObj;
        	}
        }
        
        try{
           service.updateGrade(grade);
           responseObj.setSuccess(true);
           responseObj.setErrorMessage("修改成功！");
	      }catch(Exception e){
	           responseObj.setSuccess(false);
	           responseObj.setErrorMessage("修改失败！");
	      }
        return responseObj;
    }

    /**
     * 班级顺序上移
     * @param
     * @return
     */
    //@RequiresPermissions("cloudClass:menu:grade")
    @RequestMapping(value = "/moveUp",method= RequestMethod.POST)
    public ResponseObject moveUp(Integer id){
        ResponseObject responseObject= new ResponseObject();
        int result= service.updatePreSortEntity(id);
        if(result==1){
            responseObject.setSuccess(true);
            responseObject.setErrorMessage("上移成功");
        }else{
            responseObject.setSuccess(false);
            responseObject.setErrorMessage("上移失败");
        }
        return responseObject;
    }


    /**
     * 班级顺序下移
     * @param
     * @return
     */
    //@RequiresPermissions("cloudClass:menu:grade")
    @RequestMapping(value = "/moveDown",method= RequestMethod.POST)
    public ResponseObject moveDown(Integer id){
        ResponseObject responseObject= new ResponseObject();
        int result= service.updateNextSortEntity(id);
        if(result==1){
            responseObject.setSuccess(true);
            responseObject.setErrorMessage("下移成功");
        }else{
            responseObject.setSuccess(false);
            responseObject.setErrorMessage("下移失败");
        }
        return responseObject;
    }
    
    /**
     * 检查是否有模板
     * @param
     * @return
     */
    //@RequiresPermissions("cloudClass:menu:grade")
    @RequestMapping(value = "/checkBuildPlan",method= RequestMethod.POST)
    public ResponseObject checkBuildPlan(String courseId){
    	ResponseObject responseObject = new ResponseObject();
    	responseObject.setSuccess(service.checkBuildPlan(courseId));
    	return responseObject;
    }
}
