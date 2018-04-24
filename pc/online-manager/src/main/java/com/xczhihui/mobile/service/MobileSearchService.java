package com.xczhihui.mobile.service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.mobile.vo.MobileSearchVo;

public interface MobileSearchService {

	public Page<MobileSearchVo> findMobileSearchPage(
			MobileSearchVo mobileSearchVo, int currentPage, int pageSize);

	public MobileSearchVo findMobileSearchByNameAndByType(String name,
			Integer type);

	public int getMaxSort();

	public void save(MobileSearchVo mobileSearchVo);

	MobileSearchVo findById(String parseInt);

	void update(MobileSearchVo mobileSearchVo);

	public boolean exists(MobileSearchVo existsEntity);

	public String deletes(String[] _ids);

	public void updateStatus(String id);

	public void updateSortUp(Integer id);

	public void updateSortDown(Integer id);

}
