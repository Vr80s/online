package com.xczhihui.cloudClass.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.common.dao.HibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Lecturer;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.cloudClass.vo.LecturerVo;

/**
 * 云课堂课程管理DAO
 * 
 * @author yxd
 *
 */
@Repository("lecturerDao")
public class LecturerDao extends HibernateDao<Lecturer> {

	@Autowired
	CloudClassMenuDao MenuDao;

	public Page<LecturerVo> findLecturerPage(LecturerVo lecturerVo,
			int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"select * from oe_lecturer where 1=1 and is_delete=0 ");
		if (lecturerVo.getName() != null) {
			paramMap.put("name", "%" + lecturerVo.getName() + "%");
			sql.append("and name like :name ");
		}
		if (lecturerVo.getMenuId() != null) {
			paramMap.put("menuId", lecturerVo.getMenuId());
			sql.append("and menu_id = :menuId ");
		}
		if (lecturerVo.getRoleType() != null) {
			paramMap.put("roleType", lecturerVo.getRoleType());
			sql.append("and role_type = :roleType ");
		}

		sql.append(" order by create_time desc ");
		Page<LecturerVo> result = this.findPageBySQL(sql.toString(), paramMap,
				LecturerVo.class, pageNumber, pageSize);

		for (LecturerVo lecturer : result.getItems()) {
			Menu menu = MenuDao.findById(lecturer.getMenuId());
			if (menu != null) {
				lecturer.setMenuName(menu.getName());
			}
		}

		String sqlIsUsed = "select count(1) as gradeCount from grade_r_lecturer where lecturer_id =:id ";
		for (LecturerVo temp : result.getItems()) {
			paramMap.put("id", temp.getId());
			List<LecturerVo> res = this.findEntitiesByJdbc(LecturerVo.class,
					sqlIsUsed, paramMap);
			if (res.get(0).getGradeCount() > 0) {
				temp.setGradeCount(res.get(0).getGradeCount());
			} else {
				temp.setGradeCount(0);
			}
		}

		return result;
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 */
	public void deleteById(int id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// String sqlIsUsed= " SELECT " +
		// "	count(1) as gradeCount " +
		// " FROM " +
		// "	grade_r_lecturer t, " +
		// "	oe_grade t2 " +
		// " WHERE " +
		// "	t.grade_id = t2.id " +
		// " AND t2.is_delete = 0 " +
		// " AND t.is_delete = 0 " +
		// " AND t.lecturer_id =:id ";
		String sqlIsUsed = " SELECT " + "	count(1) as gradeCount " + " FROM "
				+ "	course_r_lecturer t, " + "	oe_course t2 " + " WHERE "
				+ "	t.course_id = t2.id " + " AND t.is_delete = 0 "
				+ " AND t2.is_delete = 0 " + " and t.lecturer_id = :id ";
		paramMap.put("id", id);
		List<LecturerVo> res = this.findEntitiesByJdbc(LecturerVo.class,
				sqlIsUsed, paramMap);
		if (res.get(0).getGradeCount() > 0) {
			throw new RuntimeException("该老师已被使用，不能被删除！");
		}

		Lecturer entity = this.get(id, Lecturer.class);
		entity.setDelete(true);
		this.update(entity);

	}

}
