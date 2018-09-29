package net.shopxx.merge.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import net.shopxx.dao.CartDao;
import net.shopxx.dao.CartItemDao;
import net.shopxx.dao.ProductDao;
import net.shopxx.dao.SkuDao;
import net.shopxx.entity.Cart;
import net.shopxx.entity.CartItem;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.entity.Sku;
import net.shopxx.entity.Store;
import net.shopxx.event.CartModifiedEvent;
import net.shopxx.event.CartRemovedEvent;
import net.shopxx.merge.service.ShopCartService;
import net.shopxx.merge.service.UsersRelationService;
import net.shopxx.merge.vo.CartItemVO;
import net.shopxx.merge.vo.CartVO;
import net.shopxx.merge.vo.StoreCartItemVO;
import net.shopxx.service.CartItemService;
import net.shopxx.service.CartService;
import net.shopxx.service.SkuService;
import net.shopxx.service.impl.BaseServiceImpl;

@Service
public class ShopCartServiceImpl extends BaseServiceImpl<Cart, Long> implements ShopCartService {

    private static final Object CART_LOCK = new Object();

    @Autowired
    private CartDao cartDao;
    @Autowired
    private CartItemDao cartItemDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private ProductDao productDao;

    @Autowired
    private CartService cartService;
    @Autowired
    private UsersRelationService usersRelationService;
    @Autowired
    private SkuService skuService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public CartVO getCart(String ipandatcmUserId) {
        Member member = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
        Cart cart = member.getCart();
        if (cart == null) {
            cart = create(ipandatcmUserId);
        }
        return getCartVO(cart);
    }

    private Cart create(String ipandatcmUserId) {
        synchronized (CART_LOCK) {
            Member member = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
            if (member != null && member.getCart() != null) {
                return member.getCart();
            }
            Cart cart = new Cart();
            if (member != null) {
                cart.setMember(member);
                member.setCart(cart);
            }
            cartDao.persist(cart);
            return cart;
        }
    }

    @Override
    public void add(String ipandatcmUserId, Long skuId, int quantity) {
        Member member = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
        Cart cart = member.getCart();
        if (cart == null) {
            cart = create(ipandatcmUserId);
        }
        Sku sku = skuService.find(skuId);
        cartService.add(cart, sku, quantity);
    }

    @Override
    public Long modify(String ipandatcmUserId, Long skuId, int quantity) {
        Member member = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
        Cart cart = member.getCart();
        if (cart == null) {
            cart = create(ipandatcmUserId);
        }
        Sku sku = skuService.find(skuId);
        if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
            throw new IllegalArgumentException("参数错误");
        }
        Set<CartItem> cartItems = cart.getCartItems();
        Optional<CartItem> cartItemOptional = cartItems.stream().filter(cartItem -> {
            cartItem = cartItemDao.findFetchSku(cartItem.getId());
            return cartItem != null && cartItem.getSku().getId().equals(skuId);
        }).findFirst();
        CartItem cartItem;
        if (cartItemOptional.isPresent()) {
            cartItem = cartItemOptional.get();
            cartItem = cartItemDao.find(cartItem.getId());
            cartItem.setQuantity(quantity);
            cartItemService.update(cartItem);
            applicationEventPublisher.publishEvent(new CartModifiedEvent(this, cart, sku, quantity));
        } else {
            cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setSku(sku);
            cartItem.setCart(cart);
            cartItemDao.persist(cartItem);
        }
        return cartItem.getId();
    }

    @Override
    public void remove(String ipandatcmUserId, List<Long> skuIds) {
        Member member = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
        Cart cart = member.getCart();
        if (cart == null) {
            cart = create(ipandatcmUserId);
        }
        for (Long skuId : skuIds) {
            Sku sku = skuService.find(skuId);
            Set<CartItem> cartItems = cart.getCartItems();
            Optional<CartItem> cartItemOptional = cartItems.stream().filter(cartItem -> {
                cartItem = cartItemDao.findFetchSku(cartItem.getId());
                return cartItem != null && cartItem.getSku().getId().equals(skuId);
            }).findFirst();
            CartItem cartItem = cartItemOptional.orElseThrow(() -> new RuntimeException("skuId参数错误"));
            cartItemService.delete(cartItem);
            cart.remove(cartItem);

            applicationEventPublisher.publishEvent(new CartRemovedEvent(this, cart, sku));
        }
    }

    @Override
    public void clear(String ipandatcmUserId) {
        Member member = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
        cartService.clear(member.getCart());
    }

    private CartVO getCartVO(Cart cart) {
        CartVO cartVO = new CartVO();
        Set<CartItem> cartItems = cart.getCartItems();
        List<StoreCartItemVO> storeCartItemVOList = new ArrayList<>();
        Map<Long, List<CartItem>> allStoreCartItemMap = new HashMap<>();
        Map<Long, Store> storeMap = new LinkedHashMap<>();
        for (CartItem cartItem : cartItems) {
            cartItem = cartItemDao.findFetchSku(cartItem.getId());
            Sku sku = cartItem.getSku();
            Product product = sku.getProduct();
            product = productDao.findFetchStore(product.getId());
            Store store = product.getStore();
            List<CartItem> storeCartItemList = null;
            if (store != null) {
                Long storeId = store.getId();
                if (allStoreCartItemMap.containsKey(storeId)) {
                    storeCartItemList = allStoreCartItemMap.get(storeId);
                    storeCartItemList.add(cartItem);
                } else {
                    storeCartItemList = new ArrayList<>();
                    storeCartItemList.add(cartItem);
                    allStoreCartItemMap.put(storeId, storeCartItemList);
                }
                if (!storeMap.containsKey(storeId)) {
                    storeMap.put(storeId, store);
                }
            }
        }
        for (Store store : storeMap.values()) {
            StoreCartItemVO storeCartItemVO = new StoreCartItemVO();
            storeCartItemVO.setId(store.getId());
            storeCartItemVO.setName(store.getName());
            storeCartItemVO.setLogo(store.getLogo());
            Set<CartItemVO> cartItemVOS = new LinkedHashSet<>();
            for (CartItem cartItem : allStoreCartItemMap.get(store.getId())) {
                cartItemVOS.add(cartItem.getCartItemVO());
            }
            storeCartItemVO.setCartItems(cartItemVOS);
            storeCartItemVOList.add(storeCartItemVO);
        }
        cartVO.setStoreCartItems(storeCartItemVOList);
        return cartVO;
    }

	
	@Override
	public Integer getCartQuantity(String accountId) {
		Member member = usersRelationService.getMemberByIpandatcmUserId(accountId);
        Cart cart = member.getCart();
        if (cart == null) {
        	return 0;
        }else {
        	return cart.getQuantity(false);
        }
	}
}
