package com.xczhihui.bxg.online.manager.cloudClass.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.cloudClass.vo.StudentManagerVo;
/**
 * 学员管理业务层
 * 
 *@author yxd
 */
public interface StudentManagerService {
	/**
	 * 学员管理分页
	 * 
	 *@param StudentManagerVo searchVo, int currentPage,int pageSize 
	 *return 
	 */
	public Page<StudentManagerVo> findstudentsInfoPage(StudentManagerVo searchVo, int currentPage,int pageSize);

}
