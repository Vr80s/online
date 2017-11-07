package com.xczhihui.bxg.online.web.service;

import java.util.List;
import java.util.Map;

/**
 * 课程资源
 * @author Haicheng Jiang
 *
 */
public interface CourseResourceService {
	/**
	 * 查询章节知识点的树形结构
	 * @param courseId
	 * @param domain
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> getChapterTree(String courseId,String domain);

	public String mdFile2Html(String id) throws Exception;
}
