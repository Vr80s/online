package com.xczhihui.bxg.online.manager.boxueshe.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.boxueshe.vo.ArticleVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;

/**
 * 文章管理DAO
 * 
 * @author yxd
 *
 */
@Repository("articleDao")
public class ArticleDao extends HibernateDao<ArticleVo>{

	public Page<ArticleVo> findCloudClassCoursePage(ArticleVo articleVo, int currentPage,
			int pageSize) {
		// TODO Auto-generated method stub
		Map<String,Object> paramMap=new HashMap<String,Object>();
		StringBuilder sql =new StringBuilder("SELECT article.* ,group_concat(tag.name) as tagName ,group_concat(tag.id) as tagId,arttype.`name` as typeName,u.name as author from oe_bxs_article article,article_r_tag art ,oe_bxs_tag tag , article_type arttype, user u where article.id = art.article_id and art.tag_id = tag.id and article.type_id =arttype.id and article.user_id = u.id ");
		
		
	  if(articleVo.getTitle() != null){
		   sql.append(" and article.title like :title");
		   paramMap.put("title", "%"+articleVo.getTitle()+"%");
	   }
	  if(articleVo.getTypeId() != null){
		   sql.append(" and article.type_id =:typeId");
		   paramMap.put("typeId", articleVo.getTypeId());
	   }
      if(articleVo.getStatus()!=null){
          sql.append(" and article.status =:status ");
          paramMap.put("status",articleVo.getStatus());
      }
      if(articleVo.getIsRecommend()!=null){
          sql.append(" and article.is_recommend =:isRecommend ");
          paramMap.put("isRecommend",articleVo.getIsRecommend());
      }
      
	  if(articleVo.getStartTime() !=null){
		  sql.append(" and article.create_time >=:startTime");
		  paramMap.put("startTime", articleVo.getStartTime());
	   }

	   if(articleVo.getStopTime() !=null){
		  sql.append(" and article.create_time <=:stopTime");
		  paramMap.put("stopTime", articleVo.getStopTime());
	   }
	   
	   sql.append(" GROUP BY article.id order by create_time desc ");
	   
		Page<ArticleVo> page=this.findPageBySQL(sql.toString(), paramMap, ArticleVo.class, currentPage, pageSize);
		return page;
	}

}
