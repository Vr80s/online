package com.xczhihui.operate.service;

import java.lang.reflect.InvocationTargetException;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.operate.vo.InformationVo;
import com.xczhihui.operate.vo.TypeVo;

public interface InformationService {

	public Page<InformationVo> findInformationPage(InformationVo informationVo,
			Integer pageNumber, Integer pageSize);

	public void addInfo(InformationVo info) throws IllegalAccessException,
			InvocationTargetException;

	public void deletes(String[] _ids);

	public String updateStatus(Integer id);

	public void updateSortUp(Integer id);

	public void updateSortDown(Integer id);

	public void updateInfo(InformationVo info);

	public void updateTypes(TypeVo vo);
}
