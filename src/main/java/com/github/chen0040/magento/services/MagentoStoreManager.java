package com.github.chen0040.magento.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.store.StoreConfig;
import com.github.chen0040.magento.models.store.StoreGroup;
import com.github.chen0040.magento.models.store.StoreView;
import com.github.chen0040.magento.models.store.Website;
import com.mgiorda.oauth.OAuthConfig;

public class MagentoStoreManager extends MagentoHttpComponent {
	
	private static final Logger logger = LoggerFactory.getLogger(MagentoProductManager.class);
	private MagentoClient client;
	private static final String relativePath4Store = "rest/V1/store";
	
	public MagentoStoreManager(MagentoClient client) {
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

	public List<StoreConfig> getStoreConfigs() {
		String uri = baseUri() + "/" + relativePath4Store + "/storeConfigs";
		String json = getSecure(uri);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		logger.info("Got:\n{}", json);

		return JSON.parseArray(json, StoreConfig.class);
	}
	
	public List<Website> getWebsites() {
		String uri = baseUri() + "/" + relativePath4Store + "/websites";
		String json = getSecure(uri);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		logger.info("Got:\n{}", json);

		return JSON.parseArray(json, Website.class);
	}
	
	public List<StoreGroup> getStoreGroups() {
		String uri = baseUri() + "/" + relativePath4Store + "/storeGroups";
		String json = getSecure(uri);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		logger.info("Got:\n{}", json);

		return JSON.parseArray(json, StoreGroup.class);
	}
	
	public List<StoreView> getStoreViews() {
		String uri = baseUri() + "/" + relativePath4Store + "/storeViews";
		String json = getSecure(uri);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		logger.info("Got:\n{}", json);

		return JSON.parseArray(json, StoreView.class);
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
