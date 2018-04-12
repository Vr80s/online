package com.xczhihui.headline.service;

/**
 * Created by admin on 2016/8/29.
 */

import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.headline.vo.TagVo;

/**
 * 标签业务层接口类
 *
 * @author 王高伟
 * @create 2016-10-16 16:25:52
 */
public interface BxsTagService {

	/**
	 * 
	 * @return
	 */
	public List<TagVo> findTagVo(TagVo tagVo);

	public Page<TagVo> findPage(TagVo searchVo, int currentPage, int pageSize);

	public String addTag(TagVo tagVo);

	public String deletes(String[] _ids);

	public void updateStatus(String id);

	public void updateTag(TagVo tagVo);
}
