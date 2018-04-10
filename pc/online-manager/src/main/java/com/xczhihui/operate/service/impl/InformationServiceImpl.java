package com.xczhihui.operate.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.xczhihui.operate.dao.InformationDao;
import com.xczhihui.operate.service.InformationService;
import com.xczhihui.operate.vo.InformationVo;
import com.xczhihui.operate.vo.TypeVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.support.domain.SystemVariate;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Information;

@Service
public class InformationServiceImpl extends OnlineBaseServiceImpl implements
		InformationService {

	@Autowired
	private InformationDao informationDao;

	@Override
	public Page<InformationVo> findInformationPage(InformationVo informationVo,
			Integer pageNumber, Integer pageSize) {
		Page<InformationVo> page = informationDao.findInformationPage(
				informationVo, pageNumber, pageSize);
		return page;
	}

	@Override
	public void addInfo(InformationVo info) throws IllegalAccessException,
			InvocationTargetException {
		// TODO Auto-generated method stub
		info.setSort(informationDao.getMaxSort() + 1);
		Information entity = new Information();
		BeanUtils.copyProperties(entity, info);
		informationDao.save(entity);
	}

	@Override
	public void deletes(String[] ids) {
		// TODO Auto-generated method stub
		for (String id : ids) {
			String hqlPre = "from Information where  isDelete=0 and id = ?";
			Information info = dao.findByHQLOne(hqlPre,
					new Object[] { Integer.valueOf(id) });
			if (info != null) {
				info.setDelete(true);
				dao.update(info);
			}
		}

	}

	@Override
	public String updateStatus(Integer id) {
		// TODO Auto-generated method stub
		String hql = "from Information where  isDelete=0 and id = ?";
		Information info = dao.findByHQLOne(hql, new Object[] { id });
		String sql = "select * from oe_information where is_delete=0 and status=1";
		List<Information> liveInfo = dao.findEntitiesByJdbc(Information.class,
				sql, new HashMap());
		// List<Information>
		// liveInfo=dao.findEntitiesByProperty(Information.class, "status", 1);

		if (info.getStatus() != null && 1 == info.getStatus()) {// 禁用
			info.setEndTime(new Date());
			info.setStatus(0);
		} else {// 启用
			if (liveInfo != null && liveInfo.size() > 7) {
				return "已启用资讯数量不能超过8个！";
			}
			if (info.getStartTime() == null) {// 判断启用时间是否为空，是空的话，新增当前时间
				info.setStartTime(new Date());
			}
			info.setStatus(1);
		}
		dao.update(info);

		return "success";
	}

	@Override
	public void updateSortUp(Integer id) {
		// TODO Auto-generated method stub
		String hqlCur = "from Information where  isDelete=0 and id = ?";
		Information infoCur = dao.findByHQLOne(hqlCur, new Object[] { id });
		Integer infoCurSort = infoCur.getSort();
		if (infoCur != null && 1 == infoCur.getStatus()) {// 启用状态
			String hqlPre = "from Information where sort > (select sort from Information where id= ? )  and isDelete=0 and status=1 order by sort asc";
			Information infoPre = dao.findByHQLOne(hqlPre, new Object[] { id });
			Integer infoPreSort = infoPre.getSort();

			infoCur.setSort(infoPreSort);
			infoPre.setSort(infoCurSort);
			dao.update(infoCur);
			dao.update(infoPre);
		} else if (infoCur != null && 0 == infoCur.getStatus()) {// 禁用状态
			String hqlPre = "from Information where sort > (select sort from Information where id= ? )  and isDelete=0 and status=0 order by sort asc";
			Information infoPre = dao.findByHQLOne(hqlPre, new Object[] { id });
			Integer infoPreSort = infoPre.getSort();
			infoCur.setSort(infoPreSort);
			infoPre.setSort(infoCurSort);
			dao.update(infoCur);
			dao.update(infoPre);
		}

	}

	@Override
	public void updateSortDown(Integer id) {
		// TODO Auto-generated method stub
		String hqlCur = "from Information where  isDelete=0 and id = ?";
		Information infoCur = dao.findByHQLOne(hqlCur, new Object[] { id });
		Integer infoCurSort = infoCur.getSort();
		if (infoCur != null && 1 == infoCur.getStatus()) {// 启用状态
			String hqlPre = "from Information where sort < (select sort from Information where id= ? )  and isDelete=0 and status=1 order by sort desc";
			Information infoPre = dao.findByHQLOne(hqlPre, new Object[] { id });
			Integer infoPreSort = infoPre.getSort();

			infoCur.setSort(infoPreSort);
			infoPre.setSort(infoCurSort);
			dao.update(infoCur);
			dao.update(infoPre);
		} else if (infoCur != null && 0 == infoCur.getStatus()) {// 禁用状态
			String hqlPre = "from Information where sort < (select sort from Information where id= ? )  and isDelete=0 and status=0 order by sort desc";
			Information infoPre = dao.findByHQLOne(hqlPre, new Object[] { id });
			Integer infoPreSort = infoPre.getSort();
			infoCur.setSort(infoPreSort);
			infoPre.setSort(infoCurSort);
			dao.update(infoCur);
			dao.update(infoPre);
		}
	}

	@Override
	public void updateInfo(InformationVo info) {
		// TODO Auto-generated method stub
		String hql = "from Information where  isDelete=0 and id = ?";
		Information temp = dao.findByHQLOne(hql,
				new Object[] { Integer.parseInt(info.getId()) });

		temp.setName(info.getName());
		temp.setInformationtype(info.getInformationtype());
		temp.setIsHot(info.getIsHot());
		temp.setHrefAdress(info.getHrefAdress());
		dao.update(temp);
	}

	@Override
	public void updateTypes(TypeVo vo) {

		// 全部删除
		SystemVariate p = informationDao
				.findByHQLOne("from SystemVariate where value='informationType' and (parentId is null or parentId='') ");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("parent_id", p.getId());
		informationDao.getNamedParameterJdbcTemplate().update(
				"delete from system_variate where parent_id=:parent_id ",
				paramMap);

		// 全部新增
		List<TypeVo> vos = vo.getVos();
		int display_order = 0;

		Set<String> names = new HashSet<String>();

		for (TypeVo t : vos) {
			if (t != null && StringUtils.hasText(t.getName())) {
				names.add(t.getName().trim());
				paramMap.put("id", UUID.randomUUID().toString()
						.replace("-", ""));
				paramMap.put("name", t.getName());
				paramMap.put("value", t.getValue());
				paramMap.put("display_order", display_order);
				informationDao
						.getNamedParameterJdbcTemplate()
						.update("insert into system_variate (id,is_delete,display_order,name,parent_id,value)"
								+ " values (:id,false,:display_order,:name,:parent_id,:value) ",
								paramMap);
				display_order++;
			}
		}

		if (names.size() != vos.size()) {
			throw new IllegalArgumentException("分类名称不能重复！");
		}
	}
}
