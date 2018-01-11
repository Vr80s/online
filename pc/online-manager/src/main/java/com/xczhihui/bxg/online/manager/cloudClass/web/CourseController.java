package com.xczhihui.bxg.online.manager.cloudClass.web;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.common.domain.TeachMethod;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.LecturerVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.MenuVo;
import com.xczhihui.bxg.online.manager.user.service.OnlineUserService;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

/**
 * 课程管理控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("cloudclass/course")
public class CourseController extends AbstractController{
	protected final static String CLOUD_CLASS_PATH_PREFIX = "/cloudClass/";
	@Autowired
	private CourseService courseService;
	@Autowired
	private AttachmentCenterService att;
	@Value("${online.web.url:http://www.ixincheng.com}")
	private String weburl;
	
	@Autowired
	private OnlineUserService  onlineUserService;
	
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		
		List<Menu> menuVos= courseService.getfirstMenus(null);
		request.setAttribute("menuVo", menuVos);
		
		//在列表初始化时查找出课程类别
		List<ScoreType> scoreTypeVos = courseService.getScoreType();
		request.setAttribute("scoreTypeVo", scoreTypeVos);
		
		//在列表初始化时查找出授课方式
		List<TeachMethod> teachMethodVos= courseService.getTeachMethod();
		request.setAttribute("teachMethodVo", teachMethodVos);
		
		List<LecturerVo> lecturers = courseService.getLecturers();
		request.setAttribute("lecturerVo", lecturers);
		
		List<Map<String, Object>> mapList = onlineUserService.getAllUserLecturer();
		for (Map<String, Object> map : mapList) {
			String str = "昵称:"+map.get("name").toString() + ",账号:"+map.get("logo").toString();
			map.put("name", str);
		}
		request.setAttribute("mapList", mapList);
		
		return CLOUD_CLASS_PATH_PREFIX + "/course";
	}
	
	@RequestMapping(value = "courseDetail")
	public String courseDetail(HttpServletRequest request) {
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
		
		request.setAttribute("weburl", weburl);
		return CLOUD_CLASS_PATH_PREFIX + "/courseDetail";
	}
	
	@RequestMapping(value = "videoRes")
	public ModelAndView videoRes(HttpServletRequest request,String page,String courseId,String courseName) {
		 ModelAndView mav=new ModelAndView("cloudClass/videoRes");
		 mav.addObject("weburl", weburl);
		 mav.addObject("page", page);
	     mav.addObject("courseId", courseId);
	     mav.addObject("courseName", courseName);
	     return mav;
	}

	@RequiresPermissions("cloudClass:menu:course")
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo courses(TableVo tableVo) {
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
        	  searchVo.setType(Integer.valueOf(type.getPropertyValue1().toString()));
          }
          
          Page<CourseVo> page = courseService.findCoursePage(searchVo, currentPage, pageSize);
          int total = page.getTotalCount();
          tableVo.setAaData(page.getItems());
          tableVo.setiTotalDisplayRecords(total);
          tableVo.setiTotalRecords(total);
          return tableVo;
		
	}
	
	@RequiresPermissions("cloudClass:menu:course")
	@RequestMapping(value = "recList")
	@ResponseBody
	public TableVo recList(TableVo tableVo) {
		
		
		int pageSize = 10;
		int index = 1;
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);
		
//		 Integer offLine = courseVo.getOnlineCourse();
//		 Integer courseType = courseVo.getOnlineCourse();
//		 Integer multimediaType = courseVo.getMultimediaType();
//		 Integer liveStatus = courseVo.getLiveStatus();//直播状态1.直播中，2预告，3直播结束
		
		CourseVo searchVo=new CourseVo();
		Group onlineCourse = groups.findByName("search_onlineCourse");
		
		if (onlineCourse != null) {
			searchVo.setOnlineCourse(Integer.valueOf(onlineCourse.getPropertyValue1().toString()));
		}
		
		Group city = groups.findByName("search_city");
		
		if (city != null) {
			searchVo.setRealCitys(city.getPropertyValue1().toString());
		}
		
		Group type = groups.findByName("search_type");
		if (type != null) {
			searchVo.setType(Integer.valueOf(type.getPropertyValue1().toString()));
		}
		
		Group multimediaType = groups.findByName("search_multimediaType");
		if (multimediaType != null) {
			searchVo.setMultimediaType(Integer.valueOf(multimediaType.getPropertyValue1().toString()));
		}
		
		Group liveStatus = groups.findByName("search_liveStatus");
		
		if (liveStatus != null) {
			searchVo.setLiveStatus(Integer.valueOf(liveStatus.getPropertyValue1().toString()));
		}
		Page<CourseVo> page = courseService.findCourseRecPage(searchVo, currentPage, pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;
		
	}
	@RequestMapping(value = "getSecoundMenu",method=RequestMethod.POST)
	@ResponseBody
	public Object getSecoundMenu(String firstMenuNumber){
		//System.out.println("firstMenuNumber:"+firstMenuNumber);
		List<MenuVo> menuVo=courseService.getsecoundMenus(firstMenuNumber);
		return menuVo;
	}
	
	/**
	 * 添加
	 * @param courseVo
	 * @return
	 */
	@RequiresPermissions("cloudClass:menu:course")
	@RequestMapping(value = "addCourse", method = RequestMethod.POST)
	@ResponseBody
	 public ResponseObject add(CourseVo courseVo){
		ResponseObject responseObj = new ResponseObject();
		List<Course> entitys= courseService.findByName(courseVo.getCourseName());
		for(Course entity:entitys){
			if(entity!=null&&!entity.isDelete()&&null==entity.getType()){
				 responseObj.setSuccess(false);
		         responseObj.setErrorMessage("课程名称已存在！");
		         return responseObj;
			}
		}
	
		
		if(courseVo.getOriginalCost()!=null&&courseVo.getCurrentPrice()!=null&&courseVo.getOriginalCost()<courseVo.getCurrentPrice()){
			 responseObj.setSuccess(false);
	         responseObj.setErrorMessage("现价必须小于等于原价!");
	         return responseObj;
		}
		
		if(courseVo.getOriginalCost() == null){
			courseVo.setOriginalCost(0.0);
		}
		if(courseVo.getCurrentPrice() == null ){
			courseVo.setCurrentPrice(0.0);
		}
		courseVo.setIsRecommend(0);
		courseVo.setRecommendSort(0);

	/*	if(courseVo.getSmallimgPath() == null || "".equals(courseVo.getSmallimgPath().trim())){
			responseObj.setErrorMessage("课程展示图不能为空");
			return responseObj;
		}*/

		try{
			courseService.addCourse(courseVo);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("新增成功");
            
//        	try {
//        		//删除CC视频的分类
//        		List<CategoryBean> allCategories = CCUtils.getAllCategories();
//        		boolean b = false;
//        		for (CategoryBean categoryBean : allCategories) {
//        			if (categoryBean.getName().equals(courseVo.getCourseName())) {
//        				b = true;
//        				break;
//        			}
//        		}
//        		if (!b) {
//        			CCUtils.createCategory(courseVo.getCourseName(), null);
//        		}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
       }catch(Exception e){
    	   	e.printStackTrace();
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("新增失败");
       }
        return responseObj;
    }
	
	/**
	 * 查看
	 * @param id
	 * @return
	 */
	@RequiresPermissions("cloudClass:menu:course")
	@RequestMapping(value = "findCourseById", method = RequestMethod.GET)
	@ResponseBody
	  public List<CourseVo> findCourseById(Integer id) {
		return 	courseService.findCourseById(id);
	}
	
	/**
	 * 编辑
	 * @param courseVo
	 * @return
	 */
	@RequiresPermissions("cloudClass:menu:course")
	@RequestMapping(value = "updateCourseById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateCourseById (CourseVo courseVo){
		ResponseObject responseObj = new ResponseObject();
		List<Course> entitys= courseService.findByName(courseVo.getCourseName());
		for(Course entity: entitys){
			if(entity!=null&&!entity.isDelete()&&entity.getId()!=courseVo.getId()&&null==entity.getType()){
				 responseObj.setSuccess(false);
		         responseObj.setErrorMessage("课程名称已存在！");
		         return responseObj;
			}
		}
		
		
		if(courseVo.getOriginalCost()<courseVo.getCurrentPrice()){
			 responseObj.setSuccess(false);
	         responseObj.setErrorMessage("现价必须小于等于原价");
	         return responseObj;
		}
		if(courseVo.getOriginalCost() == null){
			courseVo.setOriginalCost(0.0);
		}
		if(courseVo.getCurrentPrice() == null){
			courseVo.setCurrentPrice(0.0);
		}

		if(courseVo.getSmallimgPath() == null || "".equals(courseVo.getSmallimgPath().trim())){
			responseObj.setErrorMessage("课程展示图不能为空");
			return responseObj;
		}

		 try{
//			 	CourseVo old = courseService.getCourseById(courseVo.getId());
//			 	String oldName = old.getCourseName();
			 	courseService.updateCourse(courseVo);
	            responseObj.setSuccess(true);
	            responseObj.setErrorMessage("修改成功");
//	            try {
//	            	if (!oldName.equals(courseVo.getCourseName())) {
//	            		this.updateCCCategory(oldName,courseVo.getCourseName());
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
	       }catch(Exception e){
	            responseObj.setSuccess(false);
	            responseObj.setErrorMessage("修改失败");
	            e.printStackTrace();
	       }
	        return responseObj;
	}
	
