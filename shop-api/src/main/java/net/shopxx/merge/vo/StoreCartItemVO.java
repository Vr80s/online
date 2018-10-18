package net.shopxx.merge.vo;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 购物车商品项分组
 *
 * @author hejiwei
 */
public class StoreCartItemVO implements Serializable {

    private Long id;

    private String logo;

    private String name;

    private Set<CartItemVO> cartItems;
    
    private Boolean isChecked;
    
    private String  doctorId;

    @Override
    public String toString() {
        return "StoreCartItemVO{" +
                "logo='" + logo + '\'' +
                ", name='" + name + '\'' +
                ", cartItems=" + cartItems +
                '}';
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CartItemVO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItemVO> cartItems) {
        this.cartItems = cartItems;
    }



	public Boolean getIsChecked() {
		return isChecked;
	}



	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}



	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}



	/**  
	 * <p>Title: build</p>  
	 * <p>Description: </p>  
	 * @param doctorInfoByStore  
	 */ 
	public void build(Map<String, Object> doctorInfoByStore) {

		if(doctorInfoByStore!=null) {
		    this.setLogo(doctorInfoByStore.get("avatar")!=null ? doctorInfoByStore.get("avatar").toString() : null);
		    this.setName(doctorInfoByStore.get("name")!=null ? doctorInfoByStore.get("name").toString() : null);
		    this.setDoctorId(doctorInfoByStore.get("id")!=null ? doctorInfoByStore.get("id").toString() : null);
		}
	}
    
}
