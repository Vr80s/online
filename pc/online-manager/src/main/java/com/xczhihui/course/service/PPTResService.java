package com.xczhihui.course.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Files;


public interface PPTResService {

	public Page<Files> findPage(Files searchVo, int currentPage, int pageSize);

	public void save(Files resourcePpt);

	public void updatePPT(Files resourcePpt,String isuploadFile);

	public void deletePPTById(String id);

	public void updateStatus(String id);

	public void deletes(String[] _ids);

	public Page<Files> findCasePage(Files searchVo, int currentPage,
			int pageSize,String domain);

	public String mdFile2Html(String id) throws Exception;


}
