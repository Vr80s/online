package com.xczhihui.bxg.online.manager.message.web;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Grade;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.Message;
import com.xczhihui.bxg.online.common.domain.MessageRecord;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.cloudClass.service.GradeService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.common.service.CommonService;
import com.xczhihui.bxg.online.manager.common.web.UserVo;
import com.xczhihui.bxg.online.manager.message.service.MessageService;
import com.xczhihui.bxg.online.manager.message.vo.MessageVo;
import com.xczhihui.bxg.online.manager.user.service.UserService;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;


/**
 * 消息管理控制器
 * @author majian
 * @date 2016-3-4 10:52:53
 */
@Controller
@RequestMapping("message")
public class MessageController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 消息服务
	 */
	@Autowired
	private MessageService messageService;

	/**
	 * 用户服务
	 */
	@Autowired
	private UserService userService;

	/**
	 * 公共服务
	 */
	@Autowired
	private CommonService commonService;
	/**
	 * 课程服务
	 */
	@Autowired
	private CourseService courseService;
	/**
	 * 班级服务
	 */
	@Autowired
	private GradeService gradeService;

	/**
	 * PATH属性
	 */
	private static final String PATH = "message/";
	
	/**
	 * 跳转到页面
	 * @param request 请求对象
	 * @param model 请求模型
	 * @return 指定页面
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		return PATH+"/index";
	}

	/**
	 * 跳转到列表页
	 * @param request 请求对象
	 * @return 指定页面
	 */
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request) {
		//在列表初始化时查找出学科
		List<Menu> menuVos= courseService.getfirstMenus(null);
		request.setAttribute("menuVo", menuVos);
		
		//在列表初始化时查找出课程
		List<Course> courseVos = courseService.getCourse();
		request.setAttribute("courseVo", courseVos);
		
		//在列表初始化时查找出班级
		List<Grade> gradeVos = courseService.getGrade(null);
		request.setAttribute("gradeVo", gradeVos);
		
		return PATH + "list";
	}

	/**
	 * 跳转到上方用户搜索页
	 * @param request 请求对象
	 * @return 指定页面
	 */
	@RequestMapping(value = "user")
	public String user(HttpServletRequest request) {
		return PATH + "user";
	}

	/**
	 * 返回所有用户信息
	 * @param request 请求对象
	 * @return 所有用户信息
	 */
	@RequestMapping(value = "users")
	@ResponseBody
	public ResponseObject findUsers(HttpServletRequest request){
		ResponseObject responseObj = new ResponseObject();
		List<UserVo> users=userService.findAll();
		responseObj.setResultObject(users);
		return responseObj;
	}

	/**
	 * 返回站内全部推送
	 * @param tableVo 表格对象
	 * @return 表格对象
	 */
	@RequestMapping(value = "load/messages")
	@ResponseBody
	public TableVo loadMessages(TableVo tableVo)
	{
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int pageNumber = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);
		MessageVo searchMessageVo=new MessageVo();
		for (Group g : groups.getGroupList()) {
			
			if ("time_start".equals(g.getPropertyName())&&g.getPropertyValue1()!=null&&!StringUtils.isEmpty(String.valueOf(g.getPropertyValue1()))) {
				searchMessageVo.setTime_start(String.valueOf(g.getPropertyValue1()));
			}
			if ("time_end".equals(g.getPropertyName())&&g.getPropertyValue1()!=null&&!StringUtils.isEmpty(String.valueOf(g.getPropertyValue1()))) {
				searchMessageVo.setTime_end(String.valueOf(g.getPropertyValue1()));
			}
		}
		Page<MessageVo> page = messageService.findPageMessages(searchMessageVo, pageNumber,
				pageSize);

		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);

		return tableVo;
	}

	/**
	 * 批量删除常量
	 *
	 * @param ids id列表
	 * @return 响应对象
	 */
	@RequestMapping(value = "deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteBatch(String ids) {
		ResponseObject responseObj = new ResponseObject();
		try {
			String[] idArr = ids.split(",");
			messageService.deleteBatch(idArr);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("操作成功");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			responseObj.setSuccess(false);
			responseObj.setErrorMessage("操作失败");
		}
		return responseObj;
	}
	/**
	 * 加载制定菜单下的视频
	 * @param userId 用户编号
	 * @param tableVo 表格对象
	 * @return 表格对象
	 */
	@RequestMapping(value = "load/user/{userId}/messages")
	@ResponseBody
	public TableVo loadVideoByMenu(@PathVariable String userId,TableVo tableVo)
	{
		if(StringUtils.isNotEmpty(userId)) {
			int pageSize = tableVo.getiDisplayLength();
			int index = tableVo.getiDisplayStart();
			int pageNumber = index / pageSize + 1;
			String params = tableVo.getsSearch();
			Groups groups = Tools.filterGroup(params);
			String vNameOrCreater = "";
			for (Group g : groups.getGroupList()) {
				if ("vNameOrCreater".equals(g.getPropertyName())) {
					vNameOrCreater = g.getPropertyValue1().toString();
				}
			}
			UserVo userVo=new UserVo();
			userVo.setId(userId);
			Page<MessageVo> page = messageService.findPageMessagesByUser(userVo, vNameOrCreater, pageNumber,
					pageSize);

			int total = page.getTotalCount();
			tableVo.setAaData(page.getItems());
			tableVo.setiTotalDisplayRecords(total);
			tableVo.setiTotalRecords(total);
		}

		return tableVo;
	}

	/**
	 * 删除消息
	 * @param id 编号
	 * @param request 请求对象
	 * @return 响应对象
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deleteById(@PathVariable String id,HttpServletRequest request) {
		ResponseObject responseObj = new ResponseObject();

		try {
			if(StringUtils.isNotEmpty(id)) {
				messageService.deleteById(id);
				responseObj.setSuccess(true);
				responseObj.setErrorMessage("操作成功");
				return responseObj;
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		responseObj.setSuccess(false);
		responseObj.setErrorMessage("操作失败");
		return responseObj;
	}

	/**
	 * 保存消息推送信息
	 * @param message 消息对象
	 * @return 响应对象
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public ResponseObject save(HttpServletRequest request,MessageVo messageVo) throws IllegalAccessException, InvocationTargetException{
		ResponseObject responseObj = new ResponseObject();

		String regEx="<a.+?href="; 
		String regEx2="\">";

		if(!"-1".equals(messageVo.getGrade())){
			//循环班级查找班级下的学生
			String[] gradeIds=messageVo.getGrade().split(",");
			String gradeIdsStr="";
			for(int i=0;i<gradeIds.length;i++){
				gradeIdsStr ="'"+gradeIds[i]+"',"+gradeIdsStr;
			}
			
			List<UserVo> users = new ArrayList<>();
			
			if(gradeIdsStr.length() > 0){
				users=userService.findAllUserByGrade(gradeIdsStr.substring(0,gradeIdsStr.length()-1));
			}
			
			for(UserVo userVo:users){
				String id = UUID.randomUUID().toString().replace("-","");
				Message message= new Message();
				//BeanUtils.copyProperties(messageVo, message);
				message.setId(id);
				message.setContext(messageVo.getContext().replaceAll(regEx, "<a href=\"javascript:void(0);\" onclick=\'on_click_msg\\(\""+id+"\",").replaceAll(regEx2,"\")\'>"));
				message.setCreateTime(new Date());
				message.setCreatePerson(UserLoginUtil.getLoginUser(request).getName());
				message.setType(0);
				message.setReadstatus(Short.valueOf("0"));
				message.setStatus((short) 1);
				message.setUserId(userVo.getId());
				messageService.saveMessage(message);
				
			}
			if(users.size() == 0){
				responseObj.setSuccess(false);
				responseObj.setErrorMessage("没有学员，请重试！");
				return responseObj; 
			}
			MessageRecord messageRecord= new MessageRecord();
			//BeanUtils.copyProperties(messageVo, messageRecord);
			messageRecord.setContext(messageVo.getContext());
			messageRecord.setSubject(messageVo.getSubjectName());
			messageRecord.setCourse(messageVo.getCourseName());
			messageRecord.setGrade(messageVo.getGradeName());
			messageRecord.setCreateTime(new Date());
			messageRecord.setCreatePerson(UserLoginUtil.getLoginUser(request).getName());
			messageRecord.setUserCount(users.size());
			messageService.saveMessageRecord(messageRecord);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("操作成功");
			return responseObj;
		}
		
		if("-1".equals(messageVo.getGrade())&&!"-1".equals(messageVo.getCourse())){
			//查找该课程下的所有班级，查找班级下的学生
			List<Grade> grades = courseService.getGrade(messageVo.getCourse());
				
			String gradeIdsStr="";
			for(int i=0;i<grades.size();i++){
				gradeIdsStr ="'"+grades.get(i).getId()+"',"+gradeIdsStr;
			}
			
			List<UserVo> users = new ArrayList<>();
			if(gradeIdsStr.length() > 0){
				users=userService.findAllUserByGrade(gradeIdsStr.substring(0,gradeIdsStr.length()-1));
			}
			
			for(UserVo userVo:users){
				String id = UUID.randomUUID().toString().replace("-","");
				Message message= new Message();
				//BeanUtils.copyProperties(messageVo, message);
				message.setId(id);
				message.setContext(messageVo.getContext().replaceAll(regEx, "<a href=\"javascript:void(0);\" onclick=\'on_click_msg\\(\""+id+"\",").replaceAll(regEx2,"\")\'>"));
				message.setCreateTime(new Date());
				message.setCreatePerson(UserLoginUtil.getLoginUser(request).getName());
				message.setType(0);
				message.setReadstatus(Short.valueOf("0"));
				message.setStatus((short) 1);
				message.setUserId(userVo.getId());
				messageService.saveMessage(message);
			}
			
			if(users.size() == 0){
				responseObj.setSuccess(false);
				responseObj.setErrorMessage("没有学员，请重试！");
				return responseObj; 
			}
			
			MessageRecord messageRecord= new MessageRecord();
			//BeanUtils.copyProperties(messageVo, messageRecord);
			messageRecord.setContext(messageVo.getContext());
			messageRecord.setSubject(messageVo.getSubjectName());
			messageRecord.setCourse(messageVo.getCourseName());
			messageRecord.setGrade(messageVo.getGradeName());
			messageRecord.setCreateTime(new Date());
			messageRecord.setCreatePerson(UserLoginUtil.getLoginUser(request).getName());
			messageRecord.setUserCount(users.size());
			messageService.saveMessageRecord(messageRecord);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("操作成功");
			return responseObj;
		}
		
		if(!"-1".equals(messageVo.getSubject())&&"-1".equals(messageVo.getGrade())&&"-1".equals(messageVo.getCourse())){
			//查找该学科下所有课程并查找课程下的所有班级，循环班级查找班级下的学生
			List<CourseVo> courses = courseService.listByMenuId(messageVo.getSubject());
			
			String courseIdsStr="";
			for(int i=0;i<courses.size();i++){
				courseIdsStr ="'"+courses.get(i).getId()+"',"+courseIdsStr;
			}
			List<Grade> grades = new ArrayList<>();
			if(courseIdsStr.length() > 0){
				grades = courseService.getGrade(courseIdsStr.substring(0,courseIdsStr.length()-1));
			}
			
			String gradeIdsStr="";
			for(int i=0;i<grades.size();i++){
				gradeIdsStr ="'"+grades.get(i).getId()+"',"+gradeIdsStr;
			}
			List<UserVo> users = new ArrayList<>();
			if(gradeIdsStr.length() > 0){
				users=userService.findAllUserByGrade(gradeIdsStr.substring(0,gradeIdsStr.length()-1));
			}
			
			for(UserVo userVo:users){
				String id = UUID.randomUUID().toString().replace("-","");
				Message message= new Message();
				//BeanUtils.copyProperties(messageVo, message);
				message.setId(id);
				message.setContext(messageVo.getContext().replaceAll(regEx, "<a href=\"javascript:void(0);\" onclick=\'on_click_msg\\(\""+id+"\",").replaceAll(regEx2,"\")\'>"));
				message.setCreateTime(new Date());
				message.setCreatePerson(UserLoginUtil.getLoginUser(request).getName());
				message.setType(0);
				message.setReadstatus(Short.valueOf("0"));
				message.setStatus((short) 1);
				message.setUserId(userVo.getId());
				messageService.saveMessage(message);
			}
				
			if(users.size() == 0){
				responseObj.setSuccess(false);
				responseObj.setErrorMessage("没有学员，请重试！");
				return responseObj; 
			}
			
			MessageRecord messageRecord= new MessageRecord();
			//BeanUtils.copyProperties(messageVo, messageRecord);
			messageRecord.setContext(messageVo.getContext());
			messageRecord.setSubject(messageVo.getSubjectName());
			messageRecord.setCourse(messageVo.getCourseName());
			messageRecord.setGrade(messageVo.getGradeName());
			messageRecord.setCreateTime(new Date());
			messageRecord.setCreatePerson(UserLoginUtil.getLoginUser(request).getName());
			messageRecord.setUserCount(users.size());
			messageService.saveMessageRecord(messageRecord);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("操作成功");
			return responseObj;
		} 
		if("-1".equals(messageVo.getSubject())&&"-1".equals(messageVo.getGrade())&&"-1".equals(messageVo.getCourse())){
			//发给所有用户
			List<UserVo> users=userService.findAll();
			if(users!=null&&users.size()>0){
				for(UserVo userVo:users){
					String id = UUID.randomUUID().toString().replace("-","");
					Message message= new Message();
					//BeanUtils.copyProperties(messageVo, message);
					message.setId(id);
					message.setContext(messageVo.getContext().replaceAll(regEx, "<a href=\"javascript:void(0);\" onclick=\'on_click_msg\\(\""+id+"\",").replaceAll(regEx2,"\")\'>"));
					message.setCreateTime(new Date());
					message.setCreatePerson(UserLoginUtil.getLoginUser(request).getName());
					message.setType(0);
					message.setReadstatus(Short.valueOf("0"));
					message.setStatus((short) 1);
					message.setUserId(userVo.getId());
					messageService.saveMessage(message);
				}
			}
			
			if(users.size() == 0){
				responseObj.setSuccess(false);
				responseObj.setErrorMessage("没有学员，请重试！");
				return responseObj; 
			}
			
			MessageRecord messageRecord= new MessageRecord();
			//BeanUtils.copyProperties(messageVo, messageRecord);
			messageRecord.setContext(messageVo.getContext());
			messageRecord.setSubject(messageVo.getSubjectName());
			messageRecord.setCourse(messageVo.getCourseName());
			messageRecord.setGrade(messageVo.getGradeName());
			messageRecord.setCreateTime(new Date());
			messageRecord.setCreatePerson(UserLoginUtil.getLoginUser(request).getName());
			messageRecord.setUserCount(users.size());
			messageService.saveMessageRecord(messageRecord);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("操作成功");
			return responseObj;
		}
		responseObj.setSuccess(true);
		responseObj.setErrorMessage("非法操作，请联系管理员！");
		return responseObj;
	}

}
