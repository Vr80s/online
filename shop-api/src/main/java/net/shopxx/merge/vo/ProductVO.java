package net.shopxx.merge.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
* @ClassName: Product
* @Description: 商品
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年9月17日
*
 */
public class ProductVO implements Serializable {
	
	
    private Long id;

    private Date createddate;

    private Date lastmodifieddate;

    private Long version;

    private String attributevalue0;

    private String attributevalue1;

    private String attributevalue10;

    private String attributevalue11;

    private String attributevalue12;

    private String attributevalue13;

    private String attributevalue14;

    private String attributevalue15;

    private String attributevalue16;

    private String attributevalue17;

    private String attributevalue18;

    private String attributevalue19;

    private String attributevalue2;

    private String attributevalue3;

    private String attributevalue4;

    private String attributevalue5;

    private String attributevalue6;

    private String attributevalue7;

    private String attributevalue8;

    private String attributevalue9;

    private String caption;

    private BigDecimal cost;

    private Long hits;

    private Boolean isactive;

    private Boolean isdelivery;

    private Boolean islist;

    private Boolean ismarketable;

    private Boolean istop;

    private String keyword;

    private BigDecimal marketprice;

    private BigDecimal maxcommission;

    private String memo;

    private Long monthhits;

    private Date monthhitsdate;

    private Long monthsales;

    private Date monthsalesdate;

    private String name;

    private String parametervalues;

    private BigDecimal price;


    private Long sales;

    private Float score;

    private Long scorecount;

    private String sn;

    private String specificationitems;

    private Long totalscore;

    private Integer type;

    private String unit;

    private Long weekhits;

    private Date weekhitsdate;

    private Long weeksales;

    private Date weeksalesdate;

    private Integer weight;

    private Long brandId;

    private Long productcategoryId;

    private Long storeId;

    private Long storeproductcategoryId;

    private String introduction;

    private static final long serialVersionUID = 1L;

    /**
     * 购物车数量
     */
    private Integer cartQuantity;
    
    /**
     * 商品评论
     */
    private Set<ReviewVO> reviewvs;
    
    /**
     * 医师推荐
     */
    private Set<Map<String, Object>> posts;
    
    
    private Map<String,Object> doctor;
    
    /**
     * 标准
     */
    private Set<SpecificationItemVO>  specificationItemvs;
    
    
    private List<ProductImageVO> productImages;
    
    private Boolean IsOutOfStock;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public Date getLastmodifieddate() {
        return lastmodifieddate;
    }

