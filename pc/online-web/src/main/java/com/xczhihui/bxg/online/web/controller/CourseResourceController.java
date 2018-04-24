package com.xczhihui.bxg.online.web.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.service.CourseResourceService;

/**
 * 课程资源
 * 
 * @author Haicheng Jiang
 *
 */
@RestController
@RequestMapping(value = "/course/resource")
public class CourseResourceController {

	@Autowired
	private CourseResourceService service;

	@RequestMapping(value = "/getChapterTree")
	public ResponseObject getChapterTree(HttpServletRequest req, String courseId) {
		StringBuffer url = req.getRequestURL();  
		String domain = url.delete(url.length() - req.getRequestURI().length(), url.length()).append("/").toString();
		return ResponseObject.newSuccessResponseObject(service.getChapterTree(courseId,domain));
	}
	
	@RequestMapping(value = "/previewmd")
	public void getChapterTree(HttpServletResponse res, String id) throws Exception {
		String html = service.mdFile2Html(id);
		res.setHeader("content-type", "text/html;charset=UTF-8");
		res.getWriter().write(html);
		res.flushBuffer();
	}
}
