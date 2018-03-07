package com.xczhihui.bxg.online.manager.medical.dao;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalDepartment;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("departmentDao")
public class DepartmentDao extends HibernateDao<MedicalDepartment> {

    public Page<MedicalDepartment> findMedicalDepartmentPage(MedicalDepartment menuVo, int pageNumber, int pageSize){
        Map<String,Object> paramMap=new HashMap<String,Object>();
        StringBuilder sql=new StringBuilder("SELECT * FROM `medical_department` ds where deleted=0 ");
        if(menuVo.getName()!=null&&!"".equals(menuVo.getName())){
            sql.append(" and ds.name like :name ");
            paramMap.put("name","%"+menuVo.getName()+"%");
        }
        if(menuVo.getCreatePerson()!=null&&!"".equals(menuVo.getCreatePerson())){
            sql.append(" and ds.create_person like :create_person ");
            paramMap.put("create_person", "%" + menuVo.getCreatePerson()+"%");
        }
        sql.append(" order by  status desc,sort desc,create_time desc ");
        Page<MedicalDepartment> pages = this.findPageBySQL(sql.toString(), paramMap, MedicalDepartment.class, pageNumber, pageSize);

        return pages;
    }

	/**
	 * 通过id进行查找
	 * @param parseInt
	 * @return
	 */
	public MedicalDepartment findById(String parseInt) {
		StringBuilder sql=new StringBuilder("select * from medical_department where id=:id");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("id",parseInt);
        List<MedicalDepartment> menus=this.getNamedParameterJdbcTemplate().query(sql.toString(),params,BeanPropertyRowMapper.newInstance(MedicalDepartment.class));
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
	public MedicalDepartment findByNotEqId(MedicalDepartment searchEntity) {
		StringBuilder sql=new StringBuilder("select * from medical_department where name=:name and  deleted =0");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("name", searchEntity.getName());
        List<MedicalDepartment> menus=this.getNamedParameterJdbcTemplate().query(sql.toString(),params,BeanPropertyRowMapper.newInstance(MedicalDepartment.class));
        if(menus.size()>0){
            return menus.get(0);
        }
        return null;
	}


	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
	public String deleteById(String id) {
		String deleteSql = " update medical_department set deleted=1 where  id = :id ";
		Map<String,Object> params2=new HashMap<String,Object>();
		params2.put("id", id);
		this.getNamedParameterJdbcTemplate().update(deleteSql, params2);
		return "删除成功";
	}


}
