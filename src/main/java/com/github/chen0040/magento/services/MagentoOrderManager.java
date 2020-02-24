package com.github.chen0040.magento.services;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.chen0040.magento.MagentoClient;
import com.github.chen0040.magento.models.invoice.Invoice;
import com.github.chen0040.magento.models.order.Order;
import com.github.chen0040.magento.models.order.OrderItem;
import com.github.chen0040.magento.models.sales.Address;
import com.github.chen0040.magento.models.sales.SalesDataRefund;
import com.github.chen0040.magento.models.sales.SalesDataShipment;
import com.github.chen0040.magento.models.sales.StatusHistory;
import com.github.chen0040.magento.models.search.SearchCriteria;
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

	public List<Order> getOrders() {
		String uri = baseUri() + "/" + relativePath4Orders + "s?searchCriteria[currentPage]=0";
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", Order.class);
	}
	
	public Order getOrder(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + orderId;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Order.class);
	}
	
	public List<Order> searchOrders(SearchCriteria criteria) {
		String uri = baseUri() + "/" + relativePath4Orders + "s?" + criteria;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", Order.class);
	}
	
	public List<OrderItem> getItems(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/items/" + orderId;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, OrderItem.class);
	}
	
	public String getStatus(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/statuses/" + orderId;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return json;
	}
	
	public List<StatusHistory> getComments(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/comments/" + orderId;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseArray(json, StatusHistory.class);
	}
	
	public List<OrderItem> searchItems(SearchCriteria criteria) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/items?" + criteria;
		
		String json = getSecure(uri, logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return RESTUtils.getArrayByKey(json, "items", OrderItem.class);
	}
	
	public Boolean cancel(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + orderId + "/cancel";
		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Boolean addComment(Integer orderId, StatusHistory comment) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + orderId + "/comments";
		
		String json = putSecure(uri, RESTUtils.payloadWrapper("statusHistory", comment), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Boolean emailToCustomer(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + orderId + "/emails";
		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Boolean hold(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + orderId + "/hold";
		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Boolean unhold(Integer orderId) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + orderId + "/unhold";
		String json = postSecure(uri, "", logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Boolean.class).booleanValue();
	}
	
	public Order saveOrder(Order order) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/";
		
		String json = postSecure(uri, RESTUtils.payloadWrapper("entity", order), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Order.class);
	}
	
	public Order createOrder(Order order) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/create";
		
		String json = putSecure(uri, RESTUtils.payloadWrapper("entity", order), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Order.class);
	}
	
	public Address assignAddress(String parent_id, Address address) {
		String uri = baseUri() + "/" + relativePath4Orders + "s/" + parent_id;
		
		String json = putSecure(uri, RESTUtils.payloadWrapper("entity", address), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Address.class);
	}
	
	public Integer createInvoice(Integer orderId, Invoice invoice) {
		String uri = baseUri() + "/" + relativePath4Orders + "/" + orderId + "/invoice";
		
		String json = postSecure(uri, JSON.toJSONString(invoice), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}
	
	public Integer refund(Integer orderId, SalesDataRefund refundInfo) {
		String uri = baseUri() + "/" + relativePath4Orders + "/" + orderId + "/refund";
		
		String json = postSecure(uri, JSON.toJSONString(refundInfo), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
	}
	
	public Integer ship(Integer orderId, SalesDataShipment shipmentInfo) {
		String uri = baseUri() + "/" + relativePath4Orders + "/" + orderId + "/ship";
		
		String json = postSecure(uri, JSON.toJSONString(shipmentInfo), logger);
		
		if (!validateJSON(json)) {
			return null;
		}
		
		return JSON.parseObject(json, Integer.class);
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
