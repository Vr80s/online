package com.xczhihui.bxg.online.manager.cloudClass.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.common.domain.TeachMethod;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.cloudClass.service.GradeService;
import com.xczhihui.bxg.online.manager.cloudClass.service.NotesService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.LecturerVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.NotesVo;

@Controller
@RequestMapping(value = "/cloudClass/notes")
public class NotesController{
	
	@Autowired
	private NotesService notesService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private GradeService gradeService;
	
	/**
     * 
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request){
    	
    	List<Menu> menuVos= courseService.getfirstMenus(null);
		request.setAttribute("menuVo", menuVos);
		
		//在列表初始化时查找出课程类别
		List<ScoreType> scoreTypeVos = courseService.getScoreType();
		request.setAttribute("scoreTypeVo", scoreTypeVos);
		
		//在列表初始化时查找出授课方式
		List<TeachMethod> teachMethodVos= courseService.getTeachMethod();
		request.setAttribute("teachMethodVo", teachMethodVos);
		
		List<LecturerVo> Lecturers = courseService.getLecturers();
		request.setAttribute("lecturerVo", Lecturers);
    	
    	
         ModelAndView mav=new ModelAndView("/cloudClass/notes");
         return mav;
    }

	@RequiresPermissions("cloudClass:menu:notes")
    @RequestMapping(value = "/findNotesList", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findNotesList(TableVo tableVo) {
         int pageSize = tableVo.getiDisplayLength();
         int index = tableVo.getiDisplayStart();
         int currentPage = index / pageSize + 1;
         String params = tableVo.getsSearch();

         Groups groups = Tools.filterGroup(params);
         Group contentGroup = groups.findByName("search_content");
         Group courseIdGroup = groups.findByName("search_courseId");
         Group createPersonNameGroup = groups.findByName("search_createPersonName");
         Group gradeIdGroup = groups.findByName("search_gradeId");
         Group chapterIdGroup = groups.findByName("search_chapterId");
         Group chapterLevelGroup = groups.findByName("search_chapterLevel");
         Group startTimeGroup = groups.findByName("startTime");
         Group stopTimeGroup = groups.findByName("stopTime");
         Group sortType = groups.findByName("sortType");

         NotesVo searchVo=new NotesVo();

         if(contentGroup!=null){
              searchVo.setContent(contentGroup.getPropertyValue1().toString());
         }
         
         if(createPersonNameGroup!=null){
        	 searchVo.setCreatePersonName(createPersonNameGroup.getPropertyValue1().toString());
         }
         
         if(gradeIdGroup!=null){
              searchVo.setGradeId(Integer.parseInt(gradeIdGroup.getPropertyValue1().toString()));
         }
         
         if(courseIdGroup!=null){
        	 searchVo.setCourseId(Integer.parseInt(courseIdGroup.getPropertyValue1().toString()));
         }
         
         if(chapterLevelGroup!=null){
        	 if(!"4".equals(chapterLevelGroup.getPropertyValue1().toString())){
        		 searchVo.setChapterId(chapterIdGroup.getPropertyValue1().toString());
        	 }else{
        		 searchVo.setVideoId(chapterIdGroup.getPropertyValue1().toString());
        	 }
         }
         
         if(startTimeGroup!=null){
        	 searchVo.setStartTime(DateUtil.parseDate(startTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }
         
         if(stopTimeGroup!=null){
        	 searchVo.setStopTime(DateUtil.parseDate(stopTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }
         
         if (sortType != null) {
       	  	 searchVo.setSortType(Integer.valueOf(sortType.getPropertyValue1().toString()));
         }
         
         Page<NotesVo> page = notesService.findNotesPage(searchVo, currentPage, pageSize);

         int total = page.getTotalCount();
         tableVo.setAaData(page.getItems());
         tableVo.setiTotalDisplayRecords(total);
         tableVo.setiTotalRecords(total);
         return tableVo;
    }
	
	@RequiresPermissions("cloudClass:menu:notes")
	@RequestMapping(value = "findCourseList")
	@ResponseBody
	public TableVo findCourseList(TableVo tableVo) {
	      int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          String params = tableVo.getsSearch();
          Groups groups = Tools.filterGroup(params);
          
          CourseVo searchVo=new CourseVo();
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
          
          Page<CourseVo> page = notesService.findCoursePage(searchVo, currentPage, pageSize);
          int total = page.getTotalCount();
          tableVo.setAaData(page.getItems());
          tableVo.setiTotalDisplayRecords(total);
          tableVo.setiTotalRecords(total);
          return tableVo;
	}
	
	/**
     * 
     * @return
     */
    @RequestMapping(value = "/notesDetail")
    public ModelAndView notesDetail(NotesVo notesVo,HttpServletRequest request){
         ModelAndView mav=new ModelAndView("/cloudClass/notesDetail");
         request.setAttribute("gradeList", gradeService.getGradeByCourseId(notesVo.getCourseId().toString()));
         return mav;
    }

	/**
	 * 批量逻辑删除
	 * @param Integer id
	 * @return
	 */
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) {
         ResponseObject responseObject=new ResponseObject();
         if(ids!=null) {
              String[] _ids = ids.split(",");
              notesService.deletes(_ids);
         }
         responseObject.setSuccess(true);
         responseObject.setErrorMessage("删除完成!");
         return responseObject;
    }
}
