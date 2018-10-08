package net.shopxx.merge.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.shopxx.dao.ProductCategoryDao;
import net.shopxx.entity.ProductCategory;
import net.shopxx.merge.service.ShopCategoryService;
import net.shopxx.merge.vo.ProductCategoryVO;

/**
 * 熊猫中医与shop用户关系
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);

	@Inject
	private ProductCategoryDao productCategoryDao;

	@Override
	@Transactional
	public Object list() {

		List<ProductCategory> productCategorys = productCategoryDao.findRoots(null);

		List<ProductCategoryVO> childrenVOs = new ArrayList<ProductCategoryVO>();
		
		for (ProductCategory productCategory : productCategorys) {
			
			ProductCategoryVO pv = new ProductCategoryVO();
			
			BeanUtils.copyProperties( productCategory,pv );
			pv.setId(productCategory.getId());
			if (productCategory.getChildren().size() > 0) {

				List<ProductCategoryVO> childrenVOsC = new ArrayList<ProductCategoryVO>();
				
				for (ProductCategory productCategoryc : productCategory.getChildren()) {
					ProductCategoryVO pvC = new ProductCategoryVO();
					BeanUtils.copyProperties(productCategoryc, pvC);
					
					pvC.setId(productCategoryc.getId());
					childrenVOsC.add(pvC);
				}
				pv.setChildrenVOs(childrenVOsC);
			}
			childrenVOs.add(pv);
		}
		return childrenVOs;
	}
	
	@Override
	@Transactional
	public Object details(Long productCateId) {
		ProductCategory productCategory = productCategoryDao.find(productCateId);
		ProductCategoryVO pv = new ProductCategoryVO();
		BeanUtils.copyProperties(productCategory,pv);
		pv.setId(productCategory.getId());
		pv.setTreepath(productCategory.getTreePath());
		return pv;
	}	

}