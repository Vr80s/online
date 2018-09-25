package net.shopxx.merge.vo;

import java.io.Serializable;
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
}
