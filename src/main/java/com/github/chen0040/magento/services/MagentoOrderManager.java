package com.github.chen0040.magento.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.order.Order;
import com.github.chen0040.magento.models.order.OrderItem;
import com.github.chen0040.magento.models.order.StatusHistory;

public class MagentoOrderManager extends MagentoHttpComponent {

	private static final Logger logger = LoggerFactory.getLogger(MagentoProductManager.class);
	private MagentoClient client;
	private static final String relativePath4Orders = "rest/V1/order";
	
	public MagentoOrderManager(MagentoClient client) {
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

	public Order getOrder(long id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + id;
		
		String json = getSecure(uri);
		
		if (!validate(json)) {
			return null;
		}
		
		logger.info("Got:\n{}", json);
		
		return JSON.parseObject(json, Order.class);
	}
	
	public List<OrderItem> getItems(long id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/items/" + id;
		
		String json = getSecure(uri);
		
		if (!validate(json)) {
			return null;
		}
		
		logger.info("Got:\n{}", json);
		
		return JSON.parseArray(json, OrderItem.class);
	}
	
	public String getStatuses(long id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/statuses/" + id;
		
		String json = getSecure(uri);
		
		if (!validate(json)) {
			return null;
		}
		
		logger.info("Got:\n{}", json);
		
		return json;
	}
	
	public List<StatusHistory> getComments(long id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/comments/" + id;
		
		String json = getSecure(uri);
		
		if (!validate(json)) {
			return null;
		}
		
		logger.info("Got:\n{}", json);
		
		return JSON.parseArray(json, StatusHistory.class);
	}
	
	public List<OrderItem> searchItems(long currentPage, long pageSize) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/items?"
				+ "searchCriteria[currentPage]=" + currentPage + "&"
				+ "searchCriteria[pageSize]=" + pageSize;
		
		String json = getSecure(uri);
		
		if (!validate(json)) {
			return null;
		}
		json = json.replace("\"[",  "[").replace("]\"",  "]");
		
		logger.info("Got:\n{}", json);
		
		Map<String, String> resp = JSON.parseObject(json, new TypeReference<HashMap<String, String>>() {});
		
		return JSON.parseArray(resp.get("items"), OrderItem.class);
	}
	
	public List<OrderItem> search(String field, String value, String condition_type) {
		String uri = baseUri() + "/" + relativePath4Orders + "s?"
				+ "searchCriteria[filterGroups][0][filters][0][field]=" + field + "&"
				+ "searchCriteria[filterGroups][0][filters][0][value]=" + value + "&"
				+ "searchCriteria[filterGroups][0][filters][0][condition_type]=" + condition_type;
		String json = getSecure(uri);

		if (!validate(json)) {
			return null;
		}
		json = json.replace("\"[",  "[").replace("]\"",  "]");
		
		logger.info("Got:\n{}", json);
		
		Map<String, String> resp = JSON.parseObject(json, new TypeReference<HashMap<String, String>>() {});
		
		return JSON.parseArray(resp.get("items"), OrderItem.class);
	}
}
