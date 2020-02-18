package com.github.chen0040.magento.services;

import com.alibaba.fastjson.JSON;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.stock.StockItem;
import com.github.chen0040.magento.utils.RESTUtils;
import com.github.chen0040.magento.utils.StringUtils;
import com.github.mgiorda.oauth.OAuthConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xschen on 12/6/2017.
 */
public class MagentoInventoryStockManager extends MagentoHttpComponent {
	private static final Logger logger = LoggerFactory.getLogger(MagentoInventoryStockManager.class);
	private static final String relativePath = "rest/V1/stockItems";
	private MagentoClient client;

	public MagentoInventoryStockManager(MagentoClient client) {
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

	public StockItem getStockItems(String productSku) {
		String uri = baseUri() + "/" + relativePath + "/" + productSku;
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		StockItem result = JSON.parseObject(json, StockItem.class);
		
		return result;
	}

	public String saveStockItem(String productSku, StockItem stockItem) {
		String uri = baseUri() + "/rest/V1/products/" + escape(productSku) + "/stockItems/" + stockItem.getItem_id();
		String body = RESTUtils.payloadWrapper("stockItem", stockItem);
		
		String json = putSecure(uri, StringUtils.toUTF8(body), logger);

		if (!validateJSON(json)) {
			return null;
		}

		return JSON.parseObject(json, String.class);
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
