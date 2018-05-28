package com.xczhihui.system.service;

import java.util.List;
import java.util.Map;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Otherlink;
import com.xczhihui.common.vo.KeyValVo;
import com.xczhihui.system.vo.OtherLinkVo;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.PageVo;

public interface LinkService {

	/**
	 * 查询一页数据
	 * 
	 * @param groups
	 * @param page
	 * @return
	 */
	public PageVo findPageLink(Groups groups, PageVo page);

	/**
	 * 添加
	 * 
	 * @param link
	 */
	public void addLink(Otherlink link);

	/**
	 * 根据id伪删除数据
	 * 
	 * @param vid
	 */
	public void deleteById(Integer id);

	/**
	 * 根据id多条删除
	 * 
	 * @param ids
	 */
	public void deleteBatch(String... ids);

	/**
	 * 获取一条数据
	 * 
	 * @param id
	 * @return
	 */
	public Otherlink getLinkById(Integer id);

	/**
	 * 修改
	 */
	public void update(Otherlink link);

	/**
	 * 获取位置列表
	 * 
	 * @return
	 */
	public List<KeyValVo> getSortlist();

	/**
	 * 根据id修改状态
	 * 
	 * @param id
	 */
	public void updateStatus(Integer id);

	/**
	 * 上移
	 * 
	 * @param id
	 */
	public void updateUpMove(Integer id);

	/**
	 * 下移
	 * 
	 * @param id
	 */
	public void updateDownMove(Integer id);

	/**
	 * 分页查询Otherlink（带条件）
	 * 
	 * @param paramMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<OtherLinkVo> findOtherLinkPage(Map<String, Object> paramMap,
			int pageNumber, int pageSize);

	/**
	 * 验证位置是否重复
	 * 
	 * @param sort
	 * @param id
	 * @param type
	 * @return
	 */
	public boolean findSort(Integer sort, Integer id);

}
