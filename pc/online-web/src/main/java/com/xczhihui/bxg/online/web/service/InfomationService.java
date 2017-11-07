package com.xczhihui.bxg.online.web.service;
import java.util.List;

import com.xczhihui.bxg.online.web.vo.InfomationVo;

/**
 * 动态资讯
 * @author Haicheng Jiang
 *
 */
public interface InfomationService {
	/**
	 * 查询资讯列表
	 * @param sum
	 * @return
	 */
	public List<InfomationVo> list(Integer sum);
	/**
	 * 点击次数加1
	 * @param id 资讯id
	 */
	public void updateClickCount(Integer id);}