    public void setLastmodifieddate(Date lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getAttributevalue0() {
        return attributevalue0;
    }

    public void setAttributevalue0(String attributevalue0) {
        this.attributevalue0 = attributevalue0 == null ? null : attributevalue0.trim();
    }

    public String getAttributevalue1() {
        return attributevalue1;
    }

    public void setAttributevalue1(String attributevalue1) {
        this.attributevalue1 = attributevalue1 == null ? null : attributevalue1.trim();
    }

    public String getAttributevalue10() {
        return attributevalue10;
    }

    public void setAttributevalue10(String attributevalue10) {
        this.attributevalue10 = attributevalue10 == null ? null : attributevalue10.trim();
    }

    public String getAttributevalue11() {
        return attributevalue11;
    }

    public void setAttributevalue11(String attributevalue11) {
        this.attributevalue11 = attributevalue11 == null ? null : attributevalue11.trim();
    }

    public String getAttributevalue12() {
        return attributevalue12;
    }

    public void setAttributevalue12(String attributevalue12) {
        this.attributevalue12 = attributevalue12 == null ? null : attributevalue12.trim();
    }

    public String getAttributevalue13() {
        return attributevalue13;
    }

    public void setAttributevalue13(String attributevalue13) {
        this.attributevalue13 = attributevalue13 == null ? null : attributevalue13.trim();
    }

    public String getAttributevalue14() {
        return attributevalue14;
    }

    public void setAttributevalue14(String attributevalue14) {
        this.attributevalue14 = attributevalue14 == null ? null : attributevalue14.trim();
    }

    public String getAttributevalue15() {
        return attributevalue15;
    }

    public void setAttributevalue15(String attributevalue15) {
        this.attributevalue15 = attributevalue15 == null ? null : attributevalue15.trim();
    }

    public String getAttributevalue16() {
        return attributevalue16;
    }

    public void setAttributevalue16(String attributevalue16) {
        this.attributevalue16 = attributevalue16 == null ? null : attributevalue16.trim();
    }

    public String getAttributevalue17() {
        return attributevalue17;
    }

    public void setAttributevalue17(String attributevalue17) {
        this.attributevalue17 = attributevalue17 == null ? null : attributevalue17.trim();
    }

    public String getAttributevalue18() {
        return attributevalue18;
    }

    public void setAttributevalue18(String attributevalue18) {
        this.attributevalue18 = attributevalue18 == null ? null : attributevalue18.trim();
    }

    public String getAttributevalue19() {
        return attributevalue19;
    }

    public void setAttributevalue19(String attributevalue19) {
        this.attributevalue19 = attributevalue19 == null ? null : attributevalue19.trim();
    }

    public String getAttributevalue2() {
        return attributevalue2;
    }

    public void setAttributevalue2(String attributevalue2) {
        this.attributevalue2 = attributevalue2 == null ? null : attributevalue2.trim();
    }

    public String getAttributevalue3() {
        return attributevalue3;
    }

    public void setAttributevalue3(String attributevalue3) {
        this.attributevalue3 = attributevalue3 == null ? null : attributevalue3.trim();
    }

    public String getAttributevalue4() {
        return attributevalue4;
    }

    public void setAttributevalue4(String attributevalue4) {
        this.attributevalue4 = attributevalue4 == null ? null : attributevalue4.trim();
    }

    public String getAttributevalue5() {
        return attributevalue5;
    }

    public void setAttributevalue5(String attributevalue5) {
        this.attributevalue5 = attributevalue5 == null ? null : attributevalue5.trim();
    }

    public String getAttributevalue6() {
        return attributevalue6;
    }

    public void setAttributevalue6(String attributevalue6) {
        this.attributevalue6 = attributevalue6 == null ? null : attributevalue6.trim();
    }

    public String getAttributevalue7() {
        return attributevalue7;
    }

    public void setAttributevalue7(String attributevalue7) {
        this.attributevalue7 = attributevalue7 == null ? null : attributevalue7.trim();
    }

    public String getAttributevalue8() {
        return attributevalue8;
    }

    public void setAttributevalue8(String attributevalue8) {
        this.attributevalue8 = attributevalue8 == null ? null : attributevalue8.trim();
    }

    public String getAttributevalue9() {
        return attributevalue9;
    }

    public void setAttributevalue9(String attributevalue9) {
        this.attributevalue9 = attributevalue9 == null ? null : attributevalue9.trim();
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption == null ? null : caption.trim();
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Long getHits() {
        return hits;
    }

    public void setHits(Long hits) {
        this.hits = hits;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Boolean getIsdelivery() {
        return isdelivery;
    }

    public void setIsdelivery(Boolean isdelivery) {
        this.isdelivery = isdelivery;
    }

    public Boolean getIslist() {
        return islist;
    }

    public void setIslist(Boolean islist) {
        this.islist = islist;
    }

    public Boolean getIsmarketable() {
        return ismarketable;
    }

    public void setIsmarketable(Boolean ismarketable) {
        this.ismarketable = ismarketable;
    }

    public Boolean getIstop() {
        return istop;
    }

    public void setIstop(Boolean istop) {
        this.istop = istop;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public BigDecimal getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(BigDecimal marketprice) {
        this.marketprice = marketprice;
    }

    public BigDecimal getMaxcommission() {
        return maxcommission;
    }

    public void setMaxcommission(BigDecimal maxcommission) {
        this.maxcommission = maxcommission;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Long getMonthhits() {
        return monthhits;
    }

    public void setMonthhits(Long monthhits) {
        this.monthhits = monthhits;
    }

    public Date getMonthhitsdate() {
        return monthhitsdate;
    }

    public void setMonthhitsdate(Date monthhitsdate) {
        this.monthhitsdate = monthhitsdate;
    }

    public Long getMonthsales() {
        return monthsales;
    }

    public void setMonthsales(Long monthsales) {
        this.monthsales = monthsales;
    }

    public Date getMonthsalesdate() {
        return monthsalesdate;
    }

    public void setMonthsalesdate(Date monthsalesdate) {
        this.monthsalesdate = monthsalesdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getParametervalues() {
        return parametervalues;
    }

    public void setParametervalues(String parametervalues) {
        this.parametervalues = parametervalues == null ? null : parametervalues.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public Long getSales() {
        return sales;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Long getScorecount() {
        return scorecount;
    }

    public void setScorecount(Long scorecount) {
        this.scorecount = scorecount;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public String getSpecificationitems() {
        return specificationitems;
    }

    public void setSpecificationitems(String specificationitems) {
        this.specificationitems = specificationitems == null ? null : specificationitems.trim();
    }

    public Long getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(Long totalscore) {
        this.totalscore = totalscore;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Long getWeekhits() {
        return weekhits;
    }

    public void setWeekhits(Long weekhits) {
        this.weekhits = weekhits;
    }

    public Date getWeekhitsdate() {
        return weekhitsdate;
    }

    public void setWeekhitsdate(Date weekhitsdate) {
        this.weekhitsdate = weekhitsdate;
    }

    public Long getWeeksales() {
        return weeksales;
    }

    public void setWeeksales(Long weeksales) {
        this.weeksales = weeksales;
    }

    public Date getWeeksalesdate() {
        return weeksalesdate;
    }

    public void setWeeksalesdate(Date weeksalesdate) {
        this.weeksalesdate = weeksalesdate;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getProductcategoryId() {
        return productcategoryId;
    }

    public void setProductcategoryId(Long productcategoryId) {
        this.productcategoryId = productcategoryId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getStoreproductcategoryId() {
        return storeproductcategoryId;
    }

    public void setStoreproductcategoryId(Long storeproductcategoryId) {
        this.storeproductcategoryId = storeproductcategoryId;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

	public Integer getCartQuantity() {
		return cartQuantity;
	}

	public void setCartQuantity(Integer cartQuantity) {
		this.cartQuantity = cartQuantity;
	}

	public Set<ReviewVO> getReviewvs() {
		return reviewvs;
	}

	public void setReviewvs(Set<ReviewVO> reviewvs) {
		this.reviewvs = reviewvs;
	}
	

	public Set<Map<String, Object>> getPosts() {
		return posts;
	}

	public void setPosts(Set<Map<String, Object>> posts) {
		this.posts = posts;
	}

	public Map<String, Object> getDoctor() {
		return doctor;
	}

	public void setDoctor(Map<String, Object> doctor) {
		this.doctor = doctor;
	}

	public Set<SpecificationItemVO> getSpecificationItemvs() {
		return specificationItemvs;
	}

	public void setSpecificationItemvs(Set<SpecificationItemVO> specificationItemvs) {
		this.specificationItemvs = specificationItemvs;
	}

	public List<ProductImageVO> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImageVO> productImages) {
		this.productImages = productImages;
	}

	public Boolean getIsOutOfStock() {
		return IsOutOfStock;
	}

	public void setIsOutOfStock(Boolean isOutOfStock) {
		IsOutOfStock = isOutOfStock;
	}
	
}
