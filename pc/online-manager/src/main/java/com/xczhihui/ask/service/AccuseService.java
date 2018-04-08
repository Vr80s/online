package com.xczhihui.ask.service;/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.ask.vo.AccuseVo;

/**
 * 投诉管理业务层接口类
 *
 * @author 王高伟
 * @create 2016-10-13 17:55:50
 */
public interface AccuseService {

	/**
	 * 	分页查找问题列表
	 * @param questionVo
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	 public Page<AccuseVo> findAccusePage(AccuseVo accuseVo, Integer pageNumber, Integer pageSize);
	 
	 /**
	  * 检查投诉状态是否被处理 
	  * @param accuseVo
	  * @return
	  */
	 public Boolean checkAccuseStatus(AccuseVo accuseVo);
}
