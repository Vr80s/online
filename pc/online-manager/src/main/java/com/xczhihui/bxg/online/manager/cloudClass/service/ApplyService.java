package com.xczhihui.bxg.online.manager.cloudClass.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.common.domain.TeachMethod;
import com.xczhihui.bxg.online.manager.cloudClass.vo.*;

import java.util.List;
import java.util.Map;

public interface ApplyService {

	/**
	 * 根据条件分页获取角色信息。
	 *
	 * @return
	 */
	public Page<ApplyVo> findPage(ApplyVo courseVo,  int pageNumber, int pageSize);

	public void deleteStudents(String[] ids);

	public void updateChangePayment(ApplyVo vo);
}
