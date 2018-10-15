package net.shopxx.merge.service;

import com.xczhihui.common.util.bean.ShareInfoVo;

import net.shopxx.merge.enums.OrderType;
import net.shopxx.merge.vo.GoodsPageParams;
import net.shopxx.merge.vo.ProductQueryParam;
import net.shopxx.merge.vo.ProductVO;

public interface GoodsService {

    /**
     * <p>Title: list</p>
     * <p>Description:查询商品列表 </p>
     *
     * @param goodsPageParams 条件参数
     * @param orderType	排序类型
     * @return list
     */
    Object list(GoodsPageParams goodsPageParams, OrderType orderType);

    /**
     * <p>Title: findProductById</p>
     * <p>Description: 查找商品详情</p>
     *
     * @param productId 商品id
     * @return object
     */
    Object findProductById(Long productId);

    /**
     * 
     * <p>Title: findIdByCategoryId</p>  
     * <p>Description: 查询分类下所属的商品id和商品name</p>  
     * @param categoryId  商品分类id
     * @return list
     */
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

    /**
     * <p>Title: findIdByShareInfo</p>
     * <p>Description: 查询商品分享出去的数据</p>
     *
     * @param shareId 商品id
     * @return
     */
    ShareInfoVo findIdByShareInfo(String shareId);

    /**
     * pv 与 uv的统计
     * @param userId userId
     * @param id id
     */
    void updateClick(String userId, Long id);

	/**  
	 * <p>Title: modifyAddDoctorRecommends</p>  
	 * <p>Description: </p>  
	 * @param id  
	 */ 
	void modifyAddDoctorRecommends(Long id);
}
