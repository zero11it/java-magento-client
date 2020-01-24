package com.github.chen0040.magento.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.Address;
import com.github.chen0040.magento.models.order.Order;
import com.github.chen0040.magento.models.order.OrderInvoice;
import com.github.chen0040.magento.models.order.OrderItem;
import com.github.chen0040.magento.models.order.OrderRefund;
import com.github.chen0040.magento.models.order.SalesDataShipment;
import com.github.chen0040.magento.models.order.StatusHistory;
import com.github.chen0040.magento.utils.RESTUtils;
import com.github.mgiorda.oauth.OAuthConfig;

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
		
		if (!validateJSON(json)) {
			return null;
		}
		
		logger.info("Got:\n{}", json);
		
		return JSON.parseObject(json, Order.class);
	}
	
	public List<OrderItem> getItems(long id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/items/" + id;
		
		String json = getSecure(uri);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		logger.info("Got:\n{}", json);
		
		return JSON.parseArray(json, OrderItem.class);
	}
	
	public String getStatuses(long id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/statuses/" + id;
		
		String json = getSecure(uri);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		logger.info("Got:\n{}", json);
		
		return json;
	}
	
	public List<StatusHistory> getComments(long id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/comments/" + id;
		
		String json = getSecure(uri);
		
		if (!validateJSON(json)) {
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
		
		if (!validateJSON(json)) {
			return null;
		}
		json = json.replace("\"[",  "[").replace("]\"",  "]");
		
		logger.info("Got:\n{}", json);
		
		Map<String, String> resp = JSON.parseObject(json, new TypeReference<HashMap<String, String>>() {});
		
		return JSON.parseArray(resp.get("items"), OrderItem.class);
	}
	
	public List<OrderItem> searchItems(String field, String value, String condition_type) {
		String uri = baseUri() + "/" + relativePath4Orders + "s?"
				+ "searchCriteria[filterGroups][0][filters][0][field]=" + field + "&"
				+ "searchCriteria[filterGroups][0][filters][0][value]=" + value + "&"
				+ "searchCriteria[filterGroups][0][filters][0][condition_type]=" + condition_type;
		String json = getSecure(uri);

		if (!validateJSON(json)) {
			return null;
		}
		json = json.replace("\"[",  "[").replace("]\"",  "]");
		
		logger.info("Got:\n{}", json);
		
		Map<String, String> resp = JSON.parseObject(json, new TypeReference<HashMap<String, String>>() {});
		
		return JSON.parseArray(resp.get("items"), OrderItem.class);
	}
	
	public boolean cancelOrder(long id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + id + "/cancel";
		String json = postSecure(uri, "");
		
		if (!validateJSON(json)) {
			return false;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public boolean addComment(long id, StatusHistory comment) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + id + "/comments";
		
		String json = putSecure(uri, RESTUtils.payloadWrapper("statusHistory", comment));
		
		if (!validateJSON(json)) {
			return false;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public boolean emailOrder(long id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + id + "/emails";
		String json = postSecure(uri, "");
		
		if (!validateJSON(json)) {
			return false;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public boolean holdOrder(long id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + id + "/hold";
		String json = postSecure(uri, "");
		
		if (!validateJSON(json)) {
			return false;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public boolean unholdOrder(long id) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + id + "/unhold";
		String json = postSecure(uri, "");
		
		if (!validateJSON(json)) {
			return false;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Order saveOrder(Order order) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/";
		
		String json = postSecure(uri, RESTUtils.payloadWrapper("entity", order));
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Order.class);
	}
	
	public Order createOrder(Order order) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/create";
		
		String json = putSecure(uri, RESTUtils.payloadWrapper("entity", order));
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Order.class);
	}
	
	public Address createOrderAddress(String parent_id, Address address) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + parent_id;
		
		String json = putSecure(uri, RESTUtils.payloadWrapper("entity", address));
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Address.class);
	}
	
	public long createOrderInvoice(long orderId, OrderInvoice invoice) {
		String uri = baseUri() + "/" + relativePath4Orders + "/" + orderId + "/invoice";
		
		String json = postSecure(uri, JSON.toJSONString(invoice));
		
		if (!validateJSON(json)) {
			return -1;
		}
		
		return JSON.parseObject(json, Long.class).longValue();
	}
	
	public long createOrderRefund(long orderId, OrderRefund refund) {
		String uri = baseUri() + "/" + relativePath4Orders + "/" + orderId + "/refund";
		
		String json = postSecure(uri, JSON.toJSONString(refund));
		
		if (!validateJSON(json)) {
			return -1;
		}
		
		return JSON.parseObject(json, Long.class).longValue();
	}
	
	public long shipOrder(long orderId, SalesDataShipment shipment) {
		String uri = baseUri() + "/" + relativePath4Orders + "/" + orderId + "/ship";
		
		String json = postSecure(uri, JSON.toJSONString(shipment));
		
		if (!validateJSON(json)) {
			return -1;
		}
		
		return JSON.parseObject(json, Long.class).longValue();
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
