package com.github.chen0040.magento.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.cart.Cart;
import com.github.chen0040.magento.models.cart.CartItem;
import com.github.chen0040.magento.models.cart.CartTotal;
import com.github.chen0040.magento.utils.StringUtils;
import com.github.mgiorda.oauth.OAuthConfig;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xschen on 10/7/2017.
 */
public class MagentoGuestCartManager extends MagentoHttpComponent {
	private static final Logger logger = LoggerFactory.getLogger(MagentoGuestCartManager.class);
	protected String relativePath = "rest/V1/guest-carts";
	protected final MagentoClient client;

	public MagentoGuestCartManager(MagentoClient client) {
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

	public String newCart() {
		String json = postSecure(baseUri() + "/" + relativePath, "", logger);

		if (!validateJSON(json)) {
			return null;
		}

		return StringUtils.stripQuotation(json);
	}

	public Cart getCart(String cartId) {
		String json = getSecure(baseUri() + "/" + relativePath + "/" + cartId, logger);

		if (!validateJSON(json)) {
			return null;
		}

		System.out.println(json);

		Cart cart = JSON.parseObject(json, Cart.class);
		return cart;
	}

	public CartTotal getCartTotal(String cartId) {
		String json = getSecure(baseUri() + "/" + relativePath + "/" + cartId + "/totals", logger);

		if (!validateJSON(json)) {
			return null;
		}

		System.out.println(json);

		CartTotal cartTotal = JSON.parseObject(json, CartTotal.class);
		return cartTotal;
	}

	public CartItem addItemToCart(String cartId, CartItem item) {
		Map<String, Map<String, Object>> request = new HashMap<>();
		Map<String, Object> cartItem = new HashMap<>();
		
		cartItem.put("qty", item.getQty());
		cartItem.put("sku", item.getSku());
		cartItem.put("quote_id", cartId);
		request.put("cartItem", cartItem);
		
		String json = JSON.toJSONString(request, SerializerFeature.BrowserCompatible);
		
		json = postSecure(baseUri() + "/" + relativePath + "/" + cartId + "/items", json, logger);

		if (!validateJSON(json)) {
			return null;
		}

		System.out.println(json);

		CartItem saved = JSON.parseObject(json, CartItem.class);

		return saved;
	}

	public CartItem updateItemInCart(String cartId, CartItem item) {
		Map<String, Map<String, Object>> request = new HashMap<>();
		Map<String, Object> cartItem = new HashMap<>();
		
		cartItem.put("qty", item.getQty());
		cartItem.put("sku", item.getSku());
		cartItem.put("quote_id", cartId);
		cartItem.put("item_id", item.getItem_id());
		request.put("cartItem", cartItem);
		
		String json = JSON.toJSONString(request, SerializerFeature.BrowserCompatible);
		
		json = putSecure(baseUri() + "/" + relativePath + "/" + cartId + "/items/" + item.getItem_id(), json, logger);

		if (!validateJSON(json)) {
			return null;
		}

		System.out.println(json);

		CartItem saved = JSON.parseObject(json, CartItem.class);

		return saved;
	}

	public boolean deleteItemInCart(String cartId, int itemId) {
		String json = deleteSecure(baseUri() + "/" + relativePath + "/" + cartId + "/items/" + itemId, logger);

		if (!validateJSON(json)) {
			return false;
		}

		System.out.println(json);

		return json.equalsIgnoreCase("true");
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
