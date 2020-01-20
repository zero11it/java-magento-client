package com.github.chen0040.magento;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.chen0040.magento.interfaces.HttpComponent;
import com.github.chen0040.magento.models.*;
import com.github.chen0040.magento.services.*;
import com.github.chen0040.magento.utils.HttpClient;
import com.github.chen0040.magento.utils.StringUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
public class MagentoClient extends MagentoHttpComponent implements Serializable {
	private static final long serialVersionUID = 3001998767951271632L;
	private static final String relativePath4LoginAsClient = "rest/V1/integration/customer/token";
	private static final String relativePath4LoginAsAdmin = "rest/V1/integration/admin/token";

	private static final Logger logger = LoggerFactory.getLogger(MagentoClient.class);

	private String token = null;

	private String baseUri = "";

	private boolean admin = false;

	private boolean authenticated = false;

	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoProductManager products;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoCategoryManager categories;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoInventoryStockManager inventory;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoProductMediaManager media;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoGuestCartManager guestCart;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoMyCartManager myCart;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoStoreManager store;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoOrderManager order;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoShipmentManager shipment;

	public MagentoClient(String baseUri, HttpComponent httpComponent) {
		super(httpComponent);

		if (baseUri.endsWith("/")) {
			baseUri = baseUri.substring(0, baseUri.length()-1);
		}
		
		this.baseUri = baseUri;
		this.products = new MagentoProductManager(this);
		this.categories = new MagentoCategoryManager(this);
		this.inventory = new MagentoInventoryStockManager(this);
		this.media = new MagentoProductMediaManager(this);
		this.guestCart = new MagentoGuestCartManager(this);
		this.myCart = new MagentoMyCartManager(this);
		this.store = new MagentoStoreManager(this);
		this.order = new MagentoOrderManager(this);
		this.shipment = new MagentoShipmentManager(this);
	}

	public MagentoClient(String baseUri) {
		this(baseUri, new BasicHttpComponent());
	}

	public Account getMyAccount() {
		if (admin) {
			logger.warn("my account access api is not supported for admin rest call");
			return null;
		}

		String uri = this.baseUri + "/rest/V1/customers/me";
		String json = getSecure(uri);

		if (!validate(json)) {
			return null;
		}

		return JSON.parseObject(json, Account.class);
	}

	public Map<String, Object> getAccountById(long id) {
		if (!admin) {
			logger.warn("other account access api is not supported for client rest call");
			return new HashMap<>();
		}

		String uri = this.baseUri + "/rest/V1/customers/" + id;
		String json = getSecure(uri);
		Map<String, Object> data = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
		}.getType());
		return data;
	}

	public String loginAsClient(String username, String password) {
		String uri = baseUri + "/" + relativePath4LoginAsClient;
		Map<String, String> data = new HashMap<>();
		
		data.put("username", username);
		data.put("password", password);
		this.token = StringUtils.stripQuotation(httpComponent.jsonPost(uri, data));
		
		logger.info("loginAsClient returns: {}", token);

		if (token.contains("You did not sign in correctly or your account is temporarily disabled")
				|| token.contains("Invalid login or password")) {
			this.token = "";
			return token;
		}
		authenticated = true;
		
		return token;
	}

	public void logout() {
		// String uri = baseUri + "/rest/V1/integration/customer/revoke";
		authenticated = false;
		token = null;
	}

	public String loginAsAdmin(String username, String password) {
		String uri = baseUri + "/" + relativePath4LoginAsAdmin;
		Map<String, String> data = new HashMap<>();
		
		data.put("username", username);
		data.put("password", password);
		token = StringUtils.stripQuotation(httpComponent.jsonPost(uri, data));
		
		logger.info("loginAsAdmin returns: {}", token);
		
		if (token.contains("You did not sign in correctly or your account is temporarily disabled")
				|| token.contains("Invalid login or password")) {
			this.token = "";
			return token;
		}
		authenticated = true;
		
		return token;
	}

	public MagentoCategoryManager categories() {
		return categories;
	}

	public MagentoProductManager products() {
		return products;
	}

	public MagentoInventoryStockManager inventory() {
		return inventory;
	}

	public MagentoProductMediaManager media() {
		return media;
	}

	public MagentoGuestCartManager guestCart() {
		return guestCart;
	}

	public MagentoMyCartManager myCart() {
		return myCart;
	}
	
	public MagentoStoreManager store() {
		return store;
	}
	
	public MagentoOrderManager order() {
		return order;
	}
	
	public MagentoShipmentManager shipment() {
		return shipment;
	}

	@Override
	public String token() {
		return this.token;
	}

	@Override
	public String baseUri() {
		return this.baseUri;
	}
}
