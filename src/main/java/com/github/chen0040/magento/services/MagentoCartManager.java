package com.github.chen0040.magento.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.cart.Cart;
import com.github.chen0040.magento.models.cart.CartItem;
import com.github.chen0040.magento.models.search.SearchCriteria;
import com.github.chen0040.magento.utils.RESTUtils;
import com.github.mgiorda.oauth.OAuthConfig;

public class MagentoCartManager extends MagentoHttpComponent {
	private static final Logger logger = LoggerFactory.getLogger(MagentoMyCartManager.class);
	protected final MagentoClient client;
	private static final String relativePath4Carts = "/rest/V1/carts";
	private static final String relativePath4MyCart = "/rest/V1/carts/mine";

	public MagentoCartManager(MagentoClient client) {
		super(client.getHttpComponent());
		this.client = client;
	}

	@Override
	public String token() {
		return client.token();
	}

	@Override
	public String baseUri() {
		return client.baseUri();
	}
	
	public Cart getCart(Integer cartId) {
		String uri = baseUri() + relativePath4Carts + "/" + cartId;
		
		String json = getSecure(uri, logger);
		
		return JSON.parseObject(json, Cart.class);
	}
	
	public List<Cart> getCarts() {
		String uri = baseUri() + relativePath4Carts + "/search?searchCriteria[currentPage]=0";
		
		String json = getSecure(uri, logger);

		return RESTUtils.getArrayByKey(json, "items", Cart.class);
	}
	
	public List<Cart> searchCarts(SearchCriteria criteria) {
		String uri = baseUri() + relativePath4Carts + "/search?" + criteria;
		
		String json = getSecure(uri, logger);
		
		return RESTUtils.getArrayByKey(json, "items", Cart.class);
	}
	
	public Cart getMyCart() {
		String uri = baseUri() + relativePath4MyCart;
		
		String json = getSecure(uri, logger);
		
		return JSON.parseObject(json, Cart.class);
	}
	
	public CartItem getItems(Integer cartId) {
		String uri = baseUri() + relativePath4Carts + "/" + cartId + "/items";
		
		String json = getSecure(uri, logger);
		
		return JSON.parseObject(json, CartItem.class);
	}

	@Override
	public boolean oauthEnabled() {
		return client.oauthEnabled();
	}

	@Override
	public OAuthConfig oAuth() {
		return client.oAuth();
	}
}
