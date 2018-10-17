/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: NKTq9GxRVdN5o2+dHeZjafoWH6EWkjKQ
 */
package net.shopxx.dao.impl;

import net.shopxx.dao.OrderItemDeleteDao;
import net.shopxx.entity.OrderItemDelete;
import net.shopxx.merge.vo.OrderItemVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.util.bean.ShareInfoVo;

/**
 * Dao - 订单
 * 
 * @author SHOP++ Team
 * @version 6.1
 */
@Repository
public class OrderItemDeleteDaoImpl extends BaseDaoImpl<OrderItemDelete, Long> implements OrderItemDeleteDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderItemDeleteDaoImpl.class);
	
	@Override
	public List<OrderItemVO> findByOrders(Long orderId) {
		
        String jpq = " SELECT oid.id,oid.sn,oid.thumbnail,oid.price,oid.quantity,oid.name  FROM orderitem_delete as oid "
        		+ " WHERE oid.orders =:orderId";
        
        List rows = entityManager.createNativeQuery(jpq).setParameter("orderId", orderId).getResultList();
        
        List<OrderItemVO> listOid = new ArrayList<OrderItemVO>();
        
        for (Object row : rows) {
        	OrderItemVO oid = new OrderItemVO();
        	Object[] cells = (Object[]) row;
            LOGGER.info("cells " + cells.toString());
            
            oid.setId(cells[0]!=null ? Long.valueOf(cells[0].toString()) : 0L);
            oid.setSn(cells[1]!=null ? cells[1].toString() : null);
            oid.setThumbnail(cells[2]!=null ? cells[2].toString() : null);
            oid.setPrice(cells[3] != null ? (BigDecimal) cells[3] : BigDecimal.ZERO);
            oid.setQuantity(cells[4]!=null ?  Integer.valueOf(cells[4].toString()) : null);
            oid.setName(cells[5]!=null ? cells[5].toString() : null);

            listOid.add(oid);
        }
		return listOid;
	}
	
	@Override
	public List<Long> findOrderIds(String productName) {
		
        String jpq = " SELECT oid.orders  FROM orderitem_delete as oid "
        		+ " WHERE oid.name like :productName";
        
        List rows = entityManager.createNativeQuery(jpq).setParameter("productName", "%"+productName+"%").getResultList();
        
        List<Long> listOid = new ArrayList<Long>();
        for (Object row : rows) {
            LOGGER.info("cells " + row.toString());
            listOid.add(Long.valueOf(row.toString()));
        }
		return listOid;
	}
	


}