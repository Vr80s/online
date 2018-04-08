package com.xczhihui.cloudClass.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.cloudClass.vo.ApplyVo;

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
