package com.xczhihui.common.support.service;

import java.util.List;

import com.xczhihui.common.support.domain.SystemVariate;
import com.xczhihui.common.util.bean.Page;

/**
 * 系统变量，处理系统的数据字典、配置等。
 * 
 * @author liyong
 */
public interface SystemVariateService {

	/**
	 * 获取变量类型，变量名为var_type的变量。这类变量的值会作为其他变量的名字，起到给变量分组的功能。
	 * 
	 * @return
	 */
	public List<SystemVariate> getSystemVariateTypes();

	/**
	 * 添加一个变量
	 * 
	 * @param systemVariate
	 */
	public void addSystemVariate(SystemVariate systemVariate);

	/**
	 * 删除一个变量(软删除)
	 * 
	 * @param id
	 */
	public void deleteSystemVariateLogic(String id);

	/**
	 * 删除一个变量(硬删除)
	 * 
	 * @param id
	 */
	public void deleteSystemVariate(String id);

	/**
	 * 根据ID获取变量
	 * 
	 * @param id
	 * @return
	 */
	public SystemVariate getSystemVariateById(String id);

	/**
	 * 根据变量名获取变量，如果有多个变量取第一个。
	 * 
	 * @param name
	 * @return
	 */
	public SystemVariate getSystemVariateByName(String name);

	/**
	 * 根据变量名取一组变量。
	 * 
	 * @param name
	 * @return
	 */
	public List<SystemVariate> getSystemVariatesByName(String name);

	/**
	 * 查找一个变量的所有子变量。
	 * 
	 * @param parentId
	 * @return
	 */
	public List<SystemVariate> getSystemVariatesByParentId(String parentId);
	
	/**
	 * 查找一个变量的所有子变量。
	 * @param parentValue
	 * @return
	 */
	public List<SystemVariate> getSystemVariatesByParentValue(String parentValue);
	/**
	 * 根据上级value和自己的value，翻译出name
	 * @param parentValue
	 * @param value
	 * @return
	 */
	public String getNameByValue(String parentValue,String value);

	public Page<SystemVariate> getSystemVariatesList(Object object,
			int currentPage, int pageSize);

}
