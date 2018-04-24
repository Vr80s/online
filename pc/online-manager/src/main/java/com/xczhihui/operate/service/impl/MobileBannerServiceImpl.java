package com.xczhihui.operate.service.impl;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.operate.dao.MobileBannerDao;
import com.xczhihui.operate.vo.MobileBannerVo;
import com.xczhihui.operate.service.MobileBannerService;

@Service
public class MobileBannerServiceImpl extends OnlineBaseServiceImpl implements
		MobileBannerService {

	@Autowired
	private MobileBannerDao mobileBannerDao;

	@Override
	public Page<MobileBannerVo> findMobileBannerPage(
			MobileBannerVo mobileBannerVo, Integer pageNumber, Integer pageSize) {
		Page<MobileBannerVo> page = mobileBannerDao.findMobileBannerPage(
				mobileBannerVo, pageNumber, pageSize);
		return page;
	}

	@Override
	public void addMobileBanner(MobileBannerVo mobileBannerVo) {
		String sql = "select ifnull(min(seq),0) from oe_course_mobile_banner ";
		int sort = dao.queryForInt(sql, null) - 1;
		mobileBannerVo.setClickSum(0);
		mobileBannerVo.setStatus(0);
		;
		sql = "INSERT INTO oe_course_mobile_banner  ( id ,  name ,  url ,  click_sum ,  "
				+ "create_person ,  create_time ,  status ,  seq ,  img_path , link_type  ,banner_type ) "
				+ "VALUES (REPLACE(UUID(),'-',''), ?, ?, ?, ?, now(), ?, ?, ?,?,?)";
		dao.getNamedParameterJdbcTemplate()
				.getJdbcOperations()
				.update(sql,
						new Object[] { mobileBannerVo.getName(),
								mobileBannerVo.getUrl(),
								mobileBannerVo.getClickSum(),
								mobileBannerVo.getCreatePerson(),
								mobileBannerVo.getStatus(), sort,
								mobileBannerVo.getImgPath(),
								mobileBannerVo.getLinkType(),
								mobileBannerVo.getBannerType() });
	}

	@Override
	public void updateMobileBanner(MobileBannerVo mobileBannerVo) {
		String sql = "UPDATE oe_course_mobile_banner "
				+ "SET name = ?, url = ?, img_path = ? ,link_type = ?  "
				+ "WHERE " + "	id = ? ";
		dao.getNamedParameterJdbcTemplate()
				.getJdbcOperations()
				.update(sql,
						new Object[] { mobileBannerVo.getName(),
								mobileBannerVo.getUrl(),
								mobileBannerVo.getImgPath(),
								mobileBannerVo.getLinkType(),
								mobileBannerVo.getId() });
	}

	@Override
	public boolean updateStatus(MobileBannerVo mobileBannerVo) {
		String sql = "select count(1) from oe_course_mobile_banner t where t.status = 1 ";
		/*
		 * if(dao.queryForInt(sql) >= 5){ throw new
		 * RuntimeException("最多启用五个banner!"); }
		 */
		sql = " UPDATE oe_course_mobile_banner "
				+ " SET status = abs(status - 1) " + " WHERE " + "	id = ? ";
		dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, new Object[] { mobileBannerVo.getId() });
		return true;
	}

	@Override
	public void deletes(String[] ids) {
		for (String id : ids) {
			String sql = "delete from oe_course_mobile_banner where id = ? ";
			dao.getNamedParameterJdbcTemplate().getJdbcOperations()
					.update(sql, new Object[] { id });
		}
	}

	@Override
	public void updateSortUp(String id) {
		String sqlPre = "select seq from oe_course_mobile_banner where status = 1 and id = ? ";// 先取出他自己的顺序
		Integer mobileBannerPreSort = dao.queryForInt(sqlPre,
				new Object[] { id });

		String sqlNext = "select seq,id from oe_course_mobile_banner where status = 1 and seq > ? order by seq desc limit 1 ";// 然后取出他相邻的顺序
		Map map = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForMap(sqlNext, new Object[] { mobileBannerPreSort });
		Integer mobileBannerNextSort = Integer.valueOf(map.get("seq")
				.toString());
		String sql = "update oe_course_mobile_banner set seq = ? where id = ? ";
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
		String sqlPre = "select seq from oe_course_mobile_banner where status = 1 and id = ? ";// 先取出他自己的顺序
		Integer mobileBannerPreSort = dao.queryForInt(sqlPre,
				new Object[] { id });

		String sqlNext = "select seq,id from oe_course_mobile_banner where status = 1 and seq < ? order by seq asc limit 1 ";// 然后取出他相邻的顺序
		Map map = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForMap(sqlNext, new Object[] { mobileBannerPreSort });
		Integer mobileBannerNextSort = Integer.valueOf(map.get("seq")
				.toString());
		String sql = "update oe_course_mobile_banner set seq = ? where id = ? ";
		dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, new Object[] { mobileBannerNextSort, id });
		dao.getNamedParameterJdbcTemplate()
				.getJdbcOperations()
				.update(sql,
						new Object[] { mobileBannerPreSort,
								map.get("id").toString() });
	}
}
