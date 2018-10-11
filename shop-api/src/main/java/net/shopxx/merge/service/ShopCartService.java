package net.shopxx.merge.service;

import java.util.List;

import net.shopxx.merge.vo.CartVO;

/**
 * 购物车服务
 *
 * @author hejiwei
 */
public interface ShopCartService {

    CartVO getCart(String ipandatcmUserId);

//    CartVO create(String ipandatcmUserId);

    /**
     * 添加购物车SKU
     *
     * @param ipandatcmUserId 熊猫中医用户id
     * @param skuId           skuId
     * @param quantity        数量
     */
    void add(String ipandatcmUserId, Long skuId, int quantity);

    /**
     * 修改购物车SKU
     *
     * @param ipandatcmUserId 熊猫中医用户id
     * @param skuId           skuId
     * @param quantity        数量
     */
    Long modify(String ipandatcmUserId, Long skuId, int quantity);

    /**
     * 移除购物车SKU
     *
     * @param ipandatcmUserId 熊猫中医用户id
     * @param skuIds          SKU
     */
    void remove(String ipandatcmUserId, List<Long> skuIds);

    /**
     * 清空购物车
     *
     * @param ipandatcmUserId 熊猫中医用户id
     */
    void clear(String ipandatcmUserId);

    /**
     * <p>Title: getCartQuantity</p>
     * <p>Description: 获取用户购物车数量</p>
     *
     * @param accountId 熊猫平台账户id
     * @return
     */
    Integer getCartQuantity(String accountId);

    /**
     * 校验所勾选的购物车的库存有效性
     *
     * @param cartItemIds cartItemIds
     */
    boolean checkInventory(List<Long> cartItemIds);

//    /**
//     * 合并购物车
//     *
//     * @param cart
//     *            购物车
//     */
//    void merge(Cart cart);

//    /**
//     * 删除过期购物车
//     */
//    void deleteExpired();
}
