package com.xczhihui.medical.dao;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalField;
import com.xczhihui.common.dao.HibernateDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("fieldDao")
public class FieldDao extends HibernateDao<MedicalField> {

	public Page<MedicalField> findMedicalFieldPage(MedicalField menuVo,
			int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"SELECT * FROM `medical_field` ds where deleted=0 ");
		if (menuVo.getName() != null && !"".equals(menuVo.getName())) {
			sql.append(" and ds.name like :name ");
			paramMap.put("name", "%" + menuVo.getName() + "%");
		}
		if (menuVo.getCreatePerson() != null
				&& !"".equals(menuVo.getCreatePerson())) {
			sql.append(" and ds.create_person like :create_person ");
			paramMap.put("create_person", "%" + menuVo.getCreatePerson() + "%");
		}
		sql.append(" order by  status desc,create_time desc ");
		Page<MedicalField> pages = this.findPageBySQL(sql.toString(), paramMap,
				MedicalField.class, pageNumber, pageSize);

		return pages;
	}

	/**
	 * 通过id进行查找
	 * 
	 * @param parseInt
	 * @return
	 */
	public MedicalField findById(String parseInt) {
		StringBuilder sql = new StringBuilder(
				"select * from medical_field where id=:id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", parseInt);
		List<MedicalField> menus = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(MedicalField.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	/**
	 * 查找实体
	 * 
	 * @param searchEntity
	 * @return
	 */
	public MedicalField findByNotEqId(MedicalField searchEntity) {
		StringBuilder sql = new StringBuilder(
				"select * from medical_field where name=:name and  deleted =0");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", searchEntity.getName());
		List<MedicalField> menus = this.getNamedParameterJdbcTemplate().query(
				sql.toString(), params,
				BeanPropertyRowMapper.newInstance(MedicalField.class));
		if (menus.size() > 0) {
			return menus.get(0);
		}
		return null;
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 * @return
	 */
	public String deleteById(String id) {
		String deleteSql = " update medical_field set deleted=1 where  id = :id ";
		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("id", id);
		this.getNamedParameterJdbcTemplate().update(deleteSql, params2);
		return "删除成功";
	}

}
