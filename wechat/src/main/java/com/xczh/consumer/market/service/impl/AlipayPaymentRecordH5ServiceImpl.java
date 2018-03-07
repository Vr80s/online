package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.AlipayPaymentRecordH5;
import com.xczh.consumer.market.dao.AlipayPaymentRecordH5Mapper;
import com.xczh.consumer.market.service.AlipayPaymentRecordH5Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * @author
 * @create 2017-08-18 17:13
 **/
@Service
public class AlipayPaymentRecordH5ServiceImpl implements AlipayPaymentRecordH5Service {

    @Autowired
    private AlipayPaymentRecordH5Mapper alipayPaymentRecordH5Mapper;

    @Override
    public void insert(AlipayPaymentRecordH5 alipayPaymentRecordH5) {
        try {
            alipayPaymentRecordH5Mapper.insert(alipayPaymentRecordH5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
    
    @Override
    public AlipayPaymentRecordH5 queryAlipayPaymentRecordH5ByOutTradeNo(String outTradeNo) {
        try {
        	AlipayPaymentRecordH5 aprh = alipayPaymentRecordH5Mapper.queryAlipayPaymentRecordH5ByOutTradeNo(outTradeNo);
            return aprh;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
