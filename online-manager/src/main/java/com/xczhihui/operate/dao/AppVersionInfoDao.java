package com.xczhihui.operate.dao;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.AppVersionInfo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class AppVersionInfoDao extends SimpleHibernateDao {

	public Page<AppVersionInfo> findAppVersionInfoPage(
			AppVersionInfo appVersionInfo, Integer pageNumber, Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"SELECT avi.`id`,avi.`type`,avi.`filename`,avi.`describe`,avi.`down_url`,avi.status,avi.`version`,u.`name` createPerson,avi.is_must_update mustUpdate"
						+ " FROM app_version_info avi JOIN `user` u ON avi.`create_person`=u.`id` where avi.is_delete=0");
		if (appVersionInfo.getStatus() != null
				&& !"".equals(appVersionInfo.getStatus())) {
			sql.append(" and avi.status = :status ");
			paramMap.put("status", appVersionInfo.getStatus());
		}
		if (appVersionInfo.getType() != null
				&& !"".equals(appVersionInfo.getType())) {
			sql.append(" and avi.type = :type ");
			paramMap.put("type", appVersionInfo.getType());
		}
		sql.append(" order by avi.status desc,avi.sort desc ");

		Page<AppVersionInfo> ms = this.findPageBySQL(sql.toString(), paramMap,
				AppVersionInfo.class, pageNumber, pageSize);
		return ms;
	}
}
