package com.xczhihui.bxg.online.manager.fcode.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Grade;
import com.xczhihui.bxg.online.manager.fcode.vo.FcodeVo;

public interface FcodeService {

	public Page<FcodeVo> findPage(FcodeVo searchVo, int currentPage, int pageSize);

	public void addFcodeRule(FcodeVo fcodeVo) throws DataAccessException, ParseException;

	public void updateFcodeRule(FcodeVo fcodeVo);

	public Page<FcodeVo> findFcodeDetailPage(FcodeVo searchVo, int currentPage, int pageSize);
	
	public List<FcodeVo> getFcodeBylotNo(String lotNo, Integer i);

}
