package com.xczhihui.bxg.online.manager.cloudClass.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.common.domain.TeachMethod;
import com.xczhihui.bxg.online.manager.cloudClass.vo.MenuVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.ScoreTypeVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.TeachMethodVo;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;

@Repository("teachMethodDao")
public class TeachMethodDao extends HibernateDao<TeachMethod> {

    public Page<TeachMethodVo> findTeachMethodPage(TeachMethodVo teachMethodVo, int pageNumber, int pageSize){
        Map<String,Object> paramMap=new HashMap<String,Object>();
        StringBuilder sql=new StringBuilder("select * from teach_method where is_delete = 0 ");
        sql.append(" order by sort desc ");
        Page<TeachMethodVo> pages= this.findPageBySQL(sql.toString(), paramMap, TeachMethodVo.class, pageNumber, pageSize);
        
        String sqlCourses="select count(1) as courseCount from oe_course where is_delete = 0 and status=1 and courseType =:id";
        
        for(TeachMethodVo temp :pages.getItems() ){
        	paramMap.put("id",temp.getId());
        	List<TeachMethodVo> res = this.findEntitiesByJdbc(TeachMethodVo.class, sqlCourses, paramMap);
        	if(res.get(0).getCourseCount()>0){
        		temp.setCourseCount(res.get(0).getCourseCount());
        	}else{
        		temp.setCourseCount(0);
        	}
        }
         return pages;
    }


    /**
     * 获取最大的
     * @return
     */
	public int getMaxSort() {
		String sql=" select ifnull(max(sort),0) as maxSort from teach_method where  is_delete=0  ";
        Map<String,Object> result=this.getNamedParameterJdbcTemplate().queryForMap(sql,new HashMap<String,Object>());
        return Integer.parseInt(result.get("maxSort") != null ? String.valueOf(result.get("maxSort")) : "0");
	}

	/**
	 * 通过id进行查找
	 * @param parseInt
	 * @return
	 */
	public TeachMethod findById(String id) {
		StringBuilder sql=new StringBuilder("select * from teach_method where id=:id");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("id",id);
        List<TeachMethod> menus=this.getNamedParameterJdbcTemplate().query(sql.toString(),params,BeanPropertyRowMapper.newInstance(TeachMethod.class));
        if(menus.size()>0){
            return menus.get(0);
        }
        return null;
	}

	/**
	 * 查找实体
	 * @param searchEntity
	 * @return
	 */
	public TeachMethod findByNotEqId(TeachMethod searchEntity) {
		StringBuilder sql=new StringBuilder("select * from teach_method where id <> :id and name=:name and  is_delete =0");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("id",searchEntity.getId());
        params.put("name", searchEntity.getName());
        List<TeachMethod> menus=this.getNamedParameterJdbcTemplate().query(sql.toString(),params,BeanPropertyRowMapper.newInstance(TeachMethod.class));
        if(menus.size()>0){
            return menus.get(0);
        }
        return null;
	}

	/**
	 * 删除数据
	 * @param _ids
	 */
	public void deletes(String[] _ids) {
		if(_ids.length>0) {
			
			//校验是否被引用
			for(String id:_ids){
				String hqlPre="from Course where  isDelete=0 and courseType = ?";
		        Course course= this.findByHQLOne(hqlPre,new Object[] {id});
	            if(course !=null){
	            	throw new RuntimeException ("该数据被引用，无法删除！");
	            }
	        }
		
            StringBuilder sql = new StringBuilder(" update teach_method set is_delete=1 where  id IN (");
            for(int i=0;i<_ids.length;i++){
                if(i!=0)
                    sql.append(",");
                sql.append("'"+_ids[i]+"'");
            }
            sql.append(")");
            this.getNamedParameterJdbcTemplate().update(sql.toString(), new HashMap<String, Object>());
        }
		
	}

}
