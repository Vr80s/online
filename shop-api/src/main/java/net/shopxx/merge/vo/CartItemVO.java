/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: z8a93N01f+KfpVOIVFhuHTUjgxZAEL0W
 */
package net.shopxx.merge.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * Entity - 购物车项
 *
 * @author ixincheng
 * @version 6.1
 */
@Data
public class CartItemVO implements Serializable {

	private Long id;
	/**
	 * 数量
	 */
	private Integer quantity;

    private SkuVO sku;
    
    private Boolean isChecked;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        CartItemVO that = (CartItemVO) o;

        return sku.getId().equals(that.sku.getId());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sku.getId() == null?0:sku.getId().hashCode());
        return result;
    }
}