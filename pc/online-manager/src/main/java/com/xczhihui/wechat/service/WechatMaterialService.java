package com.xczhihui.wechat.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.WechatMaterial;
import com.xczhihui.bxg.online.common.domain.WechatMediaManager;

public interface WechatMaterialService {
	
	
	public void addWechatMaterial(WechatMaterial WechatMaterial);


	public void updateWechatMaterial(WechatMaterial WechatMaterial) throws IllegalAccessException, InvocationTargetException;

	public WechatMaterial getWechatMaterialById(Integer WechatMaterialId);

	
	public Page<WechatMaterial> findWechatMaterialPage(WechatMaterial WechatMaterial, int pageNumber,
			int pageSize);


	public void updateStatus(Integer id);


	public void deleteWechatMaterialById(Integer id);


	public void updateSortUp(Integer id);


	public void updateSortDown(Integer id);


	public void deletes(String[] ids);

	List<WechatMaterial> getWechatMateriallist(String search);

	public void updateBrokerage(String ids, String brokerage);
	
	
	public void addWechatMediaManager(WechatMediaManager wechatMediaManager);

}
