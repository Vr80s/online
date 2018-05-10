package com.xczhihui.course.service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.vo.ApplyVo;

public interface ApplyService {

	/**
	 * 根据条件分页获取角色信息。
	 *
	 * @return
	 */
	public Page<ApplyVo> findPage(ApplyVo courseVo, int pageNumber, int pageSize);

	public void deleteStudents(String[] ids);

	public void updateChangePayment(ApplyVo vo);
}
