/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: A10LnsGPkWB/avTpdb2o5chkgAiGF0Ph
 */
package net.shopxx.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.shopxx.entity.Business;

/**
 * Dao - 商家
 *
 * @author ixincheng
 * @version 6.1
 */
public interface BusinessDao extends BaseDao<Business, Long> {

    /**
     * 通过名称查找商家
     *
     * @param keyword 关键词
     * @param count   数量
     * @return 商家
     */
    List<Business> search(String keyword, Integer count);

    /**
     * 查询商家数量
     *
     * @param beginDate 起始日期
     * @param endDate   结束日期
     * @return 商家数量
     */
    Long count(Date beginDate, Date endDate);

    /**
     * 查询总余额
     *
     * @return 总余额
     */
    BigDecimal totalBalance();

    /**
     * 查询冻结总额
     *
     * @return 冻结总额
     */
    BigDecimal frozenTotalAmount();

    /**
     * 通过医师查询用户名
     *
     * @param doctorId doctorId
     * @return
     */
    List<String> findUsernameByDoctorId(String doctorId);

	/**  
	 * <p>Title: findBusinessByDoctorId</p>  
	 * <p>Description: </p>  
	 * @param doctorId
	 * @return  
	 */ 
	List<Business> findBusinessByDoctorId(String doctorId);
}