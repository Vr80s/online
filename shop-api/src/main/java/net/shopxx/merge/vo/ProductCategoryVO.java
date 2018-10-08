package net.shopxx.merge.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductCategoryVO implements Serializable {
	
	
    private Long id;

    private Date createddate;

    private Date lastmodifieddate;

    private Long version;

    private Integer orders;

    private Double generalrate;

    private Integer grade;

    private String name;

    private Double selfrate;

    private String seodescription;

    private String seokeywords;

    private String seotitle;

    private String treepath;

    private Long parentId;
    
    private List<ProductCategoryVO> childrenVOs = new ArrayList<ProductCategoryVO>();

    private static final long serialVersionUID = 1L;

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

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Double getGeneralrate() {
        return generalrate;
    }

    public void setGeneralrate(Double generalrate) {
        this.generalrate = generalrate;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getSelfrate() {
        return selfrate;
    }

    public void setSelfrate(Double selfrate) {
        this.selfrate = selfrate;
    }

    public String getSeodescription() {
        return seodescription;
    }

    public void setSeodescription(String seodescription) {
        this.seodescription = seodescription == null ? null : seodescription.trim();
    }

    public String getSeokeywords() {
        return seokeywords;
    }

    public void setSeokeywords(String seokeywords) {
        this.seokeywords = seokeywords == null ? null : seokeywords.trim();
    }

    public String getSeotitle() {
        return seotitle;
    }

    public void setSeotitle(String seotitle) {
        this.seotitle = seotitle == null ? null : seotitle.trim();
    }

    public String getTreepath() {
        return treepath;
    }

    public void setTreepath(String treepath) {
        this.treepath = treepath == null ? null : treepath.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

	public List<ProductCategoryVO> getChildrenVOs() {
		return childrenVOs;
	}

	public void setChildrenVOs(List<ProductCategoryVO> childrenVOs) {
		this.childrenVOs = childrenVOs;
	}

	@Override
	public String toString() {
		return "ProductCategoryVO [id=" + id + ", createddate=" + createddate + ", lastmodifieddate=" + lastmodifieddate
				+ ", version=" + version + ", orders=" + orders + ", generalrate=" + generalrate + ", grade=" + grade
				+ ", name=" + name + ", selfrate=" + selfrate + ", seodescription=" + seodescription + ", seokeywords="
				+ seokeywords + ", seotitle=" + seotitle + ", treepath=" + treepath + ", parentId=" + parentId
				+ ", childrenVOs=" + childrenVOs + "]";
	}
	
	
	
}