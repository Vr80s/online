package com.xczhihui.bxg.online.manager.user.web;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.manager.user.service.PersonService;

/**
 * 个人中心
 * 
 * @author Haicheng Jiang
 *
 */
@Controller
@RequestMapping("person")
public class PersonalController extends AbstractController {

	protected final static String USER_PATH_PREFIX = "/user/";
	
	@Autowired
	private PersonService personService;
	
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		User user = (User) UserHolder.getRequireCurrentUser();
		request.setAttribute("user", user);
		return USER_PATH_PREFIX + "person";
	}

	@RequestMapping(value = "update/info")
	@ResponseBody
	public ResponseObject updatePersonInfo(User newUser) {
		User oldUser = (User) UserHolder.getRequireCurrentUser();
		this.personService.updatePersonInfo(oldUser, newUser);
		return ResponseObject.newSuccessResponseObject(oldUser);
	}

	@RequestMapping(value = "update/password")
	@ResponseBody
	public ResponseObject updatePassword(HttpServletRequest request) throws ServletRequestBindingException {
		String oldpassword = ServletRequestUtils.getRequiredStringParameter(request, "oldpassword");
		String newpassword = ServletRequestUtils.getRequiredStringParameter(request, "newpassword");
		User user = (User) UserHolder.getRequireCurrentUser();
		this.personService.updatePassword(user, newpassword, oldpassword);
		return ResponseObject.newSuccessResponseObject(user);
	}

	@RequestMapping(value = "update/head")
	@ResponseBody
	public ResponseObject updateHeadPhoto(HttpServletRequest request) throws ServletRequestBindingException {
		String content = ServletRequestUtils.getRequiredStringParameter(request, "image");
		int i = content.indexOf(',');
		if(i > 0){
			content = content.substring(i + 1);
		}
		byte[] image = Base64.getDecoder().decode(content);
		User user = (User) UserHolder.getRequireCurrentUser();
		this.personService.updateHeadPhoto(user, image);
		return ResponseObject.newSuccessResponseObject(user);
	}

}