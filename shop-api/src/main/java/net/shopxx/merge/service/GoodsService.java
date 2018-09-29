package net.shopxx.merge.service;

import net.shopxx.merge.enums.OrderType;
import net.shopxx.merge.vo.GoodsPageParams;
import net.shopxx.merge.vo.ProductQueryParam;
import net.shopxx.merge.vo.ProductVO;

public interface GoodsService {

    /**
     * 查询商品列表
     * <p>Title: list</p>
     * <p>Description: </p>
     *
     * @param goodsPageParams
     * @param orderType
     * @return
     */
    Object list(GoodsPageParams goodsPageParams, OrderType orderType);

    /**
     * 查找商品详情，根据商品id
     * <p>Title: findProductById</p>
     * <p>Description: </p>
     *
     * @param productId
     * @return
     */
    Object findProductById(Long productId);

    Object findIdByCategoryId(Long categoryId);

    /**
     * 查询医师下的商品信息
     *
     * @param productQueryParam 商品列表分页参数
     * @param doctorId          医师id
     * @return
     */
    Object listDoctorProduct(ProductQueryParam productQueryParam, String doctorId);

    /**
     * 获取商品详细信息
     *
     * @param id id
     * @return
     */
    ProductVO getProductById(Long id);
}
