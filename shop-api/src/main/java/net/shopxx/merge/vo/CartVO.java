/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: b5xT3fqM34FYc4BH4qwnUHidpCOS1sZj
 */
package net.shopxx.merge.vo;

import java.io.Serializable;
import java.util.*;

import lombok.Data;

/**
 * Entity - 购物车
 *
 * @author SHOP++ Team
 * @version 6.1
 */
@Data
public class CartVO implements Serializable{

	private Long id;

    /**
     * 密钥
     */
    private String key;

    /**
     * 过期时间
     */
    private Date expire;

    /**
     * 购物车项
     */
    private Set<CartItemVO> cartItems = new HashSet<CartItemVO>();

    private List<StoreCartItemVO> storeCartItems = new ArrayList<StoreCartItemVO>();

    private Boolean isChecked;
}