//	private void updateCCCategory(String oldName,String newName){
//		List<CategoryBean> allCategories = CCUtils.getAllCategories();
//		for (CategoryBean first : allCategories) {
//			if (first.getName().equals(oldName)) {
//				CCUtils.updateCategory(first.getId(), newName);
//				break;
//			}
//		}
//	}
	
	/**
	 * 修改状态(禁用or启用)
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(Integer id){
		courseService.updateStatus(id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}
	
	/**
	 * 修改图片
	 * @param courseVo
	 * @return
	 */
	@RequestMapping(value = "updateRecImgPath", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateRecImgPath(CourseVo courseVo){
		ResponseObject responseObj = new ResponseObject();
		courseService.updateRecImgPath(courseVo);
		responseObj.setSuccess(true);
		responseObj.setErrorMessage("设置成功！");
		return responseObj;
	}

	/**
	 * 逻辑删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteCourseById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteCourseById(Integer id){
		courseService.deleteCourseById(id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}
	
	/**
     * 上移
     * @param id
     * @return
     */
    @RequestMapping(value = "upMove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject upMove(Integer id) {
         ResponseObject responseObj = new ResponseObject();
         courseService.updateSortUp(id);
         responseObj.setSuccess(true);
         return responseObj;
    }
    
	/**
     * 上移
     * @param id
     * @return
     */
    @RequestMapping(value = "downMove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject downMove(Integer id) {
         ResponseObject responseObj = new ResponseObject();
         courseService.updateSortDown(id);
         responseObj.setSuccess(true);
         return responseObj;
    }
    
    /**
     * 上移
     * @param id
     * @return
     */
    @RequestMapping(value = "upMoveRec", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject upMoveRec(Integer id) {
    	ResponseObject responseObj = new ResponseObject();
    	courseService.updateSortUpRec(id);
    	responseObj.setSuccess(true);
    	return responseObj;
    }
    
    /**
     * 上移
     * @param id
     * @return
     */
    @RequestMapping(value = "downMoveRec", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject downMoveRec(Integer id) {
    	ResponseObject responseObj = new ResponseObject();
    	courseService.updateSortDownRec(id);
    	responseObj.setSuccess(true);
    	return responseObj;
    }

    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException, IllegalAccessException {
         ResponseObject responseObject=new ResponseObject();
         if(ids!=null) {
              String[] _ids = ids.split(",");
              courseService.deletes(_ids);
         }
         responseObject.setSuccess(true);
         responseObject.setErrorMessage("删除成功!");
         return responseObject;
    }
    
    @RequestMapping(value = "updateRec", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateRec(String ids,int isRec) {
    	ResponseObject responseObject=new ResponseObject();
    	if(ids!=null) {
    		String[] _ids = ids.split(",");
    		if(courseService.updateRec(_ids,isRec))
    		{
    			responseObject.setSuccess(true);
    	    	responseObject.setErrorMessage("操作成功!");
    		}else{
    			responseObject.setSuccess(false);
    	    	responseObject.setErrorMessage("推荐失败，最多只能推荐4个点播课程！!");
    		}
    	}
    	return responseObject;
    }
    
    @RequestMapping(value = "getCourseDetail", method = RequestMethod.POST)
	@ResponseBody
	 public ResponseObject getCourseDetail(String courseId){
    	return ResponseObject.newSuccessResponseObject(courseService.getCourseDetail(courseId));
    }
    
    /**
     * 添加课程详情
     * @param courseId
     * @param smallImgPath
     * @param courseDetail
     * @param courseOutline
     * @param commonProblem
     * @return
     */
	@RequestMapping(value = "updateCourseDetail", method = RequestMethod.POST)
	@ResponseBody
	 public ResponseObject updateCourseDetail(String courseId, String smallImgPath,String smallImgPath1,String smallImgPath2, String courseDetail,
				String courseOutline, String commonProblem){
		if(smallImgPath1!=null)smallImgPath += "dxg"+smallImgPath1;
		if(smallImgPath2!=null)smallImgPath += "dxg"+smallImgPath2;
		courseService.updateCourseDetail(courseId, smallImgPath, null, courseDetail, courseOutline, commonProblem);
        return ResponseObject.newSuccessResponseObject("修改成功！");
    }
	
	@RequestMapping(value = "addPreview", method = RequestMethod.POST)
	@ResponseBody
	 public ResponseObject addPreview(String courseId, String smallImgPath,String smallImgPath1,String smallImgPath2, String courseDetail,
				String courseOutline, String commonProblem){
		if(smallImgPath1!=null)smallImgPath += "dxg"+smallImgPath1;
		if(smallImgPath2!=null)smallImgPath += "dxg"+smallImgPath2;
		courseService.addPreview(courseId, smallImgPath, null, courseDetail, courseOutline, commonProblem);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }
	
	@RequestMapping(value = "uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject uploadImg(String content){
		String str = content.split("base64,")[1];
		byte[] b = org.apache.commons.codec.binary.Base64.decodeBase64(str);
		Attachment a = att.addAttachment(UserHolder.getCurrentUser().getId(), AttachmentType.ONLINE, "1.png", b, "image/png", null);
		if (a.getError() != 0) {
			return ResponseObject.newErrorResponseObject("上传失败！");
		}
		return ResponseObject.newSuccessResponseObject(a);
	}
	/**
	 * 获得模板内容
	 * @param type
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "getTemplate", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject getTemplate(String type,HttpSession session) throws Exception{
		
		String path = session.getServletContext().getRealPath("/template");
		File f = null;
		if ("detail".equals(type)) {
			f = new File(path+File.separator+"course_detail.html");
		} else if ("outline".equals(type)) {
			f = new File(path+File.separator+"/course_outline.html");
		}  else if ("problem".equals(type)) {
			f = new File(path+File.separator+"/course_common_problem.html");
		}
		return ResponseObject.newSuccessResponseObject(FileUtil.readAsString(f));
	}
	
	/**
	 * 设置是否展示课程介绍
	 * @param courseVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "descriptionShow", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject descriptionShow(CourseVo courseVo) throws Exception{
		ResponseObject responseObj = new ResponseObject();
		courseService.updateDescriptionShow(courseVo);
		try{
            responseObj.setSuccess(true);
       }catch(Exception e){
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
	 * 同步课程视频信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateCourseVideoInfo11111111111111111111111111111", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateCourseVideoInfo(String id) {
		return ResponseObject.newSuccessResponseObject(courseService.updateCourseVideoInfo(id));
	}
	
	/**
	 * 同步CC视频分类
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
     * 老师列表
     * @return
     */
    @RequiresPermissions("cloudClass:menu:course")
    @RequestMapping(value = "/teachers",method= RequestMethod.GET)
    @ResponseBody
    public ResponseObject teachers(String courseId){
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("roleType1",courseService.lectereListByCourseIdAndRoleType(1,courseId));
        result.put("roleType2",courseService.lectereListByCourseIdAndRoleType(2,courseId));
        result.put("roleType3", courseService.lectereListByCourseIdAndRoleType(3,courseId));
        return ResponseObject.newSuccessResponseObject(result);
    }
    
    /**
     * 增加班级信息
     * @return
     */
    @RequiresPermissions("cloudClass:menu:grade:teachers:save")
    @RequestMapping(value = "/teachers/save",method= RequestMethod.POST)
    @ResponseBody
    public ResponseObject saveTeachers(HttpServletRequest request,String gradeId,String courseId,String[] roleType1,String[] roleType2,String[] roleType3){
        List<String> roleTypes=new ArrayList<String>();
        if(roleType1!=null)
        {
            for(String _roleType1:roleType1)
            {
            	if(!"".equals(_roleType1)){
           		 roleTypes.add(_roleType1);
            	}
            }
        }
        if(roleType2!=null)
        {
            for(String _roleType2:roleType2)
            {
            	if(!"".equals(_roleType2)){
            		roleTypes.add(_roleType2);
            	}
            }
        }
        if(roleType3!=null)
        {
            for(String _roleType3:roleType3)
            {
            	if(!"".equals(_roleType3)){
            		 roleTypes.add(_roleType3);
            	}
               
            }
        }
        courseService.saveTeachers(gradeId, courseId, UserLoginUtil.getLoginUser(request).getName(), roleTypes);
        return ResponseObject.newSuccessResponseObject(null);
    }


	/**
	 * 同步课程视频信息（无章节知识点版）
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateCourseVideo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateCourseVideo(String id) {
		
		
		return ResponseObject.newSuccessResponseObject(courseService.updateCourseVideo(id));
	}

	
	
	
}
