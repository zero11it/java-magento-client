package com.github.chen0040.magento;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.chen0040.magento.http.HttpClient;
import com.github.chen0040.magento.models.cart.Account;
import com.github.chen0040.magento.models.store.StoreView;
import com.github.chen0040.magento.services.CartManager;
import com.github.chen0040.magento.services.CategoryManager;
import com.github.chen0040.magento.services.CreditMemoManager;
import com.github.chen0040.magento.services.GuestCartManager;
import com.github.chen0040.magento.services.AbstractManager;
import com.github.chen0040.magento.services.InventoryStockManager;
import com.github.chen0040.magento.services.InvoiceManager;
import com.github.chen0040.magento.services.MyCartManager;
import com.github.chen0040.magento.services.OrderManager;
import com.github.chen0040.magento.services.ProductManager;
import com.github.chen0040.magento.services.ShipmentManager;
import com.github.chen0040.magento.services.StoreManager;
import com.github.chen0040.magento.http.ApacheHttpClient;
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

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
public class MagentoClient extends AbstractManager implements Serializable {
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
	private ProductManager products;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private CategoryManager categories;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private InventoryStockManager inventory;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private CartManager carts;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private GuestCartManager guestCart;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private MyCartManager myCart;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private StoreManager store;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private OrderManager order;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private InvoiceManager invoice;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private CreditMemoManager creditmemo;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private ShipmentManager shipment;

	public MagentoClient(String baseUri, HttpClient httpClient) {
		super(httpClient);
		
		setBaseUri(baseUri);
		this.products = new ProductManager(this);
		this.categories = new CategoryManager(this);
		this.inventory = new InventoryStockManager(this);
		this.carts = new CartManager(this);
		this.guestCart = new GuestCartManager(this);
		this.myCart = new MyCartManager(this);
		this.store = new StoreManager(this);
		this.order = new OrderManager(this);
		this.invoice = new InvoiceManager(this);
		this.creditmemo = new CreditMemoManager(this);
		this.shipment = new ShipmentManager(this);
	}

	public MagentoClient(String baseUri) {
		this(baseUri, new ApacheHttpClient());
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
		String loginTokenResult = null;
		try {
			loginTokenResult = httpClient.jsonPost(uri, data);
		} catch (IOException e) {
			throw new MagentoException("Login failed", e);
		}
		token = StringUtils.stripQuotation(loginTokenResult);
		
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

	public CategoryManager categories() {
		return categories;
	}

	public ProductManager products() {
		return products;
	}

	public InventoryStockManager inventory() {
		return inventory;
	}
	
	public CartManager carts() {
		return carts;
	}

	public GuestCartManager guestCart() {
		return guestCart;
	}

	public MyCartManager myCart() {
		return myCart;
	}
	
	public StoreManager store() {
		return store;
	}
	
	public OrderManager order() {
		return order;
	}
	
	public InvoiceManager invoice() {
		return invoice;
	}
	
	public CreditMemoManager creditmemo() {
		return creditmemo;
	}
	
	public ShipmentManager shipment() {
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
