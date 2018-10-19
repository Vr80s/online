/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: NKTq9GxRVdN5o2+dHeZjafoWH6EWkjKQ
 */
package net.shopxx.dao.impl;

import net.shopxx.dao.OrderItemDeleteDao;
import net.shopxx.dao.SkuDao;
import net.shopxx.entity.OrderItemDelete;
import net.shopxx.entity.Product;
import net.shopxx.entity.Sku;
import net.shopxx.merge.vo.OrderItemVO;
import net.shopxx.merge.vo.ProductVO;
import net.shopxx.merge.vo.SkuVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.util.bean.ShareInfoVo;

/**
 * Dao - 订单
 * 
 * @author ixincheng
 * @version 6.1
 */
@Repository
public class OrderItemDeleteDaoImpl extends BaseDaoImpl<OrderItemDelete, Long> implements OrderItemDeleteDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderItemDeleteDaoImpl.class);
	
    @Autowired
    private SkuDao skuDao;
    
	@Override
	public List<OrderItemVO> findByOrders(Long orderId) {
		
        String jpq = " SELECT oid.id,oid.sn,oid.thumbnail,oid.price,oid.quantity,oid.name,oid.sku_id  FROM orderitem_delete as oid "
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

            if(cells[6].toString()!=null) {
            	Sku sku = skuDao.find(Long.valueOf(cells[6].toString()));
            	if(sku!=null) {
            		SkuVO skuVo = new SkuVO();
    				ProductVO productvo = new ProductVO();
    				BeanUtils.copyProperties(sku,skuVo);
    				
    				List<String> specification = sku.getSpecifications();
					if(specification!=null && specification.size()>0){
						String citiesCommaSeparated = String.join(";", specification);
						skuVo.setSpecifications(citiesCommaSeparated);
					}
    				
    				productvo.setId(sku.getProduct().getId());
    				productvo.setIsMarketable(sku.getProduct().getIsMarketable());
    				productvo.setIsOutOfStock(sku.getProduct().getIsOutOfStock());
    				productvo.setIsActive(sku.getProduct().getIsActive());
    				sku.setId(sku.getId());
    				oid.setSku(skuVo);
    				oid.getSku().setProduct(productvo);
            	}
            }
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