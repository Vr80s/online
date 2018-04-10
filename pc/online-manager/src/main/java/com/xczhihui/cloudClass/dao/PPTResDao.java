package com.xczhihui.cloudClass.dao;

import java.util.HashMap;
import java.util.Map;

import com.xczhihui.common.dao.HibernateDao;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Files;

@Repository("pptResDao")
public class PPTResDao extends HibernateDao<Files> {
	// Office365Util office365Util= new Office365Util();
	public Page<Files> findPPTResPage(Files searchVo, int currentPage,
			int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"select f.* from oe_chapter c,oe_files f where f.type=0 and f.course_id = "
						+ searchVo.getCourseId()
						+ " and c.course_id="
						+ searchVo.getCourseId()
						+ " and c.is_delete = 0 and f.is_delete = 0 and c.id=f.chapter_id");
		if (searchVo.getChapterId() != null) {
			sql.append(" and f.chapter_id in( ");
			String[] ids = searchVo.getChapterId().split(",");
			for (int i = 0; i < ids.length; i++) {
				if (i != 0) {
					sql.append(",");
				}
				sql.append("'" + ids[i] + "'");
			}
			sql.append(" ) ");
		}
		sql.append(" order by f.status desc ,f.create_time desc  ");
		Page<Files> ms = this.findPageBySQL(sql.toString(), paramMap,
				Files.class, currentPage, pageSize);
		for (Files file : ms.getItems()) {
			// file.setFileUrl(office365Util.getEncodeUrl(file.getFileUrl()));
		}
		return ms;
	}

	public Page<Files> findCaseResPage(Files searchVo, int currentPage,
			int pageSize, String domain) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"select f.* from oe_chapter c,oe_files f where f.type!=0 and f.course_id = "
						+ searchVo.getCourseId()
						+ " and c.course_id="
						+ searchVo.getCourseId()
						+ " and c.is_delete = 0 and f.is_delete = 0 and c.id=f.chapter_id");
		if (searchVo.getChapterId() != null) {
			sql.append(" and f.chapter_id in( ");
			String[] ids = searchVo.getChapterId().split(",");
			for (int i = 0; i < ids.length; i++) {
				if (i != 0) {
					sql.append(",");
				}
				sql.append("'" + ids[i] + "'");
			}
			sql.append(" ) ");
		}
		sql.append(" order by f.status desc ,f.create_time desc ");
		Page<Files> ms = this.findPageBySQL(sql.toString(), paramMap,
				Files.class, currentPage, pageSize);
		for (Files file : ms.getItems()) {
			if (file.getFileUrl() != null
					&& file.getFileUrl().toLowerCase().endsWith(".md")) {
				file.setFileUrl(domain + "cloudclass/ppt/previewmd?id="
						+ file.getId());
			} else {
				// file.setFileUrl(office365Util.getEncodeUrl(file.getFileUrl()));
			}
		}
		return ms;
	}

}
