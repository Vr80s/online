package com.xczhihui.operate.service.impl;

import java.util.Date;
import java.util.Map;

import com.xczhihui.operate.dao.AppVersionInfoDao;
import com.xczhihui.operate.service.AppVersionManagerService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.AppVersionInfo;

@Service
public class AppVersionManagerServiceImpl extends OnlineBaseServiceImpl
		implements AppVersionManagerService {

	@Autowired
	private AppVersionInfoDao appVersionInfoDao;

	@Override
	public Page<AppVersionInfo> findAppVersionInfoPage(
			AppVersionInfo appVersionInfo, Integer pageNumber, Integer pageSize) {
		Page<AppVersionInfo> page = appVersionInfoDao.findAppVersionInfoPage(
				appVersionInfo, pageNumber, pageSize);
		return page;
	}

	@Override
	public void addAppVersionInfo(AppVersionInfo appVersionInfo) {
		String sql = "select ifnull(max(sort),0) from app_version_info ";
		int sort = dao.queryForInt(sql, null) + 1;
		DetachedCriteria dc = DetachedCriteria.forClass(AppVersionInfo.class);
		dc.add(Restrictions.eq("version", appVersionInfo.getVersion()));
		dc.add(Restrictions.eq("version", appVersionInfo.getVersion()));
		AppVersionInfo appVersion = dao.findEntity(dc);
		if (appVersion != null && !appVersion.isDelete()) {
			throw new RuntimeException("版本号已存在！");
		}
		appVersionInfo.setStatus(0);
		appVersionInfo.setSort(sort);
		appVersionInfo.setCreateTime(new Date());
		appVersionInfo.setDelete(false);
		dao.save(appVersionInfo);
	}

	@Override
	public void updateAppVersionInfo(AppVersionInfo appVersionInfo) {
		DetachedCriteria dc = DetachedCriteria.forClass(AppVersionInfo.class);
		dc.add(Restrictions.eq("id", appVersionInfo.getId().intValue()));
		AppVersionInfo appVersion = dao.findEntity(dc);
		appVersion.setDescribe(appVersionInfo.getDescribe());
		appVersion.setVersion(appVersionInfo.getVersion());
		appVersion.setMustUpdate(appVersionInfo.isMustUpdate());

		DetachedCriteria dc1 = DetachedCriteria.forClass(AppVersionInfo.class);
		dc1.add(Restrictions.eq("version", appVersionInfo.getVersion()));
		AppVersionInfo appVersion1 = dao.findEntity(dc1);
		if (appVersion1 != null
				&& !appVersion1.isDelete()
				&& appVersion1.getId().intValue() != appVersion.getId()
						.intValue()) {
			throw new RuntimeException("版本号已存在！");
		}
		dao.update(appVersion);
	}

	@Override
	public boolean updateStatus(AppVersionInfo appVersionInfo) {
		String sql = "select count(1) from app_version_info t where t.status = 1 ";
		sql = " UPDATE app_version_info " + " SET status = abs(status - 1) "
				+ " WHERE " + "	id = ? ";
		dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, new Object[] { appVersionInfo.getId() });
		return true;
	}

	@Override
	public void deletes(String[] ids) {
		for (String id : ids) {
			String sql = "delete from app_version_info where id = ? ";
			dao.getNamedParameterJdbcTemplate().getJdbcOperations()
					.update(sql, new Object[] { id });
		}
	}

	@Override
	public void updateSortUp(String id) {
		String sqlPre = "select sort from app_version_info where status = 1 and id = ? ";// 先取出他自己的顺序
		Integer mobileBannerPreSort = dao.queryForInt(sqlPre,
				new Object[] { id });

		String sqlNext = "select sort,id from app_version_info where status = 1 and sort > ? order by sort desc limit 1 ";// 然后取出他相邻的顺序
		Map map = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForMap(sqlNext, new Object[] { mobileBannerPreSort });
		Integer mobileBannerNextSort = Integer.valueOf(map.get("sort")
				.toString());
		String sql = "update app_version_info set sort = ? where id = ? ";
		dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, new Object[] { mobileBannerNextSort, id });
		dao.getNamedParameterJdbcTemplate()
				.getJdbcOperations()
				.update(sql,
						new Object[] { mobileBannerPreSort,
								map.get("id").toString() });
	}

	@Override
	public void updateSortDown(String id) {
		String sqlPre = "select sort from app_version_info where status = 1 and id = ? ";// 先取出他自己的顺序
		Integer mobileBannerPreSort = dao.queryForInt(sqlPre,
				new Object[] { id });

		String sqlNext = "select sort,id from app_version_info where status = 1 and sort < ? order by sort asc limit 1 ";// 然后取出他相邻的顺序
		Map map = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForMap(sqlNext, new Object[] { mobileBannerPreSort });
		Integer mobileBannerNextSort = Integer.valueOf(map.get("sort")
				.toString());
		String sql = "update app_version_info set sort = ? where id = ? ";
		dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, new Object[] { mobileBannerNextSort, id });
		dao.getNamedParameterJdbcTemplate()
				.getJdbcOperations()
				.update(sql,
						new Object[] { mobileBannerPreSort,
								map.get("id").toString() });
	}
}
