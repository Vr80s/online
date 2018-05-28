package com.xczhihui.common.support.service;

import java.util.List;

import com.xczhihui.common.util.bean.DictionaryVo;

/**
 * 数据字典中心
 * @author Haicheng Jiang
 */
public interface DictionaryService {
	/**
	 * 根据父级value获得数据列表
	 * @param parentValue
	 * @return
	 */
	public List<DictionaryVo> list(String parentValue);
	/**
	 * 根据value获得name
	 * @param parentValue 父级value
	 * @param value
	 * @return
	 */
	public String name(String parentValue,String value);
}
