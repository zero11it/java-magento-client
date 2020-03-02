package com.github.chen0040.magento;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.chen0040.magento.interfaces.HttpComponent;
import com.github.chen0040.magento.models.cart.Account;
import com.github.chen0040.magento.models.store.StoreView;
import com.github.chen0040.magento.services.BasicHttpComponent;
import com.github.chen0040.magento.services.MagentoCartManager;
import com.github.chen0040.magento.services.MagentoCategoryManager;
import com.github.chen0040.magento.services.MagentoCreditMemoManager;
import com.github.chen0040.magento.services.MagentoGuestCartManager;
import com.github.chen0040.magento.services.MagentoHttpComponent;
import com.github.chen0040.magento.services.MagentoInventoryStockManager;
import com.github.chen0040.magento.services.MagentoInvoiceManager;
import com.github.chen0040.magento.services.MagentoMyCartManager;
import com.github.chen0040.magento.services.MagentoOrderManager;
import com.github.chen0040.magento.services.MagentoProductManager;
import com.github.chen0040.magento.services.MagentoShipmentManager;
import com.github.chen0040.magento.services.MagentoStoreManager;
import com.github.chen0040.magento.utils.StringUtils;
import com.github.mgiorda.oauth.OAuthConfig;
import com.github.mgiorda.oauth.OAuthConfigBuilder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.http.annotation.Experimental;
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
	private static final String invalidUrlPlaceholder = "https://invalid.url";

	private static final Logger logger = LoggerFactory.getLogger(MagentoClient.class);

	private String token = null;
	private String baseUri = "";
	@Setter(AccessLevel.NONE)
	private String defaultUri = "";
	@Getter(AccessLevel.NONE)
	private String currentView = "";
	
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private Map<String, StoreView> storeViewCache;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private int storeViewCacheAccessCount;
	@Getter(AccessLevel.NONE)
	private static final int MAX_STORE_VIEW_CACHE_ACCESS_COUNT = 100;

	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private OAuthConfig oauth = null;

	private boolean admin = false;
	private boolean authenticated = false;

	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoProductManager products;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoCategoryManager categories;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoInventoryStockManager inventory;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoCartManager carts;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoGuestCartManager guestCart;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoMyCartManager myCart;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoStoreManager store;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoOrderManager order;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoInvoiceManager invoice;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoCreditMemoManager creditmemo;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MagentoShipmentManager shipment;

	public MagentoClient(String baseUri, HttpComponent httpComponent) {
		super(httpComponent);
		
		setBaseUri(baseUri);
		this.products = new MagentoProductManager(this);
		this.categories = new MagentoCategoryManager(this);
		this.inventory = new MagentoInventoryStockManager(this);
		this.carts = new MagentoCartManager(this);
		this.guestCart = new MagentoGuestCartManager(this);
		this.myCart = new MagentoMyCartManager(this);
		this.store = new MagentoStoreManager(this);
		this.order = new MagentoOrderManager(this);
		this.invoice = new MagentoInvoiceManager(this);
		this.creditmemo = new MagentoCreditMemoManager(this);
		this.shipment = new MagentoShipmentManager(this);
	}

	public MagentoClient(String baseUri) {
		this(baseUri, new BasicHttpComponent());
	}
	
	public void setBaseUri(String baseUri) {
		String[] schemes = {"https"};
		UrlValidator validator = new UrlValidator(schemes);
		
		if (baseUri.startsWith("http://")) {
			baseUri = baseUri.replace("http", "https");
		}
		else if (!baseUri.startsWith("https://")) {
			baseUri = "https://" + baseUri;
		}
		
		if (!validator.isValid(baseUri)) {
			logger.error("URL " + baseUri + " is invalid, setting to " + invalidUrlPlaceholder);
			baseUri = "https://invalid.url";
		}

		if (baseUri.endsWith("/")) {
			baseUri = baseUri.substring(0, baseUri.length()-1);
		}
		
		this.baseUri = baseUri;
		this.defaultUri = baseUri;
	}
	
	private void updateStoreViewCache() {
		if (storeViewCache == null) {
			storeViewCache = new HashMap<>();
			storeViewCacheAccessCount = 0;
		}
		else {
			storeViewCacheAccessCount += 1;
			storeViewCacheAccessCount %= MAX_STORE_VIEW_CACHE_ACCESS_COUNT;
		}
		
		if (storeViewCacheAccessCount == 0) {
			for (StoreView view : store.getStoreViews()) {
				storeViewCache.put(view.getCode(), view);
			}
		}
	}
	
	public void switchStoreView(String code) {
		updateStoreViewCache();
		
		if (code.toLowerCase().equals("default")) {
			switchStoreViewToDefault();
		}
		else if (!storeViewCache.containsKey(code)) {
			logger.error(code + ": No such view");
		}
		else {
			baseUri = defaultUri + "/" + code;
			currentView = code;
			logger.info("Client switched to view '" + code + "'");
		}
	}

	public void switchStoreViewToDefault() {
		baseUri = defaultUri;
		currentView = "";
		logger.info("Client switched to default view");
	}

	public Account getMyAccount() {
		if (admin) {
			logger.warn("my account access api is not supported for admin rest call");
			return null;
		}

		String uri = this.baseUri + "/rest/V1/customers/me";
		String json = getSecure(uri, logger);

		if (!validateJSON(json)) {
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
		String json = getSecure(uri, logger);
		Map<String, Object> data = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
		}.getType());
		return data;
	}

	public String loginAsClient(String username, String password) {
		String uri = baseUri + "/" + relativePath4LoginAsClient;
		
		login(username, password, uri);
		
		return token;
	}

	public String loginAsAdmin(String username, String password) {
		String uri = baseUri + "/" + relativePath4LoginAsAdmin;
		
		login(username, password, uri);
		
		if (token != null) {
			admin = true;
		}
		
		return token;
	}
	
	private String login(String username, String password, String uri) {
		Map<String, String> data = new HashMap<>();
		
		data.put("username", username);
		data.put("password", password);
		token = StringUtils.stripQuotation(httpComponent.jsonPost(uri, data));
		
		logger.info("login returns: {}", token);
		
		if (!StringUtils.isAlphanumeric(token)) {
			token = null;
			return token;
		}
		authenticated = true;
		
		return token;
	}

	public void logout() {
		authenticated = false;
		token = null;
	}
	
	public OAuthConfig setOAuth(String consumerKey, String consumerSecret, String accessToken, String accessSecret) {
		String testQuery = "/rest/V1/products/types";
		
		oauth = new OAuthConfigBuilder(consumerKey, consumerSecret)
				.setTokenKeys(accessToken, accessSecret)
				.build();
		
		if (!validateJSON(getSecure(baseUri + testQuery, logger))) {
			oauth = null;
			logger.error("OAuth validation FAILED, check that your tokens are correct.");
		}
		else {
			logger.info("OAuth validation was a SUCCESS, OAuth config object is {}", oauth);
		}
		
		return oauth;
	}
	
	// FIXME : Magento 2 OAuth token request is currently bugged, test if/when it won't be
	@Experimental
	public OAuthConfig setOAuth(String consumerKey, String consumerSecret) {
		String tokenRequest = "/oauth/token/request";
		
		oauth = new OAuthConfigBuilder(consumerKey, consumerSecret)
				.setTokenKeys("", "")
				.build();
		
		String resp = postSecure(baseUri + tokenRequest, "", logger);
		
		if (!resp.contains("oauth_token")) {
			oauth = null;
			return null;
		}
		
		String[] tokens = resp.split("&");
		String accessToken = tokens[0].replace("oauth_token=", "");
		String accessSecret = tokens[1].replace("oauth_token_secret=", "");
		
		return setOAuth(consumerKey, consumerSecret, accessToken, accessSecret);
	}
	
	public void disableOAuth() {
		oauth = null;
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
	
	public MagentoCartManager carts() {
		return carts;
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
	
	public MagentoInvoiceManager invoice() {
		return invoice;
	}
	
	public MagentoCreditMemoManager creditmemo() {
		return creditmemo;
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

	@Override
	public boolean oauthEnabled() {
		return (oAuth() != null);
	}

	@Override
	public OAuthConfig oAuth() {
		return oauth;
	}
}
