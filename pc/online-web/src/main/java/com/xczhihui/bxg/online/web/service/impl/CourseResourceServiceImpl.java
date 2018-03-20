package com.xczhihui.bxg.online.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.pegdown.PegDownProcessor;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.HttpUtil;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.web.service.CourseResourceService;

/**
 * 课程资源
 * @author Haicheng Jiang
 *
 */
@Service
public class CourseResourceServiceImpl extends OnlineBaseServiceImpl implements CourseResourceService {

	private SimpleHibernateDao dao;
//	private Office365Util office365 = new Office365Util();
	
	@Override
	public List<Map<String, Object>> getChapterTree(String courseId,String domain) {
		
    	List<Map<String, Object>> returnmap = new ArrayList<Map<String, Object>>();
    	
    	//查询参数
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("course_id", courseId);
    	
    	//查询所有章节知识点
    	String sql = "select id,name,parent_id,level from oe_chapter "
    			+ "where is_delete=0 and course_id=? and level>1 order by sort";
    	List<Map<String, Object>> chapters = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql, courseId);
    	
    	sql = "select id,name,chapter_id,file_url,type,suffix from oe_files where is_delete=0 and course_id=? and status=1";
    	List<Map<String, Object>> files = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql, courseId);
    	
    	//组装树形结构
    	for (Map<String, Object> zhangmap : chapters) {
    		//循环章
    		if (((Integer)zhangmap.get("level")) == 2) {
    			List<Map<String, Object>> zhangsons = new ArrayList<Map<String, Object>>();
    			//循环取节>>>>>
    			for (Map<String, Object> jiemap : chapters) {
    				if (jiemap.get("parent_id").equals(zhangmap.get("id"))) {
    					List<Map<String, Object>> jiesons = new ArrayList<Map<String, Object>>();
    					//循环取知识点>>>>>
    	    			for (Map<String, Object> zhishidianmap : chapters) {
    	    				if (zhishidianmap.get("parent_id").equals(jiemap.get("id"))) {
    	    					List<Map<String, Object>> zhishidiansons = new ArrayList<Map<String, Object>>();
    	    					//循环取文件>>>>>
    	    	    			for (Map<String, Object> filemap : files) {
    	    	    				if (filemap.get("chapter_id").equals(zhishidianmap.get("id"))) {
    	    	    					//officeweb365.com，url加密
    	    	    					if (filemap.get("file_url") != null) {
    	    	    						if (filemap.get("file_url").toString().toLowerCase().endsWith(".md")) {
    	    	    							filemap.put("file_url",domain+"course/resource/previewmd?id="+filemap.get("id"));
											} else {
//												filemap.put("file_url",office365.getEncodeUrl(filemap.get("file_url").toString()));
												filemap.put("file_url",filemap.get("file_url").toString());
											}
										} else {
											filemap.put("file_url","");
										}
    	    	    					zhishidiansons.add(filemap);
    	    						}
    	    	    			}
    	    	    			zhishidianmap.put("files", zhishidiansons);
    	    	    			//<<<<<循环取文件
    	    	    			jiesons.add(zhishidianmap);
    						}
    	    			}
    	    			jiemap.put("chapterSons", jiesons);
    	    			//<<<<<循环取知识点
    	    			zhangsons.add(jiemap);
					}
    			}
    			zhangmap.put("chapterSons", zhangsons);
    			//<<<<<循环取节
    			returnmap.add(zhangmap);
    		}
    	}
    	return returnmap;
	}

	@Override
	public String mdFile2Html(String id) throws Exception {
		String sql = "select file_url from oe_files where id = ?";
		String url = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql, String.class,id);
		byte[] arr = HttpUtil.sendGetRequestBytes(url);
		PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);
		return pdp.markdownToHtml(new String(arr,"utf-8"));
	}
	
	@Override
    @Resource(name="simpleHibernateDao")
	public void setDao(SimpleHibernateDao dao) {
		this.dao = dao;
	}
}
