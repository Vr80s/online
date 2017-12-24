package com.xczhihui.bxg.online.manager.boxueshe.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalWritings;
import com.xczhihui.bxg.online.manager.boxueshe.vo.ArticleVo;
import com.xczhihui.bxg.online.manager.boxueshe.vo.WritingVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;

/**
 * 文章管理DAO
 * 
 * @author yxd
 *
 */
@Repository("writingDao")
public class WritingDao extends HibernateDao<ArticleVo>{

	public Page<MedicalWritings> findCloudClassCoursePage(WritingVo articleVo, int currentPage,
			int pageSize) {
		// TODO Auto-generated method stub
		Map<String,Object> paramMap=new HashMap<String,Object>();
		
		StringBuilder sql =new StringBuilder("SELECT mw.*,oba.comment_sum as commentSum  ,\n" +
				"  (SELECT GROUP_CONCAT(md.name) FROM `medical_doctor_writings` mdw JOIN `medical_doctor` md ON mdw.doctor_id = md.id WHERE mdw.writings_id = mw.id) doctorName" +
				" from "
				+ "medical_writings as mw,oe_bxs_article as oba  where 1=1 and mw.article_id = oba.id ");

		  if(articleVo.getTitle() != null){
			   sql.append(" and mw.title like :title or mw.author like :author ");
			   paramMap.put("title", "%"+articleVo.getTitle()+"%");
			   paramMap.put("author", "%"+articleVo.getTitle()+"%");
		   }
		
	      if(articleVo.getStatus()!=null){
	          sql.append(" and mw.status =:status ");
	          paramMap.put("status",articleVo.getStatus());
	      }
	      
		  if(articleVo.getStartTime() !=null){
			  sql.append(" and mw.create_time >=:startTime");
			  paramMap.put("startTime", articleVo.getStartTime());
		   }
	
		   if(articleVo.getStopTime() !=null){
			  sql.append(" and mw.create_time <=:stopTime");
			  paramMap.put("stopTime", articleVo.getStopTime());
		   }
	   
		 sql.append(" order by mw.create_time desc ");
		
		Page<MedicalWritings> page=this.findPageBySQL(sql.toString(), paramMap, MedicalWritings.class, currentPage, pageSize);
		return page;
	}

}
