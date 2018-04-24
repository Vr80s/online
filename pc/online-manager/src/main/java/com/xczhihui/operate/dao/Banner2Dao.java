package com.xczhihui.operate.dao;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.operate.vo.Banner2Vo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class Banner2Dao extends SimpleHibernateDao {

	public Page<Banner2Vo> findBanner2Page(Banner2Vo banner2Vo, int pageNumber,
			int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(" SELECT " + "	ob.id, "
				+ "	ob.create_person, " + "	ob.description, "
				+ "	ou. NAME createPersonName, " + "	ob.create_time, "
				+ "	ob.is_delete, " + "	ob.img_path, " + "	ob.img_href, "
				+ "	ob.sort, " + "	ob.start_time, " + "	ob.end_time, "
				+ "	ob. status, " + "	ob.click_count " + " FROM "
				+ "	oe_banner2 ob " + " left JOIN user ou ON ( "
				+ " 	ob.create_person = ou.login_name "
				+ " ) where ob.is_delete = 0");

		if (banner2Vo.getStatus() != null) {
			sql.append(" and ob.status = :status");
			paramMap.put("status", banner2Vo.getStatus());
		}

		if (banner2Vo.getDescription() != null
				&& banner2Vo.getDescription() != "") {
			sql.append(" and ob.description like :description");
			paramMap.put("description", "%" + banner2Vo.getDescription() + "%");
		}

		if (banner2Vo.getType() != null) {
			sql.append(" and ob.type = :type");
			paramMap.put("type", banner2Vo.getType());
		}

		sql.append(" order by  ob. status desc,ob.sort asc ,ob.create_time desc");
		Page<Banner2Vo> ms = this.findPageBySQL(sql.toString(), paramMap,
				Banner2Vo.class, pageNumber, pageSize);
		return ms;
	}
}
