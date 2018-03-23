package com.xczhihui.bxg.online.manager.cloudClass.web;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.StudentStory;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.cloudClass.service.StudentStoryService;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

import com.google.gson.Gson;

/**
 * 学员故事控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("cloudclass/studentStory")
public class StudentStoryController {
	protected final static String CLOUD_CLASS_PATH_PREFIX = "/cloudClass/";
	@Autowired
	private StudentStoryService studentStoryService;
	@Autowired
	private AttachmentCenterService att;
	@Autowired
	private CourseService courseService;
	
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		List<Menu> menuVos= courseService.getfirstMenus(null);
		request.setAttribute("menuVo", menuVos);
		
		//在列表初始化时查找出课程
		List<Course> scoreTypeVos = courseService.getCourse();
		request.setAttribute("scoreTypeVo", scoreTypeVos);
		return CLOUD_CLASS_PATH_PREFIX + "/studentStory";
	}
	
	//@RequiresPermissions("cloudClass:menu:studentStory")
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo studentStorys(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        
        StudentStory searchVo=new StudentStory();
        Group name = groups.findByName("NameSearch");
        
        if (name != null) {
      	  searchVo.setName(name.getPropertyValue1().toString());
        }
        
        Group menuId = groups.findByName("search_menu");
        if (menuId != null) {
      	  searchVo.setMenuId(Integer.valueOf(menuId.getPropertyValue1().toString()));
        }
        
        Group scoreTypeId = groups.findByName("search_scoreType");
        if (scoreTypeId != null) {
      	  searchVo.setCourseTypeId(scoreTypeId.getPropertyValue1().toString());
        }
        
        Page<StudentStory> page = studentStoryService.findStudentStoryPage(searchVo, currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
	}
	
	@RequestMapping(value = "uploadHeadImg")
	@ResponseBody
	 public void uploadHeadImg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		//防止中文乱码
		response.setCharacterEncoding("UTF-8");  
	    response.setContentType("application/json; charset=utf-8");
		
		MultipartFile attachmentFile = null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> item = multipartRequest.getFileNames();
		while (item.hasNext()) {
			attachmentFile = multipartRequest.getFile(item.next());
		}
		if (attachmentFile == null || attachmentFile.getSize() <= 0) {
			response.getWriter().write(new Gson().toJson(new Attachment(1,"请上传头像！")));
			return;
		}
		if (attachmentFile.getSize() > 1024 * 200) {
			response.getWriter().write(new Gson().toJson(new Attachment(1,"大小不能超过200k！")));
			return;
		}
		
		BufferedImage sourceImage = ImageIO.read(attachmentFile.getInputStream());
		int orgWidth = sourceImage.getWidth();
		int orgHeight = sourceImage.getHeight();
		
		if (orgWidth!=orgHeight) {
			response.getWriter().write(new Gson().toJson(new Attachment(1,"请上传1：1的图片!")));
			return;
		}
		
		BxgUser u = UserHolder.getCurrentUser();
		String upload = att.upload(u.getId(), "online", attachmentFile.getOriginalFilename(), 
				attachmentFile.getContentType(), attachmentFile.getBytes(), "1");
		response.getWriter().write(upload);
	}
	
	
	/**
	 * 添加
	 * @param vo
	 * @return
	 */
	//@RequiresPermissions("cloudClass:menu:studentStory")
	@RequestMapping(value = "addStudentStory", method = RequestMethod.POST)
	@ResponseBody
	 public ResponseObject add(StudentStory studentStory){
		String content = studentStory.getText();
		content = Base64ToimageURL(content);
		studentStory.setText(content);
		studentStoryService.addStudentStory(studentStory);
		/**
		 * edit begin   author : Aaron  DateTime : 2016-09-08
		 */
		ResponseObject responseObj = new ResponseObject();
		try{
		responseObj.setSuccess(true);
        responseObj.setErrorMessage("新增学员故事成功！");
		}catch(Exception e){
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("新增学员故事失败！");
       }
		return responseObj;
		
    }
	
	public String Base64ToimageURL(String content){
		Pattern p = Pattern.compile("<img\\s+(?:[^>]*)src\\s*=\\s*([^>]+)",Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
		Matcher matcher =  p.matcher(content);
		while (matcher.find()){
			String group = matcher.group(1);
			if (StringUtils.hasText(group))   {
				group = group.replaceAll("\"", "");
				//存在base64编码的数据，才进行64Toimage转换以及上传
				if(group.split("base64,").length>1){
					String str = group.split("base64,")[1];
					byte[] b = org.apache.commons.codec.binary.Base64.decodeBase64(str);
					Attachment a = att.addAttachment(UserHolder.getCurrentUser().getId(), AttachmentType.ONLINE, "1.png", b, "image/png");
					content = content.replace(group, a.getUrl());
				}
				
			} 
		}
		return content;
	}
	
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException, IllegalAccessException {
         ResponseObject responseObject=new ResponseObject();
         if(ids!=null) {
              String[] _ids = ids.split(",");
              studentStoryService.deletes(_ids);
         }
         responseObject.setSuccess(true);
         responseObject.setErrorMessage("删除成功!");
         return responseObject;
    }
    
	/**
	 * 查看
	 * @param vo
	 * @return
	 */
	//@RequiresPermissions("cloudClass:menu:studentStory")
	@RequestMapping(value = "findStudentStoryById", method = RequestMethod.GET)
	@ResponseBody
	 public StudentStory findStudentStoryById(String id) {
		
		 return studentStoryService.findCourseById(id);
	}
	
	/**
	 * 编辑
	 * @param vo
	 * @return
	 */
	//@RequiresPermissions("cloudClass:menu:studentStory")
	@RequestMapping(value = "updateStudentStoryById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateStudentStoryById (StudentStory studentStory){
		String content = studentStory.getText();
		content = Base64ToimageURL(content);
		studentStory.setText(content);
		studentStoryService.updateStudentStory(studentStory);
		 return ResponseObject.newSuccessResponseObject("修改成功！");
	}
}
