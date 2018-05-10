package com.xczhihui.operate.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.support.service.SystemVariateService;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.operate.vo.InformationVo;

@Repository
public class InformationDao extends SimpleHibernateDao {

	@Autowired
	SystemVariateService systemVariateService;

	public Page<InformationVo> findInformationPage(InformationVo informationVo,
			int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"select * from oe_information where is_delete = 0 ");
		if (informationVo.getName() != null
				&& !"".equals(informationVo.getName())) {
			sql.append(" and name like :name ");
			paramMap.put("name", "%" + informationVo.getName() + "%");
		}

		if (informationVo.getInformationtype() != null
				&& !"".equals(informationVo.getInformationtype())) {
			sql.append(" and informationType = :informationType ");
			paramMap.put("informationType", informationVo.getInformationtype());
		}

		if (informationVo.getStatus() != null
				&& !"".equals(informationVo.getStatus())) {
			sql.append(" and status = :status ");
			paramMap.put("status", informationVo.getStatus());
		}

		sql.append(" order by status desc,sort desc");
		Page<InformationVo> page = this.findPageBySQL(sql.toString(), paramMap,
				InformationVo.class, pageNumber, pageSize);
		boolean flag = true;
		for (int i = 0; i < page.getItems().size(); i++) {
			InformationVo info = page.getItems().get(i);
			String str = systemVariateService.getNameByValue("informationType",
					info.getInformationtype());
			if (str == null || "".equals(str)) {
				str = "暂无分类";
			}
			info.setInformationtypeName(str);
			if (0 == info.getStatus() && flag && i > 0) {
				flag = false;
				page.getItems().get(i - 1).setLast("yes");
			}
		}
		return page;
	}

	public Integer getMaxSort() {
		String sql = " select ifnull(max(sort),1) as maxSort from oe_information where is_delete=0  ";
		Map<String, Object> result = this.getNamedParameterJdbcTemplate()
				.queryForMap(sql, new HashMap<String, Object>());
		return Integer.parseInt(result.get("maxSort") != null ? String
				.valueOf(result.get("maxSort")) : "1");
	}
}
