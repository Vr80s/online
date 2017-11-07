package com.xczhihui.bxg.online.manager.exam.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.QuestionVo;
import com.xczhihui.bxg.online.manager.exam.service.ExamPaperService;
import com.xczhihui.bxg.online.manager.exam.vo.ExamPaperVo;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

@Controller
@RequestMapping(value = "/exam/examPaper")
public class ExamPaperController{
	
	@Autowired
	private ExamPaperService examPaperService;
	
	@Autowired
	private CourseService courseService;

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request){
    	List<Menu> menuVos= courseService.getfirstMenus(null);
		request.setAttribute("menuVo", menuVos);
		
        ModelAndView mav=new ModelAndView("/exam/exam");
        return mav;
    }

    @RequiresPermissions("exam:menu:paper")
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
          
          Group status = groups.findByName("search_status");
          
          if (status != null) {
        	  searchVo.setStatus(status.getPropertyValue1().toString());
          }
          
          Group courseId = groups.findByName("search_courseId");
          if (courseId != null) {
        	  searchVo.setId(Integer.valueOf(courseId.getPropertyValue1().toString()));
          }
          
          Page<CourseVo> page = examPaperService.findCoursePage(searchVo, currentPage, pageSize);
          int total = page.getTotalCount();
          tableVo.setAaData(page.getItems());
          tableVo.setiTotalDisplayRecords(total);
          tableVo.setiTotalRecords(total);
          return tableVo;
	}

    @RequiresPermissions("exam:menu:paper")
    @RequestMapping(value = "/examPaper")
    public ModelAndView examPaper(HttpServletRequest request){
        ModelAndView mav=new ModelAndView("/exam/examPaper");
        return mav;
    }
    
	@RequiresPermissions("exam:menu:paper")
    @RequestMapping(value = "/findExamPaperList", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findExamPaperList(TableVo tableVo) {
         int pageSize = tableVo.getiDisplayLength();
         int index = tableVo.getiDisplayStart();
         int currentPage = index / pageSize + 1;
         String params = tableVo.getsSearch();
         Groups groups = Tools.filterGroup(params);
         Group paperNameGroup = groups.findByName("search_paperName");
         Group difficultyGroup = groups.findByName("search_difficulty");
         Group courseIdGroup = groups.findByName("search_courseId");

         ExamPaperVo searchVo=new ExamPaperVo();
         if(paperNameGroup!=null){
              searchVo.setPaperName(paperNameGroup.getPropertyValue1().toString());
         }
         
         if(difficultyGroup!=null){
        	 searchVo.setDifficulty(difficultyGroup.getPropertyValue1().toString());
         }
         
         if(courseIdGroup!=null){
        	 searchVo.setCourseId(courseIdGroup.getPropertyValue1().toString());
         }

         Page<ExamPaperVo> page = examPaperService.findExamPaperPage(searchVo, currentPage, pageSize);

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
    @RequestMapping(value = "/examPaperAdd")
    public ModelAndView examPaperAdd(HttpServletRequest request){
         ModelAndView mav=new ModelAndView("/exam/examPaperAdd");
         return mav;
    }
    
    /**
     * 
     * @return
     */
    @RequestMapping(value = "/examPaperShow")
    public ModelAndView examPaperShow(HttpServletRequest request){
    	ModelAndView mav=new ModelAndView("/exam/examPaperShow");
    	return mav;
    }

    /**
	 * build 生成试卷
	 * @param vo
	 * @return
     * @throws Exception 
	 */
	@RequiresPermissions("exam:menu:paper")
	@RequestMapping(value = "/bulidExamPaper", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject bulidExamPaper(ExamPaperVo examPaperVo,HttpServletRequest request) throws Exception{
		examPaperVo.setCreatePerson(UserLoginUtil.getLoginUser(request).getLoginName());
		return ResponseObject.newSuccessResponseObject(examPaperService.bulidExamPaper(examPaperVo));
	}

	/**
	 * 添加
	 * @param vo
	 * @return
	 */
	@RequiresPermissions("exam:menu:paper")
	@RequestMapping(value = "/saveExamPaper", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject saveExamPaper(ExamPaperVo examPaperVo,HttpServletRequest request){
		examPaperVo.setCreatePerson(UserLoginUtil.getLoginUser(request).getLoginName());
		//获取到题的数据
		examPaperService.addExamPaper(examPaperVo);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}
	
	/**
	 * 根据知识点获取可以分配到的题
	 * @param Integer id 
	 * @return
	 */
    @RequestMapping(value = "getQuestions", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject getQuestions(String kpointIds) {
         return ResponseObject.newSuccessResponseObject(examPaperService.getQuestions(kpointIds)) ;
    }
    
    /**
     * 根据知识点、题型获取到不同的难度的题目的数量
     * 题型，0单选、1多选、2判断、3填空、4简答、5代码、6应用
     * @param Integer id
     * @return
     */
    @RequestMapping(value = "getQuestionsCnt", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject getQuestionsCnt(String kpointIds,String questionType) {
    	return ResponseObject.newSuccessResponseObject(examPaperService.getQuestionsCnt(kpointIds,questionType)) ;
    }

    @RequiresPermissions("exam:menu:paper")
	@RequestMapping(value = "findQuestionList")
	@ResponseBody
	public TableVo findQuestionList(TableVo tableVo) {
	      int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          String params = tableVo.getsSearch();
          Groups groups = Tools.filterGroup(params);
          
          QuestionVo searchVo=new QuestionVo();
          Group questionHead = groups.findByName("search_questionHead");
          
          if (questionHead != null) {
        	  searchVo.setQuestionHead(questionHead.getPropertyValue1().toString());
          }
          
          Group difficulty = groups.findByName("search_difficulty");
          if (difficulty != null) {
        	  searchVo.setDifficulty(difficulty.getPropertyValue1().toString());
          }

          Group questionType = groups.findByName("search_questionType");
          if (questionType != null) {
        	  searchVo.setQuestionType(Integer.valueOf(questionType.getPropertyValue1().toString()));
          }

          Group kpointIds = groups.findByName("search_kpointIds");
          if (kpointIds != null) {
        	  searchVo.setKpointIds(kpointIds.getPropertyValue1().toString());
          }
          
          Group allQuestionIds = groups.findByName("search_allQuestionIds");
          if (allQuestionIds != null) {
        	  searchVo.setId(allQuestionIds.getPropertyValue1().toString());//临时用id保存 页面已经存在的所有ID
          }

          Group courseId = groups.findByName("search_courseId");
          if (courseId != null) {
        	  searchVo.setCourseId(courseId.getPropertyValue1().toString());
          }

          Page<QuestionVo> page = examPaperService.findQuestionPage(searchVo, currentPage, pageSize);
          int total = page.getTotalCount();
          tableVo.setAaData(page.getItems());
          tableVo.setiTotalDisplayRecords(total);
          tableVo.setiTotalRecords(total);
          return tableVo;
	}
    
	/**
	 * 刷新试卷的难度
	 * @param vo
	 * @return
	 */
	@RequiresPermissions("exam:menu:paper")
	@RequestMapping(value = "refreshDifficulty", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject refreshDifficulty (ExamPaperVo examPaperVo){
		return ResponseObject.newSuccessResponseObject(examPaperService.refreshDifficulty(examPaperVo));
	}

	/**
     * 
     * @return
     */
    @RequestMapping(value = "/examPaperUpdate")
    public ModelAndView examPaperUpdate(HttpServletRequest request){
         ModelAndView mav=new ModelAndView("/exam/examPaperUpdate");
         return mav;
    }
    
    /**
     * 获取到考试的信息
     * @return
     * @throws Exception 
     */
    @RequiresPermissions("exam:menu:paper")
    @RequestMapping(value = "/getExamPaper")
    @ResponseBody
    public ResponseObject getExamPaper(ExamPaperVo examPaperVo,HttpServletRequest request) throws Exception{
    	return ResponseObject.newSuccessResponseObject(examPaperService.getExamPaperQuestion(examPaperVo));
    }

	/**
	 * 编辑
	 * @param vo
	 * @return
	 */
	@RequiresPermissions("exam:menu:paper")
	@RequestMapping(value = "updateExamPaperById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateExamPaperById (ExamPaperVo examPaperVo,HttpServletRequest request){
		examPaperVo.setCreatePerson(UserLoginUtil.getLoginUser(request).getLoginName());
		//获取到题的数据
		examPaperService.updateExamPaper(examPaperVo);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}

	/**
	 * 批量逻辑删除
	 * @param Integer id
	 * @return
	 */
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids){
         if(ids!=null) {
              String[] _ids = ids.split(",");
              examPaperService.deletes(_ids);
         }
         return ResponseObject.newSuccessResponseObject("删除成功！");
    }
}
