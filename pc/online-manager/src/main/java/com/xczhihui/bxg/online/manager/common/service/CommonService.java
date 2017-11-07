package com.xczhihui.bxg.online.manager.common.service;


import com.xczhihui.bxg.online.manager.common.domain.Common;
import com.xczhihui.bxg.online.manager.common.vo.KeyValVo;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.PageVo;

import java.util.List;

/**
 * 对应OE_COMMON表数据的处理服务
 * 
 * @author yuanziyang
 * @date 2016年4月5日 下午3:39:26
 */
public interface CommonService {

	/**
	 * 新增常量
	 * 
	 * @param common
	 */
	public void addCommon(Common common);

	/**
	 * 修改常量
	 * 
	 * @param common
	 */
	public void updateCommon(Common common);

	/**
	 * 根据id删除常量<br>
	 * 
	 * @param id
	 * @param isLogic
	 */
	public void deleteCommon(String id, boolean isLogic);
	
	/**
	 * 批量删除
	 * @param ids
	 * @param isLogic
	 */
	public void deleteBatchCommon(String[] ids, boolean isLogic);

	/**
	 * 分页查询常量<br>
	 * 
	 * @return
	 */
	public PageVo queryPageCommon(Groups groups, PageVo page);
	
	/**
	 * 分页查询常量分组<br>
	 * 
	 * @return
	 */
	public PageVo queryPageGroup(Groups groups, PageVo page);

	/**
	 * 查询学习方向的数据
	 * 
	 * @return
	 */
	public List<KeyValVo> getAllTargetList();

	/**
	 * 查询所有当前职业的数据
	 * 
	 * @return
	 */
	public List<KeyValVo> getAllOccupationList();
	
	/**
	 * 启用/禁用分组
	 * @param isStartUse
	 * @param groupName
	 */
	public void updateStatusByGroup(boolean isStartUse, String groupName);
	
	/**
	 * 逻辑删除分组
	 * @param groupName
	 */
	public void logicDeleteByGroup(String groupName);
	
	/**
	 * 修改常量分组名
	 * @param newGroupName
	 * @param groupName
	 */
	public void updateGroupNameByGroup(String newGroupName, String groupName);

	/**
	 * 根据类型组查询字典列表
	 * @param group
	 * @return
	 */
	public List<KeyValVo> findListByGroup(String group);
}