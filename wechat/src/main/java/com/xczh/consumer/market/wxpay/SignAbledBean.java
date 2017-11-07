package com.xczh.consumer.market.wxpay;

import com.xczh.consumer.market.wxpay.util.BeanHelper;

public class SignAbledBean {
	
	public Object getAttributeValue(String name){
		return BeanHelper.getProperty(this, name);
	}
	
}
