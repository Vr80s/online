package com.xczhihui.headline.dao;

import java.util.HashMap;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.headline.vo.AppraiseVo;

@Repository
public class AppraiseDao extends SimpleHibernateDao{

	public Page<AppraiseVo> findAppraisePage(AppraiseVo appraiseVo, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
        Map<String,Object> paramMap=new HashMap<String,Object>();
        StringBuilder sql=new StringBuilder("SELECT a.id , a.content ,a.article_id ,a.user_id name ,a.create_time from oe_bxs_appraise a ,oe_bxs_article art where a.is_delete = 0 and a.article_id = art.id ");
		
		
        if(appraiseVo.getArticleId()!=null && !"".equals(appraiseVo.getArticleId())){
  	      sql.append(" and a.article_id = :articleId ");
  	      paramMap.put("articleId", appraiseVo.getArticleId());
        }
        
  	    if(appraiseVo.getContent()!=null && !"".equals(appraiseVo.getContent())){
  	      sql.append(" and a.content like :content ");
  	      paramMap.put("content", "%" + appraiseVo.getContent()+"%");
  	    }
  	  
  	    if(appraiseVo.getName()!=null && !"".equals(appraiseVo.getName())){
  		  sql.append(" and u.`name` = :name ");
  		  paramMap.put("name", appraiseVo.getName());
  	    }
        
  	    if(appraiseVo.getStartTime() !=null){
  		  sql.append(" and a.create_time >=:startTime");
  		  paramMap.put("startTime", appraiseVo.getStartTime());
  	    }
  	    if(appraiseVo.getStopTime() !=null){
  		  sql.append(" and DATE_FORMAT(a.create_time,'%Y-%m-%d') <=:stopTime");
  		  paramMap.put("stopTime", appraiseVo.getStopTime());
  	    }
  	  sql.append(" order by a.create_time desc");
  	  Page<AppraiseVo> page=this.findPageBySQL(sql.toString(), paramMap, AppraiseVo.class,currentPage, pageSize);
  	    return page;
	}
	public void deleteById(String id){
		StringBuilder sql = new StringBuilder("update oe_bxs_article t set t.praise_sum = t.praise_sum - 1 " +
				"where EXISTS(select 1 from oe_bxs_appraise t2 where t.id = t2.article_id and t2.id = :id) ");
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		this.getNamedParameterJdbcTemplate().update(sql.toString(),map);
		sql.replace(0,sql.length(),"");//清空字符串
		sql.append("DELETE from oe_bxs_appraise  where id =:id ");
		this.getNamedParameterJdbcTemplate().update(sql.toString(),map);
	}
	
		
}
