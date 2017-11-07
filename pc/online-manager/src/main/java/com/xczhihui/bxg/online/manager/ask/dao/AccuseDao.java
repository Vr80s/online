package com.xczhihui.bxg.online.manager.ask.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.ask.vo.AccuseVo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;


/**
 * 问题管理层类
 *
 * @author 王高伟
 * @create 2016-10-13 18:03:39
 */
@Repository
public class AccuseDao extends SimpleHibernateDao {

	public Page<AccuseVo> findAccusePage(AccuseVo accuseVo, int pageNumber, int pageSize){
        Map<String,Object> paramMap=new HashMap<String,Object>();
        StringBuilder sql=new StringBuilder("select * from (" +
        									"SELECT " +//问题
							        		"	oaa.id, " +
							        		"	oaa.target_type, " +
							        		"	oaa.target_id, " +
							        		"	oaa.accuse_type, " +
							        		"	oaa.content accuse_type_content, " +
							        		"	oaa.create_time, " +
							        		"	oaa.status, " +
							        		"	oaa.create_person create_person_id, " +
							        		"	ou. name createPersonName, " +
							        		"	oaq.title content,  " +
							        		"	oaq.id questionId,  " +
							        		"	oua.name accusePersonName  " +
							        		"FROM " +
							        		"	oe_ask_accuse oaa " +
							        		"JOIN oe_ask_question oaq ON ( " +
							        		"	oaa.target_id = oaq.id " +
							        		"	AND oaa.target_type = 0 ) " +
							        		"JOIN oe_user ou ON ( " +
							        		"	oaa.user_id = ou.id ) " +
							        		"JOIN oe_user oua ON ( " +
							        		"	oaq.user_id = oua.id " +
							        		") ");

      if(accuseVo.getAccuseType()!=null && !"".equals(accuseVo.getAccuseType())){
	      sql.append(" and oaa.accuse_type = :accuseType ");
	      paramMap.put("accuseType", accuseVo.getAccuseType());
      }
      
	  if(accuseVo.getContent()!=null && !"".equals(accuseVo.getContent())){
	      sql.append(" and oaq.title like :content ");
	      paramMap.put("content", "%" + accuseVo.getContent()+"%");
	  }
	  
	  if(accuseVo.getStatus()!=null && !"".equals(accuseVo.getStatus())){
		  sql.append(" and oaa.status = :status ");
		  paramMap.put("status", accuseVo.getStatus());
	  }
      
	  if(accuseVo.getStartTime() !=null){
		  sql.append(" and oaa.create_time >=:startTime");
		  paramMap.put("startTime", accuseVo.getStartTime());
	  }
	  if(accuseVo.getStopTime() !=null){
		  sql.append(" and DATE_FORMAT(oaa.create_time,'%Y-%m-%d') <=:stopTime");
		  paramMap.put("stopTime", accuseVo.getStopTime());
	  }
        						 sql.append(" UNION ALL ");

        						 sql.append("SELECT " +//回答
							        		"	oaa.id, " +
							        		"	oaa.target_type, " +
							        		"	oaa.target_id, " +
							        		"	oaa.accuse_type, " +
							        		"	oaa.content accuse_type_content, " +
							        		"	oaa.create_time, " +
							        		"	oaa.status, " +
							        		"	oaa.create_person create_person_id, " +
							        		"	ou. name createPersonName, " +
							        		"	(case when trim(oaan.text) is not null and trim(oaan.text) != '' then oaan.text ELSE oaan.content end) content,  " +
							        		"	oaan.question_id questionId,  " +
							        		"	oua.name accusePersonName  " +
							        		"FROM " +
							        		"	oe_ask_accuse oaa " +
							        		"JOIN oe_ask_answer oaan ON ( " +
							        		"	oaa.target_id = oaan.id " +
							        		"	AND oaa.target_type = 1 " +
							        		") " +
							        		"JOIN oe_user ou ON ( " +
							        		"	oaa.user_id = ou.id  ) "+
							        		"JOIN oe_user oua ON ( " +
							        		"	oaan.user_id = oua.id " +
							        		") ");
      if(accuseVo.getAccuseType()!=null && !"".equals(accuseVo.getAccuseType())){
		      sql.append(" and oaa.accuse_type = :accuseType ");
		      paramMap.put("accuseType", accuseVo.getAccuseType());
      }
      
	  if(accuseVo.getContent()!=null && !"".equals(accuseVo.getContent())){
	      sql.append(" and oaan.text like :content ");
	      paramMap.put("content", "%" + accuseVo.getContent()+"%");
	  }
	  
	  if(accuseVo.getStatus()!=null && !"".equals(accuseVo.getStatus())){
		  sql.append(" and oaa.status = :status ");
		  paramMap.put("status", accuseVo.getStatus());
	  }
      
	  if(accuseVo.getStartTime() !=null){
		  sql.append(" and oaa.create_time >=:startTime");
		  paramMap.put("startTime", accuseVo.getStartTime());
	  }

	  if(accuseVo.getStopTime() !=null){
		  sql.append(" and DATE_FORMAT(oaa.create_time,'%Y-%m-%d') <=:stopTime");
		  paramMap.put("stopTime", accuseVo.getStopTime());
	  }
							        		
								sql.append(")  t");


      sql.append(" order by t.create_time desc");
//      System.out.println(sql.toString());
      Page<AccuseVo> ms = this.findPageBySQL(sql.toString(), paramMap, AccuseVo.class, pageNumber, pageSize);
    
      return ms;
  }
	
	/**
	 *  验证状态是否已经处理  返回true已处理 其他 未处理
	 * @param accuseVo
	 * @return
	 */
	public boolean checkAccuseStatus(AccuseVo accuseVo){
		Map<String,Object> paramMap=new HashMap<String,Object>();
		String sql = "select oaa.status from oe_ask_accuse oaa where oaa.id = :id";
		paramMap.put("id", accuseVo.getId());
		String status = this.getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, String.class);
		return "0".equals(status) ? false : true;
	}
}
