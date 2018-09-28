/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: DpHYO899mdl4qqA3kd/CRySFkEC4Nm5U
 */
package net.shopxx.merge.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * Entity - SKU
 *
 * @author SHOP++ Team
 * @version 6.1
 */
@Data
public class SkuVO implements Serializable {

    private static final long serialVersionUID = 2167830430439593293L;

    private Long id;

    /**
     * 编号
     */
    private String sn;

    /**
     * 销售价
     */
    private BigDecimal price;

    /**
     * 成本价
     */
    private BigDecimal cost;

    /**
     * 市场价
     */
    private BigDecimal marketPrice;

    /**
     * 最大佣金
     */
    private BigDecimal maxCommission;

    /**
     * 赠送积分
     */
    private Long rewardPoint;

    /**
     * 兑换积分
     */
    private Long exchangePoint;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 已分配库存
     */
    private Integer allocatedStock;

    /**
     * 是否默认
     */
    private Boolean isDefault;

    /**
     * 商品信息
     */
    private ProductVO product;

    private String specifications;

    private String image;

    private String name;
    
    private List<Integer> specificationValueIds;
    
    private boolean IsOutOfStock;

    private Long productId;
}