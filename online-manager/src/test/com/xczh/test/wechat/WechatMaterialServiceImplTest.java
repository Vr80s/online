package com.xczh.test.wechat;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.BaseJunit4Test;

import com.xczhihui.bxg.online.common.domain.WechatMaterial;
import com.xczhihui.bxg.online.common.domain.WechatMediaManager;
import com.xczhihui.bxg.online.manager.wechat.service.WechatMaterialService;

/**
 * 医馆入驻测试类
 */
public class WechatMaterialServiceImplTest extends BaseJunit4Test {


	@Autowired
	private WechatMaterialService wechatMaterialService;
	
    /**
     * 测试qq的保存信息
     */
    @Test
    public void testSaveQQInfo(){
    	System.out.println("=====================");
    	WechatMaterial wm = new WechatMaterial();
    	wm.setId(1);
    	wm.setTitle("yangxuan");
    	wm.setThumbMediaId("yangxuan");
    	wm.setAuthor("xuanyang");
    	wm.setDigest("yangxuan");
    	wm.setShowCoverPic(false);
    	wm.setContentSourceUrl("yangxuan");
    	wm.setMaterialType(1);
    	wm.setUpdateTime(new Date());
    	wm.setAssociatMenu("123");
    	wm.setContent("123");
    	wm.setCreateTime(new Date());
    	wm.setDelete(true);	
    	//wechatMaterialDao.save(wm);
		wechatMaterialService.addWechatMaterial(wm);
    	String mid = "1234567890";
    	//wechatMaterialService
    	WechatMediaManager wmm = new WechatMediaManager();
    	wmm.setId(11);
    	wmm.setCreateTime(new Date());
    	wmm.setDelete(true);
    	wmm.setMediaId(mid);
    	wmm.setUrl("==============");
    	wmm.setMediaType(1);
    	wechatMaterialService.addWechatMediaManager(wmm);
    	System.out.println("=====================");
    }
}